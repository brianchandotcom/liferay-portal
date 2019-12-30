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

package com.liferay.microblogs.internal.upgrade.v1_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Matthew Kong
 */
public class UpgradeMicroblogsEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removeReceiverUserId();
		renameReceiverMicroblogsEntryId();
	}

	protected void removeReceiverUserId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("MicroblogsEntry", "receiverUserId")) {
				return;
			}

			runSQL("drop index IX_7ABB0AB3 on MicroblogsEntry");

			runSQL("alter table MicroblogsEntry drop column receiverUserId");
		}
	}

	protected void renameReceiverMicroblogsEntryId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("MicroblogsEntry", "receiverMicroblogsEntryId")) {
				return;
			}

			runSQL(
				"alter table MicroblogsEntry add parentMicroblogsEntryId LONG");

			runSQL(
				"update MicroblogsEntry set parentMicroblogsEntryId = " +
					"receiverMicroblogsEntryId");

			runSQL("drop index IX_36CA3D37 on MicroblogsEntry");

			runSQL(
				"alter table MicroblogsEntry drop column " +
					"receiverMicroblogsEntryId");

			runSQL(
				"create index IX_6BD29B9C on MicroblogsEntry (type_, " +
					"parentMicroblogsEntryId)");
		}
	}

}