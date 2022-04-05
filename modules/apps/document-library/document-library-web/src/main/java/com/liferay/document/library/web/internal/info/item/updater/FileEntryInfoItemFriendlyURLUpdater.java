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

package com.liferay.document.library.web.internal.info.item.updater;

import com.liferay.friendly.url.info.item.updater.InfoItemFriendlyURLUpdater;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "item.class.name=com.liferay.portal.kernel.repository.model.FileEntry",
	service = InfoItemFriendlyURLUpdater.class
)
public class FileEntryInfoItemFriendlyURLUpdater
	implements InfoItemFriendlyURLUpdater<FileEntry> {

	@Override
	public void restoreFriendlyURL(
			long userId, long classPK, long friendlyURLEntryId,
			String languageId)
		throws PortalException {

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.getFriendlyURLEntry(
				friendlyURLEntryId);

		_friendlyURLEntryLocalService.setMainFriendlyURLEntry(friendlyURLEntry);
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}