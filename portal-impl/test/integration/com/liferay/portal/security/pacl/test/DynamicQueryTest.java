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

import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class DynamicQueryTest {

	@Test
	public void test1() throws Exception {
		try {
			DynamicQueryFactoryUtil.forClass(Group.class);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test2() throws Exception {
		try {
			DynamicQueryFactoryUtil.forClass(Role.class);

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void test3() throws Exception {
		try {
			GroupLocalServiceUtil.dynamicQuery();
		}
		catch (SecurityException se) {
			Assert.fail();
		}
	}

	@Test
	public void test4() throws Exception {
		try {
			UserLocalServiceUtil.dynamicQuery();

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

}