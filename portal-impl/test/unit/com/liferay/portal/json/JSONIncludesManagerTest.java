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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class JSONIncludesManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		JSONInit.init();
	}

	@Test
	public void testExtendsOne() {
		String json = JSONFactoryUtil.looseSerialize(new ExtendsOne());

		Assert.assertEquals("{\"ftwo\":173}", json);
	}

	@Test
	public void testExtendsTwo() {
		String json = JSONFactoryUtil.looseSerialize(new ExtendsTwo());

		Assert.assertEquals("{\"ftwo\":173}", json);
	}

	@Test
	public void testFour() {
		String json = JSONFactoryUtil.looseSerialize(new Four());

		Assert.assertTrue(json, json.contains("nuMber"));
		Assert.assertTrue(json, json.contains("vaLue"));
	}

	@Test
	public void testOne() {
		String json = JSONFactoryUtil.looseSerialize(new One());

		Assert.assertEquals("{\"fone\":\"string\",\"ftwo\":173}", json);
	}

	@Test
	public void testThree() {
		String json = JSONFactoryUtil.looseSerialize(new Three());

		Assert.assertEquals("{\"flag\":true}", json);
	}

	@Test
	public void testTwo() {
		String json = JSONFactoryUtil.looseSerialize(new Two());

		Assert.assertEquals("{\"ftwo\":173}", json);
	}

}