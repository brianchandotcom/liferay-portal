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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class PortalInstancesTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_virtualHostsDefaultSiteName = ReflectionTestUtil.getAndSetFieldValue(
			PropsValues.class, "VIRTUAL_HOSTS_DEFAULT_SITE_NAME", "Guest");

		_company = CompanyTestUtil.addCompany();

		Group defaultGroup = _groupLocalService.getGroup(
			_company.getCompanyId(), GroupConstants.GUEST);

		_defaultGroupPublicLayout = _layoutLocalService.fetchDefaultLayout(
			defaultGroup.getGroupId(), false);

		Group nondefaultGroup = GroupTestUtil.addGroup(
			_company.getCompanyId(), TestPropsValues.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		LayoutTestUtil.addTypePortletLayout(nondefaultGroup, false);

		_nondefaultGroupPublicLayout = _layoutLocalService.fetchDefaultLayout(
			nondefaultGroup.getGroupId(), false);

		_nondefaultGroupPublicLayoutHostname =
			RandomTestUtil.randomString(6) + "." +
				RandomTestUtil.randomString(3);

		_updateLayoutSetVirtualHostname(
			_nondefaultGroupPublicLayout, _nondefaultGroupPublicLayoutHostname);
	}

	@After
	public void tearDown() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "VIRTUAL_HOSTS_DEFAULT_SITE_NAME",
			_virtualHostsDefaultSiteName);
	}

	@Test
	public void testGetCompanyId() {
		_updateLayoutSetVirtualHostname(
			_defaultGroupPublicLayout, StringPool.BLANK);

		_testGetCompanyId(
			_company.getVirtualHostname(),
			_defaultGroupPublicLayout.getLayoutSet());
		_testGetCompanyId(
			_nondefaultGroupPublicLayoutHostname,
			_nondefaultGroupPublicLayout.getLayoutSet());

		String defaultGroupPublicLayoutHostname =
			RandomTestUtil.randomString(6) + "." +
				RandomTestUtil.randomString(3);

		_updateLayoutSetVirtualHostname(
			_defaultGroupPublicLayout, defaultGroupPublicLayoutHostname);

		_testGetCompanyId(
			_company.getVirtualHostname(),
			_defaultGroupPublicLayout.getLayoutSet());
		_testGetCompanyId(
			defaultGroupPublicLayoutHostname,
			_defaultGroupPublicLayout.getLayoutSet());
		_testGetCompanyId(
			_nondefaultGroupPublicLayoutHostname,
			_nondefaultGroupPublicLayout.getLayoutSet());
	}

	private void _testGetCompanyId(
		String hostname, LayoutSet expectedLayoutSet) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setServerName(hostname);

		mockHttpServletRequest.addHeader("Host", hostname);

		Assert.assertEquals(
			_company.getCompanyId(),
			PortalInstances.getCompanyId(mockHttpServletRequest));

		Assert.assertEquals(
			expectedLayoutSet,
			mockHttpServletRequest.getAttribute(
				WebKeys.VIRTUAL_HOST_LAYOUT_SET));
	}

	private void _updateLayoutSetVirtualHostname(
		Layout layout, String layoutHostname) {

		LayoutSet layoutSet = layout.getLayoutSet();

		_virtualHostLocalService.updateVirtualHosts(
			_company.getCompanyId(), layoutSet.getLayoutSetId(),
			TreeMapBuilder.put(
				layoutHostname, StringPool.BLANK
			).build());

		layout.setLayoutSet(null);
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _defaultGroupPublicLayout;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private Layout _nondefaultGroupPublicLayout;
	private String _nondefaultGroupPublicLayoutHostname;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

	private String _virtualHostsDefaultSiteName;

}