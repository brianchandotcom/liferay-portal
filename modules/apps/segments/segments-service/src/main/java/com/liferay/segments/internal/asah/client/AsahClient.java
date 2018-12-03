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

package com.liferay.segments.internal.asah.client;

import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
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
 * @author David Arques
 */
@Component(immediate = true, service = AsahClient.class)
public class AsahClient {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_initializeJSONWebServiceClient();

		String json = _getRoot();

		if (_log.isInfoEnabled()) {
			_log.info(json);
		}
	}

	@Reference(
		target = "(component.factory=JSONWebServiceClient)", unbind = "-"
	)
	protected void setComponentFactory(ComponentFactory componentFactory) {
		_componentFactory = componentFactory;
	}

	private String _getRoot() {
		try {
			return _jsonWebServiceClient.doGet(
				StringPool.SLASH, Collections.emptyMap(),
				Collections.emptyMap());
		}
		catch (Exception e) {
			_log.error(e, e);

			return "ERROR: " + e.getMessage();
		}
	}

	private void _initializeJSONWebServiceClient() {
		Properties properties = new Properties();

		properties.setProperty(
			"hostName",
			"osbasahfarobackend-asah93fdaf9914e34506bf664b9ab652fc01." +
				"eu-west-1.lfr.cloud");

		properties.setProperty("hostPort", "-1");
		properties.setProperty("protocol", "https");

		ComponentInstance componentInstance = _componentFactory.newInstance(
			(Dictionary)properties);

		_jsonWebServiceClient =
			(JSONWebServiceClient)componentInstance.getInstance();
	}

	private static final Log _log = LogFactoryUtil.getLog(AsahClient.class);

	private ComponentFactory _componentFactory;
	private JSONWebServiceClient _jsonWebServiceClient;

}