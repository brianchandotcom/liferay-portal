/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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