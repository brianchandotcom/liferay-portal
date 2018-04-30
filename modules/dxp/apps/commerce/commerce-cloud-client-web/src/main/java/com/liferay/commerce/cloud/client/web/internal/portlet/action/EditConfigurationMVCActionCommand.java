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

package com.liferay.commerce.cloud.client.web.internal.portlet.action;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=editCommerceCloudClientConfiguration"
	},
	service = MVCActionCommand.class
)
public class EditConfigurationMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (cmd.equals("connection")) {
				updateCommerceCloudClientConfiguration(actionRequest);
			}
			else if (cmd.equals("ordersForecasting")) {
				updateCommerceCloudOrderForecastConfiguration(actionRequest);

				updateCommerceCloudClientConfiguration(actionRequest);
			}
		}
		catch (CommerceCloudClientException ccce) {
			_log.error(ccce, ccce);

			SessionErrors.add(actionRequest, ccce.getClass(), ccce);
		}
	}

	protected JSONArray toJSONArray(String[] array) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String value : array) {
			jsonArray.put(value);
		}

		return jsonArray;
	}

	protected void updateCommerceCloudClientConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		CommerceCloudClientConfiguration commerceCloudClientConfiguration =
			_configurationProvider.getSystemConfiguration(
				CommerceCloudClientConfiguration.class);

		int orderForecastSyncCheckInterval = ParamUtil.getInteger(
			actionRequest, "orderForecastSyncCheckInterval",
			commerceCloudClientConfiguration.orderForecastSyncCheckInterval());
		boolean orderForecastSyncEnabled = ParamUtil.getBoolean(
			actionRequest, "orderForecastSyncEnabled",
			commerceCloudClientConfiguration.orderForecastSyncEnabled());
		int orderForecastSyncStatus = ParamUtil.getInteger(
			actionRequest, "orderForecastSyncStatus",
			commerceCloudClientConfiguration.orderForecastSyncStatus());
		String projectId = ParamUtil.getString(
			actionRequest, "projectId",
			commerceCloudClientConfiguration.projectId());
		String serverUrl = ParamUtil.getString(
			actionRequest, "serverUrl",
			commerceCloudClientConfiguration.serverUrl());

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"orderForecastSyncCheckInterval", orderForecastSyncCheckInterval);
		properties.put("orderForecastSyncEnabled", orderForecastSyncEnabled);
		properties.put("orderForecastSyncStatus", orderForecastSyncStatus);
		properties.put("projectId", projectId);
		properties.put("serverUrl", serverUrl);

		_configurationProvider.saveSystemConfiguration(
			CommerceCloudClientConfiguration.class, properties);
	}

	protected void updateCommerceCloudOrderForecastConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		// Server configuration

		int ahead = ParamUtil.getInteger(actionRequest, "ahead");
		String frequency = ParamUtil.getString(actionRequest, "frequency");
		String[] levels = ParamUtil.getStringValues(actionRequest, "levels");
		String[] periods = ParamUtil.getStringValues(actionRequest, "periods");
		String[] targets = ParamUtil.getStringValues(actionRequest, "targets");
		String timeZoneOffset = ParamUtil.getString(
			actionRequest, "timeZoneOffset");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("ahead", ahead);
		jsonObject.put("frequency", frequency);
		jsonObject.put("levels", toJSONArray(levels));
		jsonObject.put("periods", toJSONArray(periods));
		jsonObject.put("targets", toJSONArray(targets));
		jsonObject.put("timeZoneOffset", timeZoneOffset);

		_commerceCloudClient.updateOrderForecastConfiguration(jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditConfigurationMVCActionCommand.class);

	@Reference
	private CommerceCloudClient _commerceCloudClient;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

}