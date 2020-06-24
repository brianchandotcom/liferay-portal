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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoListProvider.class)
public class RelatedAssetsInfoListProvider
	extends BaseAssetsInfoListProvider implements InfoListProvider<AssetEntry> {

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		long assetEntryId = _getLayoutAssetEntryId();

		if (assetEntryId == 0) {
			return Collections.emptyList();
		}

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			infoListProviderContext, Field.MODIFIED_DATE, "DESC", pagination);

		assetEntryQuery.setLinkedAssetEntryId(assetEntryId);

		try {
			return _assetEntryService.getEntries(assetEntryQuery);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return Collections.emptyList();
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		long assetEntryId = _getLayoutAssetEntryId();

		if (assetEntryId == 0) {
			return 0;
		}

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			infoListProviderContext, Field.MODIFIED_DATE, "DESC", null);

		assetEntryQuery.setLinkedAssetEntryId(assetEntryId);

		try {
			return _assetEntryService.getEntriesCount(assetEntryQuery);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries count", exception);
		}

		return 0;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "related-assets");
	}

	@Override
	public boolean isAvailable(
		InfoListProviderContext infoListProviderContext) {

		Optional<Layout> layoutOptional =
			infoListProviderContext.getLayoutOptional();

		if (layoutOptional.isPresent()) {
			Layout layout = layoutOptional.get();

			return layout.isTypeAssetDisplay();
		}

		return false;
	}

	private long _getLayoutAssetEntryId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		AssetEntry layoutAssetEntry =
			(AssetEntry)httpServletRequest.getAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY);

		if (layoutAssetEntry != null) {
			return layoutAssetEntry.getEntryId();
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RelatedAssetsInfoListProvider.class);

	@Reference
	private AssetEntryService _assetEntryService;

}