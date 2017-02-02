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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.documentlibrary.store.bundle.storefactory.DelegatorStore;
import com.liferay.portlet.documentlibrary.store.bundle.storefactory.FirstStoreWrapper;

import java.lang.reflect.Method;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Adolfo Pérez
 */
public class StoreFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.storefactory"));

	@Test
	public void testGetDelegatorStoresCount() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertEquals(2, getDelegatorStoresCount(store));
	}

	@Test
	public void testGetStore() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertNotNull(store);

		String[] fileNames = store.getFileNames(0, 0);

		Assert.assertEquals(Arrays.toString(fileNames), 1, fileNames.length);
		Assert.assertEquals("TestStore", fileNames[0]);
	}

	@Test
	public void testGetStoreReturnsDelegatorStore() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertTrue(
			_isAssignableFrom(store, DelegatorStore.class.getName()));
	}

	@Test
	public void testGetStoreReturnsFirstDelegatorStore() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertTrue(
			_isAssignableFrom(
				store, FirstStoreWrapper.FirstDelegatorStore.class.getName()));
	}

	protected int getDelegatorStoresCount(Store store) throws Exception {
		try {
			Class<? extends Store> storeClass = store.getClass();

			Method method = storeClass.getMethod("getDelegatorStoresCount");

			return (Integer)method.invoke(store);
		}
		catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException(
				ClassUtil.getClassName(store) +
					" does not implement DelegatorStore",
				nsme);
		}
	}

	private boolean _isAssignableFrom(Store store, String className)
		throws ClassNotFoundException {

		Class<? extends Store> storeClass = store.getClass();

		ClassLoader classLoader = storeClass.getClassLoader();

		Class<?> clazz = classLoader.loadClass(className);

		return clazz.isAssignableFrom(storeClass);
	}

}