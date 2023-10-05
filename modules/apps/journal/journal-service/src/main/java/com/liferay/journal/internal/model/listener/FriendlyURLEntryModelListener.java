/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.model.listener;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Díaz
 */
@Component(service = ModelListener.class)
public class FriendlyURLEntryModelListener
	extends BaseModelListener<FriendlyURLEntry> {

	@Override
	public void onBeforeCreate(FriendlyURLEntry newFriendlyURLEntry) {
		if (newFriendlyURLEntry.getClassNameId() != _portal.getClassNameId(
				JournalArticle.class)) {

			return;
		}

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				newFriendlyURLEntry.getGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				newFriendlyURLEntry.getClassPK());

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			if (newFriendlyURLEntry.getFriendlyURLEntryId() ==
					friendlyURLEntry.getFriendlyURLEntryId()) {

				continue;
			}

			_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
				friendlyURLEntry);
		}
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}