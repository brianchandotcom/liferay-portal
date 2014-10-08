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

package com.liferay.portal.dao.shard;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 */
public class ShardDataSourceTargetSource implements TargetSource {

	public String[] getAvailableShardNames() {
		return _availableShardNames;
	}

	public DataSource getDataSource() {
		return _dataSource.get();
	}

	public Map<String, DataSource> getDataSources() {
		return _dataSources;
	}

	@Override
	public Object getTarget() throws Exception {
		return getDataSource();
	}

	@Override
	public Class<DataSource> getTargetClass() {
		return DataSource.class;
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public void releaseTarget(Object target) throws Exception {
	}

	public void resetDataSource() {
		DataSource dataSource = _dataSources.get(
			PropsValues.SHARD_DEFAULT_NAME);

		_dataSource.set(dataSource);
	}

	public void setDataSource(String shardName) {
		DataSource dataSource = _dataSources.get(shardName);

		_dataSource.set(dataSource);
	}

	public void setDataSources(Map<String, DataSource> dataSources) {
		_dataSources = dataSources;

		Set<String> shardNames = _dataSources.keySet();

		_availableShardNames = shardNames.toArray(
			new String[shardNames.size()]);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sharding configured with " + _availableShardNames.length +
					" data sources");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ShardDataSourceTargetSource.class);

	private static String[] _availableShardNames;

	private static final ThreadLocal<DataSource> _dataSource =
		new CentralizedThreadLocal<DataSource>(false) {

		@Override
		protected DataSource initialValue() {
			return _dataSources.get(PropsValues.SHARD_DEFAULT_NAME);
		}

	};

	private static Map<String, DataSource> _dataSources;

}