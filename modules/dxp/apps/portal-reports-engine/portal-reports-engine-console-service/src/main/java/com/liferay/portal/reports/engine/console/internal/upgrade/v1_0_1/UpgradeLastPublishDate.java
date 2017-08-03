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