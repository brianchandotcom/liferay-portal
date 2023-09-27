/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.internal.upgrade.v1_0_2;

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
			alterTableDropColumn("MicroblogsEntry", "receiverUserId");
		}
	}

	private void _renameReceiverMicroblogsEntryId() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("MicroblogsEntry", "receiverMicroblogsEntryId")) {
				return;
			}

			alterTableAddColumn(
				"MicroblogsEntry", "parentMicroblogsEntryId", "LONG");

			runSQL(
				"update MicroblogsEntry set parentMicroblogsEntryId = " +
					"receiverMicroblogsEntryId");

			alterTableDropColumn(
				"MicroblogsEntry", "receiverMicroblogsEntryId");
		}
	}

}