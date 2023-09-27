/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_21_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update ObjectDefinition set enableCategorization = [$TRUE$], ",
				"enableComments = [$FALSE$] where storageType = 'default' and ",
				"system_ = [$FALSE$]"));

		runSQL(
			StringBundler.concat(
				"update ObjectDefinition set enableCategorization = ",
				"[$FALSE$], enableComments = [$FALSE$] where storageType != ",
				"'default' or system_ = [$TRUE$]"));
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"ObjectDefinition", "enableCategorization BOOLEAN",
				"enableComments BOOLEAN")
		};
	}

}