/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v0_0_5;

import com.liferay.journal.constants.JournalPortletKeys;

/**
 * @author Máté Thurzó
 */
public class UpgradeLastPublishDate
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeLastPublishDate {

	@Override
	protected void doUpgrade() throws Exception {
		updateLastPublishDates(JournalPortletKeys.JOURNAL, "JournalArticle");

		updateLastPublishDates(JournalPortletKeys.JOURNAL, "JournalFeed");

		updateLastPublishDates(JournalPortletKeys.JOURNAL, "JournalFolder");
	}

}