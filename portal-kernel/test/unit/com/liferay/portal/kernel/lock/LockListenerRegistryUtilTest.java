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

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Dante Wang
 * @author Peter Fellwock
 */
public class LockListenerRegistryUtilTest {

	@Test
	public void testGetLockListener() {
		LockListener lockListener = (LockListener)ProxyUtil.newProxyInstance(
			LockListener.class.getClassLoader(),
			new Class<?>[] {LockListener.class},
			(proxy, method, args) -> {
				if ("getClassName".equals(method.getName())) {
					return _CLASS_NAME;
				}

				return null;
			});

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<LockListener> serviceRegistration =
			registry.registerService(LockListener.class, lockListener);

		try {
			Assert.assertSame(
				lockListener,
				LockListenerRegistryUtil.getLockListener(_CLASS_NAME));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _CLASS_NAME = "TestLockListener";

}