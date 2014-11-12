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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.listeners.ResetDatabaseExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.util.PortletKeys;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		ResetDatabaseExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LayoutTypePortletTest {

	@Test
	public void testAddModeAboutPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(layoutTypePortlet.hasModeAboutPortletId(portletId));

		layoutTypePortlet.addModeAboutPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeAboutPortletId(portletId));
	}

	@Test
	public void testAddModeConfigPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(layoutTypePortlet.hasModeConfigPortletId(portletId));

		layoutTypePortlet.addModeConfigPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeConfigPortletId(portletId));
	}

	@Test
	public void testAddModeEditDefaultsPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(
			layoutTypePortlet.hasModeEditDefaultsPortletId(portletId));

		layoutTypePortlet.addModeEditDefaultsPortletId(portletId);

		Assert.assertTrue(
			layoutTypePortlet.hasModeEditDefaultsPortletId(portletId));
	}

	@Test
	public void testAddModeEditGuestPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(
			layoutTypePortlet.hasModeEditGuestPortletId(portletId));

		layoutTypePortlet.addModeEditGuestPortletId(portletId);

		Assert.assertTrue(
			layoutTypePortlet.hasModeEditGuestPortletId(portletId));
	}

	@Test
	public void testAddModeEditPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(layoutTypePortlet.hasModeEditPortletId(portletId));

		layoutTypePortlet.addModeEditPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeEditPortletId(portletId));
	}

	@Test
	public void testAddModeHelpPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(layoutTypePortlet.hasModeHelpPortletId(portletId));

		layoutTypePortlet.addModeHelpPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeHelpPortletId(portletId));
	}

	@Test
	public void testAddModePreviewPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(
			layoutTypePortlet.hasModePreviewPortletId(portletId));

		layoutTypePortlet.addModePreviewPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModePreviewPortletId(portletId));
	}

	@Test
	public void testAddModePrintPortletId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		String portletId = PortletKeys.TEST;

		Assert.assertFalse(layoutTypePortlet.hasModePrintPortletId(portletId));

		layoutTypePortlet.addModePrintPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModePrintPortletId(portletId));
	}

	@Test
	public void testAddPortletIdCheckColumn() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		Layout layout = layoutTypePortlet.getLayout();

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = PortletKeys.TEST;

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		String column1 = columns.get(0);

		Assert.assertEquals(2, columns.size());

		portletId = layoutTypePortlet.addPortletId(user.getUserId(), portletId);

		Assert.assertNotNull(portletId);

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(column1);

		Assert.assertEquals(1, portlets.size());
	}

	@Test
	public void testAddPortletIdColumn2() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		Layout layout = layoutTypePortlet.getLayout();

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = PortletKeys.TEST;

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		Assert.assertEquals(2, columns.size());

		String column1 = columns.get(0);
		String column2 = columns.get(1);

		portletId = layoutTypePortlet.addPortletId(
			user.getUserId(), portletId, column2, -1);

		Assert.assertNotNull(portletId);

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(column1);

		Assert.assertEquals(0, portlets.size());

		portlets = layoutTypePortlet.getAllPortlets(column2);

		Assert.assertEquals(1, portlets.size());
	}

	@Test
	public void testAddPortletIdWithInvalidId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		Layout layout = layoutTypePortlet.getLayout();

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = RandomTestUtil.randomString();

		portletId = layoutTypePortlet.addPortletId(user.getUserId(), portletId);

		Assert.assertNull(portletId);
	}

	@Test
	public void testAddPortletIdWithInvalidIdWithoutPermission()
		throws Exception {

		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		Layout layout = layoutTypePortlet.getLayout();

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = RandomTestUtil.randomString();

		portletId = layoutTypePortlet.addPortletId(user.getUserId(), portletId);

		Assert.assertNull(portletId);
	}

	@Test
	public void testAddPortletIdWithValidId() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		Layout layout = layoutTypePortlet.getLayout();

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = PortletKeys.TEST;

		portletId = layoutTypePortlet.addPortletId(user.getUserId(), portletId);

		Assert.assertNotNull(portletId);
	}

	@Test
	public void testGetAllPortlets() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		Layout layout = layoutTypePortlet.getLayout();

		User user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = layoutTypePortlet.addPortletId(
			user.getUserId(), PortletKeys.TEST);

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(1, portlets.size());

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			TestPropsValues.getCompanyId(), portletId);

		PortletInstanceFactoryUtil.destroy(portlet);

		portlets = layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(0, portlets.size());
	}

	@Test
	public void testNoPortlets() throws Exception {
		LayoutTypePortlet layoutTypePortlet = getLayoutTypePortlet();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(0, portlets.size());
	}

	protected LayoutTypePortlet getLayoutTypePortlet() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group, false);

		return (LayoutTypePortlet)layout.getLayoutType();
	}

}