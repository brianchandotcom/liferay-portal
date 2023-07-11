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

package com.liferay.headless.builder.internal.jaxrs.context.provider;

import com.liferay.headless.builder.application.APIApplication;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Luis Miguel Barcos
 */
@Provider
public class APIApplicationContextProvider
	implements ContextProvider<APIApplication> {

	public APIApplicationContextProvider(APIApplication apiApplication) {
		_apiApplication = apiApplication;
	}

	@Override
	public APIApplication createContext(Message message) {
		return _apiApplication;
	}

	private final APIApplication _apiApplication;

}