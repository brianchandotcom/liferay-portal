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

package com.liferay.asset.browser.web.internal.item.selector;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.criteria.AssetEntryItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barbara Cabrera
 */
public class AssetEntryItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<AssetEntry> {

	public AssetEntryItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public ItemDescriptor getItemDescriptor(AssetEntry assetEntry) {
		return new AssetEntryItemDescriptor(assetEntry, _httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new AssetEntryItemSelectorReturnType();
	}

	@Override
	public SearchContainer<AssetEntry> getSearchContainer()
		throws PortalException {

		return null;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final HttpServletRequest _httpServletRequest;

}