/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
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
		catch (NoSuchLayoutException noSuchLayoutException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchLayoutException);
			}
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
			StringPool.BLANK, LayoutConstants.TYPE_NODE, false,
			StringPool.BLANK, serviceContext);

		Layout childLayout = _layoutLocalService.addLayout(
			serviceContext.getUserId(), _group.getGroupId(), true,
			nodeLayout.getLayoutId(), "Child Layout", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		Map<String, String[]> parameterMap = HttpComponentsUtil.getParameterMap(
			HttpComponentsUtil.getQueryString(
				_portal.getActualURL(
					_group.getGroupId(), true, Portal.PATH_MAIN,
					"/~/" + userGroup.getUserGroupId() + "/node",
					new HashMap<>(), _getRequestContext())));

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
		return HashMapBuilder.<String, Object>put(
			"request", new MockHttpServletRequest(Method.GET, "/")
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalImplActualURLTest.class);

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}