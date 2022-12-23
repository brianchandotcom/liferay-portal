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

package com.liferay.asset.list.item.selector.web.internal;

import com.liferay.asset.list.item.selector.web.internal.display.context.AssetListEntryItemSelectorDisplayContext;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetListItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<AssetListEntry> {

	public AssetListItemSelectorViewDescriptor(
		AssetListEntryItemSelectorDisplayContext
			assetListEntryItemSelectorDisplayContext,
		HttpServletRequest httpServletRequest) {

		_assetListEntryItemSelectorDisplayContext =
			assetListEntryItemSelectorDisplayContext;
		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"icon", "list"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(AssetListEntry assetListEntry) {
		return new AssetListItemDescriptor(
			assetListEntry, _assetListEntryItemSelectorDisplayContext,
			_httpServletRequest);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new InfoListItemSelectorReturnType();
	}

	@Override
	public SearchContainer<AssetListEntry> getSearchContainer() {
		return _assetListEntryItemSelectorDisplayContext.getSearchContainer();
	}

	@Override
	public TableItemView getTableItemView(AssetListEntry assetListEntry) {
		return new AssetListTableItemView(
			assetListEntry, _assetListEntryItemSelectorDisplayContext);
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final AssetListEntryItemSelectorDisplayContext
		_assetListEntryItemSelectorDisplayContext;
	private final HttpServletRequest _httpServletRequest;

}