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

package com.liferay.layout.type.controller.collection.internal.display.context;

import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.DefaultInfoListProviderContext;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Jürgen Kappler
 */
public class CollectionItemsDetailDisplayContext {

	public CollectionItemsDetailDisplayContext(
		AssetListEntryLocalService assetListEntryLocalService,
		AssetListAssetEntryProvider assetListAssetEntryProvider,
		InfoItemServiceTracker infoItemServiceTracker,
		ThemeDisplay themeDisplay) {

		_assetListEntryLocalService = assetListEntryLocalService;
		_assetListAssetEntryProvider = assetListAssetEntryProvider;
		_infoItemServiceTracker = infoItemServiceTracker;
		_themeDisplay = themeDisplay;
	}

	public long getCollectionItemsCount() {
		Layout layout = _themeDisplay.getLayout();

		String collectionPK = layout.getTypeSettingsProperty("collectionPK");

		String collectionType = layout.getTypeSettingsProperty(
			"collectionType");

		if (Validator.isNull(collectionType) ||
			Validator.isNull(collectionPK)) {

			return 0;
		}

		if (Objects.equals(
				collectionType,
				InfoListProviderItemSelectorReturnType.class.getName())) {

			return _getInfoListProviderItemCount(collectionPK);
		}
		else if (Objects.equals(
					collectionType,
					InfoListItemSelectorReturnType.class.getName())) {

			return _getAssetListEntryItemCount(collectionPK);
		}

		return 0;
	}

	private long _getAssetListEntryItemCount(String classPK) {
		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				GetterUtil.getLong(classPK));

		if (assetListEntry == null) {
			return 0;
		}

		return _assetListAssetEntryProvider.getAssetEntriesCount(
			assetListEntry, 0);
	}

	private long _getInfoListProviderItemCount(String collectionPK) {
		List<InfoListProvider<?>> infoListProviders =
			(List<InfoListProvider<?>>)
				(List<?>)_infoItemServiceTracker.getAllInfoItemServices(
					InfoListProvider.class);

		Stream<InfoListProvider<?>> stream = infoListProviders.stream();

		Optional<InfoListProvider<?>> infoListProviderOptional = stream.filter(
			infoListProvider -> Objects.equals(
				infoListProvider.getKey(), collectionPK)
		).findFirst();

		if (!infoListProviderOptional.isPresent()) {
			return 0;
		}

		InfoListProvider<?> infoListProvider = infoListProviderOptional.get();

		InfoListProviderContext infoListProviderContext =
			new DefaultInfoListProviderContext(
				_themeDisplay.getScopeGroup(), _themeDisplay.getUser());

		return infoListProvider.getInfoListCount(infoListProviderContext);
	}

	private final AssetListAssetEntryProvider _assetListAssetEntryProvider;
	private final AssetListEntryLocalService _assetListEntryLocalService;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final ThemeDisplay _themeDisplay;

}