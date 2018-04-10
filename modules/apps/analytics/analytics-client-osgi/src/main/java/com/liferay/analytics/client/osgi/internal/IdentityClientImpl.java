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
import com.liferay.analytics.client.osgi.internal.configuration.IdentifyClientConfiguration;
import com.liferay.analytics.data.binding.JSONObjectMapper;
import com.liferay.analytics.model.IdentityContextMessage;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Dictionary;
import java.util.Map;
import java.util.Properties;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	configurationPid = "com.liferay.analytics.client.osgi.internal.configuration.IdentifyClientConfiguration",
	immediate = true, service = IdentityClient.class
)
public class IdentityClientImpl implements IdentityClient {

	@Override
	public String getUserId(IdentityContextMessage identityContextMessage)
		throws Exception {

		String jsonIdentityContextMessage = _jsonObjectMapper.map(
			identityContextMessage);

		String identityPath = String.format(
			"/%s%s", identityContextMessage.getAnalyticsKey(),
			_identifyClientConfiguration.identifyGatewayPath());

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Sending identity request %s to destination %s//%s:%s%s",
					jsonIdentityContextMessage,
					_identifyClientConfiguration.identifyGatewayProtocol(),
					_identifyClientConfiguration.identifyGatewayHost(),
					_identifyClientConfiguration.identifyGatewayPort(),
					identityPath));
		}

		return _jsonWebServiceClient.doPostAsJSON(
			identityPath, jsonIdentityContextMessage);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_identifyClientConfiguration = ConfigurableUtil.createConfigurable(
			IdentifyClientConfiguration.class, properties);

		initializeJSONWebServiceClient();
	}

	protected void initializeJSONWebServiceClient() {
		Properties properties = new Properties();

		properties.setProperty(
			"hostName", _identifyClientConfiguration.identifyGatewayHost());
		properties.setProperty(
			"hostPort", _identifyClientConfiguration.identifyGatewayPort());
		properties.setProperty(
			"protocol", _identifyClientConfiguration.identifyGatewayProtocol());

		ComponentInstance componentInstance = _componentFactory.newInstance(
			(Dictionary)properties);

		componentInstance.getInstance();

		_jsonWebServiceClient =
			(JSONWebServiceClient)componentInstance.getInstance();
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

	private static final Log _log = LogFactoryUtil.getLog(
		IdentityClientImpl.class);

	private ComponentFactory _componentFactory;
	private volatile IdentifyClientConfiguration _identifyClientConfiguration;
	private JSONObjectMapper<IdentityContextMessage> _jsonObjectMapper;
	private JSONWebServiceClient _jsonWebServiceClient;

}