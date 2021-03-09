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
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.constants.BlogsWebConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = BlogsEntryAssetEntryUtil.class)
public class BlogsEntryAssetEntryUtil {

	public static AssetEntry getAssetEntry(
			HttpServletRequest httpServletRequest, BlogsEntry blogsEntry)
		throws PortalException {

		String key =
			BlogsWebConstants.BLOGS_ENTRY_ASSET_ENTRY + StringPool.UNDERLINE +
				blogsEntry.getEntryId();

		AssetEntry assetEntry = (AssetEntry)httpServletRequest.getAttribute(
			key);

		if (assetEntry == null) {
			assetEntry = _assetEntryLocalService.getEntry(
				BlogsEntry.class.getName(), blogsEntry.getEntryId());

			httpServletRequest.setAttribute(key, assetEntry);
		}

		return assetEntry;
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	private static AssetEntryLocalService _assetEntryLocalService;

}