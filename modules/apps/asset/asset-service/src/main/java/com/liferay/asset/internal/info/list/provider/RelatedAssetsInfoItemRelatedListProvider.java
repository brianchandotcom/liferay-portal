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

package com.liferay.asset.internal.info.list.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.info.list.provider.InfoItemRelatedListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemRelatedListProvider.class)
public class RelatedAssetsInfoItemRelatedListProvider
	implements InfoItemRelatedListProvider<AssetEntry, AssetEntry> {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "related-assets");
	}

	@Override
	public InfoPage<AssetEntry> getRelatedItemsInfoPage(
		AssetEntry assetEntry, InfoListProviderContext infoListProviderContext,
		Pagination pagination, Sort sort) {

		try {
			AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
				assetEntry.getCompanyId(), assetEntry.getGroupId(), pagination,
				sort);

			assetEntryQuery.setLinkedAssetEntryId(assetEntry.getEntryId());

			return InfoPage.of(
				_assetEntryService.getEntries(assetEntryQuery), pagination,
				() -> _getTotalCount(assetEntry, sort));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private AssetEntryQuery _getAssetEntryQuery(
			long companyId, long groupId, Pagination pagination, Sort sort)
		throws PortalException {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassNameIds(
			ArrayUtil.filter(
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					companyId, true),
				availableClassNameId -> {
					Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
						_portal.getClassName(availableClassNameId));

					if (indexer == null) {
						return false;
					}

					return true;
				}));
		assetEntryQuery.setEnablePermissions(true);

		if (pagination != null) {
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		String orderByCol = Field.MODIFIED_DATE;
		String orderByType = "DESC";

		if (sort != null) {
			orderByCol = sort.getFieldName();
			orderByType = _getOrderByType(sort);
		}

		assetEntryQuery.setGroupIds(new long[] {groupId});
		assetEntryQuery.setOrderByCol1(orderByCol);
		assetEntryQuery.setOrderByCol2(Field.CREATE_DATE);
		assetEntryQuery.setOrderByType1(orderByType);
		assetEntryQuery.setOrderByType2("DESC");

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
		}

		return assetEntryQuery;
	}

	private String _getOrderByType(Sort sort) {
		if (sort.isReverse()) {
			return "DESC";
		}

		return "ASC";
	}

	private int _getTotalCount(AssetEntry assetEntry, Sort sort) {
		try {
			AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
				assetEntry.getCompanyId(), assetEntry.getGroupId(), null, sort);

			assetEntryQuery.setLinkedAssetEntryId(assetEntry.getEntryId());

			return _assetEntryService.getEntriesCount(assetEntryQuery);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private Portal _portal;

}