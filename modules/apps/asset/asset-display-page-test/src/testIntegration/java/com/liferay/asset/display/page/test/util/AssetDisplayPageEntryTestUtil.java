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

package com.liferay.asset.display.page.test.util;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.TestPropsValues;

/**
 * @author Jürgen Kappler
 */
public class AssetDisplayPageEntryTestUtil {

	public static AssetDisplayPageEntry addAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId, int type)
		throws PortalException {

		return AssetDisplayPageEntryLocalServiceUtil.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), groupId, classNameId, classPK,
			layoutPageTemplateEntryId, type, new ServiceContext());
	}

	public static AssetDisplayPageEntry addDefaultAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId)
		throws PortalException {

		return AssetDisplayPageEntryLocalServiceUtil.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), groupId, classNameId, classPK,
			layoutPageTemplateEntryId, new ServiceContext());
	}

}