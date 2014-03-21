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

package com.liferay.portal.service;

import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class LayoutServiceTest {

	@Test
	public void testDeleteGuestGroupLastPrivateLayout() throws Exception {
		doTestDeleteGuestGroupLastLayout(true);
	}

	@Test
	public void testDeleteGuestGroupLastPublicLayout() throws Exception {
		doTestDeleteGuestGroupLastLayout(false);
	}

	protected void doTestDeleteGuestGroupLastLayout(boolean privateLayout)
		throws Exception {

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.GUEST);

		List<Layout> rootLayoutList = new ArrayList<Layout>();

		rootLayoutList.addAll(
			LayoutLocalServiceUtil.getLayouts(
				guestGroup.getGroupId(), privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID));

		if (rootLayoutList.isEmpty()) {
			rootLayoutList.add(
				LayoutTestUtil.addLayout(
					guestGroup.getGroupId(), StringUtil.randomString(),
					privateLayout));
		}

		ServiceContext serviceContext = new ServiceContext();

		try {
			LayoutLocalServiceUtil.deleteLayouts(
				guestGroup.getGroupId(), privateLayout, serviceContext);
		}
		catch (RequiredLayoutException rle) {
			if (privateLayout) {
				Assert.fail("Guest site may not have any private pages");
			}

			return;
		}

		if (!privateLayout) {
			Assert.fail("Guest site must have at least one public page");
		}
	}

}