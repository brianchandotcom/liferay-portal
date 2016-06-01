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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.test.rule.PACLTestRule;
import com.liferay.portlet.PortletBagImpl;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class PortletBagPoolTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void test1() throws Exception {
		PortletBagPool.get("1_WAR_pacl_testportlet");
	}

	@Test
	public void test2() throws Exception {
		try {
			PortletBagPool.get("fail");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test3() throws Exception {
		PortletBagPool.get("pacl-test-portlet");
	}

	@Test
	public void test4() throws Exception {
		PortletBagPool.put(
			"1_WAR_flashportlet",
			new PortletBagImpl(
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null));
	}

	@Test
	public void test5() throws Exception {
		try {
			PortletBagPool.put(
				"fail",
				new PortletBagImpl(
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null,
					null));

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test6() throws Exception {
		PortletBagPool.put(
			"flash-portlet",
			new PortletBagImpl(
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null));
	}

	@Test
	public void test7() throws Exception {
		PortletBagPool.remove("1_WAR_flashportlet");
	}

	@Test
	public void test8() throws Exception {
		try {
			PortletBagPool.remove("fail");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test9() throws Exception {
		PortletBagPool.remove("flash-portlet");
	}

	@Test
	public void test10() throws Exception {
		try {
			PortletBagPool.reset();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}