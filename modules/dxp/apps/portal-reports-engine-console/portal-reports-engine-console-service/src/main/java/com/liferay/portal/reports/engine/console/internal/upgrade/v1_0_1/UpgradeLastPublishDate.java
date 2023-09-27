/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1;

import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;

/**
 * @author Marcellus Tavares
 */
public class UpgradeLastPublishDate
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeLastPublishDate {

	@Override
	protected void doUpgrade() throws Exception {
		addLastPublishDateColumn("Reports_Definition");

		updateLastPublishDates(
			ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
			"Reports_Definition");

		addLastPublishDateColumn("Reports_Source");

		updateLastPublishDates(
			ReportsEngineConsolePortletKeys.REPORTS_ADMIN, "Reports_Source");
	}

}