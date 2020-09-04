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

package com.liferay.frontend.taglib.clay.view.state.internal;

import com.liferay.frontend.taglib.clay.view.state.ManagementToolbarViewState;
import com.liferay.frontend.taglib.clay.view.state.ManagementToolbarViewStateFactory;
import com.liferay.frontend.taglib.liferay.ui.view.state.SearchContainerURLFactory;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.portlet.RenderParameters;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author  Neil Griffin
 */
@Component(service = ManagementToolbarViewStateFactory.class)
public class ManagementToolbarViewStateFactoryImpl
	implements ManagementToolbarViewStateFactory {

	@Override
	public ManagementToolbarViewState create(
		String addEntryMessage, RenderURL addEntryURL,
		RenderURL clearResultsURL, String displayStyle,
		RenderURL displayStyleURL, String searchFormMethod,
		String searchFormName, String searchInputName, RenderURL searchURL,
		String searchValue, boolean showCreationMenu,
		boolean showDisplayStyleCard, boolean showDisplayStyleList,
		boolean showDisplayStyleTable, String sortingOrder,
		RenderURL sortingURLCurrent, RenderURL sortingURLReverse) {

		return new ManagementToolbarViewStateImpl(
			addEntryMessage, addEntryURL, clearResultsURL, displayStyle,
			displayStyleURL, searchFormMethod, searchFormName, searchInputName,
			searchURL, searchValue, showCreationMenu, showDisplayStyleCard,
			showDisplayStyleList, showDisplayStyleTable, sortingOrder,
			sortingURLCurrent, sortingURLReverse);
	}

	@Override
	public ManagementToolbarViewState create(
		String addEntryMessage, String defaultDisplayStyle,
		String defaultOrderByCol, String defaultOrderByType,
		RenderRequest renderRequest, RenderResponse renderResponse,
		boolean showCreationMenu, boolean showDisplayStyleCard,
		boolean showDisplayStyleList, boolean showDisplayStyleTable) {

		return create(
			addEntryMessage, defaultDisplayStyle, defaultOrderByCol,
			defaultOrderByType, renderRequest, renderResponse, null, null, null,
			null, showCreationMenu, showDisplayStyleCard, showDisplayStyleList,
			showDisplayStyleTable);
	}

	@Override
	public ManagementToolbarViewState create(
		String addEntryMessage, String defaultDisplayStyle,
		String defaultOrderByCol, String defaultOrderByType,
		RenderRequest renderRequest, RenderResponse renderResponse,
		String searchFormMethod, String searchFormName, String searchInputName,
		String searchValue, boolean showCreationMenu,
		boolean showDisplayStyleCard, boolean showDisplayStyleList,
		boolean showDisplayStyleTable) {

		RenderParameters renderParameters = renderRequest.getRenderParameters();

		int cur = GetterUtil.getInteger(
			renderParameters.getValue(SearchContainer.DEFAULT_CUR_PARAM),
			SearchContainer.DEFAULT_CUR);

		int delta = GetterUtil.getInteger(
			renderParameters.getValue(SearchContainer.DEFAULT_DELTA_PARAM),
			SearchContainer.DEFAULT_DELTA);

		String displayStyle = GetterUtil.getString(
			renderParameters.getValue("displayStyle"), defaultDisplayStyle);

		String keywords = renderParameters.getValue("keywords");

		String orderByCol = GetterUtil.getString(
			renderParameters.getValue("orderByCol"), defaultOrderByCol);

		String orderByType = GetterUtil.getString(
			renderParameters.getValue("orderByType"), defaultOrderByType);

		boolean resetCur = GetterUtil.getBoolean(
			renderParameters.getValue("resetCur"));

		return create(
			addEntryMessage,
			_searchContainerURLFactory.create(
				cur, delta, displayStyle, keywords, orderByCol, orderByType,
				renderResponse::createRenderURL, resetCur,
				SearchContainerURLFactory.Type.ADD_ENTRY),
			_searchContainerURLFactory.create(
				cur, delta, displayStyle, keywords, orderByCol, orderByType,
				renderResponse::createRenderURL, resetCur,
				SearchContainerURLFactory.Type.CLEAR_RESULTS),
			displayStyle,
			_searchContainerURLFactory.create(
				cur, delta, displayStyle, keywords, orderByCol, orderByType,
				renderResponse::createRenderURL, resetCur,
				SearchContainerURLFactory.Type.DISPLAY_STYLE),
			searchFormMethod, searchFormName, searchInputName,
			_searchContainerURLFactory.create(
				cur, delta, displayStyle, keywords, orderByCol, orderByType,
				renderResponse::createRenderURL, resetCur,
				SearchContainerURLFactory.Type.SEARCH),
			searchValue, showCreationMenu, showDisplayStyleCard,
			showDisplayStyleList, showDisplayStyleTable, orderByType,
			_searchContainerURLFactory.create(
				cur, delta, displayStyle, keywords, orderByCol, orderByType,
				renderResponse::createRenderURL, resetCur,
				SearchContainerURLFactory.Type.CURRENT_SORT),
			_searchContainerURLFactory.create(
				cur, delta, displayStyle, keywords, orderByCol, orderByType,
				renderResponse::createRenderURL, resetCur,
				SearchContainerURLFactory.Type.REVERSE_SORT));
	}

	@Reference
	private SearchContainerURLFactory _searchContainerURLFactory;

}