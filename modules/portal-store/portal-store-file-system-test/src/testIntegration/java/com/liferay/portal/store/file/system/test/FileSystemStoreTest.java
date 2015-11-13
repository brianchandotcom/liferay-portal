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

package com.liferay.portal.store.file.system.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.module.framework.test.ModuleFrameworkTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.store.StoreWrapper;
import com.liferay.portlet.documentlibrary.store.test.BaseStoreTestCase;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class FileSystemStoreTest extends BaseStoreTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_bundleIds = ModuleFrameworkTestUtil.getBundleIds(
			StoreWrapper.class, "(store.type=" + _STORE_TYPE + ")");

		ModuleFrameworkTestUtil.stopBundles(_bundleIds);
	}

	@AfterClass
	public static void tearDownClass() {
		ModuleFrameworkTestUtil.startBundles(_bundleIds);
	}

	@Override
	protected String getStoreType() {
		return _STORE_TYPE;
	}

	private static final String _STORE_TYPE =
		"com.liferay.portal.store.file.system.FileSystemStore";

	private static Collection<Long> _bundleIds;

}