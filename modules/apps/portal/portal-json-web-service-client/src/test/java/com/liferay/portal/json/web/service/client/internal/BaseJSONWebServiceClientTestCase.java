/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.web.service.client.internal;

import com.liferay.portal.json.web.service.client.server.simulator.HTTPServerSimulator;
import com.liferay.portal.json.web.service.client.server.simulator.constants.SimulatorConstants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author Igor Beslic
 */
public abstract class BaseJSONWebServiceClientTestCase {

	protected Map<String, Object> getBaseProperties() {
		return HashMapBuilder.<String, Object>put(
			"hostName", HTTPServerSimulator.HOST_ADDRESS
		).put(
			"hostPort", HTTPServerSimulator.HOST_PORT
		).put(
			"protocol", "http"
		).build();
	}

	protected List<NameValuePair> getParameters(String status) {
		return ListUtil.fromArray(
			new BasicNameValuePair(
				SimulatorConstants.HTTP_PARAMETER_RESPOND_WITH_STATUS, status),
			new BasicNameValuePair(
				SimulatorConstants.HTTP_PARAMETER_RETURN_PARMS_IN_JSON,
				"true"));
	}

}