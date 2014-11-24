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

import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.PACLTestRule;
import com.liferay.portal.test.runners.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.TestPropsValues;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class PortalServicesTest {

	@ClassRule
	public static final PACLTestRule paclTestRule = PACLTestRule.INSTANCE;

	@Test
	public void test1() throws Exception {

		// We need CompanyLocalServiceUtil#getCompanyId to work for our message
		// bus listeners. Test CompanyLocalServiceUtil#getCompanyByWebId since
		// it is an unallowed method.

		try {
			CompanyLocalServiceUtil.getCompanyByWebId("liferay.com");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test2() throws Exception {
		try {
			GroupLocalServiceUtil.getGroup(TestPropsValues.getGroupId());
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void test3() throws Exception {
		try {
			UserLocalServiceUtil.getUser(TestPropsValues.getUserId());

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}