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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Shinn Lok
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class SearchContainerReference {

	public SearchContainerReference(
		HttpServletRequest httpServletRequest, String namespace) {

		_namespace = namespace;

		httpServletRequest.setAttribute(
			WebKeys.SEARCH_CONTAINER_REFERENCE, this);
	}

	public String getId(HttpServletRequest httpServletRequest) {
		return getId(httpServletRequest, SearchContainer.DEFAULT_VAR);
	}

	public String getId(HttpServletRequest httpServletRequest, String var) {
		if (_searchContainers == null) {
			return StringPool.BLANK;
		}

		SearchContainer<?> searchContainer = _searchContainers.get(var);

		if (searchContainer == null) {
			return StringPool.BLANK;
		}

		return searchContainer.getId(httpServletRequest, _namespace);
	}

	public void register(SearchContainer<?> searchContainer) {
		register(SearchContainer.DEFAULT_VAR, searchContainer);
	}

	public void register(String var, SearchContainer<?> searchContainer) {
		if (_searchContainers == null) {
			_searchContainers = new HashMap<>();
		}

		_searchContainers.put(var, searchContainer);
	}

	private final String _namespace;
	private Map<String, SearchContainer<?>> _searchContainers;

}