/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LayoutTypePortletTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testBasic() throws Exception {
		LayoutType layoutType = getLayoutType();

		Assert.assertTrue(layoutType instanceof LayoutTypePortlet);
	}

	@Test
	@Transactional
	public void testNoPortlets() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(0, portlets.size());
	}

	@Test
	@Transactional
	public void testAddModeAboutPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModeAboutPortletId(portletId));

		layoutTypePortlet.addModeAboutPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeAboutPortletId(portletId));
	}

	@Test
	@Transactional
	public void testAddModeConfigPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModeConfigPortletId(portletId));

		layoutTypePortlet.addModeConfigPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeConfigPortletId(portletId));
	}

	@Test
	@Transactional
	public void testAddModeEditDefaultsPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModeEditDefaultsPortletId(
			portletId));

		layoutTypePortlet.addModeEditDefaultsPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeEditDefaultsPortletId(
			portletId));
	}

	@Test
	@Transactional
	public void testAddModeEditGuestPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModeEditGuestPortletId(
			portletId));

		layoutTypePortlet.addModeEditGuestPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeEditGuestPortletId(
			portletId));
	}

	@Test
	@Transactional
	public void testAddModeEditPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModeEditPortletId(portletId));

		layoutTypePortlet.addModeEditPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeEditPortletId(portletId));
	}

	@Test
	@Transactional
	public void testAddModeHelpPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModeHelpPortletId(portletId));

		layoutTypePortlet.addModeHelpPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModeHelpPortletId(portletId));
	}

	@Test
	@Transactional
	public void testAddModePreviewPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModePreviewPortletId(
			portletId));

		layoutTypePortlet.addModePreviewPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModePreviewPortletId(
			portletId));
	}

	@Test
	@Transactional
	public void testAddModePrintPortletId() throws Exception {
		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		String portletId = PortletKeys.JOURNAL_CONTENT;

		Assert.assertFalse(layoutTypePortlet.hasModePrintPortletId(
			portletId));

		layoutTypePortlet.addModePrintPortletId(portletId);

		Assert.assertTrue(layoutTypePortlet.hasModePrintPortletId(
			portletId));
	}

	@Test
	@Transactional
	public void testAddPortletId_WithInvalidIdWithoutPermission()
		throws Exception {

		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		User user = ServiceTestUtil.addUser(
			ServiceTestUtil.randomString(), false,
			new long[] {layoutType.getLayout().getGroupId()});

		long userId = user.getUserId();

		String portletId = ServiceTestUtil.randomString();

		portletId = layoutTypePortlet.addPortletId(userId, portletId);

		Assert.assertNull(portletId);
	}

	@Test
	@Transactional
	public void testAddPortletId_WithInvalidId()
		throws Exception {

		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		User user = ServiceTestUtil.addUser(
			ServiceTestUtil.randomString(), false,
			new long[] {layoutType.getLayout().getGroupId()});

		long userId = user.getUserId();

		String portletId = ServiceTestUtil.randomString();

		portletId = layoutTypePortlet.addPortletId(userId, portletId);

		Assert.assertNull(portletId);
	}

	@Test
	@Transactional
	public void testAddPortletId_WithValidId()
		throws Exception {

		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		User user = ServiceTestUtil.addUser(
			ServiceTestUtil.randomString(), false,
			new long[] {layoutType.getLayout().getGroupId()});

		long userId = user.getUserId();

		String portletId = PortletKeys.JOURNAL_CONTENT;

		portletId = layoutTypePortlet.addPortletId(userId, portletId);

		Assert.assertNotNull(portletId);
	}

	@Test
	@Transactional
	public void testAddPortletId_CheckColumn()
		throws Exception {

		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		User user = ServiceTestUtil.addUser(
			ServiceTestUtil.randomString(), false,
			new long[] {layoutType.getLayout().getGroupId()});

		long userId = user.getUserId();

		String portletId = PortletKeys.JOURNAL_CONTENT;

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		String column1 = columns.get(0);

		Assert.assertEquals(2, columns.size());

		portletId = layoutTypePortlet.addPortletId(userId, portletId);

		Assert.assertNotNull(portletId);

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(column1);

		Assert.assertEquals(1, portlets.size());
	}

	@Test
	@Transactional
	public void testAddPortletId_Column2()
		throws Exception {

		LayoutType layoutType = getLayoutType();

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		User user = ServiceTestUtil.addUser(
			ServiceTestUtil.randomString(), false,
			new long[] {layoutType.getLayout().getGroupId()});

		long userId = user.getUserId();

		String portletId = PortletKeys.JOURNAL_CONTENT;

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		Assert.assertEquals(2, columns.size());

		String column1 = columns.get(0);
		String column2 = columns.get(1);

		portletId = layoutTypePortlet.addPortletId(
			userId, portletId, column2, -1);

		Assert.assertNotNull(portletId);

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(column1);

		Assert.assertEquals(0, portlets.size());

		portlets = layoutTypePortlet.getAllPortlets(column2);

		Assert.assertEquals(1, portlets.size());
	}

	protected LayoutType getLayoutType() throws Exception {
		Group group = ServiceTestUtil.addGroup(ServiceTestUtil.randomString());

		Layout layout = ServiceTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString(), false);

		return layout.getLayoutType();
	}

}