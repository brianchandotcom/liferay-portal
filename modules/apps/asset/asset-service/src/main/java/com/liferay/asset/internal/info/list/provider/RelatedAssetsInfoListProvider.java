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
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = InfoListProvider.class)
public class RelatedAssetsInfoListProvider
	implements InfoListProvider<AssetEntry> {

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long assetEntryId = _getLayoutAssetEntryId(serviceContext.getRequest());

		if (assetEntryId == 0) {
			return Collections.emptyList();
		}

		Company company = infoListProviderContext.getCompany();

		List<AssetLink> directAssetLinks =
			_assetLinkLocalService.getDirectLinks(assetEntryId, true);

		return _toAssetEntry(
			assetEntryId, company.getCompanyId(), directAssetLinks);
	}

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		List<AssetEntry> assetEntries = getInfoList(infoListProviderContext);

		return ListUtil.subList(
			assetEntries, pagination.getStart(), pagination.getEnd());
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		List<AssetEntry> assetEntries = getInfoList(infoListProviderContext);

		return assetEntries.size();
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "related-assets");
	}

	private AssetEntry _getAssetEntry(long assetEntryId, AssetLink assetLink) {
		if ((assetEntryId > 0) || (assetLink.getEntryId1() == assetEntryId)) {
			return _assetEntryLocalService.fetchEntry(assetLink.getEntryId2());
		}

		return _assetEntryLocalService.fetchEntry(assetLink.getEntryId1());
	}

	private long _getLayoutAssetEntryId(HttpServletRequest httpServletRequest) {
		AssetEntry layoutAssetEntry =
			(AssetEntry)httpServletRequest.getAttribute(
				WebKeys.LAYOUT_ASSET_ENTRY);

		if (layoutAssetEntry != null) {
			return layoutAssetEntry.getEntryId();
		}

		return 0;
	}

	private List<AssetEntry> _toAssetEntry(
		long assetEntryId, long companyId, List<AssetLink> directAssetLinks) {

		List<AssetEntry> assetEntries = new ArrayList<>();

		for (AssetLink assetLink : directAssetLinks) {
			AssetEntry assetEntry = _getAssetEntry(assetEntryId, assetLink);

			AssetRendererFactory<?> assetRendererFactory =
				assetEntry.getAssetRendererFactory();

			if (assetRendererFactory.isActive(companyId)) {
				assetEntries.add(assetEntry);
			}
		}

		return assetEntries;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

}