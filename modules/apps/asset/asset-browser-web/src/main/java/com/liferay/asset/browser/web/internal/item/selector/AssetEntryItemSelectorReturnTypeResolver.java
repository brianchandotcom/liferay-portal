/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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