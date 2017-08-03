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

/**
 * Provides methods to help an <code>IdFilterProvider</code> performing its
 * operations.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = IdFilterProviderHelper.class)
public class IdFilterProviderHelper {

	/**
	 * Returns the ID from the actual HTTP request.
	 *
	 * @param  httpServletRequest actual HTTP request.
	 * @return the ID from the request.
	 */
	public String getId(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getParameter("id");
	}

	/**
	 * Returns a map of query params for an {@link IdFilter}.
	 *
	 * @param  idFilter ID filter from which the ID is going to be extracted.
	 * @return map of query params for the {@link IdFilter}.
	 */
	public Map<String, String> getQueryParamMap(IdFilter idFilter) {
		return new HashMap<String, String>() {
			{
				put("id", String.valueOf(idFilter.getId()));
			}
		};
	}

}