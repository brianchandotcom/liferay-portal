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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Instances of this interface will be used to provide instances of different
 * filters applied to the current request.
 *
 * The class of the filter can then be provided as a parameter in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder}
 * <code>filteredCollectionPage</code> methods.
 *
 * @author Alejandro Hernández
 */
public interface FilterProvider<T extends QueryParamFilterType> {

	/**
	 * Returns the name for the filter this provider provides.
	 *
	 * @return the name of the provided filter.
	 */
	public String getFilterName();

	/**
	 * Returns the query param map for an instance of the provided filter.
	 *
	 * @return the filter query param map.
	 */
	public Map<String, String> getQueryParamMap(T queryParamFilterType);

	/**
	 * Provides an instance of the filter based on the current HTTP request.
	 *
	 * @param  httpServletRequest the actual HTTP request.
	 * @return an instance of the filter extracted from the request.
	 */
	public T provide(HttpServletRequest httpServletRequest);

}