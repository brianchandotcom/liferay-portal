/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.sort.display.context;

import com.liferay.portal.search.web.internal.sort.configuration.SortPortletInstanceConfiguration;

import java.util.List;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SortDisplayContext {

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterValue() {
		return _parameterValue;
	}

	public SortPortletInstanceConfiguration
		getSortPortletInstanceConfiguration() {

		return _sortPortletInstanceConfiguration;
	}

	public List<SortTermDisplayContext> getSortTermDisplayContexts() {
		return _sortTermDisplayContexts;
	}

	public boolean isAnySelected() {
		return _anySelected;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setAnySelected(boolean anySelected) {
		_anySelected = anySelected;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String parameterValue) {
		_parameterValue = parameterValue;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setSortPortletInstanceConfiguration(
		SortPortletInstanceConfiguration sortPortletInstanceConfiguration) {

		_sortPortletInstanceConfiguration = sortPortletInstanceConfiguration;
	}

	public void setSortTermDisplayContexts(
		List<SortTermDisplayContext> sortTermDisplayContexts) {

		_sortTermDisplayContexts = sortTermDisplayContexts;
	}

	private boolean _anySelected;
	private long _displayStyleGroupId;
	private String _parameterName;
	private String _parameterValue;
	private boolean _renderNothing;
	private SortPortletInstanceConfiguration _sortPortletInstanceConfiguration;
	private List<SortTermDisplayContext> _sortTermDisplayContexts;

}