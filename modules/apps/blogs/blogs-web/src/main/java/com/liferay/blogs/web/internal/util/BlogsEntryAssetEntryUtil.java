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

package com.liferay.blogs.web.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.constants.BlogsWebConstants;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class BlogsEntryAssetEntryUtil {

	public static AssetEntry getAssetEntry(
			HttpServletRequest httpServletRequest, BlogsEntry blogsEntry)
		throws PortalException {

		AssetEntry assetEntry = (AssetEntry)httpServletRequest.getAttribute(
			BlogsWebConstants.BLOGS_ENTRY_ASSET_ENTRY);

		if (assetEntry == null) {
			assetEntry = AssetEntryLocalServiceUtil.getEntry(
				BlogsEntry.class.getName(), blogsEntry.getEntryId());

			httpServletRequest.setAttribute(
				BlogsWebConstants.BLOGS_ENTRY_ASSET_ENTRY, assetEntry);
		}

		return assetEntry;
	}

}