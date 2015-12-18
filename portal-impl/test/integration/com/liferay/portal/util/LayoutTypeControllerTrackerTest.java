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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypeController;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutTypeControllerImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.bundle.layouttypecontrollertracker.TestLayoutTypeController;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class LayoutTypeControllerTrackerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.layouttypecontrollertracker"));

	@Test
	public void testGetLayoutTypeController1() {
		Layout layout = new TestLayoutImpl();

		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(layout);

		Assert.assertNotNull(layoutTypeController);

		Class<?> clazz = layoutTypeController.getClass();

		Assert.assertEquals(
			LayoutTypeControllerImpl.class.getName(), clazz.getName());

		layout.setType("testLayoutTypeController");

		layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(layout);

		Assert.assertNotNull(layoutTypeController);

		clazz = layoutTypeController.getClass();

		Assert.assertEquals(
			TestLayoutTypeController.class.getName(), clazz.getName());
	}

	@Test
	public void testGetLayoutTypeController2() {
		LayoutTypeController layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				RandomTestUtil.randomString());

		Assert.assertNotNull(layoutTypeController);

		Class<?> clazz = layoutTypeController.getClass();

		Assert.assertEquals(
			LayoutTypeControllerImpl.class.getName(), clazz.getName());

		layoutTypeController =
			LayoutTypeControllerTracker.getLayoutTypeController(
				"testLayoutTypeController");

		Assert.assertNotNull(layoutTypeController);

		clazz = layoutTypeController.getClass();

		Assert.assertEquals(
			TestLayoutTypeController.class.getName(), clazz.getName());
	}

	@Test
	public void testGetLayoutTypeControllers() {
		Map<String, LayoutTypeController> layoutTypeControllers =
			LayoutTypeControllerTracker.getLayoutTypeControllers();

		Assert.assertNotNull(layoutTypeControllers);

		LayoutTypeController layoutTypeController = layoutTypeControllers.get(
			"testLayoutTypeController");

		Class<?> clazz = layoutTypeController.getClass();

		Assert.assertEquals(
			TestLayoutTypeController.class.getName(), clazz.getName());
	}

	@Test
	public void testGetTypes() {
		String[] types = LayoutTypeControllerTracker.getTypes();

		Assert.assertNotNull(types);

		boolean found = false;

		for (String type : types) {
			if (type.equals("testLayoutTypeController")) {
				found = true;
			}
		}

		Assert.assertTrue(found);
	}

	private static class TestLayoutImpl extends LayoutImpl {

		@Override
		public String getName() {
			return toString();
		}

		@Override
		public String getName(Locale locale) {
			return toString();
		}

		@Override
		public String toString() {
			return getParentLayoutId() + "/" + getPlid();
		}

	}

}