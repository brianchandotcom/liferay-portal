/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.filter.display.context;

import com.liferay.portal.search.web.internal.custom.filter.configuration.CustomFilterPortletInstanceConfiguration;

/**
 * @author André de Oliveira
 */
public class CustomFilterDisplayContext {

	public CustomFilterPortletInstanceConfiguration
		getCustomFilterPortletInstanceConfiguration() {

		return _customFilterPortletInstanceConfiguration;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

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

	public void setCustomFilterPortletInstanceConfiguration(
		CustomFilterPortletInstanceConfiguration
			customFilterPortletInstanceConfiguration) {

		_customFilterPortletInstanceConfiguration =
			customFilterPortletInstanceConfiguration;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
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

	private CustomFilterPortletInstanceConfiguration
		_customFilterPortletInstanceConfiguration;
	private long _displayStyleGroupId;
	private String _filterValue;
	private String _heading;
	private boolean _immutable;
	private String _parameterName;
	private boolean _renderNothing;
	private String _searchURL;

}