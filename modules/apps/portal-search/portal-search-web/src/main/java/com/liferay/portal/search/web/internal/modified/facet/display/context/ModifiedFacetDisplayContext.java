/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.modified.facet.display.context;

import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.search.web.internal.modified.facet.configuration.ModifiedFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.List;

/**
 * @author Lino Alves
 */
public class ModifiedFacetDisplayContext implements Serializable {

	public List<BucketDisplayContext> getBucketDisplayContexts() {
		return _bucketDisplayContexts;
	}

	public BucketDisplayContext getCustomRangeBucketDisplayContext() {
		return _customRangeBucketDisplayContext;
	}

	public BucketDisplayContext getDefaultBucketDisplayContext() {
		return _defaultBucketDisplayContext;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public ModifiedFacetCalendarDisplayContext
		getModifiedFacetCalendarDisplayContext() {

		return _modifiedFacetCalendarDisplayContext;
	}

	public ModifiedFacetPortletInstanceConfiguration
		getModifiedFacetPortletInstanceConfiguration() {

		return _modifiedFacetPortletInstanceConfiguration;
	}

	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	public String getParameterName() {
		return _parameterName;
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

	public void setCalendarDisplayContext(
		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext) {

		_modifiedFacetCalendarDisplayContext =
			modifiedFacetCalendarDisplayContext;
	}

	public void setCustomRangeBucketDisplayContext(
		BucketDisplayContext customRangeTermDisplayContext) {

		_customRangeBucketDisplayContext = customRangeTermDisplayContext;
	}

	public void setDefaultBucketDisplayContext(
		BucketDisplayContext defaultTermDisplayContext) {

		_defaultBucketDisplayContext = defaultTermDisplayContext;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setModifiedFacetPortletInstanceConfiguration(
		ModifiedFacetPortletInstanceConfiguration
			modifiedFacetPortletInstanceConfiguration) {

		_modifiedFacetPortletInstanceConfiguration =
			modifiedFacetPortletInstanceConfiguration;
	}

	public void setNothingSelected(boolean nothingSelected) {
		_nothingSelected = nothingSelected;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void setParameterName(String paramName) {
		_parameterName = paramName;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	private List<BucketDisplayContext> _bucketDisplayContexts;
	private BucketDisplayContext _customRangeBucketDisplayContext;
	private BucketDisplayContext _defaultBucketDisplayContext;
	private long _displayStyleGroupId;
	private ModifiedFacetCalendarDisplayContext
		_modifiedFacetCalendarDisplayContext;
	private ModifiedFacetPortletInstanceConfiguration
		_modifiedFacetPortletInstanceConfiguration;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _parameterName;
	private boolean _renderNothing;

}