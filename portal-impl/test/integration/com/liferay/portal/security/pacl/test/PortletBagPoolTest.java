/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;
import com.liferay.portlet.PortletBagImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class PortletBagPoolTest {

	@Test
	public void test1() throws Exception {
		try {
			PortletBagPool.get("1_WAR_flashportlet");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
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
		try {
			PortletBagPool.get("flash-portlet");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void test4() throws Exception {
		try {
			PortletBagPool.put(
				"1_WAR_flashportlet",
				new PortletBagImpl(
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null));
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void test5() throws Exception {
		try {
			PortletBagPool.put(
				"fail",
				new PortletBagImpl(
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null));

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test6() throws Exception {
		try {
			PortletBagPool.put(
				"flash-portlet",
				new PortletBagImpl(
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, null, null));
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void test7() throws Exception {
		try {
			PortletBagPool.remove("1_WAR_flashportlet");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
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
		try {
			PortletBagPool.remove("flash-portlet");
		}
		catch (SecurityException se) {
			Assert.fail();
		}
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