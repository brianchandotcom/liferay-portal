/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Vendel Toreki
 * @author Luis Miguel Barcos
 */
public class UpgradeAssetCategory extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update AssetCategory set externalReferenceCode = ",
				"CAST_TEXT(categoryId) where externalReferenceCode is null or ",
				"externalReferenceCode =''"));
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.alterColumnType(
				"AssetCategory", "description", "TEXT null"),
			UpgradeProcessFactory.alterColumnType(
				"AssetCategory", "title", "TEXT null"),
			UpgradeProcessFactory.addColumns(
				"AssetCategory", "externalReferenceCode VARCHAR(75)")
		};
	}

}