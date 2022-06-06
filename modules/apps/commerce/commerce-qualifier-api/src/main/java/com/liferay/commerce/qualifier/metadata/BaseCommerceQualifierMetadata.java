/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.qualifier.metadata;

import com.liferay.commerce.qualifier.configuration.CommerceQualifierConfiguration;
import com.liferay.commerce.qualifier.helper.CommerceQualifierHelper;
import com.liferay.commerce.qualifier.model.CommerceQualifierEntryTable;
import com.liferay.commerce.qualifier.service.CommerceQualifierEntryLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.expression.step.WhenThenStep;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
public abstract class BaseCommerceQualifierMetadata<T extends BaseModel<T>>
	extends BaseModelListener<T> implements CommerceQualifierMetadata<T> {

	@Override
	public String[][] getAllowedTargetKeysArray() {
		try {
			CommerceQualifierConfiguration commerceQualifierConfiguration =
				_getCommerceQualifierConfiguration();

			String[] allowedTargetKeys =
				commerceQualifierConfiguration.allowedTargetKeys();

			String[][] allowedTargetKeysArray =
				new String[allowedTargetKeys.length][];

			for (int i = 0; i < allowedTargetKeys.length; i++) {
				allowedTargetKeysArray[i] = StringUtil.split(
					allowedTargetKeys[i], StringPool.PIPE);
			}

			return allowedTargetKeysArray;
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return new String[0][0];
	}

	@Override
	public Predicate getFilterPredicate() {
		return null;
	}

	@Override
	public OrderByExpression[] getOrderByExpressions(
		Map<String, ?> targetAttributes) {

		if (targetAttributes == null) {
			return null;
		}

		try {
			WhenThenStep<Integer> whenThenStep = null;

			Set<String> targetAttributeKeySet = targetAttributes.keySet();

			CommerceQualifierConfiguration commerceQualifierConfiguration =
				_getCommerceQualifierConfiguration();

			String[] orderByTargetKeys =
				commerceQualifierConfiguration.orderByTargetKeys();

			int orderByTargetKeysLength = orderByTargetKeys.length;

			for (int i = 0; i < orderByTargetKeysLength; i++) {
				String[] orderByTargetKeyArray = StringUtil.split(
					orderByTargetKeys[i], StringPool.PIPE);

				if (!targetAttributeKeySet.containsAll(
						Arrays.asList(orderByTargetKeyArray))) {

					continue;
				}

				Predicate predicate = null;

				for (String orderByTargetKey : orderByTargetKeyArray) {
					Predicate subpredicate = _getPredicate(
						orderByTargetKey,
						targetAttributes.get(orderByTargetKey));

					if (predicate == null) {
						predicate = subpredicate;
					}
					else {
						predicate = predicate.and(subpredicate);
					}
				}

				if (predicate == null) {
					continue;
				}

				whenThenStep = _getWhenThenStep(
					whenThenStep, predicate, orderByTargetKeysLength - i);
			}

			return ArrayUtil.append(
				new OrderByExpression[] {
					whenThenStep.elseEnd(
						-1
					).descending()
				},
				getAdditionalOrderByExpressions(targetAttributes));
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(configurationException);
			}
		}

		return new OrderByExpression[0];
	}

	@Override
	public void onBeforeRemove(T model) {
		try {
			commerceQualifierEntryLocalService.
				deleteSourceCommerceQualifierEntries(
					model.getModelClassName(), (Long)model.getPrimaryKeyObj());

			commerceQualifierEntryLocalService.
				deleteTargetCommerceQualifierEntries(
					model.getModelClassName(), (Long)model.getPrimaryKeyObj());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	protected abstract OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes);

	protected abstract Class<?> getConfigurationBeanClass();

	@Reference
	protected CommerceQualifierEntryLocalService
		commerceQualifierEntryLocalService;

	@Reference
	protected CommerceQualifierHelper commerceQualifierHelper;

	@Reference
	protected ConfigurationProvider configurationProvider;

	private CommerceQualifierConfiguration _getCommerceQualifierConfiguration()
		throws ConfigurationException {

		return (CommerceQualifierConfiguration)
			configurationProvider.getCompanyConfiguration(
				getConfigurationBeanClass(), CompanyThreadLocal.getCompanyId());
	}

	private Predicate _getPredicate(
		String targetCommerceQualifierMetadataKey, Object value) {

		CommerceQualifierEntryTable aliasCommerceQualifierEntryTable =
			commerceQualifierHelper.getAliasCommerceQualifierEntryTable(
				getKey(), targetCommerceQualifierMetadataKey);

		if (value == null) {
			return aliasCommerceQualifierEntryTable.commerceQualifierEntryId.
				isNull();
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Long[] longValueArray = (Long[])value;

			if (longValueArray.length == 0) {
				longValueArray = new Long[] {0L};
			}

			return aliasCommerceQualifierEntryTable.targetClassPK.in(
				longValueArray);
		}

		return aliasCommerceQualifierEntryTable.targetClassPK.eq((Long)value);
	}

	private WhenThenStep<Integer> _getWhenThenStep(
		WhenThenStep<Integer> whenThenStep, Predicate predicate,
		Integer value) {

		if (whenThenStep == null) {
			return DSLFunctionFactoryUtil.caseWhenThen(predicate, value);
		}

		return whenThenStep.whenThen(predicate, value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCommerceQualifierMetadata.class);

}