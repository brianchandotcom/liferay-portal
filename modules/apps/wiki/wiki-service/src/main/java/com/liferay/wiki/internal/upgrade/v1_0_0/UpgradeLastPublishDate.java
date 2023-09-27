/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.wiki.constants.WikiPortletKeys;

/**
 * @author Levente Hudák
 */
public class UpgradeLastPublishDate
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeLastPublishDate {

	@Override
	protected void doUpgrade() throws Exception {
		_addLastPublishDateColumns();

		updateLastPublishDates(WikiPortletKeys.WIKI, "WikiNode");
		updateLastPublishDates(WikiPortletKeys.WIKI, "WikiPage");
	}

	private void _addLastPublishDateColumns() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("WikiNode");
			addLastPublishDateColumn("WikiPage");
		}
	}

}