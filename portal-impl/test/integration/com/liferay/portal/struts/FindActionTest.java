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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		createPages(true);

		Object[] plidAndPorltetId = FindAction.getPlidAndPortletId(
			createThemeDisplay(), _blogsEntry.getGroupId(), _asset.getPlid(),
			_portletIds);

		Assert.assertEquals(_blog.getPlid(), plidAndPorltetId[0]);
		Assert.assertEquals(PortletKeys.BLOGS, plidAndPorltetId[1]);
	}

	@Test
	public void testGetPlidAndPortletIdWhenPortletDoesNotExist()
		throws Exception {

		createPages(false);

		try {
			FindAction.getPlidAndPortletId(
				createThemeDisplay(), _blogsEntry.getGroupId(),
				_asset.getPlid(), _portletIds);

			Assert.fail("The page should have not be found");
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	protected void createPages(boolean portletExists) throws Exception {
		_group = GroupTestUtil.addGroup();

		_blog = LayoutTestUtil.addLayout(_group.getGroupId(), "blog");
		_asset = LayoutTestUtil.addLayout(_group.getGroupId(), "asset");

		if (portletExists) {
			LayoutTestUtil.addPortletToLayout(_blog, PortletKeys.BLOGS);
		}

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put("assetLinkBehavior", new String[] {"viewInPortlet"});

		_assetPublisherPortletId =
			PortletKeys.ASSET_PUBLISHER + PortletConstants.INSTANCE_SEPARATOR +
			ServiceTestUtil.randomString();

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _asset, _assetPublisherPortletId,
			"column-1", preferenceMap);

		_blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);
	}

	protected ThemeDisplay createThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		themeDisplay.setPermissionChecker(permissionChecker);

		return themeDisplay;
	}

	private Layout _asset;
	private String _assetPublisherPortletId;
	private Layout _blog;
	private BlogsEntry _blogsEntry;
	private Group _group;
	private String[] _portletIds = {
		PortletKeys.BLOGS_ADMIN, PortletKeys.BLOGS,
		PortletKeys.BLOGS_AGGREGATOR};

}