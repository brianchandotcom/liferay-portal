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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Alejandro Hernández
 */
@Provider
public class FieldsQueryParamContextProvider
	implements ContextProvider<FieldsQueryParam> {

	@Override
	public FieldsQueryParam createContext(Message message) {
		HttpServletRequest httpServletRequest =
			ContextProviderUtil.getHttpServletRequest(message);

		String fieldNamesString = httpServletRequest.getParameter("fields");

		if (fieldNamesString == null) {
			return () -> null;
		}

		if (fieldNamesString.isEmpty()) {
			return Collections::emptySet;
		}

		Set<String> paths = new HashSet<>();

		for (String fieldName : fieldNamesString.split(",")) {
			paths.addAll(_toPaths(fieldName));
		}

		return () -> paths;
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