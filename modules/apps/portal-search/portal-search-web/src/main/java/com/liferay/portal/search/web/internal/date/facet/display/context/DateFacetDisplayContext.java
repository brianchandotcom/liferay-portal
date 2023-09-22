/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.display.context;

import com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;

import java.io.Serializable;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public class DateFacetDisplayContext implements Serializable {

	public List<BucketDisplayContext> getBucketDisplayContexts() {
		return _bucketDisplayContexts;
	}

	public BucketDisplayContext getCustomRangeBucketDisplayContext() {
		return _customRangeBucketDisplayContext;
	}

	public DateFacetCalendarDisplayContext
		getDateFacetCalendarDisplayContext() {

		return _dateFacetCalendarDisplayContext;
	}

	public DateFacetPortletInstanceConfiguration
		getDateFacetPortletInstanceConfiguration() {

		return _dateFacetPortletInstanceConfiguration;
	}

	public BucketDisplayContext getDefaultBucketDisplayContext() {
		return _defaultBucketDisplayContext;
	}

	public String getDisplayCaption() {
		return _displayCaption;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
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
		DateFacetCalendarDisplayContext dateFacetCalendarDisplayContext) {

		_dateFacetCalendarDisplayContext = dateFacetCalendarDisplayContext;
	}

	public void setCustomRangeBucketDisplayContext(
		BucketDisplayContext customRangeTermDisplayContext) {

		_customRangeBucketDisplayContext = customRangeTermDisplayContext;
	}

	public void setDateFacetPortletInstanceConfiguration(
		DateFacetPortletInstanceConfiguration
			dateFacetPortletInstanceConfiguration) {

		_dateFacetPortletInstanceConfiguration =
			dateFacetPortletInstanceConfiguration;
	}

	public void setDefaultBucketDisplayContext(
		BucketDisplayContext defaultTermDisplayContext) {

		_defaultBucketDisplayContext = defaultTermDisplayContext;
	}

	public void setDisplayCaption(String displayCaption) {
		_displayCaption = displayCaption;
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

	public void setParameterName(String paramName) {
		_parameterName = paramName;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	private List<BucketDisplayContext> _bucketDisplayContexts;
	private BucketDisplayContext _customRangeBucketDisplayContext;
	private DateFacetCalendarDisplayContext _dateFacetCalendarDisplayContext;
	private DateFacetPortletInstanceConfiguration
		_dateFacetPortletInstanceConfiguration;
	private BucketDisplayContext _defaultBucketDisplayContext;
	private String _displayCaption;
	private long _displayStyleGroupId;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _parameterName;
	private boolean _renderNothing;

}