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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jürgen Kappler
 */
public class LayoutUtilityPageEntryDisplayContext {

	public LayoutUtilityPageEntryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<LayoutUtilityPageEntry>
		getLayoutUtilityPageEntrySearchContainer() {

		if (_layoutUtilityPageEntrySearchContainer != null) {
			return _layoutUtilityPageEntrySearchContainer;
		}

		SearchContainer<LayoutUtilityPageEntry>
			layoutUtilityPageEntrySearchContainer = new SearchContainer<>(
				_renderRequest, _getPortletURL(), null,
				"there-are-no-utility-pages");

		layoutUtilityPageEntrySearchContainer.setOrderByCol(getOrderByCol());
		layoutUtilityPageEntrySearchContainer.setOrderByType(getOrderByType());
		layoutUtilityPageEntrySearchContainer.setResultsAndTotal(
			() ->
				LayoutUtilityPageEntryLocalServiceUtil.
					getLayoutUtilityPageEntries(
						_themeDisplay.getScopeGroupId(),
						layoutUtilityPageEntrySearchContainer.getStart(),
						layoutUtilityPageEntrySearchContainer.getEnd(), null),
			LayoutUtilityPageEntryLocalServiceUtil.
				getLayoutUtilityPageEntriesCount(
					_themeDisplay.getScopeGroupId()));
		layoutUtilityPageEntrySearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		_layoutUtilityPageEntrySearchContainer =
			layoutUtilityPageEntrySearchContainer;

		return _layoutUtilityPageEntrySearchContainer;
	}

	protected String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modified-date");

		return _orderByCol;
	}

	protected String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");

		return _orderByType;
	}

	private PortletURL _getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setTabs1(
			_getTabs1()
		).buildPortletURL();
	}

	private String _getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_renderRequest, "tabs1");

		return _tabs1;
	}

	private SearchContainer<LayoutUtilityPageEntry>
		_layoutUtilityPageEntrySearchContainer;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;

}