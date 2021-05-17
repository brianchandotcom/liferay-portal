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
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
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
public class RelatedAssetCategoriesInfoItemRelatedListProvider
	implements InfoItemRelatedListProvider<AssetEntry, AssetCategory> {

	@Override
	public String getLabel(Locale locale) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByBundleSymbolicName(
					bundle.getSymbolicName());

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return LanguageUtil.get(resourceBundle, "related-categories");
	}

	@Override
	public InfoPage<AssetCategory> getRelatedItemsInfoPage(
		AssetEntry assetEntry, InfoListProviderContext infoListProviderContext,
		Pagination pagination, Sort sort) {

		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetEntryId(
					assetEntry.getEntryId(), pagination.getStart(),
					pagination.getEnd(),
					new OrderByComparator<AssetEntryAssetCategoryRel>() {

						@Override
						public int compare(
							AssetEntryAssetCategoryRel
								assetEntryAssetCategoryRel1,
							AssetEntryAssetCategoryRel
								assetEntryAssetCategoryRel2) {

							int value = Long.compare(
								assetEntryAssetCategoryRel1.
									getAssetCategoryId(),
								assetEntryAssetCategoryRel2.
									getAssetCategoryId());

							if (!sort.isReverse()) {
								return value;
							}

							return Math.negateExact(value);
						}

						@Override
						public String[] getOrderByFields() {
							return new String[] {"assetCategoryId"};
						}

					});

		List<AssetCategory> categories = new ArrayList<>();

		for (AssetEntryAssetCategoryRel assetEntryAssetCategoryRel :
				assetEntryAssetCategoryRels) {

			AssetCategory category = _assetCategoryLocalService.fetchCategory(
				assetEntryAssetCategoryRel.getAssetCategoryId());

			if (category != null) {
				categories.add(category);
			}
		}

		return InfoPage.of(
			categories, pagination,
			() ->
				_assetEntryAssetCategoryRelLocalService.
					getAssetEntryAssetCategoryRelsCount(
						assetEntry.getEntryId()));
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

}