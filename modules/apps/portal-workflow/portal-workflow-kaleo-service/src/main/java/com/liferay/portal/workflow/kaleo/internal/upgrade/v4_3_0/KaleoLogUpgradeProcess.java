/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v4_3_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.workflow.kaleo.definition.LogType;

/**
 * @author Pedro Leite
 */
public class KaleoLogUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateKaleoLog(LogType.INSTANCE_END.name(), "WORKFLOW_INSTANCE_END");
		_updateKaleoLog(
			LogType.INSTANCE_START.name(), "WORKFLOW_INSTANCE_START");
	}

	private void _updateKaleoLog(String newType, String oldType)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update KaleoLog set type_ = '", newType, "' where type_ ='",
				oldType, "'"));
	}

}