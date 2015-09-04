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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.documentlibrary.store.bundle.storefactory.StoreWrapperDelegate;
import com.liferay.portlet.documentlibrary.store.bundle.storefactory.TopTestStoreWrapper;

import java.lang.reflect.Method;

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
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.storefactory"));

	@Test
	public void testGetStore() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertNotNull(store);

		String[] fileNames = store.getFileNames(0, 0);

		Assert.assertEquals(1, fileNames.length);
		Assert.assertEquals("TestStore", fileNames[0]);
	}

	@Test
	public void testGetStoreIsStoreWrapperDelegate() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertTrue(
			isStoreWrapperDelegate(
				store, StoreWrapperDelegate.class.getName()));
	}

	@Test
	public void testGetStoreShouldReturnTopPriorityStoreWrapper()
		throws Exception {

		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertTrue(
			isStoreWrapperDelegate(
				store, TopTestStoreWrapper.Delegate.class.getName()));
	}

	@Test
	public void testGetStoreWrapperDelegateChainLength() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertEquals(2, getStoreWrapperDelegateChainLength(store));
	}

	protected int getStoreWrapperDelegateChainLength(Store store)
		throws Exception {

		try {
			Class<? extends Store> storeClass = store.getClass();

			Method method = storeClass.getMethod(
				"getStoreWrapperDelegateChainLengthForTest");

			return (Integer)method.invoke(store);
		}
		catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException(
				store.getClass() +
					" is not an subclass of StoreWrapperDelegate",
				nsme);
		}
	}

	private boolean isStoreWrapperDelegate(Store store, String className)
		throws ClassNotFoundException {

		Class<? extends Store> storeClass = store.getClass();

		ClassLoader classLoader = storeClass.getClassLoader();

		Class<?> clazz = classLoader.loadClass(className);

		return clazz.isAssignableFrom(storeClass);
	}

}