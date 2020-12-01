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