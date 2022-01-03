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

import com.liferay.microblogs.internal.upgrade.v1_0_2.util.MicroblogsEntryTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Matthew Kong
 */
public class MicroblogsEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_removeReceiverUserId();
		_renameReceiverMicroblogsEntryId();
	}

	private void _removeReceiverUserId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("MicroblogsEntry", "receiverUserId")) {
				return;
			}

			alter(
				MicroblogsEntryTable.class,
				new AlterTableDropColumn("receiverUserId"));
		}
	}

	private void _renameReceiverMicroblogsEntryId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("MicroblogsEntry", "receiverMicroblogsEntryId")) {
				return;
			}

			alter(
				MicroblogsEntryTable.class,
				new AlterTableAddColumn("parentMicroblogsEntryId", "LONG"));

			runSQL(
				"update MicroblogsEntry set parentMicroblogsEntryId = " +
					"receiverMicroblogsEntryId");

			alter(
				MicroblogsEntryTable.class,
				new AlterTableDropColumn("receiverMicroblogsEntryId"));
		}
	}

}