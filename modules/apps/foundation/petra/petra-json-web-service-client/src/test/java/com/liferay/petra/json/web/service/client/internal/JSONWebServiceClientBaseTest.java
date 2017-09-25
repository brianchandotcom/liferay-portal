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

package com.liferay.petra.json.web.service.client.internal;

import com.liferay.petra.json.web.service.client.serversimulator.HTTPServerSimulator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class JSONWebServiceClientBaseTest {

	protected Map<String, Object> getBaseProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("hostName", HTTPServerSimulator.HOST_ADDRESS);
		properties.put("hostPort", HTTPServerSimulator.HOST_PORT);
		properties.put("protocol", "http");

		return properties;
	}

}