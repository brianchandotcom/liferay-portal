/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.search.web.internal.tag.facet.configuration.TagFacetPortletInstanceConfiguration;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class AssetTagsSearchFacetDisplayContext implements FacetDisplayContext {

	@Override
	public List<BucketDisplayContext> getBucketDisplayContexts() {
		return _bucketDisplayContexts;
	}

	@Override
	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public String getFacetLabel() {
		return _facetLabel;
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

	public TagFacetPortletInstanceConfiguration
		getTagFacetPortletInstanceConfiguration() {

		return _tagFacetPortletInstanceConfiguration;
	}

	public boolean isCloudWithCount() {
		return _cloudWithCount;
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

	public void setCloudWithCount(boolean cloudWithCount) {
		_cloudWithCount = cloudWithCount;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setFacetLabel(String facetLabel) {
		_facetLabel = facetLabel;
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

	public void setTagFacetPortletInstanceConfiguration(
		TagFacetPortletInstanceConfiguration
			tagFacetPortletInstanceConfiguration) {

		_tagFacetPortletInstanceConfiguration =
			tagFacetPortletInstanceConfiguration;
	}

	private List<BucketDisplayContext> _bucketDisplayContexts;
	private boolean _cloudWithCount;
	private long _displayStyleGroupId;
	private String _facetLabel;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _parameterName;
	private String _parameterValue;
	private List<String> _parameterValues;
	private boolean _renderNothing;
	private TagFacetPortletInstanceConfiguration
		_tagFacetPortletInstanceConfiguration;

}