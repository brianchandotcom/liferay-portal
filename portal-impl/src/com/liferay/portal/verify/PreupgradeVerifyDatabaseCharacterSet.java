/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;

/**
 * @author Jorge Avalos
 */
public class PreupgradeVerifyDatabaseCharacterSet
	extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (!db.isSupportsCharacterSet(connection)) {
			throw new VerifyException(
				"Unsupported database character set: " +
					db.getCharacterSet(connection));
		}
	}

	@Override
	protected boolean isSkipDBPartitions() {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.MYSQL) {
			return true;
		}

		return false;
	}

}