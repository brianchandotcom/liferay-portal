/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.security.permission;

import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalFeed",
	service = PermissionUpdateHandler.class
)
public class JournalFeedPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		JournalFeed feed = _journalFeedLocalService.fetchJournalFeed(
			GetterUtil.getLong(primKey));

		if (feed == null) {
			return;
		}

		feed.setModifiedDate(new Date());

		_journalFeedLocalService.updateJournalFeed(feed);
	}

	@Reference
	private JournalFeedLocalService _journalFeedLocalService;

}