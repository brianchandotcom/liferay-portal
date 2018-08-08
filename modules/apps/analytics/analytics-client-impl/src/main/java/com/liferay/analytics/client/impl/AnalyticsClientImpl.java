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

package com.liferay.analytics.client.impl;

import com.liferay.analytics.client.AnalyticsClient;
import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.data.binding.internal.AnalyticsEventsMessageJSONObjectMapper;
import com.liferay.analytics.model.AnalyticsEventsMessage;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Eduardo Garcia
 */
public class AnalyticsClientImpl implements AnalyticsClient {

	public String sendAnalytics(AnalyticsEventsMessage analyticsEventsMessage)
		throws Exception {

		String jsonAnalyticsEventsMessage = _jsonObjectMapper.map(
			analyticsEventsMessage);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Sending analytics message %s to destination %s//%s:%s%s",
					jsonAnalyticsEventsMessage,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PROTOCOL,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_HOST,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PORT,
					_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PATH));
		}

		_jsonWebServiceClient.setHostName(
			_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_HOST);
		_jsonWebServiceClient.setHostPort(
			GetterUtil.getInteger(
				_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PORT));
		_jsonWebServiceClient.setProtocol(
			_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PROTOCOL);

		return _jsonWebServiceClient.doPostAsJSON(
			_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PATH,
			jsonAnalyticsEventsMessage);
	}

	private static final String _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_HOST =
		System.getProperty(
			"analytics.gateway.host", "analytics-gw.liferay.com");

	private static final String _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PATH =
		System.getProperty(
			"analytics.gateway.path",
			"/api/analyticsgateway/send-analytics-events");

	private static final String _SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PORT =
		System.getProperty("analytics.gateway.port", "80");

	private static final String
		_SYSTEM_PROPERTY_VALUE_ANALYTICS_GATEWAY_PROTOCOL = System.getProperty(
			"analytics.gateway.protocol", "https");

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsClientImpl.class);

	private final JSONObjectMapper<AnalyticsEventsMessage> _jsonObjectMapper =
		new AnalyticsEventsMessageJSONObjectMapper();
	private final JSONWebServiceClient _jsonWebServiceClient =
		new JSONWebServiceClientImpl();

}