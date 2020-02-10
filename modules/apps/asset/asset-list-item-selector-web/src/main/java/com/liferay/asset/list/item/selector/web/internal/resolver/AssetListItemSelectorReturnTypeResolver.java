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

package com.liferay.asset.list.item.selector.web.internal.resolver;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = ItemSelectorReturnTypeResolver.class)
public class AssetListItemSelectorReturnTypeResolver
	implements ItemSelectorReturnTypeResolver
		<InfoListItemSelectorReturnType, AssetListEntry> {

	@Override
	public Class<InfoListItemSelectorReturnType>
		getItemSelectorReturnTypeClass() {

		return InfoListItemSelectorReturnType.class;
	}

	@Override
	public Class<AssetListEntry> getModelClass() {
		return AssetListEntry.class;
	}

	@Override
	public String getValue(
		AssetListEntry assetListEntry, ThemeDisplay themeDisplay) {

		return JSONUtil.put(
			"assetListEntryId", assetListEntry.getAssetListEntryId()
		).put(
			"assetListEntryTitle", assetListEntry.getTitle()
		).toString();
	}

}