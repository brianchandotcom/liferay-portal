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

import com.liferay.portal.search.web.internal.folder.facet.configuration.FolderFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.List;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetDisplayContext implements Serializable {

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public FolderFacetPortletInstanceConfiguration
		getFolderFacetPortletInstanceConfiguration() {

		return _folderFacetPortletInstanceConfiguration;
	}

	public List<FolderSearchFacetTermDisplayContext>
		getFolderSearchFacetTermDisplayContexts() {

		return _folderSearchFacetTermDisplayContexts;
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

	public boolean isNothingSelected() {
		return _nothingSelected;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setFolderFacetPortletInstanceConfiguration(
		FolderFacetPortletInstanceConfiguration
			folderFacetPortletInstanceConfiguration) {

		_folderFacetPortletInstanceConfiguration =
			folderFacetPortletInstanceConfiguration;
	}

	public void setFolderSearchFacetTermDisplayContexts(
		List<FolderSearchFacetTermDisplayContext>
			folderSearchFacetTermDisplayContexts) {

		_folderSearchFacetTermDisplayContexts =
			folderSearchFacetTermDisplayContexts;
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

	private long _displayStyleGroupId;
	private FolderFacetPortletInstanceConfiguration
		_folderFacetPortletInstanceConfiguration;
	private List<FolderSearchFacetTermDisplayContext>
		_folderSearchFacetTermDisplayContexts;
	private boolean _nothingSelected;
	private String _parameterName;
	private String _parameterValue;
	private List<String> _parameterValues;
	private boolean _renderNothing;

}