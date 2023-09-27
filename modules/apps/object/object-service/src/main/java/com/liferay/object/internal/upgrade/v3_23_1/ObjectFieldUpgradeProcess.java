/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_23_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author José Ángel Jiménez
 */
public class ObjectFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update ObjectField set indexed = [$TRUE$], indexedAsKeyWord = " +
				"[$TRUE$] where indexed = [$FALSE$] and name = 'id' and " +
					"system_ = [$TRUE$]");
	}

}