/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.internal.upgrade.v2_0_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Adam Brandizzi
 * @author Ryan Park
 */
public class AppUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("delete from Marketplace_App where appId is not null");
		runSQL("delete from Marketplace_Module where moduleId is not null");
	}

}