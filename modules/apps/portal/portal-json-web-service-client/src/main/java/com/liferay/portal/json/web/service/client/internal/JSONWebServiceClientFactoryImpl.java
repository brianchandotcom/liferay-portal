/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.internal;

import com.liferay.portal.json.web.service.client.JSONWebServiceClient;
import com.liferay.portal.json.web.service.client.JSONWebServiceClientFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 */
@Component(service = JSONWebServiceClientFactory.class)
public class JSONWebServiceClientFactoryImpl
	implements JSONWebServiceClientFactory {

	@Override
	public JSONWebServiceClient getInstance(
			Map<String, Object> properties, boolean oAuthEnabled)
		throws Exception {

		JSONWebServiceClientImpl jsonWebServiceClientImpl = null;

		if (oAuthEnabled) {
			jsonWebServiceClientImpl = new OAuthJSONWebServiceClientImpl();
		}
		else {
			jsonWebServiceClientImpl = new JSONWebServiceClientImpl();
		}

		jsonWebServiceClientImpl.activate(properties);

		return jsonWebServiceClientImpl;
	}

}