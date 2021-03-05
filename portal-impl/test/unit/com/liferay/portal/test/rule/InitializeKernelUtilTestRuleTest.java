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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.util.PropsUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
@InitializeKernelUtil(properties = "testClassKey=testClassValue")
public class InitializeKernelUtilTestRuleTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testProps() {
		Assert.assertNotNull(PropsUtil.getProps());

		Assert.assertEquals("testClassValue", PropsUtil.get("testClassKey"));
	}

	@InitializeKernelUtil(properties = "testMethodKey=testMethodValue")
	@Test
	public void testPropsMethodAnnotation() {
		Assert.assertNotNull(PropsUtil.getProps());

		Assert.assertEquals("testClassValue", PropsUtil.get("testClassKey"));

		Assert.assertEquals("testMethodValue", PropsUtil.get("testMethodKey"));
	}

}