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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Map;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.analytics.client.osgi.internal.configuration.IdentifyClientConfiguration",
	service = IdentityClient.class
)
public class IdentityClientImpl implements IdentityClient {

	@Override
	public String getUserId(IdentityContextMessage identityContextMessage)
		throws Exception {

		String jsonIdentityContextMessage = _jsonObjectMapper.map(
			identityContextMessage);

		String identityPath = String.format(
			"/%s%s", identityContextMessage.getDataSourceId(),
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

		ComponentInstance componentInstance = _componentFactory.newInstance(
			HashMapDictionaryBuilder.put(
				"hostName", _identifyClientConfiguration.identifyGatewayHost()
			).put(
				"hostPort", _identifyClientConfiguration.identifyGatewayPort()
			).put(
				"protocol",
				_identifyClientConfiguration.identifyGatewayProtocol()
			).build());

		_jsonWebServiceClient =
			(JSONWebServiceClient)componentInstance.getInstance();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IdentityClientImpl.class);

	@Reference(
		target = "(component.factory=com.liferay.petra.json.web.service.client.JSONWebServiceClient)"
	)
	private ComponentFactory _componentFactory;

	private volatile IdentifyClientConfiguration _identifyClientConfiguration;

	@Reference(
		target = "(model=com.liferay.analytics.model.IdentityContextMessage)"
	)
	private JSONObjectMapper<IdentityContextMessage> _jsonObjectMapper;

	private volatile JSONWebServiceClient _jsonWebServiceClient;

}