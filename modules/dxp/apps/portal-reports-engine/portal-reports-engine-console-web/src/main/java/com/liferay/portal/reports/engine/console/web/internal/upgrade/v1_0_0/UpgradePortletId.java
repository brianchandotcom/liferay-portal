/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;

/**
 * @author Prathima Shreenath
 */
public class UpgradePortletId extends BaseUpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"1_WAR_reportsportlet",
				ReportsEngineConsolePortletKeys.REPORTS_ADMIN
			},
			new String[] {
				"2_WAR_reportsportlet",
				ReportsEngineConsolePortletKeys.DISPLAY_REPORTS
			}
		};
	}

}