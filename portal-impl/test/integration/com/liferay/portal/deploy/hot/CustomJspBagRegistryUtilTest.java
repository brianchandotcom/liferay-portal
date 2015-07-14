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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class CustomJspBagRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.customjspbagregistryutil"));

	@BeforeClass
	public static void setUpClass() {
		_customJspBagRegistryUtil = CustomJspBagRegistryUtil.getInstance();
	}

	@Test
	public void testGet() {
		CustomJspBag customJspBag = _customJspBagRegistryUtil.get(
			_TEST_SERVLET_CONTEXT_NAME);
		customJspBag = _customJspBagRegistryUtil.get(
			_TEST_SERVLET_CONTEXT_NAME);

		Assert.assertTrue(
			_TEST_SERVLET_CONTEXT_NAME.equals(
				customJspBag.getServletContextName()));
	}

	@Test
	public void testGetKeys() {
		Set<String> keys = _customJspBagRegistryUtil.getKeys();

		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			if (_TEST_SERVLET_CONTEXT_NAME.equals(iterator.next())) {
				return;
			}
		}

		Assert.fail();
	}

	private static final String _TEST_SERVLET_CONTEXT_NAME = "test-jsp-bag";

	private static CustomJspBagRegistryUtil _customJspBagRegistryUtil;

}