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
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleListener;

import java.util.Set;

import org.junit.Assert;
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
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule(
				"bundle.exportimportlifecycleeventlistenerregistryutil"));

	@Test
	public void testGetAsyncExportImportLifecycleListeners() {
		boolean exists = false;

		Set<ExportImportLifecycleListener> exportImportLifecycleListeners =
			ExportImportLifecycleEventListenerRegistryUtil.
				getAsyncExportImportLifecycleListeners();

		for (ExportImportLifecycleListener exportImportLifecycleListener :
				exportImportLifecycleListeners) {

			Class<?> clazz = exportImportLifecycleListener.getClass();

			String className = clazz.getName();

			Assert.assertNotEquals(
				TestSyncExportImportLifecycleListener.class.getName(),
				className);

			if (className.equals(
					TestAsyncExportImportLifecycleListener.class.getName())) {

				exists = true;

				break;
			}
		}

		Assert.assertTrue(exists);
	}

	@Test
	public void testGetSyncExportImportLifecycleListeners() {
		boolean exists = false;

		Set<ExportImportLifecycleListener> exportImportLifecycleListeners =
			ExportImportLifecycleEventListenerRegistryUtil.
				getSyncExportImportLifecycleListeners();

		for (ExportImportLifecycleListener exportImportLifecycleListener :
				exportImportLifecycleListeners) {

			Class<?> clazz = exportImportLifecycleListener.getClass();

			String className = clazz.getName();

			Assert.assertNotEquals(
				TestAsyncExportImportLifecycleListener.class.getName(),
				className);

			if (className.equals(
					TestSyncExportImportLifecycleListener.class.getName())) {

				exists = true;

				break;
			}
		}

		Assert.assertTrue(exists);
	}

}