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

package com.liferay.segments.asah.internal.client;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

/**
 * @author David Arques
 */
public interface JSONWebServiceClient {

	public String doGet(
		String url, MultivaluedMap<String, Object> parameters,
		Map<String, String> headers);

	public String getBaseURI();

	public void setBaseURI(String baseURI);

}