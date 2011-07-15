/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.shard;

import com.liferay.portal.kernel.util.StringPool;

import javax.sql.DataSource;

/**
 * @author Alexander Chow
 */
public class ShardUtil {

	public static String[] getAvailableShardNames() {
		if (_shard != null) {
			String[] availableShardNames = _shard.getAvailableShardNames();

			if (availableShardNames != null) {
				return availableShardNames;
			}
		}

		return _DEFAULT_SHARD_ARRAY;
	}

	public static String getCurrentShardName() {
		if (_shard != null) {
			return _shard.getCurrentShardName();
		}

		return StringPool.BLANK;
	}

	public static DataSource getDataSource() {
		if (_shard != null) {
			return _shard.getDataSource();
		}

		return null;
	}

	public static String getDefaultShardName() {
		if (_shard != null) {
			return _shard.getDefaultShardName();
		}

		return null;
	}

	public static boolean isEnabled() {
		if (_shard != null) {
			return _shard.isEnabled();
		}

		return false;
	}

	public static String popCompanyService() {
		String value = null;

		if (_shard != null) {
			value = _shard.popCompanyService();
		}

		return value;
	}

	public static void pushCompanyService(long companyId) {
		if (_shard != null) {
			_shard.pushCompanyService(companyId);
		}
	}

	public static void pushCompanyService(String shardName) {
		if (_shard != null) {
			_shard.pushCompanyService(shardName);
		}
	}

	public void setShard(Shard shard) {
		_shard = shard;
	}

	private static final String[] _DEFAULT_SHARD_ARRAY = new String[0];

	private static Shard _shard;

}