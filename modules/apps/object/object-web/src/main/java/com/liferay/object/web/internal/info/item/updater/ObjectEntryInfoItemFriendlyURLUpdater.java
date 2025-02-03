/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.updater;

import com.liferay.friendly.url.info.item.updater.InfoItemFriendlyURLUpdater;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryInfoItemFriendlyURLUpdater
	implements InfoItemFriendlyURLUpdater<ObjectEntry> {

	public ObjectEntryInfoItemFriendlyURLUpdater(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

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

	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}