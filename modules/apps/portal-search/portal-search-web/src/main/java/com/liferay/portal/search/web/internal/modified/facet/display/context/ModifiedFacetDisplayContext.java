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

package com.liferay.portal.search.web.internal.modified.facet.display.context;

import com.liferay.portal.search.web.internal.modified.facet.configuration.ModifiedFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.List;

/**
 * @author Lino Alves
 */
public class ModifiedFacetDisplayContext implements Serializable {

	public ModifiedFacetTermDisplayContext
		getCustomRangeModifiedFacetTermDisplayContext() {

		return _customRangeModifiedFacetTermDisplayContext;
	}

	public ModifiedFacetTermDisplayContext
		getDefaultModifiedFacetTermDisplayContext() {

		return _defaultModifiedFacetTermDisplayContext;
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

	public List<ModifiedFacetTermDisplayContext>
		getModifiedFacetTermDisplayContexts() {

		return _modifiedFacetTermDisplayContexts;
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

	public void setCalendarDisplayContext(
		ModifiedFacetCalendarDisplayContext
			modifiedFacetCalendarDisplayContext) {

		_modifiedFacetCalendarDisplayContext =
			modifiedFacetCalendarDisplayContext;
	}

	public void setCustomRangeModifiedFacetTermDisplayContext(
		ModifiedFacetTermDisplayContext customRangeTermDisplayContext) {

		_customRangeModifiedFacetTermDisplayContext =
			customRangeTermDisplayContext;
	}

	public void setDefaultModifiedFacetTermDisplayContext(
		ModifiedFacetTermDisplayContext defaultTermDisplayContext) {

		_defaultModifiedFacetTermDisplayContext = defaultTermDisplayContext;
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

	public void setModifiedFacetTermDisplayContexts(
		List<ModifiedFacetTermDisplayContext>
			modifiedFacetTermDisplayContexts) {

		_modifiedFacetTermDisplayContexts = modifiedFacetTermDisplayContexts;
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

	private ModifiedFacetTermDisplayContext
		_customRangeModifiedFacetTermDisplayContext;
	private ModifiedFacetTermDisplayContext
		_defaultModifiedFacetTermDisplayContext;
	private long _displayStyleGroupId;
	private ModifiedFacetCalendarDisplayContext
		_modifiedFacetCalendarDisplayContext;
	private ModifiedFacetPortletInstanceConfiguration
		_modifiedFacetPortletInstanceConfiguration;
	private List<ModifiedFacetTermDisplayContext>
		_modifiedFacetTermDisplayContexts;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _parameterName;
	private boolean _renderNothing;

}