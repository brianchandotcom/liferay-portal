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

package com.liferay.analytics.client.osgi.internal;

import com.liferay.analytics.client.IdentityClient;
import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.IdentityContextMessage;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = IdentityClient.class)
public class IdentityClientImpl implements IdentityClient {

	@Override
	public String getUserId(IdentityContextMessage identityContextMessage)
		throws Exception {

		String jsonIdentityContextMessage = _jsonObjectMapper.map(
			identityContextMessage);

		String identityPath = String.format(
			"/%s%s", identityContextMessage.getAnalyticsKey(),
			_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PATH);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Sending identity request %s to destination %s//%s:%s%s",
					jsonIdentityContextMessage,
					_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PROTOCOL,
					_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_HOST,
					_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PORT,
					identityPath));
		}

		_jsonWebServiceClient.setHostName(
			_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_HOST);
		_jsonWebServiceClient.setHostPort(
			Integer.parseInt(_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PORT));
		_jsonWebServiceClient.setProtocol(
			_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PROTOCOL);

		return _jsonWebServiceClient.doPostAsJSON(
			identityPath, jsonIdentityContextMessage);
	}

	@Activate
	protected void activate() {
		Properties properties = new Properties();

		properties.setProperty(
			"hostName", _SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_HOST);
		properties.setProperty(
			"hostPort", _SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PORT);
		properties.setProperty(
			"protocol", _SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PROTOCOL);

		ComponentInstance componentInstance = _componentFactory.newInstance(
			(Dictionary)properties);

		componentInstance.getInstance();

		_jsonWebServiceClient =
			(JSONWebServiceClient)componentInstance.getInstance();
	}

	@Deactivate
	protected void deactivate() {
		_jsonWebServiceClient.destroy();
	}

	@Reference(
		target = "(component.factory=JSONWebServiceClient)", unbind = "-"
	)
	protected void setComponentFactory(ComponentFactory componentFactory) {
		_componentFactory = componentFactory;
	}

	@Reference(
		target = "(model=com.liferay.analytics.model.IdentityContextMessage)",
		unbind = "-"
	)
	protected void setJSONObjectMapper(
		JSONObjectMapper<IdentityContextMessage> jsonObjectMapper) {

		_jsonObjectMapper = jsonObjectMapper;
	}

	private static final String _SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_HOST =
		System.getProperty(
			"identity.gateway.host", "contacts-prod.liferay.com");

	private static final String _SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PATH =
		System.getProperty("identity.gateway.path", "/identity");

	private static final String _SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PORT =
		System.getProperty("identity.gateway.port", "443");

	private static final String
		_SYSTEM_PROPERTY_VALUE_IDENTITY_GATEWAY_PROTOCOL = System.getProperty(
			"identity.gateway.protocol", "https");

	private static final Log _log = LogFactoryUtil.getLog(
		IdentityClientImpl.class);

	private ComponentFactory _componentFactory;
	private JSONObjectMapper<IdentityContextMessage> _jsonObjectMapper;
	private JSONWebServiceClient _jsonWebServiceClient;

}