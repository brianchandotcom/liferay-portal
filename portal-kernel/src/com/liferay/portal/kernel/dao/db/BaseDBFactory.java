/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.dao.db;

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

		String version = majorVersion.concat(
			StringPool.POUND
		).concat(
			minorVersion
		);

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