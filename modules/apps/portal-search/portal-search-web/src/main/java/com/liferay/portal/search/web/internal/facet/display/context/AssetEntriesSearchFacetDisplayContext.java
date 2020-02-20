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

import com.liferay.portal.search.web.internal.type.facet.configuration.TypeFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.List;

/**
 * @author Lino Alves
 */
public class AssetEntriesSearchFacetDisplayContext implements Serializable {

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

	public List<AssetEntriesSearchFacetTermDisplayContext>
		getTermDisplayContexts() {

		return _assetEntriesSearchFacetTermDisplayContext;
	}

	public TypeFacetPortletInstanceConfiguration
		getTypeFacetPortletInstanceConfiguration() {

		return _typeFacetPortletInstanceConfiguration;
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

	public void setTermDisplayContexts(
		List<AssetEntriesSearchFacetTermDisplayContext>
			assetEntriesSearchFacetFieldDisplayContext) {

		_assetEntriesSearchFacetTermDisplayContext =
			assetEntriesSearchFacetFieldDisplayContext;
	}

	public void setTypeFacetPortletInstanceConfiguration(
		TypeFacetPortletInstanceConfiguration
			typeFacetPortletInstanceConfiguration) {

		_typeFacetPortletInstanceConfiguration =
			typeFacetPortletInstanceConfiguration;
	}

	private List<AssetEntriesSearchFacetTermDisplayContext>
		_assetEntriesSearchFacetTermDisplayContext;
	private long _displayStyleGroupId;
	private boolean _nothingSelected;
	private String _parameterName;
	private String _parameterValue;
	private List<String> _parameterValues;
	private boolean _renderNothing;
	private TypeFacetPortletInstanceConfiguration
		_typeFacetPortletInstanceConfiguration;

}