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

package com.liferay.layout.theme.item.selector.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.theme.item.selector.web.internal.LayoutThemeTableItemView;
import com.liferay.layout.theme.item.selector.web.internal.display.context.LayoutThemeItemSelectorDisplayContext;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Theme;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stefan Tanasie
 */
public class LayoutThemeItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<Theme> {

	public LayoutThemeItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest,
		LayoutThemeItemSelectorDisplayContext
			layoutThemeItemSelectorDisplayContext) {

		_httpServletRequest = httpServletRequest;
		_layoutThemeItemSelectorDisplayContext =
			layoutThemeItemSelectorDisplayContext;
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"icon", "descriptive", "list"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(Theme theme) {
		return new LayoutThemeItemDescriptor(theme, _httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name"};
	}

	@Override
	public SearchContainer<Theme> getSearchContainer() {
		return _layoutThemeItemSelectorDisplayContext.
			getThemesSearchContainer();
	}

	@Override
	public TableItemView getTableItemView(Theme theme) {
		return new LayoutThemeTableItemView(theme, _httpServletRequest);
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
	private final LayoutThemeItemSelectorDisplayContext
		_layoutThemeItemSelectorDisplayContext;

}