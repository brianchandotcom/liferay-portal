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

package com.liferay.portal.dao.shard.advice;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Portlet;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Michael Young
 * @author Alexander Chow
 * @author Shuyang Zhou
 */
public class ShardPortletAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
		String methodName = method.getName();

		Object[] arguments = methodInvocation.getArguments();

		if (ArrayUtil.isEmpty(arguments)) {
			return methodInvocation.proceed();
		}

		Object argument = arguments[0];

		long companyId = -1;

		if (argument instanceof Long) {
			if (methodName.equals("checkPortlets") ||
				methodName.equals("clonePortlet") ||
				methodName.equals("getPortletById") ||
				methodName.equals("getPortletByStrutsPath") ||
				methodName.equals("getPortlets") ||
				methodName.equals("hasPortlet") ||
				methodName.equals("loadGetPortletsPool") ||
				methodName.equals("updatePortlet")) {

				companyId = (Long)argument;
			}
		}
		else if (argument instanceof Portlet) {
			if (methodName.equals("checkPortlet") ||
				methodName.equals("deployRemotePortlet") ||
				methodName.equals("destroyPortlet") ||
				methodName.equals("destroyRemotePortlet")) {

				Portlet portlet = (Portlet)argument;

				companyId = portlet.getCompanyId();
			}
		}

		if (companyId <= 0) {
			return methodInvocation.proceed();
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Setting company service to shard of companyId " + companyId +
					" for " + methodInvocation.toString());
		}

		Object returnValue = null;

		_shardAdvice.pushCompanyService(companyId);

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

	private static final Log _log = LogFactoryUtil.getLog(
		ShardPortletAdvice.class);

	private ShardAdvice _shardAdvice;

}