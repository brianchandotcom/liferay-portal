/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.ShardLocalServiceUtil;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michael Young
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
public class ShardParameterAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object[] arguments = methodInvocation.getArguments();

		long companyId = (Long)arguments[0];

		Shard shard = ShardLocalServiceUtil.getShard(
			Company.class.getName(), companyId);

		String shardName = shard.getName();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Setting service to shard " + shardName + " for " +
					methodInvocation.toString());
		}

		Object returnValue = null;

		_shardAdvice.pushCompanyService(shardName);

		try {
			returnValue = methodInvocation.proceed();
		}
		finally {
			_shardAdvice.popCompanyService();
		}

		return returnValue;
	}

	public void setShardAdvice(ShardAdvice shardAdvice) {
		_shardAdvice = shardAdvice;
	}

	private static Log _log = LogFactoryUtil.getLog(ShardParameterAdvice.class);

	private ShardAdvice _shardAdvice;

}