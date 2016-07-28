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

package com.liferay.portal.struts;

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.portlet.BasePortletLayoutFinder;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Julio Camarero
 * @author Laszlo Csontos
 * @author Eduardo Garcia
 */
@Sync
public class FindActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_portletIds = new String[] {
			PortletProviderUtil.getPortletId(
				BlogsEntry.class.getName(), PortletProvider.Action.MANAGE),
			PortletProviderUtil.getPortletId(
				BlogsEntry.class.getName(), PortletProvider.Action.VIEW)
		};

		_portletLayoutFinder = new BasePortletLayoutFinder() {

			@Override
			protected String[] getPortletIds() {
				return _portletIds;
			}

		};
	}

	@Test
	public void testGetPlidAndPortletIdViewInContext() throws Exception {
		addLayouts(true, false);

		PortletLayoutFinder.Result result = _portletLayoutFinder.find(
			getThemeDisplay(), _blogsEntryGroupId);

		Assert.assertEquals(_blogLayout.getPlid(), result.getPlid());

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

		Assert.assertEquals(portletId, result.getPortletId());
	}

	@Test
	public void testGetPlidAndPortletIdWhenPortletDoesNotExist()
		throws Exception {

		addLayouts(false, false);

		try {
			_portletLayoutFinder.find(getThemeDisplay(), _blogsEntryGroupId);

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	@Test
	public void testSetTargetGroupWithDifferentGroup() throws Exception {
		addLayouts(true, true);

		HttpServletRequest request = getHttpServletRequest();

		BaseFindActionHelper.setTargetLayout(
			request, _blogsEntryGroupId, _blogLayout.getPlid());

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		Assert.assertTrue(layout instanceof VirtualLayout);
		Assert.assertNotEquals(_group.getGroupId(), layout.getGroupId());
	}

	@Test
	public void testSetTargetGroupWithSameGroup() throws Exception {
		addLayouts(true, false);

		HttpServletRequest request = getHttpServletRequest();

		BaseFindActionHelper.setTargetLayout(
			request, _blogsEntryGroupId, _blogLayout.getPlid());

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		Assert.assertNull(layout);
	}

	protected void addLayouts(
			boolean portletExists, boolean blogEntryWithDifferentGroup)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_blogLayout = LayoutTestUtil.addLayout(_group);
		_assetLayout = LayoutTestUtil.addLayout(_group);

		if (portletExists) {
			String portletId = PortletProviderUtil.getPortletId(
				BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

			LayoutTestUtil.addPortletToLayout(_blogLayout, portletId);
		}

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put("assetLinkBehavior", new String[] {"viewInPortlet"});

		PortletInstance portletInstance = new PortletInstance(
			com.liferay.portlet.util.test.PortletKeys.TEST);

		_testPortletId = portletInstance.getPortletInstanceKey();

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _assetLayout, _testPortletId,
			"column-1", preferenceMap);

		Group group = _group;

		if (blogEntryWithDifferentGroup) {
			group = GroupTestUtil.addGroup();
		}

		_blogsEntryGroupId = group.getGroupId();
	}

	protected HttpServletRequest getHttpServletRequest() throws Exception {
		HttpServletRequest request = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = getThemeDisplay();

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return request;
	}

	protected ThemeDisplay getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		themeDisplay.setPermissionChecker(permissionChecker);

		themeDisplay.setPlid(_assetLayout.getPlid());

		return themeDisplay;
	}

	private static String[] _portletIds;

	private Layout _assetLayout;
	private Layout _blogLayout;
	private long _blogsEntryGroupId;

	@DeleteAfterTestRun
	private Group _group;

	private PortletLayoutFinder _portletLayoutFinder;
	private String _testPortletId;

}