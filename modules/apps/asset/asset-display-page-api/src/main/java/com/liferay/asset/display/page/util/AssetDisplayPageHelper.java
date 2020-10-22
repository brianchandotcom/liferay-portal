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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author     Jürgen Kappler
 * @deprecated As of Athanasius (7.3.x), replaced by {@link
 *             AssetDisplayPageUtil}
 */
@Deprecated
public class AssetDisplayPageHelper {

	public static LayoutPageTemplateEntry
			getAssetDisplayPageLayoutPageTemplateEntry(
				long groupId, long classNameId, long classPK, long classTypeId)
		throws PortalException {

		return AssetDisplayPageUtil.getAssetDisplayPageLayoutPageTemplateEntry(
			groupId, classNameId, classPK, classTypeId);
	}

	public static boolean hasAssetDisplayPage(
			long groupId, AssetEntry assetEntry)
		throws PortalException {

		return AssetDisplayPageUtil.hasAssetDisplayPage(groupId, assetEntry);
	}

	public static boolean hasAssetDisplayPage(
			long groupId, long classNameId, long classPK, long classTypeId)
		throws PortalException {

		return AssetDisplayPageUtil.hasAssetDisplayPage(
			groupId, classNameId, classPK, classTypeId);
	}

}