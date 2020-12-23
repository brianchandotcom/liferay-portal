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
import com.liferay.info.item.provider.InfoItemRelatedItemsProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	service = InfoItemRelatedItemsProvider.class
)
public class RelatedAssetsInfoItemRelatedItemsProvider implements
	InfoItemRelatedItemsProvider<AssetEntry, AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getRelatedItemsInfoPage(
		AssetEntry sourceAssetEntry, Pagination pagination, Sort sort) {

		try {
			AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
				sourceAssetEntry.getCompanyId(), sourceAssetEntry.getGroupId(),
				Field.MODIFIED_DATE, "DESC", pagination);

			assetEntryQuery.setLinkedAssetEntryId(sourceAssetEntry.getEntryId());

			return InfoPage.of(
				_assetEntryService.getEntries(assetEntryQuery), pagination,
				() -> getTotalCount(sourceAssetEntry));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get asset entries", portalException);
		}
	}

	public int getTotalCount(AssetEntry assetEntry) {
		try {
			AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
				assetEntry.getCompanyId(), assetEntry.getGroupId(),
				Field.MODIFIED_DATE, "DESC", null);

			assetEntryQuery.setLinkedAssetEntryId(assetEntry.getEntryId());

			return _assetEntryService.getEntriesCount(assetEntryQuery);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Unable to get asset entries", portalException);
		}

	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "related-assets");
	}

	private AssetEntryQuery _getAssetEntryQuery(
			long companyId, long groupId, String orderByCol,
			String orderByType, Pagination pagination)
		throws PortalException {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		Company company = _companyLocalService.getCompany(companyId);

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				company.getCompanyId(), true);

		availableClassNameIds = ArrayUtil.filter(
			availableClassNameIds,
			availableClassNameId -> {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					portal.getClassName(availableClassNameId));

				if (indexer == null) {
					return false;
				}

				return true;
			});

		assetEntryQuery.setClassNameIds(availableClassNameIds);

		assetEntryQuery.setEnablePermissions(true);

		Group group = _groupLocalService.getGroup(groupId);

		assetEntryQuery.setGroupIds(new long[] {group.getGroupId()});

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		assetEntryQuery.setOrderByCol1(orderByCol);
		assetEntryQuery.setOrderByType1(orderByType);

		assetEntryQuery.setOrderByCol2(Field.CREATE_DATE);
		assetEntryQuery.setOrderByType2("DESC");

		return assetEntryQuery;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	protected Portal portal;

}