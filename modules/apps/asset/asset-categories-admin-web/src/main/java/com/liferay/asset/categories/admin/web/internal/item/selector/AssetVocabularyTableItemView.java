/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.item.selector;

import com.liferay.asset.categories.admin.web.internal.display.context.AssetCategoriesDisplayContext;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.item.selector.TableItemView;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.search.DateSearchEntry;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class AssetVocabularyTableItemView implements TableItemView {

	public AssetVocabularyTableItemView(
		AssetCategoriesDisplayContext assetCategoriesDisplayContext,
		AssetVocabulary assetVocabulary) {

		_assetCategoriesDisplayContext = assetCategoriesDisplayContext;
		_assetVocabulary = assetVocabulary;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray(
			"name", "description", "create-date", "number-of-categories",
			"asset-type");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		nameTextSearchEntry.setCssClass(
			"table-cell-expand table-cell-minw-200 table-title");
		nameTextSearchEntry.setName(
			HtmlUtil.escape(_assetVocabulary.getTitle(locale)));

		searchEntries.add(nameTextSearchEntry);

		TextSearchEntry descriptionTextSearchEntry = new TextSearchEntry();

		descriptionTextSearchEntry.setCssClass(
			"table-cell-expand table-cell-minw-200");
		descriptionTextSearchEntry.setName(
			HtmlUtil.escape(_assetVocabulary.getDescription(locale)));

		searchEntries.add(descriptionTextSearchEntry);

		DateSearchEntry createDateTextSearchEntry = new DateSearchEntry();

		createDateTextSearchEntry.setCssClass("table-cell-ws-nowrap");
		createDateTextSearchEntry.setDate(_assetVocabulary.getCreateDate());

		searchEntries.add(createDateTextSearchEntry);

		TextSearchEntry numberOfCategoriesTextSearchEntry =
			new TextSearchEntry();

		numberOfCategoriesTextSearchEntry.setCssClass(
			"table-column-text-center");
		numberOfCategoriesTextSearchEntry.setName(
			String.valueOf(_assetVocabulary.getCategoriesCount()));

		searchEntries.add(numberOfCategoriesTextSearchEntry);

		TextSearchEntry assetTypeTextSearchEntry = new TextSearchEntry();

		assetTypeTextSearchEntry.setCssClass(
			"table-cell-expand-smallest table-cell-minw-150");
		assetTypeTextSearchEntry.setName(
			_assetCategoriesDisplayContext.getAssetType(_assetVocabulary));

		searchEntries.add(assetTypeTextSearchEntry);

		return searchEntries;
	}

	private final AssetCategoriesDisplayContext _assetCategoriesDisplayContext;
	private final AssetVocabulary _assetVocabulary;

}