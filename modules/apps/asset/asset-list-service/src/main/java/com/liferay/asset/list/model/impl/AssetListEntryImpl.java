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

package com.liferay.asset.list.model.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalServiceUtil;
import com.liferay.asset.util.AssetHelper;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pavel Savinov
 */
public class AssetListEntryImpl extends AssetListEntryBaseImpl {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 *             long)}
	 */
	@Deprecated
	@Override
	public List<AssetEntry> getAssetEntries(long segmentsEntryId) {
		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntries(
			this, segmentsEntryId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 *             long, int, int)}
	 */
	@Deprecated
	public List<AssetEntry> getAssetEntries(
		long segmentsEntryId, int start, int end) {

		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntries(
			this, segmentsEntryId, start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 *             long[])}
	 */
	@Deprecated
	public List<AssetEntry> getAssetEntries(long[] segmentsEntryIds) {
		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntries(
			this, segmentsEntryIds);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntries(AssetListEntry,
	 *             long[], int, int)}
	 */
	@Deprecated
	public List<AssetEntry> getAssetEntries(
		long[] segmentsEntryIds, int start, int end) {

		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntries(
			this, segmentsEntryIds, start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntriesCount(
	 *             AssetListEntry, long)}
	 */
	@Deprecated
	public int getAssetEntriesCount(long segmentsEntryId) {
		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntriesCount(
			this, segmentsEntryId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntriesCount(
	 *             AssetListEntry, long[])}
	 */
	@Deprecated
	public int getAssetEntriesCount(long[] segmentsEntryIds) {
		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntriesCount(
			this, segmentsEntryIds);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntryQuery(
	 *             AssetListEntry, long)}
	 */
	@Deprecated
	public AssetEntryQuery getAssetEntryQuery(long segmentsEntryId) {
		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntryQuery(
			this, segmentsEntryId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             AssetListAssetEntryProvider#getAssetEntryQuery(
	 *             AssetListEntry, long[])}
	 */
	@Deprecated
	public AssetEntryQuery getAssetEntryQuery(long[] segmentsEntryIds) {
		AssetListAssetEntryProvider assetListAssetEntryProvider =
			_getAssetListAssetEntryProvider();

		return assetListAssetEntryProvider.getAssetEntryQuery(
			this, segmentsEntryIds);
	}

	@Override
	public String getTypeLabel() {
		return AssetListEntryTypeConstants.getTypeLabel(getType());
	}

	@Override
	public String getTypeSettings(long segmentsEntryId) {
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			AssetListEntrySegmentsEntryRelLocalServiceUtil.
				fetchAssetListEntrySegmentsEntryRel(
					getAssetListEntryId(), segmentsEntryId);

		if (assetListEntrySegmentsEntryRel != null) {
			return assetListEntrySegmentsEntryRel.getTypeSettings();
		}

		return null;
	}

	private AssetListAssetEntryProvider _getAssetListAssetEntryProvider() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<AssetListAssetEntryProvider, AssetListAssetEntryProvider>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetHelper.class);

		ServiceTracker<AssetListAssetEntryProvider, AssetListAssetEntryProvider>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), AssetListAssetEntryProvider.class,
				null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}