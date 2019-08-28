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

package com.liferay.asset.list.asset.entry.provider;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.model.AssetListEntry;

import java.util.List;

/**
 * @author Sarai Díaz
 */
public interface AssetListAssetEntryProvider {

	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long segmentsEntryId);

	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long segmentsEntryId, int start,
		int end);

	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds);

	public List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, int start,
		int end);

	public default List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId) {

		return getAssetEntries(assetListEntry, segmentsEntryIds);
	}

	public default List<AssetEntry> getAssetEntries(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId,
		int start, int end) {

		return getAssetEntries(assetListEntry, segmentsEntryIds, start, end);
	}

	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long segmentsEntryId);

	public int getAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds);

	public default int getAssetEntriesCount(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId) {

		return getAssetEntriesCount(assetListEntry, segmentsEntryIds);
	}

	public AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long segmentsEntryId);

	public AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long[] segmentsEntryIds);

	public default AssetEntryQuery getAssetEntryQuery(
		AssetListEntry assetListEntry, long[] segmentsEntryIds, String userId) {

		return getAssetEntryQuery(assetListEntry, segmentsEntryIds);
	}

}