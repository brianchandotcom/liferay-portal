/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v3_9_0;

import com.liferay.object.constants.ObjectLayoutBoxConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Selton Guedes
 */
public class ObjectLayoutBoxUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("ObjectLayoutBox", "type_")) {
			return;
		}

		alterTableAddColumn("ObjectLayoutBox", "type_", "VARCHAR(75) null");

		runSQL(
			"update ObjectLayoutBox set type_ = '" +
				ObjectLayoutBoxConstants.TYPE_REGULAR + "'");
	}

}