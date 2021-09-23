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

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class ExportImportLifecycleEventListenerRegistryUtilTest {

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetAsyncExportImportLifecycleListeners() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ExportImportLifecycleListener asyncExportImportLifecycleListener =
			new ExportImportLifecycleListener() {

				@Override
				public boolean isParallel() {
					return true;
				}

				@Override
				public void onExportImportLifecycleEvent(
					ExportImportLifecycleEvent exportImportLifecycleEvent) {
				}

			};

		_serviceRegistration = bundleContext.registerService(
			ExportImportLifecycleListener.class,
			asyncExportImportLifecycleListener, null);

		Set<ExportImportLifecycleListener> exportImportLifecycleListeners =
			ExportImportLifecycleEventListenerRegistryUtil.
				getAsyncExportImportLifecycleListeners();

		Assert.assertTrue(
			asyncExportImportLifecycleListener + " not found in " +
				exportImportLifecycleListeners,
			exportImportLifecycleListeners.removeIf(
				exportImportLifecycleListener ->
					asyncExportImportLifecycleListener ==
						exportImportLifecycleListener));
	}

	@Test
	public void testGetSyncExportImportLifecycleListeners() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ExportImportLifecycleListener syncExportImportLifecycleListener =
			new ExportImportLifecycleListener() {

				@Override
				public boolean isParallel() {
					return false;
				}

				@Override
				public void onExportImportLifecycleEvent(
					ExportImportLifecycleEvent exportImportLifecycleEvent) {
				}

			};

		_serviceRegistration = bundleContext.registerService(
			ExportImportLifecycleListener.class,
			syncExportImportLifecycleListener, null);

		Set<ExportImportLifecycleListener> exportImportLifecycleListeners =
			ExportImportLifecycleEventListenerRegistryUtil.
				getSyncExportImportLifecycleListeners();

		Assert.assertTrue(
			syncExportImportLifecycleListener + " not found in " +
				exportImportLifecycleListeners,
			exportImportLifecycleListeners.removeIf(
				exportImportLifecycleListener ->
					syncExportImportLifecycleListener ==
						exportImportLifecycleListener));
	}

	private static ServiceRegistration<ExportImportLifecycleListener>
		_serviceRegistration;

}