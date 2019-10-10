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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.vulcan.fields.RestrictFieldsQueryParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Javier Gamarra
 */
@Provider
public class RestrictFieldsQueryParamContextProvider
	implements ContextProvider<RestrictFieldsQueryParam> {

	@Override
	public RestrictFieldsQueryParam createContext(Message message) {
		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		String restrictFields = httpServletRequest.getParameter(
			"restrictFields");

		if (restrictFields == null) {
			return () -> null;
		}

		if (restrictFields.isEmpty()) {
			return Collections::emptySet;
		}

		return () -> new HashSet<>(Arrays.asList(restrictFields.split(",")));
	}

}