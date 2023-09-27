/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_5_0;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Guilherme Camacho
 */
public class ObjectDefinitionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("ObjectDefinition", "storageType")) {
			alterTableAddColumn(
				"ObjectDefinition", "storageType", "VARCHAR(75)");

			runSQL(
				"update ObjectDefinition set storageType = '" +
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + "'");
		}
	}

}