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
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Kyle Miho
 */
public class AssetListTestUtil {

	public static AssetEntry addAssetEntry(long groupId)
		throws PortalException {

		long assetEntryId = CounterLocalServiceUtil.increment();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.createAssetEntry(
			assetEntryId);

		assetEntry.setClassName(JournalArticle.class.getName());
		assetEntry.setGroupId(groupId);
		assetEntry.setClassPK(RandomTestUtil.randomLong());
		assetEntry.setVisible(true);
		assetEntry.setTitle(RandomTestUtil.randomString());

		AssetEntryLocalServiceUtil.addAssetEntry(assetEntry);

		return assetEntry;
	}

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
			long groupId, AssetEntry assetEntry, AssetListEntry assetListEntry)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return AssetListEntryAssetEntryRelLocalServiceUtil.
			addAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), assetEntry.getEntryId(),
				serviceContext);
	}

	public static AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel(
			long groupId, AssetEntry assetEntry, AssetListEntry assetListEntry,
			int position)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return AssetListEntryAssetEntryRelLocalServiceUtil.
			addAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), assetEntry.getEntryId(),
				position, serviceContext);
	}

}