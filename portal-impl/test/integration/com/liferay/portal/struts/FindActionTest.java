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

package com.liferay.portal.struts;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.BlogsTestUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Julio Camarero
 * @author Laszlo Csontos
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class FindActionTest {

	@Test
	public void testGetPlidAndPortletIdViewInContext() throws Exception {
		addLayouts(true, false);

		Object[] plidAndPorltetId = FindAction.getPlidAndPortletId(
			getThemeDisplay(), _blogsEntry.getGroupId(), _assetLayout.getPlid(),
			_PORTLET_IDS);

		Assert.assertEquals(_blogLayout.getPlid(), plidAndPorltetId[0]);
		Assert.assertEquals(PortletKeys.BLOGS, plidAndPorltetId[1]);
	}

	@Test
	public void testGetPlidAndPortletIdWhenPortletDoesNotExist()
		throws Exception {

		addLayouts(false, false);

		try {
			FindAction.getPlidAndPortletId(
				getThemeDisplay(), _blogsEntry.getGroupId(),
				_assetLayout.getPlid(), _PORTLET_IDS);

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	@Test
	public void testSetTargetGroupWithDifferentGroup() throws Exception {
		addLayouts(true, true);

		HttpServletRequest request = getHttpServletRequest();

		FindAction.setTargetGroup(
			request, _blogsEntry.getGroupId(), _blogLayout.getPlid());

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		Assert.assertTrue(layout instanceof VirtualLayout);
		Assert.assertNotEquals(_group.getGroupId(), layout.getGroupId());
	}

	@Test
	public void testSetTargetGroupWithSameGroup() throws Exception {
		addLayouts(true, false);

		HttpServletRequest request = getHttpServletRequest();

		FindAction.setTargetGroup(
			request, _blogsEntry.getGroupId(), _blogLayout.getPlid());

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		Assert.assertNull(layout);
	}

	protected void addLayouts(
			boolean portletExists, boolean blogEntryWithDifferentGroup)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_blogLayout = LayoutTestUtil.addLayout(_group.getGroupId(), "Blog");
		_assetLayout = LayoutTestUtil.addLayout(_group.getGroupId(), "Asset");

		if (portletExists) {
			LayoutTestUtil.addPortletToLayout(_blogLayout, PortletKeys.BLOGS);
		}

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put("assetLinkBehavior", new String[] {"viewInPortlet"});

		_assetPublisherPortletId =
			PortletKeys.ASSET_PUBLISHER + PortletConstants.INSTANCE_SEPARATOR +
				ServiceTestUtil.randomString();

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _assetLayout, _assetPublisherPortletId,
			"column-1", preferenceMap);

		Group group = _group;

		if (blogEntryWithDifferentGroup) {
			group = GroupTestUtil.addGroup();
		}

		_blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);
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

		return themeDisplay;
	}

	private final static String[] _PORTLET_IDS = {
		PortletKeys.BLOGS_ADMIN, PortletKeys.BLOGS, PortletKeys.BLOGS_AGGREGATOR
	};

	private Layout _assetLayout;
	private String _assetPublisherPortletId;
	private Layout _blogLayout;
	private BlogsEntry _blogsEntry;
	private Group _group;

}