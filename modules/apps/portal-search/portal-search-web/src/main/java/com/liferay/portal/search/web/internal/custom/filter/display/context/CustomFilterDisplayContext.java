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

package com.liferay.portal.search.web.internal.custom.filter.display.context;

/**
 * @author André de Oliveira
 */
public class CustomFilterDisplayContext {

	public String getFilterValue() {
		return _filterValue;
	}

	public String getHeading() {
		return _heading;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getSearchURL() {
		return _searchURL;
	}

	public boolean isImmutable() {
		return _immutable;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setFilterValue(String filterValue) {
		_filterValue = filterValue;
	}

	public void setHeading(String heading) {
		_heading = heading;
	}

	public void setImmutable(boolean immutable) {
		_immutable = immutable;
	}

	public void setParameterName(String paramName) {
		_parameterName = paramName;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setSearchURL(String searchURL) {
		_searchURL = searchURL;
	}

	private String _filterValue;
	private String _heading;
	private boolean _immutable;
	private String _parameterName;
	private boolean _renderNothing;
	private String _searchURL;

}