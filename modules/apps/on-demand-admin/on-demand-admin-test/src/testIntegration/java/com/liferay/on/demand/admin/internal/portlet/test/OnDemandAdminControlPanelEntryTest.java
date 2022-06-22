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

package com.liferay.on.demand.admin.internal.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.on.demand.admin.constants.OnDemandAdminPortletKeys;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class OnDemandAdminControlPanelEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testHasAccessPermission() throws Exception {
		Assert.assertTrue(
			_onDemandAdminControlPanelEntry.hasAccessPermission(
				_permissionCheckerFactory.create(TestPropsValues.getUser()),
				null, _onDemandAdminPortlet));

		Company company = CompanyTestUtil.addCompany();

		User companyAdminUser = UserTestUtil.addCompanyAdminUser(company);

		Assert.assertFalse(
			_onDemandAdminControlPanelEntry.hasAccessPermission(
				_permissionCheckerFactory.create(companyAdminUser), null,
				_onDemandAdminPortlet));
	}

	@Inject(
		filter = "javax.portlet.name=" + OnDemandAdminPortletKeys.ON_DEMAND_ADMIN
	)
	private ControlPanelEntry _onDemandAdminControlPanelEntry;

	@Inject(
		filter = "javax.portlet.name=" + OnDemandAdminPortletKeys.ON_DEMAND_ADMIN
	)
	private Portlet _onDemandAdminPortlet;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

}