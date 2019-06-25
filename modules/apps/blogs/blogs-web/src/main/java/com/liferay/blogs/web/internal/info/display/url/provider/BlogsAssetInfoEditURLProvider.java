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

package com.liferay.blogs.web.internal.info.display.url.provider;

import com.liferay.asset.info.display.url.provider.AssetInfoEditURLProvider;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.display.url.provider.InfoEditURLProvider;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = InfoEditURLProvider.class
)
public class BlogsAssetInfoEditURLProvider
	implements InfoEditURLProvider<BlogsEntry> {

	@Override
	public String getURL(
			BlogsEntry blogsEntry, HttpServletRequest httpServletRequest)
		throws Exception {

		return _assetInfoEditURLProvider.getURL(
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			httpServletRequest);
	}

	@Reference
	private AssetInfoEditURLProvider _assetInfoEditURLProvider;

}