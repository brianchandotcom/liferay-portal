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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
@PortalProps(properties = "testClassKey=testClassValue")
public class InitializeKernelUtilTestRuleTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCalendarFactoryUtil() {
		Assert.assertNotNull(CalendarFactoryUtil.getCalendar());
	}

	@Test
	public void testFileUtil() {
		Assert.assertNotNull(FileUtil.getFile());
	}

	@Test
	public void testHtmlUtil() {
		Assert.assertNotNull(HtmlUtil.getHtml());
	}

	@Test
	public void testHttpUtil() {
		Assert.assertNotNull(HttpUtil.getHttp());
	}

	@Test
	public void testJSONFactoryUtil() {
		Assert.assertNotNull(JSONFactoryUtil.getJSONFactory());
	}

	@Test
	public void testProps() {
		Assert.assertNotNull(PropsUtil.getProps());

		Assert.assertEquals("testClassValue", PropsUtil.get("testClassKey"));
	}

	@PortalProps(properties = "testMethodKey=testMethodValue")
	@Test
	public void testPropsMethodAnnotation() {
		Assert.assertNotNull(PropsUtil.getProps());

		Assert.assertEquals("testClassValue", PropsUtil.get("testClassKey"));

		Assert.assertEquals("testMethodValue", PropsUtil.get("testMethodKey"));
	}

	@Test
	public void testPropsUtil() {
		Assert.assertNotNull(PropsUtil.getProps());
	}

}