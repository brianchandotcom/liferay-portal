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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.store.StoreWrapper;
import com.liferay.portlet.documentlibrary.store.test.BaseStoreTestCase;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Vilmos Papp
 */
@RunWith(Arquillian.class)
public class AdvancedFileSystemStoreTest extends BaseStoreTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		Registry registry = RegistryUtil.getRegistry();

		Collection<ServiceReference<StoreWrapper>>
			serviceReferences = registry.getServiceReferences(
				StoreWrapper.class, "(store.type="+ _STORE_TYPE +")");

		for (ServiceReference<StoreWrapper> serviceReference :
				serviceReferences) {

			Long bundleId = (Long)serviceReference.getProperty(
				"service.bundleid");

			_bundleIds.add(bundleId);

			ModuleFrameworkUtilAdapter.stopBundle(bundleId);
		}
	}

	@AfterClass
	public static void tearDownClass() {
		for (long bundleId : _bundleIds) {
			try {
				ModuleFrameworkUtilAdapter.startBundle(bundleId);
			}
			catch (Exception e) {
				_log.error("Unable to start bundle " + bundleId, e);
			}
		}
	}

	@Override
	protected String getStoreType() {
		return _STORE_TYPE;
	}

	private static final String _STORE_TYPE =
		"com.liferay.portal.store.file.system.AdvancedFileSystemStore";

	private static final Log _log = LogFactoryUtil.getLog(
		AdvancedFileSystemStoreTest.class);

	private static final List<Long> _bundleIds = new ArrayList<>();

}