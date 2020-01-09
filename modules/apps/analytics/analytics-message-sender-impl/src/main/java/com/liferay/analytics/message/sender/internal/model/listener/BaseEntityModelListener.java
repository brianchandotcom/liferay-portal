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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
public abstract class BaseEntityModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> implements EntityModelListener<T> {

	@Override
	public void addAnalyticsMessage(
		boolean checkExclusions, String eventType,
		List<String> includeAttributeNames, T model) {

		if (checkExclusions && isExcluded(model)) {
			return;
		}

		JSONObject jsonObject = _serialize(includeAttributeNames, model);

		ShardedModel shardedModel = (ShardedModel)model;

		try {
			AnalyticsMessage.Builder analyticsMessageBuilder =
				AnalyticsMessage.builder(
					_getDataSourceId(shardedModel.getCompanyId()),
					model.getModelClassName());

			analyticsMessageBuilder.action(eventType);
			analyticsMessageBuilder.object(jsonObject);

			String analyticsMessageJSON =
				analyticsMessageBuilder.buildJSONString();

			analyticsMessageLocalService.addAnalyticsMessage(
				shardedModel.getCompanyId(),
				userLocalService.getDefaultUserId(shardedModel.getCompanyId()),
				analyticsMessageJSON.getBytes(Charset.defaultCharset()));
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to add analytics message " + jsonObject.toString());
			}
		}
	}

	@Override
	public void onAfterCreate(T model) throws ModelListenerException {
		addAnalyticsMessage(true, "add", getAttributeNames(), model);
	}

	@Override
	public void onBeforeRemove(T model) throws ModelListenerException {
		addAnalyticsMessage(true, "delete", new ArrayList<>(), model);
	}

	@Override
	public void onBeforeUpdate(T model) throws ModelListenerException {
		try {
			List<String> modifiedAttributeNames = _getModifiedAttributeNames(
				getAttributeNames(), model, getOriginalModel(model));

			if (modifiedAttributeNames.isEmpty()) {
				return;
			}

			addAnalyticsMessage(true, "update", getAttributeNames(), model);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected abstract T getOriginalModel(T model) throws Exception;

	protected abstract String getPrimaryKeyName();

	protected boolean isExcluded(
			AnalyticsConfiguration analyticsConfiguration, User user)
		throws PortalException {

		for (long organizationId : user.getOrganizationIds()) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedOrganizationIds(),
					String.valueOf(organizationId))) {

				return false;
			}
		}

		for (long userGroupId : user.getUserGroupIds()) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedUserGroupIds(),
					String.valueOf(userGroupId))) {

				return false;
			}
		}

		return true;
	}

	protected boolean isExcluded(T model) {
		return false;
	}

	protected void updateConfigurationProperties(
		long companyId, String configurationPropertyName, String modelId,
		String preferencePropertyName) {

		Dictionary<String, Object> configurationProperties =
			analyticsConfigurationTracker.getAnalyticsConfigurationProperties(
				companyId);

		if (configurationProperties == null) {
			return;
		}

		String[] ids = (String[])configurationProperties.get(
			configurationPropertyName);

		if (!ArrayUtil.contains(ids, modelId)) {
			return;
		}

		ids = ArrayUtil.remove(ids, modelId);

		if (Validator.isNotNull(preferencePropertyName)) {
			UnicodeProperties unicodeProperties = new UnicodeProperties(true);

			unicodeProperties.setProperty(
				preferencePropertyName,
				StringUtil.merge(ids, StringPool.COMMA));

			try {
				companyService.updatePreferences(companyId, unicodeProperties);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update preferences for company ID " +
							companyId);
				}
			}
		}

		configurationProperties.put(configurationPropertyName, ids);

		try {
			configurationProvider.saveCompanyConfiguration(
				AnalyticsConfiguration.class, companyId,
				configurationProperties);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update configuration for company ID " +
						companyId);
			}
		}
	}

	@Reference
	protected AnalyticsConfigurationTracker analyticsConfigurationTracker;

	@Reference
	protected AnalyticsMessageLocalService analyticsMessageLocalService;

	@Reference
	protected CompanyService companyService;

	@Reference
	protected ConfigurationProvider configurationProvider;

	@Reference
	protected UserLocalService userLocalService;

	private String _getDataSourceId(long companyId) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		return analyticsConfiguration.liferayAnalyticsDataSourceId();
	}

	private List<String> _getModifiedAttributeNames(
		List<String> attributeNames, T model, T originalModel) {

		List<String> modifiedAttributeNames = new ArrayList<>();

		for (String attributeName : attributeNames) {
			String value = String.valueOf(
				BeanPropertiesUtil.getObject(model, attributeName));
			String originalValue = String.valueOf(
				BeanPropertiesUtil.getObject(originalModel, attributeName));

			if (!Objects.equals(value, originalValue)) {
				modifiedAttributeNames.add(attributeName);
			}
		}

		return modifiedAttributeNames;
	}

	private JSONObject _serialize(List<String> includeAttributeNames, T model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> modelAttributes = model.getModelAttributes();

		for (String includeAttributeName : includeAttributeNames) {
			jsonObject.put(
				includeAttributeName,
				modelAttributes.get(includeAttributeName));
		}

		jsonObject.put(
			getPrimaryKeyName(), String.valueOf(model.getPrimaryKeyObj()));

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEntityModelListener.class);

}