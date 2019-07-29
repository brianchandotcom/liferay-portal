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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Vilmos Papp
 */
@RunWith(Arquillian.class)
public class PortalImplActualURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testChildLayoutFriendlyURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			"Test " + RandomTestUtil.nextInt(), StringPool.BLANK,
			serviceContext);

		_group = userGroup.getGroup();

		Layout homeLayout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		_layoutLocalService.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			homeLayout.getLayoutId(), "Child Layout", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		Assert.assertNotNull(
			_portal.getActualURL(
				_group.getGroupId(), true, Portal.PATH_MAIN,
				"/~/" + userGroup.getUserGroupId() + "/child-layout",
				new HashMap<>(), _getRequestContext()));

		try {
			_portal.getActualURL(
				_group.getGroupId(), true, Portal.PATH_MAIN,
				"/~/" + userGroup.getUserGroupId() +
					"/nonexistent-child-layout",
				new HashMap<>(), _getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	@Test
	public void testNodeLayoutActualURL() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			"Test " + RandomTestUtil.nextInt(), StringPool.BLANK,
			serviceContext);

		_group = userGroup.getGroup();

		Layout homeLayout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		Layout nodeLayout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			homeLayout.getLayoutId(), "Node", StringPool.BLANK,
			StringPool.BLANK, "node", false, StringPool.BLANK, serviceContext);

		Layout childLayout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			nodeLayout.getLayoutId(), "Child Layout", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		String queryString = _http.getQueryString(
			_portal.getActualURL(
				_group.getGroupId(), true, Portal.PATH_MAIN,
				"/~/" + userGroup.getUserGroupId() + "/node", new HashMap<>(),
				_getRequestContext()));

		Map<String, String[]> parameterMap = _http.getParameterMap(queryString);

		Assert.assertNull(parameterMap.get("p_l_id"));

		Assert.assertEquals(
			childLayout.getGroupId(), MapUtil.getLong(parameterMap, "groupId"));

		Assert.assertEquals(
			childLayout.isPrivateLayout(),
			MapUtil.getBoolean(parameterMap, "privateLayout"));

		Assert.assertEquals(
			childLayout.getLayoutId(),
			MapUtil.getLong(parameterMap, "layoutId"));
	}

	private Map<String, Object> _getRequestContext() {
		Map<String, Object> requestContext = new HashMap<>();

		requestContext.put(
			"request", new MockHttpServletRequest(Method.GET, "/"));

		return requestContext;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Http _http;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}