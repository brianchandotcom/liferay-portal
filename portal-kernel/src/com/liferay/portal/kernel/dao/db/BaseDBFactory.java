/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.db;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseDBFactory implements DBFactory {

	@Override
	public DB create(int dbMajorVersion, int dbMinorVersion) {
		String majorVersion = StringUtil.toHexString(dbMajorVersion);
		String minorVersion = StringUtil.toHexString(dbMinorVersion);

		String version = StringBundler.concat(
			majorVersion, StringPool.POUND, minorVersion);

		DB db = _dbs.get(version);

		if (db == null) {
			db = doCreate(dbMajorVersion, dbMinorVersion);

			DB previousDB = _dbs.putIfAbsent(version, db);

			if (previousDB != null) {
				db = previousDB;
			}
		}

		return db;
	}

	protected abstract DB doCreate(int dbMajorVersion, int dbMinorVersion);

	private final ConcurrentMap<String, DB> _dbs = new ConcurrentHashMap<>();

}