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

package com.liferay.asset.categories.admin.web.internal.info.collection.provider;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = RelatedInfoItemCollectionProvider.class)
public class AssetCategoriesForAssetEntryRelatedInfoItemCollectionProvider
	implements RelatedInfoItemCollectionProvider<AssetEntry, AssetCategory> {

	@Override
	public InfoPage<AssetCategory> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Optional<Object> relatedItemOptional =
			collectionQuery.getRelatedItemObjectOptional();

		Object relatedItem = relatedItemOptional.orElse(null);

		if (!(relatedItem instanceof AssetEntry)) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		AssetEntry assetEntry = (AssetEntry)relatedItem;

		Pagination pagination = collectionQuery.getPagination();

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

							if (isAscending()) {
								return value;
							}

							return Math.negateExact(value);
						}

						@Override
						public String[] getOrderByFields() {
							return new String[] {"assetCategoryId"};
						}

						@Override
						public boolean isAscending() {
							Optional<Sort> sortOptional =
								collectionQuery.getSortOptional();

							if (!sortOptional.isPresent()) {
								return true;
							}

							Sort sort = sortOptional.get();

							if (sort.isReverse()) {
								return false;
							}

							return true;
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

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "categories-for-this-item");
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

}