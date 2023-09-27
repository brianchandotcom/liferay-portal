/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
public abstract class BaseCommerceQualifierMetadata<T extends BaseModel<T>>
	implements CommerceQualifierMetadata<T> {

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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_modelListenerServiceRegistration = bundleContext.registerService(
			(Class<ModelListener<T>>)(Class<?>)ModelListener.class,
			new CommerceQualifierMetadataModelListener(getModelClass()), null);
	}

	@Deactivate
	protected void deactivate() {
		_modelListenerServiceRegistration.unregister();
	}

	protected abstract OrderByExpression[] getAdditionalOrderByExpressions(
		Map<String, ?> targetAttributes);

	protected abstract Class<?> getConfigurationBeanClass();

	protected abstract Class<T> getModelClass();

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

	private ServiceRegistration<ModelListener<T>>
		_modelListenerServiceRegistration;

	private class CommerceQualifierMetadataModelListener
		extends BaseModelListener<T> {

		@Override
		public Class<T> getModelClass() {
			return _modelClass;
		}

		@Override
		public void onBeforeRemove(T model) {
			try {
				commerceQualifierEntryLocalService.
					deleteSourceCommerceQualifierEntries(
						model.getModelClassName(),
						(Long)model.getPrimaryKeyObj());

				commerceQualifierEntryLocalService.
					deleteTargetCommerceQualifierEntries(
						model.getModelClassName(),
						(Long)model.getPrimaryKeyObj());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException);
				}
			}
		}

		private CommerceQualifierMetadataModelListener(Class<T> modelClass) {
			_modelClass = modelClass;
		}

		private final Class<T> _modelClass;

	}

}