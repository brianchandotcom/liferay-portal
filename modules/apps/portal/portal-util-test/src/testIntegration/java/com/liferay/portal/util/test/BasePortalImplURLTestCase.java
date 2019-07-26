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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Vilmos Papp
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public abstract class BasePortalImplURLTestCase {

	@Before
	public void setUp() throws Exception {
		company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		controlPanelLayout = layoutLocalService.getLayout(
			portal.getControlPanelPlid(company.getCompanyId()));

		group = GroupTestUtil.addGroup();

		privateLayout = LayoutTestUtil.addLayout(group, true);
		publicLayout = LayoutTestUtil.addLayout(group);
	}

	protected ThemeDisplay initThemeDisplay(
			Company company, Group group, Layout layout,
			String companyVirtualHostname)
		throws Exception {

		return initThemeDisplay(
			company, group, layout, companyVirtualHostname,
			companyVirtualHostname);
	}

	protected ThemeDisplay initThemeDisplay(
			Company company, Group group, Layout layout,
			String companyVirtualHostname, String serverName)
		throws Exception {

		company.setVirtualHostname(companyVirtualHostname);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);
		themeDisplay.setI18nLanguageId(StringPool.BLANK);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setSecure(false);
		themeDisplay.setServerName(serverName);
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());
		themeDisplay.setWidget(false);

		return themeDisplay;
	}

	protected static final String LOCALHOST = "localhost";

	protected static final String VIRTUAL_HOSTNAME = "test.com";

	protected Company company;
	protected Layout controlPanelLayout;

	@DeleteAfterTestRun
	protected Group group;

	@Inject
	protected LayoutLocalService layoutLocalService;

	@Inject
	protected Portal portal;

	protected Layout privateLayout;
	protected Layout publicLayout;

	@Inject
	private CompanyLocalService _companyLocalService;

}