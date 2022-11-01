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

package com.liferay.asset.display.page.item.selector.web.internal.item.selector;

import com.liferay.asset.display.page.item.selector.web.internal.display.context.AssetDisplayPagesItemSelectorViewDisplayContext;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.criteria.AssetEntryItemSelectorReturnType;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import javax.portlet.PortletException;

/**
 * @author Eudaldo Alonso
 */
public class AssetDisplayPageItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<LayoutPageTemplateEntry> {

	public AssetDisplayPageItemSelectorViewDescriptor(
		AssetDisplayPagesItemSelectorViewDisplayContext
			assetDisplayPagesItemSelectorViewDisplayContext) {

		_assetDisplayPagesItemSelectorViewDisplayContext =
			assetDisplayPagesItemSelectorViewDisplayContext;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "icon";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"icon"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		return new AssetDisplayPageItemDescriptor(layoutPageTemplateEntry);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new AssetEntryItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"name", "create-date"};
	}

	@Override
	public SearchContainer<LayoutPageTemplateEntry> getSearchContainer()
		throws PortalException {

		try {
			return _assetDisplayPagesItemSelectorViewDisplayContext.
				getAssetDisplayPageSearchContainer();
		}
		catch (PortletException portletException) {
			throw new PortalException(portletException);
		}
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final AssetDisplayPagesItemSelectorViewDisplayContext
		_assetDisplayPagesItemSelectorViewDisplayContext;

}