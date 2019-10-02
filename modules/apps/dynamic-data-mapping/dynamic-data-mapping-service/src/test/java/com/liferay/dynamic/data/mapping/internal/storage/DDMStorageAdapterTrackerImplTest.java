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

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(ServiceTrackerMapFactory.class)
@RunWith(PowerMockRunner.class)
public class DDMStorageAdapterTrackerImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		mockStatic(ServiceTrackerMapFactory.class);
	}

	@Test
	public void testActivate() {
		DDMStorageAdapterTrackerImpl ddmStorageAdapterTrackerImpl =
			new DDMStorageAdapterTrackerImpl();

		BundleContext bundleContext = mock(BundleContext.class);

		when(
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, DDMStorageAdapter.class,
				"ddm.storage.adapter.type")
		).thenReturn(
			_ddmStorageAdapterServiceTrackerMap
		);

		ddmStorageAdapterTrackerImpl.activate(bundleContext);

		Assert.assertNotNull(
			ddmStorageAdapterTrackerImpl.ddmStorageAdapterServiceTrackerMap);
	}

	@Test
	public void testDeactivate() {
		DDMStorageAdapterTrackerImpl ddmStorageAdapterTrackerImpl =
			new DDMStorageAdapterTrackerImpl();

		ddmStorageAdapterTrackerImpl.ddmStorageAdapterServiceTrackerMap =
			_ddmStorageAdapterServiceTrackerMap;

		ddmStorageAdapterTrackerImpl.deactivate();

		Mockito.verify(
			_ddmStorageAdapterServiceTrackerMap, Mockito.times(1)
		).close();
	}

	@Test
	public void testGetDDMStorageAdapter() {
		DDMStorageAdapterTrackerImpl ddmStorageAdapterTrackerImpl =
			new DDMStorageAdapterTrackerImpl();

		ddmStorageAdapterTrackerImpl.ddmStorageAdapterServiceTrackerMap =
			_ddmStorageAdapterServiceTrackerMap;

		ddmStorageAdapterTrackerImpl.getDDMStorageAdapter("json");

		Mockito.verify(
			_ddmStorageAdapterServiceTrackerMap, Mockito.times(1)
		).getService(
			"json"
		);
	}

	@Test
	public void testGetDDMStorageAdapterTypes() {
		DDMStorageAdapterTrackerImpl ddmStorageAdapterTrackerImpl =
			new DDMStorageAdapterTrackerImpl();

		ddmStorageAdapterTrackerImpl.ddmStorageAdapterServiceTrackerMap =
			_ddmStorageAdapterServiceTrackerMap;

		ddmStorageAdapterTrackerImpl.getDDMStorageAdapterTypes();

		Mockito.verify(
			_ddmStorageAdapterServiceTrackerMap, Mockito.times(1)
		).keySet();
	}

	@Mock
	private ServiceTrackerMap<String, DDMStorageAdapter>
		_ddmStorageAdapterServiceTrackerMap;

}