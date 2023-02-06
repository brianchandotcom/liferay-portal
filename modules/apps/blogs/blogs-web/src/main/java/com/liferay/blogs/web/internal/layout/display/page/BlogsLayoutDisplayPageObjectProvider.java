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

package com.liferay.blogs.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class BlogsLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<BlogsEntry> {

	public BlogsLayoutDisplayPageObjectProvider(
			AssetHelper assetHelper, BlogsEntry blogsEntry,
			InfoItemFriendlyURLProvider<BlogsEntry> infoItemFriendlyURLProvider,
			Language language)
		throws PortalException {

		_assetHelper = assetHelper;
		_blogsEntry = blogsEntry;
		_infoItemFriendlyURLProvider = infoItemFriendlyURLProvider;
		_language = language;
	}

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(BlogsEntry.class.getName());
	}

	@Override
	public long getClassPK() {
		return _blogsEntry.getEntryId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _blogsEntry.getDescription();
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
		return _assetHelper.getAssetKeywords(
			BlogsEntry.class.getName(), _blogsEntry.getEntryId(), locale);
	}

	@Override
	public String getTitle(Locale locale) {
		return _blogsEntry.getTitle();
	}

	@Override
	public String getURLTitle(Locale locale) {
		return _infoItemFriendlyURLProvider.getFriendlyURL(
			_blogsEntry, _language.getLanguageId(locale));
	}

	private final AssetHelper _assetHelper;
	private final BlogsEntry _blogsEntry;
	private final InfoItemFriendlyURLProvider<BlogsEntry>
		_infoItemFriendlyURLProvider;
	private final Language _language;

}