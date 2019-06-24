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
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalServiceUtil;
import com.liferay.asset.list.util.AssetListHelper;
import com.liferay.asset.util.AssetHelper;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Pavel Savinov
 */
@ProviderType
public class AssetListEntryImpl extends AssetListEntryBaseImpl {

	@Override
	public List<AssetEntry> getAssetEntries(long segmentsEntryId) {
		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntries(this, segmentsEntryId);
	}

	public List<AssetEntry> getAssetEntries(
		long segmentsEntryId, int start, int end) {

		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntries(
			this, segmentsEntryId, start, end);
	}

	public List<AssetEntry> getAssetEntries(long[] segmentsEntryIds) {
		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntries(this, segmentsEntryIds);
	}

	public List<AssetEntry> getAssetEntries(
		long[] segmentsEntryIds, int start, int end) {

		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntries(
			this, segmentsEntryIds, start, end);
	}

	public int getAssetEntriesCount(long segmentsEntryId) {
		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntriesCount(this, segmentsEntryId);
	}

	public int getAssetEntriesCount(long[] segmentsEntryIds) {
		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntriesCount(this, segmentsEntryIds);
	}

	public AssetEntryQuery getAssetEntryQuery(long segmentsEntryId) {
		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntryQuery(this, segmentsEntryId);
	}

	public AssetEntryQuery getAssetEntryQuery(long[] segmentsEntryIds) {
		AssetListHelper assetListHelper = _getAssetListHelper();

		return assetListHelper.getAssetEntryQuery(this, segmentsEntryIds);
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

	private AssetListHelper _getAssetListHelper() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker<AssetListHelper, AssetListHelper>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetHelper.class);

		ServiceTracker<AssetListHelper, AssetListHelper> serviceTracker =
			new ServiceTracker<>(
				bundle.getBundleContext(), AssetListHelper.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}