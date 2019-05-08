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

package com.liferay.portal.security.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.PermissionService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Philip Jones
 */
@RunWith(Arquillian.class)
public class PermissionServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testCheckPermission() throws PortalException {
		Bundle bundle = FrameworkUtil.getBundle(
			PermissionServiceImplTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		boolean[] calledCheckBaseModel = {false};

		ServiceRegistration<ModelResourcePermission> serviceRegistration =
			bundleContext.registerService(
				ModelResourcePermission.class,
				(ModelResourcePermission)ProxyUtil.newProxyInstance(
					ModelResourcePermission.class.getClassLoader(),
					new Class<?>[] {ModelResourcePermission.class},
					(proxy, method, args) -> {
						calledCheckBaseModel[0] = true;

						return null;
					}),
				new HashMapDictionary<String, Object>() {
					{
						put("model.class.name", _CLASS_NAME);
						put("service.ranking", Integer.MAX_VALUE);
					}
				});

		try {
			_permissionService.checkPermission(0, _CLASS_NAME, 0);

			Assert.assertTrue(calledCheckBaseModel[0]);

			calledCheckBaseModel[0] = false;

			_permissionService.checkPermission(0, _CLASS_NAME, null);

			Assert.assertTrue(calledCheckBaseModel[0]);
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _CLASS_NAME = "PermissionServiceImplTest";

	@Inject
	private PermissionService _permissionService;

}