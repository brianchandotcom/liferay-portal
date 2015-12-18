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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
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
		LayoutType layoutType = _layout.getLayoutType();

		Field field = ReflectionUtil.getDeclaredField(
			LayoutTypeImpl.class, "_layoutTypeController");

		field.set(
			layoutType,
			new LayoutTypeControllerImpl(LayoutConstants.TYPE_EMBEDDED));

		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_EMBEDDED)) {
				continue;
			}

			_layout.setType(type);

			Assert.assertTrue(_layout.isTypeEmbedded());
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
		LayoutType layoutType = _layout.getLayoutType();

		Field field = ReflectionUtil.getDeclaredField(
			LayoutTypeImpl.class, "_layoutTypeController");

		field.set(
			layoutType,
			new LayoutTypeControllerImpl(LayoutConstants.TYPE_PANEL));

		for (String layoutTypeValue : _TYPES) {
			if (layoutTypeValue.equals(LayoutConstants.TYPE_PANEL)) {
				continue;
			}

			_layout.setType(layoutTypeValue);

			Assert.assertTrue(_layout.isTypePanel());
		}
	}

	@Test
	public void testIsTypePortletReturnsFalse() throws Exception {
		LayoutType layoutType = _layout.getLayoutType();

		Field field = ReflectionUtil.getDeclaredField(
			LayoutTypeImpl.class, "_layoutTypeController");

		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				continue;
			}

			field.set(layoutType, new LayoutTypeControllerImpl(type));

			_layout.setType(type);

			Assert.assertFalse(_layout.isTypePortlet());
		}
	}

	@Test
	public void testIsTypePortletReturnsTrue() {
		_layout.setType(LayoutConstants.TYPE_PORTLET);

		Assert.assertTrue(_layout.isTypePortlet());
	}

	@Test
	public void testIsTypePortletWithLayoutTypeController() throws Exception {
		LayoutType layoutType = _layout.getLayoutType();

		Field field = ReflectionUtil.getDeclaredField(
			LayoutTypeImpl.class, "_layoutTypeController");

		field.set(
			layoutType,
			new LayoutTypeControllerImpl(LayoutConstants.TYPE_PORTLET));

		for (String type : _TYPES) {
			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				continue;
			}

			_layout.setType(type);

			Assert.assertTrue(_layout.isTypePortlet());
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