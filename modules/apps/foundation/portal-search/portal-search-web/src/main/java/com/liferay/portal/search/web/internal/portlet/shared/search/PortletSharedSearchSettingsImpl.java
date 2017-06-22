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

package com.liferay.portal.search.web.internal.portlet.shared.search;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.portlet.shared.task.PortletSharedRequestHelper;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.Optional;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

/**
 * @author André de Oliveira
 */
public class PortletSharedSearchSettingsImpl
	implements PortletSharedSearchSettings {

	public PortletSharedSearchSettingsImpl(
		SearchSettings searchSettings,
		Optional<PortletPreferences> portletPreferencesOptional,
		PortletSharedRequestHelper portletSharedRequestHelper,
		RenderRequest renderRequest) {

		_searchSettings = searchSettings;
		_portletPreferencesOptional = portletPreferencesOptional;
		_portletSharedRequestHelper = portletSharedRequestHelper;
		_renderRequest = renderRequest;
	}

	@Override
	public void addCondition(BooleanClause<Query> booleanClause) {
		_searchSettings.addCondition(booleanClause);
	}

	@Override
	public void addFacet(Facet facet) {
		_searchSettings.addFacet(facet);
	}

	@Override
	public Optional<Integer> getPaginationDelta() {
		return _searchSettings.getPaginationDelta();
	}

	@Override
	public Optional<String> getPaginationDeltaParameterName() {
		return _searchSettings.getPaginationDeltaParameterName();
	}

	@Override
	public Optional<Integer> getPaginationStart() {
		return _searchSettings.getPaginationStart();
	}

	@Override
	public Optional<String> getPaginationStartParameterName() {
		return _searchSettings.getPaginationStartParameterName();
	}

	@Override
	public Optional<String> getParameter(String name) {
		return _portletSharedRequestHelper.getParameter(name, _renderRequest);
	}

	@Override
	public Optional<String[]> getParameterValues(String name) {
		return _portletSharedRequestHelper.getParameterValues(
			name, _renderRequest);
	}

	@Override
	public Optional<PortletPreferences> getPortletPreferences() {
		return _portletPreferencesOptional;
	}

	@Override
	public QueryConfig getQueryConfig() {
		return _searchSettings.getQueryConfig();
	}

	@Override
	public RenderRequest getRenderRequest() {
		return _renderRequest;
	}

	@Override
	public SearchContext getSearchContext() {
		return _searchSettings.getSearchContext();
	}

	@Override
	public ThemeDisplay getThemeDisplay() {
		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(_renderRequest);

		return themeDisplaySupplier.getThemeDisplay();
	}

	@Override
	public void setKeywords(String keywords) {
		_searchSettings.setKeywords(keywords);
	}

	@Override
	public void setPaginationDelta(int delta) {
		_searchSettings.setPaginationDelta(delta);
	}

	@Override
	public void setPaginationDeltaParameterName(String deltaParameterName) {
		_searchSettings.setPaginationDeltaParameterName(deltaParameterName);
	}

	@Override
	public void setPaginationStart(int paginationStart) {
		_searchSettings.setPaginationStart(paginationStart);
	}

	@Override
	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_searchSettings.setPaginationStartParameterName(
			paginationStartParameterName);
	}

	private final Optional<PortletPreferences> _portletPreferencesOptional;
	private final PortletSharedRequestHelper _portletSharedRequestHelper;
	private final RenderRequest _renderRequest;
	private final SearchSettings _searchSettings;

}