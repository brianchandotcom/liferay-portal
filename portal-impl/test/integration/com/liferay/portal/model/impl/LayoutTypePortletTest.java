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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.impl.PortletLocalServiceImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.util.test.PortletKeys;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class LayoutTypePortletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(_group, false);

		_layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();
	}

	@Test
	public void testAddModeAboutPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(_layoutTypePortlet.hasModeAboutPortletId(portletId));

		_layoutTypePortlet.addModeAboutPortletId(portletId);

		Assert.assertTrue(_layoutTypePortlet.hasModeAboutPortletId(portletId));
	}

	@Test
	public void testAddModeConfigPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(
			_layoutTypePortlet.hasModeConfigPortletId(portletId));

		_layoutTypePortlet.addModeConfigPortletId(portletId);

		Assert.assertTrue(_layoutTypePortlet.hasModeConfigPortletId(portletId));
	}

	@Test
	public void testAddModeEditDefaultsPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(
			_layoutTypePortlet.hasModeEditDefaultsPortletId(portletId));

		_layoutTypePortlet.addModeEditDefaultsPortletId(portletId);

		Assert.assertTrue(
			_layoutTypePortlet.hasModeEditDefaultsPortletId(portletId));
	}

	@Test
	public void testAddModeEditGuestPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(
			_layoutTypePortlet.hasModeEditGuestPortletId(portletId));

		_layoutTypePortlet.addModeEditGuestPortletId(portletId);

		Assert.assertTrue(
			_layoutTypePortlet.hasModeEditGuestPortletId(portletId));
	}

	@Test
	public void testAddModeEditPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(_layoutTypePortlet.hasModeEditPortletId(portletId));

		_layoutTypePortlet.addModeEditPortletId(portletId);

		Assert.assertTrue(_layoutTypePortlet.hasModeEditPortletId(portletId));
	}

	@Test
	public void testAddModeHelpPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(_layoutTypePortlet.hasModeHelpPortletId(portletId));

		_layoutTypePortlet.addModeHelpPortletId(portletId);

		Assert.assertTrue(_layoutTypePortlet.hasModeHelpPortletId(portletId));
	}

	@Test
	public void testAddModePreviewPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(
			_layoutTypePortlet.hasModePreviewPortletId(portletId));

		_layoutTypePortlet.addModePreviewPortletId(portletId);

		Assert.assertTrue(
			_layoutTypePortlet.hasModePreviewPortletId(portletId));
	}

	@Test
	public void testAddModePrintPortletId() throws Exception {
		String portletId = PortletKeys.TEST;

		Assert.assertFalse(_layoutTypePortlet.hasModePrintPortletId(portletId));

		_layoutTypePortlet.addModePrintPortletId(portletId);

		Assert.assertTrue(_layoutTypePortlet.hasModePrintPortletId(portletId));
	}

	@Test
	public void testAddPortletIdCheckColumn() throws Exception {
		Layout layout = _layoutTypePortlet.getLayout();

		_user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = PortletKeys.TEST;

		LayoutTemplate layoutTemplate = _layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		String column1 = columns.get(0);

		Assert.assertEquals(2, columns.size());

		portletId = _layoutTypePortlet.addPortletId(
			_user.getUserId(), portletId);

		Assert.assertNotNull(portletId);

		List<Portlet> portlets = _layoutTypePortlet.getAllPortlets(column1);

		Assert.assertEquals(1, portlets.size());
	}

	@Test
	public void testAddPortletIdColumn2() throws Exception {
		Layout layout = _layoutTypePortlet.getLayout();

		_user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = PortletKeys.TEST;

		LayoutTemplate layoutTemplate = _layoutTypePortlet.getLayoutTemplate();

		List<String> columns = layoutTemplate.getColumns();

		Assert.assertEquals(2, columns.size());

		String column1 = columns.get(0);
		String column2 = columns.get(1);

		portletId = _layoutTypePortlet.addPortletId(
			_user.getUserId(), portletId, column2, -1);

		Assert.assertNotNull(portletId);

		List<Portlet> portlets = _layoutTypePortlet.getAllPortlets(column1);

		Assert.assertEquals(0, portlets.size());

		portlets = _layoutTypePortlet.getAllPortlets(column2);

		Assert.assertEquals(1, portlets.size());
	}

	@Test
	public void testAddPortletIdWithInvalidId() throws Exception {
		Layout layout = _layoutTypePortlet.getLayout();

		_user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = RandomTestUtil.randomString();

		portletId = _layoutTypePortlet.addPortletId(
			_user.getUserId(), portletId);

		Assert.assertNull(portletId);
	}

	@Test
	public void testAddPortletIdWithValidId() throws Exception {
		Layout layout = _layoutTypePortlet.getLayout();

		_user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		String portletId = PortletKeys.TEST;

		portletId = _layoutTypePortlet.addPortletId(
			_user.getUserId(), portletId);

		Assert.assertNotNull(portletId);
	}

	@Test
	public void testGetAllPortlets() throws Exception {
		Layout layout = _layoutTypePortlet.getLayout();

		_user = UserTestUtil.addUser(
			RandomTestUtil.randomString(), layout.getGroupId());

		final String portletId = _layoutTypePortlet.addPortletId(
			_user.getUserId(), PortletKeys.TEST);

		List<Portlet> portlets = _layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(1, portlets.size());

		final long companyId = TestPropsValues.getCompanyId();

		PortletLocalService portletLocalService =
			PortletLocalServiceUtil.getService();

		ReflectionTestUtil.setFieldValue(
			PortletLocalServiceUtil.class, "_service",
			new PortletLocalServiceImpl() {

				@Override
				public Portlet getPortletById(
					long localCompanyId, String localPortletId) {

					Portlet portlet = super.getPortletById(
						localCompanyId, localPortletId);

					if ((companyId == localCompanyId) &&
						portletId.equals(localPortletId)) {

						portlet = (Portlet)portlet.clone();

						portlet.setUndeployedPortlet(true);
					}

					return portlet;
				}

			});

		try {
			portlets = _layoutTypePortlet.getAllPortlets();

			Assert.assertTrue(portlets.isEmpty());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PortletLocalServiceUtil.class, "_service", portletLocalService);
		}
	}

	@Test
	public void testNoPortlets() throws Exception {
		List<Portlet> portlets = _layoutTypePortlet.getAllPortlets();

		Assert.assertEquals(0, portlets.size());
	}

	@DeleteAfterTestRun
	private Group _group;

	private LayoutTypePortlet _layoutTypePortlet;

	@DeleteAfterTestRun
	private User _user;

}