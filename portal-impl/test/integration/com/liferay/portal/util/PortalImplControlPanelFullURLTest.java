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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.VirtualLayoutConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Julio Camarero
 */
public class PortalImplControlPanelFullURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testControlPanelPortlet() throws Exception {
		StringBuilder sb = new StringBuilder(5);

		sb.append(getPortalURL());
		sb.append(_portalImpl.getPathFriendlyURLPrivateGroup());
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		String portletId = PortletKeys.SERVER_ADMIN;

		sb.append(getQueryString(portletId));

		Assert.assertEquals(
			sb.toString(),
			_portalImpl.getControlPanelFullURL(
				_group.getGroupId(), portletId, null));
	}

	@Test
	public void testMyAccountPortlet() throws Exception {
		StringBuilder sb = new StringBuilder(5);

		sb.append(getPortalURL());

		sb.append(_portalImpl.getPathFriendlyURLPrivateGroup());
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		String portletId = PortletKeys.MY_ACCOUNT;

		sb.append(getQueryString(portletId));

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			_group.getCompanyId(), portletId);

		Assert.assertEquals(
			portlet.toString() + ", controlPanelEntryCategory=" +
				portlet.getControlPanelEntryCategory(),
			sb.toString(),
			_portalImpl.getControlPanelFullURL(
				_group.getGroupId(), portletId, null));
	}

	@Test
	public void testSiteAdministrationPortlet() throws Exception {
		StringBuilder sb = new StringBuilder(7);

		sb.append(getPortalURL());
		sb.append(_portalImpl.getPathFriendlyURLPrivateGroup());
		sb.append(_group.getFriendlyURL());
		sb.append(VirtualLayoutConstants.CANONICAL_URL_SEPARATOR);
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		String portletId = "com_liferay_journal_web_portlet_JournalPortlet";

		sb.append(getQueryString(portletId));

		Assert.assertEquals(
			sb.toString(),
			_portalImpl.getControlPanelFullURL(
				_group.getGroupId(), portletId, null));
	}

	protected String getPortalURL() throws PortalException {
		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		return _portalImpl.getPortalURL(
			company.getVirtualHostname(),
			_portalImpl.getPortalServerPort(false), false);
	}

	protected String getQueryString(String portletId) {
		StringBuilder sb = new StringBuilder(6);

		sb.append("?p_p_id=");
		sb.append(portletId);
		sb.append("&p_p_lifecycle=0&p_p_state=");
		sb.append(WindowState.MAXIMIZED.toString());
		sb.append("&p_p_mode=");
		sb.append(PortletMode.VIEW.toString());

		return sb.toString();
	}

	@DeleteAfterTestRun
	private Group _group;

	private final PortalImpl _portalImpl = new PortalImpl();

}