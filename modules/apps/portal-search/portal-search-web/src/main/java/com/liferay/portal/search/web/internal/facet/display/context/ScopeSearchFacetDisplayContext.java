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

import com.liferay.portal.search.web.internal.site.facet.configuration.SiteFacetPortletInstanceConfiguration;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class ScopeSearchFacetDisplayContext {

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterValue() {
		return _parameterValue;
	}

	public List<String> getParameterValues() {
		return _parameterValues;
	}

	public SiteFacetPortletInstanceConfiguration
		getSiteFacetPortletInstanceConfiguration() {

		return _siteFacetPortletInstanceConfiguration;
	}

	public List<ScopeSearchFacetTermDisplayContext> getTermDisplayContexts() {
		return _scopeSearchFacetTermDisplayContexts;
	}

	public boolean isNothingSelected() {
		return _nothingSelected;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setNothingSelected(boolean nothingSelected) {
		_nothingSelected = nothingSelected;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String parameterValue) {
		_parameterValue = parameterValue;
	}

	public void setParameterValues(List<String> parameterValues) {
		_parameterValues = parameterValues;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setSiteFacetPortletInstanceConfiguration(
		SiteFacetPortletInstanceConfiguration
			siteFacetPortletInstanceConfiguration) {

		_siteFacetPortletInstanceConfiguration =
			siteFacetPortletInstanceConfiguration;
	}

	public void setTermDisplayContexts(
		List<ScopeSearchFacetTermDisplayContext>
			scopeSearchFacetTermDisplayContexts) {

		_scopeSearchFacetTermDisplayContexts =
			scopeSearchFacetTermDisplayContexts;
	}

	private long _displayStyleGroupId;
	private boolean _nothingSelected;
	private String _parameterName;
	private String _parameterValue;
	private List<String> _parameterValues;
	private boolean _renderNothing;
	private List<ScopeSearchFacetTermDisplayContext>
		_scopeSearchFacetTermDisplayContexts;
	private SiteFacetPortletInstanceConfiguration
		_siteFacetPortletInstanceConfiguration;

}