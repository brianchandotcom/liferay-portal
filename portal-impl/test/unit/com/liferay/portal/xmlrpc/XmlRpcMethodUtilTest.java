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

package com.liferay.portal.xmlrpc;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class XmlRpcMethodUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testNoReturn() {
		Method xmlRpcMethod = (Method)ProxyUtil.newProxyInstance(
			Method.class.getClassLoader(), new Class<?>[] {Method.class},
			(proxy, method, args) -> {
				if (Objects.equals(method.getName(), "getToken")) {
					return _TOKEN;
				}

				if (Objects.equals(method.getName(), "getMethodName")) {
					return _METHOD_NAME;
				}

				return null;
			});

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceRegistration<Method> serviceRegistration =
			bundleContext.registerService(Method.class, xmlRpcMethod, null);

		try {
			Assert.assertSame(
				xmlRpcMethod, XmlRpcMethodUtil.getMethod(_TOKEN, _METHOD_NAME));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _METHOD_NAME = "METHOD_NAME";

	private static final String _TOKEN = "TOKEN";

}