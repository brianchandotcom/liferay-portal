/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSCreationMenu;
import com.liferay.frontend.data.set.internal.SystemFDSEntryRegistryImpl;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Sanz
 */
public class SystemFDSCreationMenuSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_bundleContext = SystemBundleUtil.getBundleContext();

		_systemFDSEntryserviceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, SystemFDSEntry.class, "frontend.data.set.name");

		ReflectionTestUtil.setFieldValue(
			_systemFDSEntryRegistryImpl, "_serviceTrackerMap",
			_systemFDSEntryserviceTrackerMap);

		_creationMenuServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				_bundleContext, FDSCreationMenu.class, "frontend.data.set.name",
				ServiceTrackerCustomizerFactory.<FDSCreationMenu>serviceWrapper(
					_bundleContext));

		ReflectionTestUtil.setFieldValue(
			_fdsCreationMenuRegistryImpl, "_serviceTrackerMap",
			_creationMenuServiceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_systemFDSCreationMenuSerializerImpl, "_fdsCreationMenuRegistry",
			_fdsCreationMenuRegistryImpl);
	}

	@After
	public void tearDown() {
		_creationMenuServiceTrackerMap.close();
		_systemFDSEntryserviceTrackerMap.close();
	}

	@Test
	public void testFDSCreationMenuSerialization() throws Exception {
		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		CreationMenu creationMenu = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration =
			_registerCreationMenu("fdsName", creationMenu);

		Assert.assertEquals(
			creationMenu,
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName", _httpServletRequest));

		creationMenuServiceRegistration.unregister();

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSCreationMenuSerializationNoCreationMenu()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration =
			_registerSystemFDSEntry("fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName", _httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration.unregister();
	}

	@Test
	public void testFDSCreationMenuSerializationSeparateCreationMenus()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		CreationMenu creationMenu1 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration1 =
			_registerCreationMenu("fdsName1", creationMenu1);

		CreationMenu creationMenu2 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"cog"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration2 =
			_registerCreationMenu("fdsName2", creationMenu2);

		Assert.assertNotEquals(
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName1", _httpServletRequest),
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		Assert.assertEquals(
			creationMenu1,
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName1", _httpServletRequest));

		Assert.assertEquals(
			creationMenu2,
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		creationMenuServiceRegistration1.unregister();

		creationMenuServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	@Test
	public void testFDSCreationMenuSerializationSharingCreationMenu()
		throws Exception {

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			_registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			_registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		CreationMenu creationMenu = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration1 =
			_registerCreationMenu("fdsName1", creationMenu);

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration2 =
			_registerCreationMenu("fdsName2", creationMenu);

		Assert.assertEquals(
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName1", _httpServletRequest),
			_systemFDSCreationMenuSerializerImpl.serialize(
				"fdsName2", _httpServletRequest));

		creationMenuServiceRegistration1.unregister();

		creationMenuServiceRegistration2.unregister();

		systemFDSEntryServiceRegistration1.unregister();

		systemFDSEntryServiceRegistration2.unregister();
	}

	private ServiceRegistration<FDSCreationMenu> _registerCreationMenu(
		String fdsName, CreationMenu creationMenu) {

		return _bundleContext.registerService(
			FDSCreationMenu.class,
			new FDSCreationMenu() {

				@Override
				public CreationMenu getCreationMenu(
					HttpServletRequest httpServletRequest) {

					return creationMenu;
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private ServiceRegistration<SystemFDSEntry> _registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema) {

		return _registerSystemFDSEntry(
			fdsName, restApplication, restEndpoint, restSchema, null);
	}

	private ServiceRegistration<SystemFDSEntry> _registerSystemFDSEntry(
		String fdsName, String restApplication, String restEndpoint,
		String restSchema, String additionalURLParameters) {

		return _bundleContext.registerService(
			SystemFDSEntry.class,
			new SystemFDSEntry() {

				@Override
				public String getAdditionalAPIURLParameters() {
					return additionalURLParameters;
				}

				@Override
				public String getDescription() {
					return "";
				}

				@Override
				public String getName() {
					return fdsName;
				}

				@Override
				public String getRESTApplication() {
					return restApplication;
				}

				@Override
				public String getRESTEndpoint() {
					return restEndpoint;
				}

				@Override
				public String getRESTSchema() {
					return restSchema;
				}

				@Override
				public String getTitle() {
					return "";
				}

			},
			MapUtil.singletonDictionary("frontend.data.set.name", fdsName));
	}

	private static BundleContext _bundleContext;
	private static ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper<FDSCreationMenu>>
			_creationMenuServiceTrackerMap;
	private static final FDSCreationMenuRegistryImpl
		_fdsCreationMenuRegistryImpl = new FDSCreationMenuRegistryImpl();
	private static final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private static final SystemFDSCreationMenuSerializerImpl
		_systemFDSCreationMenuSerializerImpl =
			new SystemFDSCreationMenuSerializerImpl();
	private static final SystemFDSEntryRegistryImpl
		_systemFDSEntryRegistryImpl = new SystemFDSEntryRegistryImpl();
	private static ServiceTrackerMap<String, SystemFDSEntry>
		_systemFDSEntryserviceTrackerMap;

}