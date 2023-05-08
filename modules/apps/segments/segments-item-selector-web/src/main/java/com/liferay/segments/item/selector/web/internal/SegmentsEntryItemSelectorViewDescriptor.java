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

package com.liferay.segments.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.item.selector.web.internal.display.context.SegmentsEntryBrowserDisplayContext;
import com.liferay.segments.model.SegmentsEntry;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stefan Tanasie
 */
public class SegmentsEntryItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<SegmentsEntry> {

	public SegmentsEntryItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest,
		SegmentsEntryBrowserDisplayContext segmentsEntryBrowserDisplayContext) {

		_httpServletRequest = httpServletRequest;
		_segmentsEntryBrowserDisplayContext =
			segmentsEntryBrowserDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[0];
	}

	@Override
	public ItemDescriptor getItemDescriptor(SegmentsEntry segmentsEntry) {
		return new SegmentsEntryItemDescriptor(
			segmentsEntry, _httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"modified-date", "name"};
	}

	@Override
	public SearchContainer<SegmentsEntry> getSearchContainer()
		throws PortalException {

		return _segmentsEntryBrowserDisplayContext.
			getSegmentEntrySearchContainer();
	}

	@Override
	public TableItemView getTableItemView(SegmentsEntry segmentsEntry) {
		return new SegmentsEntryTableItemView(segmentsEntry, _themeDisplay);
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final HttpServletRequest _httpServletRequest;
	private final SegmentsEntryBrowserDisplayContext
		_segmentsEntryBrowserDisplayContext;
	private final ThemeDisplay _themeDisplay;

}