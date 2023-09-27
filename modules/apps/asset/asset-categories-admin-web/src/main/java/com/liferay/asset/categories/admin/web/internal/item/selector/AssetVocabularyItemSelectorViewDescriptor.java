/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.item.selector;

import com.liferay.asset.categories.admin.web.internal.display.context.AssetCategoriesDisplayContext;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eudaldo Alonso
 */
public class AssetVocabularyItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<AssetVocabulary> {

	public AssetVocabularyItemSelectorViewDescriptor(
		AssetCategoriesDisplayContext assetCategoriesDisplayContext) {

		_assetCategoriesDisplayContext = assetCategoriesDisplayContext;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	@Override
	public String[] getDisplayViews() {
		return new String[] {"list", "descriptive"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(AssetVocabulary assetVocabulary) {
		return new AssetVocabularyItemDescriptor(
			_assetCategoriesDisplayContext, assetVocabulary);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public String[] getOrderByKeys() {
		return new String[] {"create-date"};
	}

	@Override
	public SearchContainer<AssetVocabulary> getSearchContainer()
		throws PortalException {

		return _assetCategoriesDisplayContext.getVocabulariesSearchContainer();
	}

	@Override
	public TableItemView getTableItemView(AssetVocabulary assetVocabulary) {
		return new AssetVocabularyTableItemView(
			_assetCategoriesDisplayContext, assetVocabulary);
	}

	@Override
	public boolean isMultipleSelection() {
		return true;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final AssetCategoriesDisplayContext _assetCategoriesDisplayContext;

}