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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageDisplayContext {

	public DisplayPageDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public SearchContainer<LayoutPageTemplateEntry>
		getDisplayPagesSearchContainer() {

		if (_displayPagesSearchContainer != null) {
			return _displayPagesSearchContainer;
		}

		SearchContainer<LayoutPageTemplateEntry> displayPagesSearchContainer =
			new SearchContainer(
				_renderRequest, getPortletURL(), null,
				"there-are-no-display-page-templates");

		displayPagesSearchContainer.setOrderByCol(getOrderByCol());
		displayPagesSearchContainer.setOrderByComparator(
			LayoutPageTemplatePortletUtil.
				getLayoutPageTemplateEntryOrderByComparator(
					getOrderByCol(), getOrderByType()));
		displayPagesSearchContainer.setOrderByType(getOrderByType());

		if (isSearch()) {
			displayPagesSearchContainer.setResultsAndTotal(
				() ->
					LayoutPageTemplateEntryServiceUtil.
						getLayoutPageTemplateEntries(
							_themeDisplay.getScopeGroupId(), getKeywords(),
							LayoutPageTemplateEntryTypeConstants.
								TYPE_DISPLAY_PAGE,
							displayPagesSearchContainer.getStart(),
							displayPagesSearchContainer.getEnd(),
							displayPagesSearchContainer.getOrderByComparator()),
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(), getKeywords(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_DISPLAY_PAGE));
		}
		else {
			displayPagesSearchContainer.setResultsAndTotal(
				() ->
					LayoutPageTemplateEntryServiceUtil.
						getLayoutPageTemplateEntries(
							_themeDisplay.getScopeGroupId(),
							LayoutPageTemplateEntryTypeConstants.
								TYPE_DISPLAY_PAGE,
							displayPagesSearchContainer.getStart(),
							displayPagesSearchContainer.getEnd(),
							displayPagesSearchContainer.getOrderByComparator()),
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_DISPLAY_PAGE));
		}

		displayPagesSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		_displayPagesSearchContainer = displayPagesSearchContainer;

		return _displayPagesSearchContainer;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public long getLayoutPageTemplateEntryId() {
		if (Validator.isNotNull(_layoutPageTemplateEntryId)) {
			return _layoutPageTemplateEntryId;
		}

		_layoutPageTemplateEntryId = ParamUtil.getLong(
			_httpServletRequest, "layoutPageTemplateEntryId");

		return _layoutPageTemplateEntryId;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest,
			LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
			"display-page-order-by-col", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest,
			LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
			"display-page-order-by-type", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_display_pages.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setKeywords(
			() -> {
				String keywords = getKeywords();

				if (Validator.isNotNull(keywords)) {
					return keywords;
				}

				return null;
			}
		).setTabs1(
			"display-page-templates"
		).setParameter(
			"orderByCol",
			() -> {
				String orderByCol = getOrderByCol();

				if (Validator.isNotNull(orderByCol)) {
					return orderByCol;
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String orderByType = getOrderByType();

				if (Validator.isNotNull(orderByType)) {
					return orderByType;
				}

				return null;
			}
		).buildPortletURL();
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private SearchContainer<LayoutPageTemplateEntry>
		_displayPagesSearchContainer;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private Long _layoutPageTemplateEntryId;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}