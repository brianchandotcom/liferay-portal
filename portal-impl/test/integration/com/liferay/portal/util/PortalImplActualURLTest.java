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

package com.liferay.portal.util;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Vilmos Papp
 */
public class PortalImplActualURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testChildLayoutFriendlyURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		UserGroup userGroup = UserGroupLocalServiceUtil.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			"Test " + RandomTestUtil.nextInt(), StringPool.BLANK,
			serviceContext);

		_group = userGroup.getGroup();

		Layout homeLayout = LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			homeLayout.getLayoutId(), "Child Layout", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		String actualURL = PortalUtil.getActualURL(
			userGroup.getGroup().getGroupId(), true, Portal.PATH_MAIN,
			"/~/" + userGroup.getUserGroupId() + "/child-layout",
			new HashMap<String, String[]>(), getRequestContext());

		Assert.assertNotNull(actualURL);

		try {
			PortalUtil.getActualURL(
				userGroup.getGroup().getGroupId(), true, Portal.PATH_MAIN,
				"/~/" + userGroup.getUserGroupId() +
					"/non-existing-child-layout",
				new HashMap<String, String[]>(), getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	protected Map<String, Object> getRequestContext() {
		Map<String, Object> requestContext = new HashMap<>();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, "/");

		requestContext.put("request", mockHttpServletRequest);

		return requestContext;
	}

	@DeleteAfterTestRun
	private Group _group;

}