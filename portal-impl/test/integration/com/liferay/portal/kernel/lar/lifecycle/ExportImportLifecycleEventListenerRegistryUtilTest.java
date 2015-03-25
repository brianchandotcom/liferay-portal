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

package com.liferay.portal.kernel.lar.lifecycle;

import com.liferay.portal.kernel.lar.lifecycle.bundle.exportimportlifecycleeventlistenerregistryutil.TestAsyncExportImportLifecycleListener;
import com.liferay.portal.kernel.lar.lifecycle.bundle.exportimportlifecycleeventlistenerregistryutil.TestSyncExportImportLifecycleListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class ExportImportLifecycleEventListenerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule(
				"bundle.exportimportlifecycleeventlistenerregistryutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testAsyncInstance() {
		Set<ExportImportLifecycleListener> asyncListeners =
			ExportImportLifecycleEventListenerRegistryUtil.
				getAsyncExportImportLifecycleListeners();

		Class<TestAsyncExportImportLifecycleListener> clazz =
			TestAsyncExportImportLifecycleListener.class;

		String testClassName = clazz.getName();

		Class<? extends ExportImportLifecycleListener> asyncClazz;

		for (ExportImportLifecycleListener asyncListener : asyncListeners) {
			asyncClazz = asyncListener.getClass();

			if (testClassName.equals(asyncClazz.getName())) {
				Assert.assertTrue(true);
				return;
			}
		}

		Assert.fail();
	}

	@Test
	public void testSyncInstance() {
		Set<ExportImportLifecycleListener> syncListeners =
			ExportImportLifecycleEventListenerRegistryUtil.
				getSyncExportImportLifecycleListeners();

		Class<TestSyncExportImportLifecycleListener> clazz =
			TestSyncExportImportLifecycleListener.class;

		String testClassName = clazz.getName();

		Class<? extends ExportImportLifecycleListener> syncClazz;

		for (ExportImportLifecycleListener syncListener : syncListeners) {
			syncClazz = syncListener.getClass();

			if (testClassName.equals(syncClazz.getName())) {
				Assert.assertTrue(true);
				return;
			}
		}

		Assert.fail();
	}

	private static AtomicState _atomicState;

}