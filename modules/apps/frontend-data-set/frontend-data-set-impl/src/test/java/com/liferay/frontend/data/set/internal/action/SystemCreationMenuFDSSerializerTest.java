/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.action;

import com.liferay.frontend.data.set.SystemFDSEntry;
import com.liferay.frontend.data.set.action.FDSCreationMenu;
import com.liferay.frontend.data.set.action.FDSCreationMenuRegistry;
import com.liferay.frontend.data.set.internal.BaseFDSSerializerTestCase;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Daniel Sanz
 */
public class SystemCreationMenuFDSSerializerTest
	extends BaseFDSSerializerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		FDSCreationMenuRegistry fdsCreationMenuRegistry =
			new FDSCreationMenuRegistryImpl();

		ReflectionTestUtil.setFieldValue(
			fdsCreationMenuRegistry, "_serviceTrackerMap", serviceTrackerMap);

		ReflectionTestUtil.setFieldValue(
			_fdsSerializer, "_fdsCreationMenuRegistry",
			fdsCreationMenuRegistry);
	}

	@Test
	public void testSerialize() throws Exception {

		// Different creation menu

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration1 =
			registerSystemFDSEntry("fdsName1", "/app", "/endpoint", "schema");

		CreationMenu creationMenu1 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration1 =
			_registerFDSCreationMenu(creationMenu1, "fdsName1");

		Assert.assertEquals(
			creationMenu1,
			_fdsSerializer.serialize("fdsName1", httpServletRequest));

		ServiceRegistration<SystemFDSEntry> systemFDSEntryServiceRegistration2 =
			registerSystemFDSEntry("fdsName2", "/app", "/endpoint", "schema");

		CreationMenu creationMenu2 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"cog"
			).build()
		).build();

		ServiceRegistration<FDSCreationMenu> creationMenuServiceRegistration2 =
			_registerFDSCreationMenu(creationMenu2, "fdsName2");

		Assert.assertEquals(
			creationMenu2,
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		Assert.assertNotEquals(
			_fdsSerializer.serialize("fdsName1", httpServletRequest),
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		creationMenuServiceRegistration1.unregister();
		creationMenuServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();

		// No creation menu

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName", "/app", "/endpoint", "schema");

		Assert.assertTrue(
			_fdsSerializer.serialize(
				"fdsName", httpServletRequest
			).isEmpty());

		systemFDSEntryServiceRegistration1.unregister();

		// Shared creation menu

		systemFDSEntryServiceRegistration1 = registerSystemFDSEntry(
			"fdsName1", "/app", "/endpoint", "schema");
		systemFDSEntryServiceRegistration2 = registerSystemFDSEntry(
			"fdsName2", "/app", "/endpoint", "schema");

		creationMenu1 = CreationMenuBuilder.addDropdownItem(
			DropdownItemBuilder.setIcon(
				"times"
			).build()
		).build();

		creationMenuServiceRegistration1 = _registerFDSCreationMenu(
			creationMenu1, "fdsName1");
		creationMenuServiceRegistration2 = _registerFDSCreationMenu(
			creationMenu1, "fdsName2");

		Assert.assertEquals(
			_fdsSerializer.serialize("fdsName1", httpServletRequest),
			_fdsSerializer.serialize("fdsName2", httpServletRequest));

		creationMenuServiceRegistration1.unregister();
		creationMenuServiceRegistration2.unregister();
		systemFDSEntryServiceRegistration1.unregister();
		systemFDSEntryServiceRegistration2.unregister();
	}

	@Override
	protected ServiceTrackerMap<String, ?> createServiceTrackerMap() {
		return ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSCreationMenu.class, "frontend.data.set.name",
			ServiceTrackerCustomizerFactory.<FDSCreationMenu>serviceWrapper(
				bundleContext));
	}

	private ServiceRegistration<FDSCreationMenu> _registerFDSCreationMenu(
		CreationMenu creationMenu, String fdsName) {

		return bundleContext.registerService(
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

	private static final FDSSerializer<CreationMenu> _fdsSerializer =
		new SystemCreationMenuFDSSerializerImpl();

}