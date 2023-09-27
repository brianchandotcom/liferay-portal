/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = RelatedInfoItemCollectionProvider.class)
public class AssetCategoriesForAssetEntryRelatedInfoItemCollectionProvider
	implements RelatedInfoItemCollectionProvider<AssetEntry, AssetCategory> {

	@Override
	public InfoPage<AssetCategory> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

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
							Sort sort = collectionQuery.getSort();

							if (sort == null) {
								return true;
							}

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
		return _language.get(locale, "categories-for-this-item");
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@Reference
	private Language _language;

}