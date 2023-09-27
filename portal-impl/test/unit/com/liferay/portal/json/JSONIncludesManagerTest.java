/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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