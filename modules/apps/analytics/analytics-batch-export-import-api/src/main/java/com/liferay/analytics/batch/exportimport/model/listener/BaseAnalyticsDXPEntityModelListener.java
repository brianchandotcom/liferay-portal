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

package com.liferay.analytics.batch.exportimport.model.listener;

import com.liferay.analytics.message.storage.service.AnalyticsAssociationLocalService;
import com.liferay.analytics.message.storage.service.AnalyticsDeleteMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Dictionary;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
public abstract class BaseAnalyticsDXPEntityModelListener
	<T extends BaseModel<T>>
		extends BaseModelListener<T> {

	@Override
	public void onAfterAddAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		_addAnalyticsAssociation(
			associationClassName, associationClassPK, classPK);
	}

	@Override
	public void onAfterRemove(T model) throws ModelListenerException {
		ShardedModel shardedModel = (ShardedModel)model;

		analyticsAssociationLocalService.deleteAnalyticsAssociations(
			shardedModel.getCompanyId(), model.getModelClassName(),
			(long)model.getPrimaryKeyObj());
	}

	@Override
	public void onAfterRemoveAssociation(
			Object classPK, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		_addAnalyticsAssociation(
			associationClassName, associationClassPK, classPK);
	}

	@Override
	public void onBeforeRemove(T model) throws ModelListenerException {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LRAC-10632")) ||
			!isTracked(model)) {

			return;
		}

		ShardedModel shardedModel = (ShardedModel)model;

		long companyId = shardedModel.getCompanyId();

		try {
			analyticsDeleteMessageLocalService.addAnalyticsDeleteMessage(
				companyId, new Date(), model.getModelClassName(),
				(long)model.getPrimaryKeyObj(),
				userLocalService.getDefaultUserId(companyId));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to add analytics delete message for model " + model,
					exception);
			}
		}
	}

	protected T getModel(Object classPK) {
		return null;
	}

	protected boolean isTracked(T model) {
		return true;
	}

	protected void updateConfigurationProperties(
		long companyId, String configurationPropertyName, String modelId,
		String preferencePropertyName) {

		Dictionary<String, Object> configurationProperties =
			analyticsConfigurationRegistry.getAnalyticsConfigurationProperties(
				companyId);

		if (configurationProperties == null) {
			return;
		}

		String[] modelIds = (String[])configurationProperties.get(
			configurationPropertyName);

		if (!ArrayUtil.contains(modelIds, modelId)) {
			return;
		}

		modelIds = ArrayUtil.remove(modelIds, modelId);

		if (Validator.isNotNull(preferencePropertyName)) {
			try {
				companyService.updatePreferences(
					companyId,
					UnicodePropertiesBuilder.create(
						true
					).put(
						preferencePropertyName,
						StringUtil.merge(modelIds, StringPool.COMMA)
					).build());
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update preferences for company " + companyId,
						exception);
				}
			}
		}

		configurationProperties.put(configurationPropertyName, modelIds);

		try {
			configurationProvider.saveCompanyConfiguration(
				AnalyticsConfiguration.class, companyId,
				configurationProperties);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update configuration for company " + companyId,
					exception);
			}
		}
	}

	@Reference
	protected AnalyticsAssociationLocalService analyticsAssociationLocalService;

	@Reference
	protected AnalyticsConfigurationRegistry analyticsConfigurationRegistry;

	@Reference
	protected AnalyticsDeleteMessageLocalService
		analyticsDeleteMessageLocalService;

	@Reference
	protected CompanyService companyService;

	@Reference
	protected ConfigurationProvider configurationProvider;

	@Reference
	protected UserLocalService userLocalService;

	private void _addAnalyticsAssociation(
		String associationClassName, Object associationClassPK,
		Object classPK) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LRAC-10632")) ||
			!analyticsConfigurationRegistry.isActive()) {

			return;
		}

		T model = getModel(classPK);

		if (model == null) {
			return;
		}

		try {
			ShardedModel shardedModel = (ShardedModel)model;

			long companyId = shardedModel.getCompanyId();

			Class<?> modelClass = getModelClass();

			analyticsAssociationLocalService.addAnalyticsAssociation(
				companyId, new Date(),
				userLocalService.getDefaultUserId(companyId),
				associationClassName, (long)associationClassPK,
				modelClass.getName(), (long)classPK);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to add analytics association for model " + model,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAnalyticsDXPEntityModelListener.class);

}