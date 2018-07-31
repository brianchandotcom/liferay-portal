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

package com.liferay.portal.cache.internal.dao.orm;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Tina Tian
 * @author Preston Crary
 */
public class MultiVMPoolInvocationHandler implements InvocationHandler {

	public MultiVMPoolInvocationHandler(
		ClassLoader classLoader, boolean serialized) {

		_classLoader = classLoader;
		_serialized = serialized;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		String methodName = method.getName();

		if (methodName.equals("getPortalCache") && (args != null) &&
			(args.length > 0) && (args[0] instanceof String)) {

			return ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {PortalCache.class},
				new PortalCacheInvocationHandler((String)args[0], _serialized));
		}

		if (methodName.equals("getPortalCacheManager")) {
			return ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {PortalCacheManager.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						String methodName = method.getName();

						if (methodName.equals(
								"registerPortalCacheManagerListener")) {

							return true;
						}

						return null;
					}

				});
		}

		return null;
	}

	private final ClassLoader _classLoader;
	private final boolean _serialized;

}