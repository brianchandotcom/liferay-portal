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

package com.liferay.blogs.web.internal.info.item.updater;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.friendly.url.info.item.updater.InfoItemFriendlyURLUpdater;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.class.name=com.liferay.blogs.model.BlogsEntry",
	service = InfoItemFriendlyURLUpdater.class
)
public class BlogsEntryInfoItemFriendlyURLUpdater
	implements InfoItemFriendlyURLUpdater<BlogsEntry> {

	@Override
	public void restoreFriendlyURL(
			long userId, long classPK, long friendlyURLEntryId,
			String languageId)
		throws PortalException {

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getFriendlyURLEntry(
				friendlyURLEntryId);

		_friendlyURLEntryLocalService.setMainFriendlyURLEntry(friendlyURLEntry);

		BlogsEntry entry = _blogsEntryLocalService.getEntry(classPK);

		entry.setUrlTitle(friendlyURLEntry.getUrlTitle());

		_blogsEntryLocalService.updateBlogsEntry(entry);
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}