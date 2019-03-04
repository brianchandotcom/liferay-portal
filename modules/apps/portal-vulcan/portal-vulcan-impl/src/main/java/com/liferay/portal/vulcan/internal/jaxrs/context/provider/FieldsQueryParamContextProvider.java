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

import com.liferay.portal.vulcan.fields.FieldsQueryParam;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * Allows JAX-RS resources to provide {@link FieldsQueryParam} objects in method
 * parameters, fields or setters by annotating them with {@code
 * javax.ws.rs.core.Context}.
 *
 * @author Alejandro Hernández
 * @review
 */
@Provider
public class FieldsQueryParamContextProvider
	implements ContextProvider<FieldsQueryParam> {

	@Override
	public FieldsQueryParam createContext(Message message) {
		HttpServletRequest request = ContextProviderUtil.getHttpServletRequest(
			message);

		String fieldsValue = request.getParameter("fields");

		if (fieldsValue == null) {
			return () -> null;
		}

		return () -> new HashSet<>(Arrays.asList(fieldsValue.split(",")));
	}

}