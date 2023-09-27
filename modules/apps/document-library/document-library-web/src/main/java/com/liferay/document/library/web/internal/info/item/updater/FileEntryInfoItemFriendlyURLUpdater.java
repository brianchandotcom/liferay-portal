/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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