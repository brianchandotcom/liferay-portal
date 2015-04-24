/* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.dynamicdatamapping.storage.bundle.storageadapterregistryimpl.TestStorageAdapterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class StorageAdapterRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.storageadapterregistryimpl"));

	@Test
	public void testGetDefaultStorageAdapter() {
		StorageAdapterRegistry storageAdapterRegistry =
			StorageAdapterRegistryUtil.getStorageAdapterRegistry();

		StorageAdapter storageAdapter =
			storageAdapterRegistry.getDefaultStorageAdapter();

		Assert.assertNotNull(storageAdapter);

		Class<?> clazz = storageAdapter.getClass();

		Assert.assertEquals(
			JSONStorageAdapter.class.getName(), clazz.getName());
	}

	@Test
	public void testGetStorageAdapter() {
		StorageAdapterRegistry storageAdapterRegistry =
			StorageAdapterRegistryUtil.getStorageAdapterRegistry();

		StorageAdapter storageAdapter =
			storageAdapterRegistry.getStorageAdapter("testStorageAdapterImpl");

		Assert.assertNotNull(storageAdapter);

		Class<?> clazz = storageAdapter.getClass();

		Assert.assertEquals(
			TestStorageAdapterImpl.class.getName(), clazz.getName());
	}

	@Test
	public void testGetStorageTypes() {
		StorageAdapterRegistry storageAdapterRegistry =
			StorageAdapterRegistryUtil.getStorageAdapterRegistry();

		Set<String> storageTypes = storageAdapterRegistry.getStorageTypes();

		Assert.assertEquals(2, storageTypes.size());
		Assert.assertTrue(storageTypes.contains("json"));
		Assert.assertTrue(storageTypes.contains("testStorageAdapterImpl"));
	}
}