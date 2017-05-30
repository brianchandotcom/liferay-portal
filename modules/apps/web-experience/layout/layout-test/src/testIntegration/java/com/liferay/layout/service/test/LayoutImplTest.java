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

package com.liferay.layout.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.LayoutTypeControllerImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class LayoutImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testGetTypeReturnsBlank() {
		_layout.setType(null);

		Assert.assertEquals(StringPool.BLANK, _layout.getType());
	}

	@Test
	public void testGetTypeReturnsType() {
		String type = RandomTestUtil.randomString();

		_layout.setType(type);

		Assert.assertEquals(type, _layout.getType());
	}

	@Test
	public void testIsSupportsEmbeddedPortletsWithTypeEmbedded() {
		_layout.setType(LayoutConstants.TYPE_EMBEDDED);

		Assert.assertTrue(_layout.isSupportsEmbeddedPortlets());
	}

	@Test
	public void testIsSupportsEmbeddedPortletsWithTypePanel() {
		_layout.setType(LayoutConstants.TYPE_PANEL);

		Assert.assertTrue(_layout.isSupportsEmbeddedPortlets());
	}

	@Test
	public void testIsSupportsEmbeddedPortletsWithTypePortlet() {
		_layout.setType(LayoutConstants.TYPE_PORTLET);

		Assert.assertTrue(_layout.isSupportsEmbeddedPortlets());
	}

	@Test
	public void testIsTypeEmbeddedReturnsFalse() {
		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_EMBEDDED)) {
				continue;
			}

			_layout.setType(type);

			Assert.assertFalse(_layout.isTypeEmbedded());
		}
	}

	@Test
	public void testIsTypeEmbeddedReturnsTrue() {
		_layout.setType(LayoutConstants.TYPE_EMBEDDED);

		Assert.assertTrue(_layout.isTypeEmbedded());
	}

	@Test
	public void testIsTypeEmbeddedWithLayoutTypeController() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		LayoutTypeController layoutTypeController =
			new LayoutTypeControllerImpl(LayoutConstants.TYPE_EMBEDDED);

		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_EMBEDDED)) {
				continue;
			}

			_layout.setType(type);

			Map<String, Object> properties = new HashMap<>();

			properties.put("layout.type", type);

			ServiceRegistration<LayoutTypeController> serviceRegistration =
				registry.registerService(
					LayoutTypeController.class, layoutTypeController,
					properties);

			try {
				Assert.assertTrue(_layout.isTypeEmbedded());
			}
			finally {
				serviceRegistration.unregister();
			}
		}
	}

	@Test
	public void testIsTypePanelReturnsFalse() {
		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_PANEL)) {
				continue;
			}

			_layout.setType(type);

			Assert.assertFalse(_layout.isTypePanel());
		}
	}

	@Test
	public void testIsTypePanelReturnsTrue() {
		_layout.setType(LayoutConstants.TYPE_PANEL);

		Assert.assertTrue(_layout.isTypePanel());
	}

	@Test
	public void testIsTypePanelWithLayoutTypeController() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		LayoutTypeController layoutTypeController =
			new LayoutTypeControllerImpl(LayoutConstants.TYPE_PANEL);

		for (String layoutTypeValue : _TYPES) {
			if (layoutTypeValue.equals(LayoutConstants.TYPE_PANEL)) {
				continue;
			}

			_layout.setType(layoutTypeValue);

			Map<String, Object> properties = new HashMap<>();

			properties.put("layout.type", layoutTypeValue);

			ServiceRegistration<LayoutTypeController> serviceRegistration =
				registry.registerService(
					LayoutTypeController.class, layoutTypeController,
					properties);

			try {
				Assert.assertTrue(_layout.isTypePanel());
			}
			finally {
				serviceRegistration.unregister();
			}
		}
	}

	@Test
	public void testIsTypePortletReturnsFalse() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				continue;
			}

			LayoutTypeController layoutTypeController =
				new LayoutTypeControllerImpl(type);

			Map<String, Object> properties = new HashMap<>();

			properties.put("layout.type", type);

			ServiceRegistration<LayoutTypeController> serviceRegistration =
				registry.registerService(
					LayoutTypeController.class, layoutTypeController,
					properties);

			try {
				_layout.setType(type);

				Assert.assertFalse(_layout.isTypePortlet());
			}
			finally {
				serviceRegistration.unregister();
			}
		}
	}

	@Test
	public void testIsTypePortletReturnsTrue() {
		_layout.setType(LayoutConstants.TYPE_PORTLET);

		Assert.assertTrue(_layout.isTypePortlet());
	}

	@Test
	public void testIsTypePortletWithLayoutTypeController() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		LayoutTypeController layoutTypeController =
			new LayoutTypeControllerImpl(LayoutConstants.TYPE_PORTLET);

		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				continue;
			}

			_layout.setType(type);

			Map<String, Object> properties = new HashMap<>();

			properties.put("layout.type", type);

			ServiceRegistration<LayoutTypeController> serviceRegistration =
				registry.registerService(
					LayoutTypeController.class, layoutTypeController,
					properties);

			try {
				Assert.assertTrue(_layout.isTypePortlet());
			}
			finally {
				serviceRegistration.unregister();
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static final String[] _TYPES = {
		LayoutConstants.TYPE_ARTICLE, LayoutConstants.TYPE_CONTROL_PANEL,
		LayoutConstants.TYPE_EMBEDDED, LayoutConstants.TYPE_LINK_TO_LAYOUT,
		LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET,
		LayoutConstants.TYPE_URL
	};

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}