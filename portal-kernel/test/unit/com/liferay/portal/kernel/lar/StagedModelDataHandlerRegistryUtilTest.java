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

package com.liferay.portal.kernel.lar;

import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Leon Chi
 */
public class StagedModelDataHandlerRegistryUtilTest {

	@BeforeClass
	public static void setUpClass() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		_stagedModelDataHandler =
			(StagedModelDataHandler<?>)ProxyUtil.newProxyInstance(
				StagedModelDataHandlerRegistryUtilTest.class.getClassLoader(),
				new Class<?>[] {StagedModelDataHandler.class},
				(proxy, method, args) -> {
					if ("getClassNames".equals(method.getName())) {
						return new String[] {_CLASS_NAME};
					}

					return null;
				});

		_serviceRegistration = registry.registerService(
			StagedModelDataHandler.class, _stagedModelDataHandler);
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetStagedModelDataHandler() {
		Assert.assertSame(
			_stagedModelDataHandler,
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				_CLASS_NAME));
	}

	@Test
	public void testGetStagedModelDataHandlers() {
		List<StagedModelDataHandler<?>> stagedModelDataHandlers =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandlers();

		Assert.assertTrue(
			_CLASS_NAME + " not found in " + stagedModelDataHandlers,
			stagedModelDataHandlers.removeIf(
				stagedModelDataHandler ->
					_stagedModelDataHandler == stagedModelDataHandler));
	}

	private static final String _CLASS_NAME = "TestStagedModelDataHandler";

	private static ServiceRegistration<StagedModelDataHandler>
		_serviceRegistration;
	private static StagedModelDataHandler<?> _stagedModelDataHandler;

}