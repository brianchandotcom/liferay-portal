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

package com.liferay.blogs.web.internal.info.display.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class BlogsInfoDisplayObjectProvider
	implements InfoDisplayObjectProvider<BlogsEntry> {

	public BlogsInfoDisplayObjectProvider(BlogsEntry blogsEntry)
		throws PortalException {

		_blogsEntry = blogsEntry;

		_assetEntry = _getAssetEntry(blogsEntry);
	}

	@Override
	public long getClassNameId() {
		return _assetEntry.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _blogsEntry.getEntryId();
	}

	@Override
	public long getClassTypeId() {
		return _assetEntry.getClassTypeId();
	}

	@Override
	public String getDescription(Locale locale) {
		return _assetEntry.getDescription(locale);
	}

	@Override
	public BlogsEntry getDisplayObject() {
		return _blogsEntry;
	}

	@Override
	public long getGroupId() {
		return _blogsEntry.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			_assetEntry.getClassName(), _assetEntry.getClassPK());
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				_assetEntry.getClassName(), _assetEntry.getClassPK());

		String[] keywords =
			new String[assetTagNames.length + assetCategoryNames.length];

		ArrayUtil.combine(assetTagNames, assetCategoryNames, keywords);

		return StringUtil.merge(keywords);
	}

	@Override
	public String getTitle(Locale locale) {
		return _assetEntry.getTitle(locale);
	}

	@Override
	public String getURLTitle(Locale locale) {
		AssetRenderer assetRenderer = _assetEntry.getAssetRenderer();

		return assetRenderer.getUrlTitle(locale);
	}

	private AssetEntry _getAssetEntry(BlogsEntry blogsEntry)
		throws PortalException {

		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		return assetRendererFactory.getAssetEntry(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());
	}

	private final AssetEntry _assetEntry;
	private final BlogsEntry _blogsEntry;

}