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

package com.liferay.portal.cache.thread.local;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class ThreadLocalCacheAdvice extends ChainableMethodAdvice {

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		return annotations.get(ThreadLocalCachable.class);
	}

	@Override
	public Object invoke(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments)
		throws Throwable {

		ThreadLocalCachable threadLocalCachable =
			serviceBeanMethodInvocation.getAdviceMethodContext();

		ThreadLocalCache<Object> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				threadLocalCachable.scope(),
				serviceBeanMethodInvocation.getMethod());

		String cacheKey = _getCacheKey(arguments);

		Object value = threadLocalCache.get(cacheKey);

		if (value != null) {
			if (value == nullResult) {
				return null;
			}

			return value;
		}

		Object result = serviceBeanMethodInvocation.proceed(arguments);

		if (result == null) {
			threadLocalCache.put(cacheKey, nullResult);
		}
		else {
			threadLocalCache.put(cacheKey, result);
		}

		return result;
	}

	private String _getCacheKey(Object[] arguments) {
		if (arguments.length == 1) {
			return StringUtil.toHexString(arguments[0]);
		}

		StringBundler sb = new StringBundler(arguments.length * 2 - 1);

		for (int i = 0; i < arguments.length; i++) {
			sb.append(StringUtil.toHexString(arguments[i]));

			if ((i + 1) < arguments.length) {
				sb.append(StringPool.POUND);
			}
		}

		return sb.toString();
	}

}