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

package com.liferay.journal.service.permission;

import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalFeed"},
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

	@Reference(unbind = "-")
	protected void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	private volatile JournalFeedLocalService _journalFeedLocalService;

}