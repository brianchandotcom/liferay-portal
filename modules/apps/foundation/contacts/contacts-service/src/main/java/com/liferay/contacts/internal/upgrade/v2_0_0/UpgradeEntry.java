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

package com.liferay.contacts.internal.upgrade.v2_0_0;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.util.List;

/**
 * @author Jonathan Lee
 */
public class UpgradeEntry extends UpgradeProcess {

	public UpgradeEntry(
		EntryLocalService entryLocalService,
		UserLocalService userLocalService) {

		_entryLocalService = entryLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateEntries();
	}

	protected void updateEntries() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Entry> entries = _entryLocalService.getEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (Entry entry : entries) {
				try {
					_userLocalService.getUserByEmailAddress(
						entry.getCompanyId(), entry.getEmailAddress());

					_entryLocalService.deleteEntry(entry);
				}
				catch (Exception e) {
				}
			}
		}
	}

	private final EntryLocalService _entryLocalService;
	private final UserLocalService _userLocalService;

}