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

package com.liferay.portal.dao.shard.advice;

import com.liferay.portal.dao.shard.ShardDataSourceTargetSource;
import com.liferay.portal.dao.shard.ShardSessionFactoryTargetSource;
import com.liferay.portal.kernel.dao.shard.ShardUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michael Young
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
public class ShardGloballyAdvice implements MethodInterceptor {

	/**
	 * Invoke a join point across all shards while ignoring the company service
	 * stack.
	 *
	 * @see ShardIterativelyAdvice
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		_shardAdvice.setGlobalCall(new Object());

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"All shards invoked for " + methodInvocation.toString());
			}

			for (String shardName : ShardUtil.getAvailableShardNames()) {
				ShardDataSourceTargetSource dataSourceTargetSource =
					_shardAdvice.getShardDataSourceTargetSource();

				dataSourceTargetSource.setDataSource(shardName);

				ShardSessionFactoryTargetSource shardSessionFactoryTargetSource
					= _shardAdvice.getShardSessionFactoryTargetSource();

				shardSessionFactoryTargetSource.setSessionFactory(shardName);

				methodInvocation.proceed();
			}
		}
		finally {
			_shardAdvice.setGlobalCall(null);
		}

		return null;
	}

	public void setShardAdvice(ShardAdvice shardAdvice) {
		_shardAdvice = shardAdvice;
	}

	private static Log _log = LogFactoryUtil.getLog(ShardGloballyAdvice.class);

	private ShardAdvice _shardAdvice;

}