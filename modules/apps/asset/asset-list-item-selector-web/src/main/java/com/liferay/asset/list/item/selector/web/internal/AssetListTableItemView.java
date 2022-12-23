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

import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.item.selector.web.internal.display.context.AssetListEntryItemSelectorDisplayContext;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.item.selector.TableItemView;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.search.IconSearchEntry;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class AssetListTableItemView implements TableItemView {

	public AssetListTableItemView(
		AssetListEntry assetListEntry,
		AssetListEntryItemSelectorDisplayContext
			assetListEntryItemSelectorDisplayContext) {

		_assetListEntry = assetListEntry;
		_assetListEntryItemSelectorDisplayContext =
			assetListEntryItemSelectorDisplayContext;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray(
			"", "name", "type", "item-type", "subtype", "variations", "usages",
			"modified");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		IconSearchEntry iconSearchEntry = new IconSearchEntry();

		if (_assetListEntry.getType() ==
				AssetListEntryTypeConstants.TYPE_DYNAMIC) {

			iconSearchEntry.setIcon("bolt");
		}
		else {
			iconSearchEntry.setIcon("list");
		}

		searchEntries.add(iconSearchEntry);

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		nameTextSearchEntry.setCssClass(
			"entry entry-selector table-cell-expand text-truncate");
		nameTextSearchEntry.setName(
			HtmlUtil.escape(_assetListEntry.getTitle()));

		searchEntries.add(nameTextSearchEntry);

		TextSearchEntry typeTextSearchEntry = new TextSearchEntry();

		typeTextSearchEntry.setCssClass("text-truncate");
		typeTextSearchEntry.setName(
			LanguageUtil.get(
				locale, HtmlUtil.escape(_assetListEntry.getTypeLabel())));

		searchEntries.add(typeTextSearchEntry);

		TextSearchEntry itemTypeTextSearchEntry = new TextSearchEntry();

		itemTypeTextSearchEntry.setCssClass("table-cell-expand text-truncate");
		itemTypeTextSearchEntry.setName(
			_assetListEntryItemSelectorDisplayContext.getType(
				_assetListEntry, locale));

		searchEntries.add(itemTypeTextSearchEntry);

		TextSearchEntry subtypeTextSearchEntry = new TextSearchEntry();

		subtypeTextSearchEntry.setCssClass("table-cell-expand text-truncate");
		subtypeTextSearchEntry.setName(
			_assetListEntryItemSelectorDisplayContext.getSubtype(
				_assetListEntry));

		searchEntries.add(subtypeTextSearchEntry);

		TextSearchEntry variationsTextSearchEntry = new TextSearchEntry();

		variationsTextSearchEntry.setCssClass("text-truncate");
		variationsTextSearchEntry.setName(
			String.valueOf(
				_assetListEntryItemSelectorDisplayContext.
					getAssetListEntrySegmentsEntryRelsCount(_assetListEntry)));

		searchEntries.add(variationsTextSearchEntry);

		TextSearchEntry usagesTextSearchEntry = new TextSearchEntry();

		usagesTextSearchEntry.setCssClass("text-truncate");
		usagesTextSearchEntry.setName(
			String.valueOf(
				_assetListEntryItemSelectorDisplayContext.
					getAssetListEntryUsageCount(_assetListEntry)));

		searchEntries.add(usagesTextSearchEntry);

		TextSearchEntry modifiedSearchEntry = new TextSearchEntry();

		modifiedSearchEntry.setCssClass("table-cell-expand text-truncate");

		Date modifiedDate = _assetListEntry.getModifiedDate();

		if (Objects.nonNull(modifiedDate)) {
			modifiedSearchEntry.setName(
				LanguageUtil.format(
					locale, "x-ago",
					LanguageUtil.getTimeDescription(
						locale,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true)));
		}

		searchEntries.add(modifiedSearchEntry);

		return searchEntries;
	}

	private final AssetListEntry _assetListEntry;
	private final AssetListEntryItemSelectorDisplayContext
		_assetListEntryItemSelectorDisplayContext;

}