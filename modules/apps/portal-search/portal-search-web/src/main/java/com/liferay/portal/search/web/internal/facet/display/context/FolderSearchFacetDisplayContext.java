/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.search.web.internal.folder.facet.configuration.FolderFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.List;

/**
 * @author Lino Alves
 */
public class FolderSearchFacetDisplayContext
	implements FacetDisplayContext, Serializable {

	@Override
	public List<BucketDisplayContext> getBucketDisplayContexts() {
		return _bucketDisplayContexts;
	}

	@Override
	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public FolderFacetPortletInstanceConfiguration
		getFolderFacetPortletInstanceConfiguration() {

		return _folderFacetPortletInstanceConfiguration;
	}

	@Override
	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	@Override
	public String getParameterName() {
		return _parameterName;
	}

	@Override
	public String getParameterValue() {
		return _parameterValue;
	}

	@Override
	public List<String> getParameterValues() {
		return _parameterValues;
	}

	@Override
	public boolean isNothingSelected() {
		return _nothingSelected;
	}

	@Override
	public boolean isRenderNothing() {
		return _renderNothing;
	}

	@Override
	public void setBucketDisplayContexts(
		List<BucketDisplayContext> bucketDisplayContexts) {

		_bucketDisplayContexts = bucketDisplayContexts;
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

	@Override
	public void setNothingSelected(boolean nothingSelected) {
		_nothingSelected = nothingSelected;
	}

	@Override
	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	@Override
	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	@Override
	public void setParameterValue(String parameterValue) {
		_parameterValue = parameterValue;
	}

	@Override
	public void setParameterValues(List<String> parameterValues) {
		_parameterValues = parameterValues;
	}

	@Override
	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	private List<BucketDisplayContext> _bucketDisplayContexts;
	private long _displayStyleGroupId;
	private FolderFacetPortletInstanceConfiguration
		_folderFacetPortletInstanceConfiguration;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _parameterName;
	private String _parameterValue;
	private List<String> _parameterValues;
	private boolean _renderNothing;

}