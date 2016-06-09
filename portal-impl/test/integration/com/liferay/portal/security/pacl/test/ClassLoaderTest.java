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

import com.liferay.blogs.kernel.service.BlogsEntryLocalService;
import com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.PACLTestRule;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class ClassLoaderTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Test
	public void testCreate1() throws Exception {
		try {
			Class<?> clazz = getClass();

			new URLClassLoader(new URL[0], clazz.getClassLoader());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet1() throws Exception {
		try {
			BlogsEntryLocalService blogsEntryLocalService =
				BlogsEntryLocalServiceUtil.getService();

			Class<?> clazz = blogsEntryLocalService.getClass();

			clazz.getClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet2() throws Exception {
		try {
			ClassLoader.getSystemClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet3() throws Exception {
		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			TestPropsValues.getCompanyId());

		defaultUser.toEscapedModel();
	}

	@Test
	public void testGet4() throws Exception {
		BeanPropertyTest.class.getClassLoader();
	}

	@Test
	public void testGet5() throws Exception {
		Class<?> clazz = getClass();

		clazz.getClassLoader();
	}

	@Test
	public void testGet6() throws Exception {
		Object.class.getClassLoader();
	}

	@Test
	public void testGet7() throws Exception {
		Object object = new Object();

		Class<?> clazz = object.getClass();

		clazz.getClassLoader();
	}

	@Test
	public void testGet8() throws Exception {
		try {
			Portal portal = PortalUtil.getPortal();

			Class<?> clazz = portal.getClass();

			clazz.getClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet9() throws Exception {
		try {
			PortalClassLoaderUtil.getClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet10() throws Exception {
		try {
			PortalRuntimePermission.checkGetClassLoader("");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet11() throws Exception {
		PortalRuntimePermission.checkGetClassLoader("pacl-test-portlet");
	}

	@Test
	public void testGet12() throws Exception {
		try {
			PortalRuntimePermission.checkGetClassLoader(null);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet13() throws Exception {
		try {
			PortalRuntimePermission.checkGetClassLoader("portal");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet14() throws Exception {
		try {
			PortalRuntimePermission.class.getClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet15() throws Exception {
		try {
			PortletClassLoaderUtil.getClassLoader("1_WAR_chatportlet");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet16() throws Exception {
		PortletClassLoaderUtil.getClassLoader("1_WAR_pacl_testportlet");
	}

	@Test
	public void testGet17() throws Exception {
		try {
			PortletClassLoaderUtil.getClassLoader("chat-portlet");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet18() throws Exception {
		PortletClassLoaderUtil.getClassLoader("pacl-test-portlet");
	}

	@Test
	public void testGet19() throws Exception {
		try {
			Thread thread = Thread.currentThread();

			thread.getContextClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet20() throws Exception {
		try {
			User.class.getClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testGet21() throws Exception {
		try {
			UserLocalService userLocalService =
				UserLocalServiceUtil.getService();

			Class<?> clazz = userLocalService.getClass();

			clazz.getClassLoader();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}