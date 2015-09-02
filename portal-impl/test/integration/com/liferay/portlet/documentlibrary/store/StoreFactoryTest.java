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
import com.liferay.portlet.documentlibrary.store.bundle.storefactory.DelegatingTestStore;
import com.liferay.portlet.documentlibrary.store.bundle.storefactory.TopTestStoreWrapper;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class StoreFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.storefactory"));

	@Test
	public void testGetInstance() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertNotNull(store);

		String[] fileNames = store.getFileNames(0, 0);

		Assert.assertEquals(1, fileNames.length);
		Assert.assertEquals("TestStore", fileNames[0]);
	}

	@Test
	public void testInstanceIsWrapped() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		assertWrapperClass(store, DelegatingTestStore.class.getName());
	}

	@Test
	public void testWrapperChain() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		Assert.assertEquals(2, getWrapperChainLength(store));
	}

	@Test
	public void testWrapperPriority() throws Exception {
		StoreFactory storeFactory = StoreFactory.getInstance();

		Store store = storeFactory.getStore("test");

		assertWrapperClass(store, TopTestStoreWrapper.Wrapper.class.getName());
	}

	protected void assertWrapperClass(Store store, String className)
		throws Exception {

		Assert.assertTrue(isWrapper(store, className));
	}

	protected int getWrapperChainLength(Store store) throws Exception {
		assertWrapperClass(store, DelegatingTestStore.class.getName());

		Class<? extends Store> storeClass = store.getClass();

		Method method = storeClass.getMethod("getWrapperChainLength");

		return (Integer)method.invoke(store);
	}

	private boolean isWrapper(Store store, String className)
		throws ClassNotFoundException {

		Class<? extends Store> storeClass = store.getClass();

		ClassLoader classLoader = storeClass.getClassLoader();

		Class<?> clazz = classLoader.loadClass(className);

		return clazz.isAssignableFrom(storeClass);
	}

}