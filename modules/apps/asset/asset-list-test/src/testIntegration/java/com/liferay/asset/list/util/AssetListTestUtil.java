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

package com.liferay.asset.list.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Kyle Miho
 */
public class AssetListTestUtil {

	public static AssetListEntry addAssetListEntry(long groupId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return AssetListEntryLocalServiceUtil.addAssetListEntry(
			TestPropsValues.getUserId(), groupId, RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_MANUAL, serviceContext);
	}

	public static AssetListEntry addAssetListEntry(long groupId, String title)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return AssetListEntryLocalServiceUtil.addAssetListEntry(
			TestPropsValues.getUserId(), groupId, title,
			AssetListEntryTypeConstants.TYPE_MANUAL, serviceContext);
	}

	public static AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel(
			long groupId, AssetEntry assetEntry, AssetListEntry assetListEntry,
			long segmentsEntryId)
		throws PortalException {

		return AssetListEntryAssetEntryRelLocalServiceUtil.
			addAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), assetEntry.getEntryId(),
				segmentsEntryId,
				ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel(
			long groupId, AssetEntry assetEntry, AssetListEntry assetListEntry,
			long segmentsEntryId, int position)
		throws PortalException {

		return AssetListEntryAssetEntryRelLocalServiceUtil.
			addAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), assetEntry.getEntryId(),
				segmentsEntryId, position,
				ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static AssetListEntrySegmentsEntryRel
			addAssetListEntrySegmentsEntryRel(
				long groupId, AssetListEntry assetListEntry)
		throws PortalException {

		return addAssetListEntrySegmentsEntryRel(
			groupId, assetListEntry, RandomTestUtil.nextLong());
	}

	public static AssetListEntrySegmentsEntryRel
			addAssetListEntrySegmentsEntryRel(
				long groupId, AssetListEntry assetListEntry,
				long segmentsEntryId)
		throws PortalException {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return AssetListEntrySegmentsEntryRelLocalServiceUtil.
			addAssetListEntrySegmentsEntryRel(
				TestPropsValues.getUserId(), groupId,
				assetListEntry.getAssetListEntryId(), segmentsEntryId,
				typeSettingsProperties.toString(), serviceContext);
	}

}