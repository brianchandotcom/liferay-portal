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

package com.liferay.asset.categories.admin.web.internal.info.list.provider;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.list.provider.InfoItemRelatedListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoItemRelatedListProvider.class)
public class AssetEntriesWithSameAssetCategoryInfoItemRelatedListProvider
	implements InfoItemRelatedListProvider<AssetCategory, AssetEntry> {

	@Override
	public String getLabel(Locale locale) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					bundle.getSymbolicName());

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return LanguageUtil.get(resourceBundle, "items-with-this-category");
	}

	@Override
	public InfoPage<AssetEntry> getRelatedItemsInfoPage(
		AssetCategory assetCategory,
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategory.getCategoryId(), pagination.getStart(),
					pagination.getEnd(),
					new OrderByComparator<AssetEntryAssetCategoryRel>() {

						@Override
						public int compare(
							AssetEntryAssetCategoryRel
								assetEntryAssetCategoryRel1,
							AssetEntryAssetCategoryRel
								assetEntryAssetCategoryRel2) {

							int value = Long.compare(
								assetEntryAssetCategoryRel1.getAssetEntryId(),
								assetEntryAssetCategoryRel2.getAssetEntryId());

							if (isAscending()) {
								return value;
							}

							return Math.negateExact(value);
						}

						@Override
						public String[] getOrderByFields() {
							return new String[] {"assetEntryId"};
						}

						@Override
						public boolean isAscending() {
							if (sort == null) {
								return true;
							}

							if (sort.isReverse()) {
								return false;
							}

							return true;
						}

					});

		List<AssetEntry> assetEntries = new ArrayList<>();

		for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel :
				assetEntryAssetCategoryRels) {

			AssetEntry assetEntry = _assetEntryLocalService.fetchAssetEntry(
				assetEntryAssetCategoryRel.getAssetEntryId());

			if (assetEntry != null) {
				assetEntries.add(assetEntry);
			}
		}

		return InfoPage.of(
			assetEntries, pagination,
			() ->
				_assetEntryAssetCategoryRelLocalService.
					getAssetEntryAssetCategoryRelsCountByAssetCategoryId(
						assetCategory.getCategoryId()));
	}

	@Reference
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}