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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		String fieldNames = httpServletRequest.getParameter("fields");

		if (fieldNames == null) {
			return () -> null;
		}

		if (fieldNames.isEmpty()) {
			return Collections::emptySet;
		}

		Stream<String> stream = Arrays.stream(fieldNames.split(","));

		Set<String> fields = stream.map(
			this::_toPaths
		).flatMap(
			List::stream
		).collect(
			Collectors.toSet()
		);

		return () -> fields;
	}

	private List<String> _toPaths(String string) {
		if (!string.contains(".")) {
			return Collections.singletonList(string);
		}

		List<String> list = new ArrayList<>();

		String pending = string;

		while (!pending.equals("")) {
			list.add(pending);

			if (pending.contains(".")) {
				pending = pending.substring(0, pending.lastIndexOf("."));
			}
			else {
				pending = "";
			}
		}

		return list;
	}

}