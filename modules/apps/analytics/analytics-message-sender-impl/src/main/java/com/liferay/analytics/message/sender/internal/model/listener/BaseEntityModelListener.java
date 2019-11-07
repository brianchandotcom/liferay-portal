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

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.message.sender.model.AnalyticsMessage;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
public abstract class BaseEntityModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	@Override
	public void onAfterCreate(T model) throws ModelListenerException {
		_send("add", getAttributes(), model);
	}

	@Override
	public void onBeforeRemove(T model) throws ModelListenerException {
		_send("delete", new ArrayList<>(), model);
	}

	@Override
	public void onBeforeUpdate(T model) throws ModelListenerException {
		try {
			List<String> modifiedAttributes = _getModifiedAttributes(
				getAttributes(), model, getOriginalModel(model));

			if (modifiedAttributes.isEmpty()) {
				return;
			}

			_send("update", modifiedAttributes, model);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected abstract List<String> getAttributes();

	protected abstract T getOriginalModel(T model) throws Exception;

	protected abstract String getPrimaryKeyName();

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference
	protected AnalyticsMessageSenderClient analyticsMessageSenderClient;

	@Reference
	protected UserLocalService userLocalService;

	private String _getDataSourceId(long companyId) {
		try {
			AnalyticsConfiguration analyticsConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AnalyticsConfiguration.class, companyId);

			return analyticsConfiguration.dataSourceId();
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to find analytics configuration for company ID " +
						companyId);
			}

			return null;
		}
	}

	private List<String> _getModifiedAttributes(
		List<String> attributeNames, T model, T originalModel) {

		List<String> modifiedAttributes = new ArrayList<>();

		for (String attributeName : attributeNames) {
			String value = String.valueOf(
				BeanPropertiesUtil.getObject(model, attributeName));
			String originalValue = String.valueOf(
				BeanPropertiesUtil.getObject(originalModel, attributeName));

			if (!Objects.equals(value, originalValue)) {
				modifiedAttributes.add(attributeName);
			}
		}

		return modifiedAttributes;
	}

	private void _send(
		String eventType, List<String> includeAttributes, T model) {

		JSONObject jsonObject = _serialize(includeAttributes, model);

		ShardedModel shardedModel = (ShardedModel)model;

		try {
			AnalyticsMessage.Builder analyticsMessageBuilder =
				AnalyticsMessage.builder(
					_getDataSourceId(shardedModel.getCompanyId()),
					model.getModelClassName());

			analyticsMessageBuilder.action(eventType);
			analyticsMessageBuilder.object(jsonObject);

			analyticsMessageSenderClient.send(
				Collections.singletonList(analyticsMessageBuilder.build()));
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to send analytics message " +
						jsonObject.toString());
			}
		}
	}

	private JSONObject _serialize(List<String> includeAttributes, T model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> modelAttributes = model.getModelAttributes();

		for (String includeAttribute : includeAttributes) {
			jsonObject.put(
				includeAttribute, modelAttributes.get(includeAttribute));
		}

		jsonObject.put(
			getPrimaryKeyName(), String.valueOf(model.getPrimaryKeyObj()));

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEntityModelListener.class);

	private ConfigurationProvider _configurationProvider;

}