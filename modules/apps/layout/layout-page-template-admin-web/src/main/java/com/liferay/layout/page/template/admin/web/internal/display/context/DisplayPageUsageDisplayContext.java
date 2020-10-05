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

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryServiceUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.layout.page.template.admin.web.internal.util.comparator.AssetDisplayPageEntryModifiedDateComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class DisplayPageUsageDisplayContext {

	public DisplayPageUsageDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
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

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "modified-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(getLayoutPageTemplateEntryId()));
		portletURL.setParameter(
			"mvcRenderCommandName",
			"/layout_page_template/view_display_page_usages");
		portletURL.setParameter("redirect", getRedirect());

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	public SearchContainer<AssetDisplayPageEntry> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<AssetDisplayPageEntry> searchContainer =
			new SearchContainer<>(
				_renderRequest, getPortletURL(), null,
				"there-are-no-display-page-template-usages");

		searchContainer.setId(
			"assetDisplayPageEntries" + getLayoutPageTemplateEntryId());

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetDisplayPageEntry> orderByComparator =
			new AssetDisplayPageEntryModifiedDateComparator(orderByAsc);

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		List<AssetDisplayPageEntry> assetDisplayPageEntries =
			AssetDisplayPageEntryServiceUtil.
				getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
					getLayoutPageTemplateEntryId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(assetDisplayPageEntries);

		int count =
			AssetDisplayPageEntryServiceUtil.
				getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
					getLayoutPageTemplateEntryId());

		searchContainer.setTotal(count);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getTitle(
			AssetDisplayPageEntry assetDisplayPageEntry, Locale locale)
		throws PortalException {

		String className = assetDisplayPageEntry.getClassName();

		if (Objects.equals(className, FileEntry.class.getName())) {
			className = DLFileEntry.class.getName();
		}

		AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(
			className, assetDisplayPageEntry.getClassPK());

		return assetEntry.getTitle(locale);
	}

	private final HttpServletRequest _httpServletRequest;
	private Long _layoutPageTemplateEntryId;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<AssetDisplayPageEntry> _searchContainer;

}