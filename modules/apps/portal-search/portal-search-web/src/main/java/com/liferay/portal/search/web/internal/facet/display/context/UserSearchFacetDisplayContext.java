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

package com.liferay.portal.search.web.internal.facet.display.context;

import java.io.Serializable;

import java.util.List;

/**
 * @author Lino Alves
 */
public class UserSearchFacetDisplayContext implements Serializable {

	public String getParamName() {
		return _paramName;
	}

	public String getParamValue() {
		return _paramValue;
	}

	public List<String> getParamValues() {
		return _paramValues;
	}

	public List<UserSearchFacetTermDisplayContext> getTermDisplayContexts() {
		return _userSearchFacetTermDisplayContexts;
	}

	public boolean isNothingSelected() {
		return _nothingSelected;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setNothingSelected(boolean nothingSelected) {
		_nothingSelected = nothingSelected;
	}

	public void setParamName(String paramName) {
		_paramName = paramName;
	}

	public void setParamValue(String paramValue) {
		_paramValue = paramValue;
	}

	public void setParamValues(List<String> paramValues) {
		_paramValues = paramValues;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setTermDisplayContexts(
		List<UserSearchFacetTermDisplayContext>
			userSearchFacetTermDisplayContexts) {

		_userSearchFacetTermDisplayContexts =
			userSearchFacetTermDisplayContexts;
	}

	private boolean _nothingSelected;
	private String _paramName;
	private String _paramValue;
	private List<String> _paramValues;
	private boolean _renderNothing;
	private List<UserSearchFacetTermDisplayContext>
		_userSearchFacetTermDisplayContexts;

}