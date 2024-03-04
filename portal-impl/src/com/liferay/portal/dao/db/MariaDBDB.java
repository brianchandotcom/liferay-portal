/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DBType;

/**
 * @author Preston Crary
 */
public class MariaDBDB extends MySQLDB {

	public MariaDBDB(int majorVersion, int minorVersion) {
		super(DBType.MARIADB, majorVersion, minorVersion);
	}

	@Override
	public boolean isSupportsDBPartition() {
		return false;
	}

}