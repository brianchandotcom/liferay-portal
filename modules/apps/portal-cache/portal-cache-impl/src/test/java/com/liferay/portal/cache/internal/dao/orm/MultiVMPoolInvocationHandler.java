/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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