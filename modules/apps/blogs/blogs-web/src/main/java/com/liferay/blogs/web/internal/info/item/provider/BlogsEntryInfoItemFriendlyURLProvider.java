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

package com.liferay.blogs.web.internal.info.item.provider;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryLocalizationComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.class.name=com.liferay.blogs.model.BlogsEntry",
	service = InfoItemFriendlyURLProvider.class
)
public class BlogsEntryInfoItemFriendlyURLProvider
	implements InfoItemFriendlyURLProvider<BlogsEntry> {

	@Override
	public String getFriendlyURL(BlogsEntry blogsEntry, String languageId) {
		return blogsEntry.getUrlTitle();
	}

	@Override
	public List<FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		BlogsEntry blogsEntry, String languageId) {

		return _friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
			blogsEntry.getGroupId(), _portal.getClassNameId(BlogsEntry.class),
			blogsEntry.getEntryId(),
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			_friendlyURLEntryLocalizationComparator);
	}

	private final FriendlyURLEntryLocalizationComparator
		_friendlyURLEntryLocalizationComparator =
			new FriendlyURLEntryLocalizationComparator();

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}