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
public class ShardIterativelyAdvice implements MethodInterceptor {

	/**
	 * Invoke a join point across all shards while using the company service
	 * stack.
	 *
	 * @see ShardGloballyAdvice
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Iterating through all shards for " +
					methodInvocation.toString());
		}

		for (String shardName : ShardUtil.getAvailableShardNames()) {
			_shardAdvice.pushCompanyService(shardName);

			try {
				methodInvocation.proceed();
			}
			finally {
				_shardAdvice.popCompanyService();
			}
		}

		return null;
	}

	public void setShardAdvice(ShardAdvice shardAdvice) {
		_shardAdvice = shardAdvice;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ShardIterativelyAdvice.class);

	private ShardAdvice _shardAdvice;

}