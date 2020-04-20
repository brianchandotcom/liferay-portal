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
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = InfoListProvider.class)
public class MostViewedAssetsInfoListProvider
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

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			infoListProviderContext, "viewCount", "DESC", pagination);

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

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			infoListProviderContext, "viewCount", "DESC", null);

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
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "most-viewed-assets");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MostViewedAssetsInfoListProvider.class);

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference(target = "(bundle.symbolic.name=com.liferay.asset.service)")
	private ResourceBundleLoader _resourceBundleLoader;

}