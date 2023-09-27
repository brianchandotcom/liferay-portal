/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;

/**
 * @author Leonardo Barros
 */
public class DDMStorageAdapterRegistryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testActivate() throws Exception {
		DDMStorageAdapterRegistryImpl ddmStorageAdapterRegistryImpl =
			new DDMStorageAdapterRegistryImpl();

		BundleContext bundleContext = Mockito.mock(BundleContext.class);

		Filter filter = Mockito.mock(Filter.class);

		Mockito.doReturn(
			filter
		).when(
			bundleContext
		).createFilter(
			Mockito.anyString()
		);

		ddmStorageAdapterRegistryImpl.activate(bundleContext);

		Assert.assertNotNull(
			ddmStorageAdapterRegistryImpl.ddmStorageAdapterServiceTrackerMap);
	}

	@Test
	public void testDeactivate() {
		DDMStorageAdapterRegistryImpl ddmStorageAdapterRegistryImpl =
			new DDMStorageAdapterRegistryImpl();

		ddmStorageAdapterRegistryImpl.ddmStorageAdapterServiceTrackerMap =
			_ddmStorageAdapterServiceTrackerMap;

		ddmStorageAdapterRegistryImpl.deactivate();

		Mockito.verify(
			_ddmStorageAdapterServiceTrackerMap, Mockito.times(1)
		).close();
	}

	@Test
	public void testGetDDMStorageAdapter() {
		DDMStorageAdapterRegistryImpl ddmStorageAdapterRegistryImpl =
			new DDMStorageAdapterRegistryImpl();

		ddmStorageAdapterRegistryImpl.ddmStorageAdapterServiceTrackerMap =
			_ddmStorageAdapterServiceTrackerMap;

		ddmStorageAdapterRegistryImpl.getDDMStorageAdapter("json");

		Mockito.verify(
			_ddmStorageAdapterServiceTrackerMap, Mockito.times(1)
		).getService(
			"json"
		);
	}

	@Test
	public void testGetDDMStorageAdapterTypes() {
		DDMStorageAdapterRegistryImpl ddmStorageAdapterRegistryImpl =
			new DDMStorageAdapterRegistryImpl();

		ddmStorageAdapterRegistryImpl.ddmStorageAdapterServiceTrackerMap =
			_ddmStorageAdapterServiceTrackerMap;

		ddmStorageAdapterRegistryImpl.getDDMStorageAdapterTypes();

		Mockito.verify(
			_ddmStorageAdapterServiceTrackerMap, Mockito.times(1)
		).keySet();
	}

	private final ServiceTrackerMap<String, DDMStorageAdapter>
		_ddmStorageAdapterServiceTrackerMap = Mockito.mock(
			ServiceTrackerMap.class);

}