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

package com.liferay.asset.list.item.selector.web.internal.layout.list.retriever;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutListRetriever.class)
public class AssetEntryListLayoutListRetriever
	implements LayoutListRetriever
		<InfoListItemSelectorReturnType, ClassedModelListObjectReference> {

	@Override
	public List<AssetEntry> getList(
		ClassedModelListObjectReference classedModelListObjectReference,
		LayoutListRetrieverContext layoutListRetrieverContext) {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				classedModelListObjectReference.getClassPK());

		if (assetListEntry == null) {
			return Collections.emptyList();
		}

		Optional<long[]> segmentsExperienceIdsOptional =
			layoutListRetrieverContext.getSegmentsExperienceIdsOptional();

		long[] segmentsExperienceIds = segmentsExperienceIdsOptional.orElse(
			new long[] {0});

		return _assetListAssetEntryProvider.getAssetEntries(
			assetListEntry, segmentsExperienceIds[0]);
	}

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

}