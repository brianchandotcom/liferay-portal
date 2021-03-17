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

import com.liferay.layout.page.template.admin.web.internal.util.LayoutPageTemplatePortletUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class MasterLayoutDisplayContext {

	public MasterLayoutDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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

	public SearchContainer<LayoutPageTemplateEntry>
		getMasterLayoutsSearchContainer() {

		if (_masterLayoutsSearchContainer != null) {
			return _masterLayoutsSearchContainer;
		}

		SearchContainer<LayoutPageTemplateEntry> masterLayoutsSearchContainer =
			new SearchContainer(
				_renderRequest, getPortletURL(), null,
				"there-are-no-master-pages");

		masterLayoutsSearchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<LayoutPageTemplateEntry> orderByComparator =
			LayoutPageTemplatePortletUtil.
				getLayoutPageTemplateEntryOrderByComparator(
					getOrderByCol(), getOrderByType());

		masterLayoutsSearchContainer.setOrderByComparator(orderByComparator);

		masterLayoutsSearchContainer.setOrderByType(getOrderByType());

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries = null;
		int layoutPageTemplateEntriesCount = 0;

		if (isSearch()) {
			layoutPageTemplateEntries =
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					_themeDisplay.getScopeGroupId(), getKeywords(),
					LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
					masterLayoutsSearchContainer.getStart(),
					masterLayoutsSearchContainer.getEnd(), orderByComparator);

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(), getKeywords(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_MASTER_LAYOUT);
		}
		else {
			if (masterLayoutsSearchContainer.getStart() == 0) {
				layoutPageTemplateEntries = new ArrayList<>();

				layoutPageTemplateEntries.add(_addBlankMasterLayout());
			}

			layoutPageTemplateEntries.addAll(
				LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
					_themeDisplay.getScopeGroupId(),
					LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
					masterLayoutsSearchContainer.getStart(),
					masterLayoutsSearchContainer.getEnd(), orderByComparator));

			layoutPageTemplateEntriesCount =
				LayoutPageTemplateEntryServiceUtil.
					getLayoutPageTemplateEntriesCount(
						_themeDisplay.getScopeGroupId(),
						LayoutPageTemplateEntryTypeConstants.
							TYPE_MASTER_LAYOUT);

			if (masterLayoutsSearchContainer.getStart() == 0) {
				layoutPageTemplateEntriesCount++;
			}
		}

		masterLayoutsSearchContainer.setResults(layoutPageTemplateEntries);
		masterLayoutsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));
		masterLayoutsSearchContainer.setTotal(layoutPageTemplateEntriesCount);

		_masterLayoutsSearchContainer = masterLayoutsSearchContainer;

		return _masterLayoutsSearchContainer;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_master_layouts.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).setParameter(
			"tabs1", "master-layouts"
		).build();

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	private LayoutPageTemplateEntry _addBlankMasterLayout() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				createLayoutPageTemplateEntry(0);

		layoutPageTemplateEntry.setName(
			LanguageUtil.get(_httpServletRequest, "blank"));
		layoutPageTemplateEntry.setStatus(WorkflowConstants.STATUS_APPROVED);

		return layoutPageTemplateEntry;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private Long _layoutPageTemplateEntryId;
	private SearchContainer<LayoutPageTemplateEntry>
		_masterLayoutsSearchContainer;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}