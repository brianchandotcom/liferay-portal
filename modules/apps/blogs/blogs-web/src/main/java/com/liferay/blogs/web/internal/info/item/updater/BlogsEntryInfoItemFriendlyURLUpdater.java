/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		BlogsEntry blogsEntry = _blogsEntryLocalService.getEntry(classPK);

		blogsEntry.setUrlTitle(friendlyURLEntry.getUrlTitle());

		_blogsEntryLocalService.updateBlogsEntry(blogsEntry);
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}