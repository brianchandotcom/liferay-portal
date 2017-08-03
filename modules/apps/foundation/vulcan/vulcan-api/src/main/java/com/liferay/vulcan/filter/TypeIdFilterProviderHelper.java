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

package com.liferay.vulcan.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods to help a <code>TypeIdFilterProvider</code> performing its
 * operations.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = TypeIdFilterProviderHelper.class)
public class TypeIdFilterProviderHelper {

	/**
	 * Returns the ID from the actual HTTP request.
	 *
	 * @param  httpServletRequest actual HTTP request.
	 * @return the ID from the request.
	 */
	public String getId(HttpServletRequest httpServletRequest) {
		return _idFilterProviderHelper.getId(httpServletRequest);
	}

	/**
	 * Returns a map of query params for an {@link TypeIdFilter}.
	 *
	 * @param  typeIdFilter type-id filter from which the ID and the type are
	 *         going to be extracted.
	 * @return map of query params for the {@link TypeIdFilter}.
	 */
	public Map<String, String> getQueryParamMap(TypeIdFilter typeIdFilter) {
		HashMap<String, String> typeMap = new HashMap<String, String>() {
			{
				put("type", typeIdFilter.getType());
			}
		};

		typeMap.putAll(_idFilterProviderHelper.getQueryParamMap(typeIdFilter));

		return typeMap;
	}

	/**
	 * Returns the type from the actual HTTP request.
	 *
	 * @param  httpServletRequest actual HTTP request.
	 * @return the type from the request.
	 */
	public String getType(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getParameter("type");
	}

	@Reference
	private IdFilterProviderHelper _idFilterProviderHelper;

}