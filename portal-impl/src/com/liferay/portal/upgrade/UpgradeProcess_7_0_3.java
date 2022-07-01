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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeModulesFactory;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v7_0_3.UpgradeGroup;
import com.liferay.portal.upgrade.v7_0_3.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v7_0_3.UpgradeOracle;
import com.liferay.portal.upgrade.v7_0_3.UpgradeOrganization;
import com.liferay.portal.upgrade.v7_0_3.UpgradeSQLServer;
import com.liferay.portal.upgrade.v7_0_3.UpgradeSybase;

/**
 * @author Adolfo Pérez
 */
public class UpgradeProcess_7_0_3 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_7_0_3_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(new UpgradeGroup());
		upgrade(new UpgradeMessageBoards());
		upgrade(
			UpgradeModulesFactory.create(
				_BUNDLE_SYMBOLIC_NAMES, _CONVERTED_LEGACY_MODULES));
		upgrade(new UpgradeOrganization());
		upgrade(new UpgradeOracle());
		upgrade(new UpgradeSQLServer());
		upgrade(new UpgradeSybase());

		clearIndexesCache();
	}

	private static final String[] _BUNDLE_SYMBOLIC_NAMES = {
		"com.liferay.portal.reports.engine.console.web",
		"com.liferay.portal.workflow.kaleo.forms.web"
	};

	private static final String[][] _CONVERTED_LEGACY_MODULES = {
		{
			"reports-portlet",
			"com.liferay.portal.reports.engine.console.service", "Reports"
		}
	};

}