/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shinn Lok
 */
public class SearchContainerReference {

	public SearchContainerReference(
		HttpServletRequest request, String namespace) {

		_request = request;
		_namespace = namespace;
	}

	public String getId() {
		return getId(DEFAULT_VAR);
	}

	public String getId(String var) {
		SearchContainer searchContainer = _searchContainerMap.get(var);

		if (searchContainer == null) {
			return StringPool.BLANK;
		}

		return searchContainer.getId(_request, _namespace);
	}

	public void register(SearchContainer searchContainer) {
		register(DEFAULT_VAR, searchContainer);
	}

	public void register(String var, SearchContainer searchContainer) {
		_searchContainerMap.put(var, searchContainer);
	}

	private static final String DEFAULT_VAR = "searchContainer";

	private String _namespace;
	private HttpServletRequest _request;
	private Map<String, SearchContainer> _searchContainerMap =
		new HashMap<String, SearchContainer>();

}