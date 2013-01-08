/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.dao.shard.advice.ShardAdvice;
import com.liferay.portal.kernel.dao.shard.Shard;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.util.PropsValues;

import javax.sql.DataSource;

/**
 * @author Alexander Chow
 */
public class ShardImpl implements Shard {

	public String[] getAvailableShardNames() {
		ShardDataSourceTargetSource shardDataSourceTargetSource =
			(ShardDataSourceTargetSource)
				InfrastructureUtil.getShardDataSourceTargetSource();

		if (shardDataSourceTargetSource != null) {
			return shardDataSourceTargetSource.getAvailableShardNames();
		}

		return null;
	}

	public String getCurrentShardName() {
		return _shardAdvice.getCurrentShardName();
	}

	public DataSource getDataSource() {
		return _shardAdvice.getDataSource();
	}

	public String getDefaultShardName() {
		return PropsValues.SHARD_DEFAULT_NAME;
	}

	public boolean isEnabled() {
		if (_shardAdvice != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public String popCompanyService() {
		String value = null;

		if (_shardAdvice != null) {
			value = _shardAdvice.popCompanyService();
		}

		return value;
	}

	public void pushCompanyService(long companyId) {
		if (_shardAdvice != null) {
			_shardAdvice.pushCompanyService(companyId);
		}
	}

	public void pushCompanyService(String shardName) {
		if (_shardAdvice != null) {
			_shardAdvice.pushCompanyService(shardName);
		}
	}

	public void setShardAdvice(ShardAdvice shardAdvice) {
		_shardAdvice = shardAdvice;
	}

	public String setTargetSource(String shardName) {
		if (_shardAdvice == null) {
			return null;
		}

		String currentShardName = getCurrentShardName();

		ShardDataSourceTargetSource shardDataSourceTargetSource =
			_shardAdvice.getShardDataSourceTargetSource();

		shardDataSourceTargetSource.setDataSource(shardName);

		ShardSessionFactoryTargetSource shardSessionFactoryTargetSource =
			_shardAdvice.getShardSessionFactoryTargetSource();

		shardSessionFactoryTargetSource.setSessionFactory(shardName);

		return currentShardName;
	}

	private static ShardAdvice _shardAdvice;

}