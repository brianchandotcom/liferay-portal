/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.upgrade.v1_1_0;

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pedro Queiroz
 */
public class DDLRecordUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"update DDLRecord set recordSetVersion = '" +
				DDLRecordSetConstants.VERSION_DEFAULT + "'");
	}

}