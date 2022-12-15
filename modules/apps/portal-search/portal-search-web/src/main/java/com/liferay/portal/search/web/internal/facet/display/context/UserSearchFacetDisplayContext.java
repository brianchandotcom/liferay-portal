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

import com.liferay.portal.search.web.internal.user.facet.configuration.UserFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.List;

/**
 * @author Lino Alves
 */
public class UserSearchFacetDisplayContext implements Serializable {

	public List<BucketDisplayContext> getBucketDisplayContexts() {
		return _bucketDisplayContexts;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	public String getParamName() {
		return _paramName;
	}

	public String getParamValue() {
		return _paramValue;
	}

	public List<String> getParamValues() {
		return _paramValues;
	}

	public UserFacetPortletInstanceConfiguration
		getUserFacetPortletInstanceConfiguration() {

		return _userFacetPortletInstanceConfiguration;
	}

	public boolean isNothingSelected() {
		return _nothingSelected;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setBucketDisplayContexts(
		List<BucketDisplayContext> bucketDisplayContexts) {

		_bucketDisplayContexts = bucketDisplayContexts;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setNothingSelected(boolean nothingSelected) {
		_nothingSelected = nothingSelected;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
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

	public void setUserFacetPortletInstanceConfiguration(
		UserFacetPortletInstanceConfiguration
			userFacetPortletInstanceConfiguration) {

		_userFacetPortletInstanceConfiguration =
			userFacetPortletInstanceConfiguration;
	}

	private List<BucketDisplayContext> _bucketDisplayContexts;
	private long _displayStyleGroupId;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _paramName;
	private String _paramValue;
	private List<String> _paramValues;
	private boolean _renderNothing;
	private UserFacetPortletInstanceConfiguration
		_userFacetPortletInstanceConfiguration;

}