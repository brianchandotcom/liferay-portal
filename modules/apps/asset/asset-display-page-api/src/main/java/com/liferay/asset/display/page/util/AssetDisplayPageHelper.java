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

package com.liferay.asset.display.page.util;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jürgen Kappler
 */
public class AssetDisplayPageHelper {

	public static LayoutPageTemplateEntry
			getAssetDisplayPageLayoutPageTemplateEntry(
				long groupId, long classNameId, long classPK, long classTypeId)
		throws PortalException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				groupId, classNameId, classPK);

		if ((assetDisplayPageEntry == null) ||
			(assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_NONE)) {

			return null;
		}

		if (assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return LayoutPageTemplateEntryServiceUtil.
				fetchLayoutPageTemplateEntry(
					assetDisplayPageEntry.getLayoutPageTemplateEntryId());
		}

		return LayoutPageTemplateEntryServiceUtil.
			fetchDefaultLayoutPageTemplateEntry(
				groupId, classNameId, classTypeId);
	}

	public static boolean hasAssetDisplayPage(
			long groupId, AssetEntry assetEntry)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getAssetDisplayPageLayoutPageTemplateEntry(
				groupId, assetEntry.getClassNameId(), assetEntry.getClassPK(),
				assetEntry.getClassTypeId());

		if (layoutPageTemplateEntry != null) {
			return true;
		}

		return false;
	}

	public static boolean hasAssetDisplayPage(
			long groupId, long classNameId, long classPK, long classTypeId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getAssetDisplayPageLayoutPageTemplateEntry(
				groupId, classNameId, classPK, classTypeId);

		if (layoutPageTemplateEntry != null) {
			return true;
		}

		return false;
	}

}