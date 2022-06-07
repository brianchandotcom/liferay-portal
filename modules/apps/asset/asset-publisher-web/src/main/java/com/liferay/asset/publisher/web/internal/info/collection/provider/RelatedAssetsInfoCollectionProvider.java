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

package com.liferay.asset.publisher.web.internal.info.collection.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoCollectionProvider.class)
public class RelatedAssetsInfoCollectionProvider
	implements InfoCollectionProvider<AssetEntry> {

	@Override
	public InfoPage<AssetEntry> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		Set<Long> linkedAssetEntryIds =
			(Set<Long>)httpServletRequest.getAttribute(
				WebKeys.LINKED_ASSET_ENTRY_IDS);

		if (SetUtil.isEmpty(linkedAssetEntryIds)) {
			return InfoPage.of(
				Collections.emptyList(), collectionQuery.getPagination(), 0);
		}

		AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
			collectionQuery.getPagination());

		assetEntryQuery.setLinkedAssetEntryIds(
			ArrayUtil.toLongArray(linkedAssetEntryIds));

		try {
			return InfoPage.of(
				_assetEntryService.getEntries(assetEntryQuery),
				collectionQuery.getPagination(),
				_assetEntryService.getEntriesCount(assetEntryQuery));
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return InfoPage.of(
			Collections.emptyList(), collectionQuery.getPagination(), 0);
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "related-assets");
	}

	@Override
	public boolean isAvailable() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		String itemSelectorPortletNamespace = _portal.getPortletNamespace(
			ItemSelectorPortletKeys.ITEM_SELECTOR);

		String itemSelectedEventName = ParamUtil.getString(
			themeDisplay.getRequest(),
			itemSelectorPortletNamespace + "itemSelectedEventName");

		String assetPublisherPortletNamespace = _portal.getPortletNamespace(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);

		if (Objects.equals(
				itemSelectedEventName,
				assetPublisherPortletNamespace + "selectAssetList")) {

			return true;
		}

		return false;
	}

	private AssetEntryQuery _getAssetEntryQuery(Pagination pagination) {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		assetEntryQuery.setClassNameIds(
			ArrayUtil.filter(
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					serviceContext.getCompanyId(), true),
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

		assetEntryQuery.setGroupIds(
			new long[] {serviceContext.getScopeGroupId()});
		assetEntryQuery.setOrderByCol1(Field.MODIFIED_DATE);
		assetEntryQuery.setOrderByCol2(Field.CREATE_DATE);
		assetEntryQuery.setOrderByType1("DESC");
		assetEntryQuery.setOrderByType2("DESC");

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
		}

		return assetEntryQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RelatedAssetsInfoCollectionProvider.class);

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}