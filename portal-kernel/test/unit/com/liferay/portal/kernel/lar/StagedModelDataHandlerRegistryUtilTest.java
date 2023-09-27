/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.lar;

import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class StagedModelDataHandlerRegistryUtilTest {

	@Before
	public void setUp() {
		_stagedModelDataHandler =
			(StagedModelDataHandler<?>)ProxyUtil.newProxyInstance(
				StagedModelDataHandlerRegistryUtilTest.class.getClassLoader(),
				new Class<?>[] {StagedModelDataHandler.class},
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getClassNames")) {
						return new String[] {_CLASS_NAME};
					}

					return null;
				});

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			(Class<StagedModelDataHandler<?>>)
				(Class<?>)StagedModelDataHandler.class,
			_stagedModelDataHandler, null);
	}

	@After
	public void tearDown() {
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

	private ServiceRegistration<StagedModelDataHandler<?>> _serviceRegistration;
	private StagedModelDataHandler<?> _stagedModelDataHandler;

}