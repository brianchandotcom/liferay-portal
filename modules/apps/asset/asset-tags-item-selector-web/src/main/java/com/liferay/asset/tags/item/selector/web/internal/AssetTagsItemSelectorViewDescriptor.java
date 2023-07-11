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

package com.liferay.asset.tags.item.selector.web.internal;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.tags.item.selector.AssetTagsItemSelectorReturnType;
import com.liferay.asset.tags.item.selector.criterion.AssetTagsItemSelectorCriterion;
import com.liferay.asset.tags.item.selector.web.internal.display.context.AssetTagsDisplayContext;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.portal.kernel.dao.search.SearchContainer;

/**
 * @author Stefan Tanasie
 */
public class AssetTagsItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<AssetTag> {

	public AssetTagsItemSelectorViewDescriptor(
		AssetTagsItemSelectorCriterion assetTagsItemSelectorCriterion,
		AssetTagsDisplayContext assetTagsDisplayContext) {

		_assetTagsItemSelectorCriterion = assetTagsItemSelectorCriterion;
		_assetTagsDisplayContext = assetTagsDisplayContext;
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
	public ItemDescriptor getItemDescriptor(AssetTag assetTag) {
		return new AssetTagsItemDescriptor(assetTag);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new AssetTagsItemSelectorReturnType();
	}

	@Override
	public String getKeyProperty() {
		return "name";
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name"};
	}

	@Override
	public SearchContainer<AssetTag> getSearchContainer() {
		return _assetTagsDisplayContext.getTagSearchContainer();
	}

	@Override
	public TableItemView getTableItemView(AssetTag assetTag) {
		return new AssetTagsTableItemView(
			assetTag, _assetTagsItemSelectorCriterion);
	}

	@Override
	public boolean isMultipleSelection() {
		return _assetTagsItemSelectorCriterion.isMultiSelection();
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final AssetTagsDisplayContext _assetTagsDisplayContext;
	private final AssetTagsItemSelectorCriterion
		_assetTagsItemSelectorCriterion;

}