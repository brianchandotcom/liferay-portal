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
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.AssetEntryItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = ItemSelectorReturnTypeResolver.class)
public class AssetEntryItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<AssetEntryItemSelectorReturnType, AssetEntry> {

	@Override
	public Class<AssetEntryItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return AssetEntryItemSelectorReturnType.class;
	}

	@Override
	public Class<AssetEntry> getModelClass() {
		return AssetEntry.class;
	}

	@Override
	public String getValue(AssetEntry assetEntry, ThemeDisplay themeDisplay) {
		return JSONUtil.put(
			"className", assetEntry.getClassName()
		).put(
			"classNameId", assetEntry.getClassNameId()
		).put(
			"classPK", String.valueOf(assetEntry.getClassPK())
		).put(
			"title", assetEntry.getTitle(themeDisplay.getLocale())
		).toString();
	}

}