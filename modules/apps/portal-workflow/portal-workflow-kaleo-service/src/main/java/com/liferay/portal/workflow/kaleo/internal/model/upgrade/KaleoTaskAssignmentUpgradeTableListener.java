/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.model.upgrade;

import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class KaleoTaskAssignmentUpgradeTableListener
	extends BaseKaleoUpgradeTableListener {

	@Override
	public void onAfterUpdateTable(
			ServiceComponent previousServiceComponent,
			UpgradeTable upgradeTable)
		throws Exception {

		if (_keyValueMap == null) {
			return;
		}

		Map<Long, Long> keyValueMap = _keyValueMap;

		_keyValueMap = null;

		updateKeyValueMap(
			keyValueMap, "com.liferay.portal.workflow.kaleo.model.KaleoTask",
			"KaleoTaskAssignment", "kaleoTaskAssignmentId");
	}

	@Override
	public void onBeforeUpdateTable(
			ServiceComponent previousServiceComponent,
			UpgradeTable upgradeTable)
		throws Exception {

		if (!isFixAutoUpgrade(previousServiceComponent)) {
			return;
		}

		_keyValueMap = getKeyValueMap(
			"KaleoTaskAssignment", "kaleoTaskAssignmentId", "kaleoTaskId");
	}

	private Map<Long, Long> _keyValueMap;

}