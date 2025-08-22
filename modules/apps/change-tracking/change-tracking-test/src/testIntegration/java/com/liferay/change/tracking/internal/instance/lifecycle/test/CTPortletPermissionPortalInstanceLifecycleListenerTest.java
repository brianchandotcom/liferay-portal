/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.instance.lifecycle.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class CTPortletPermissionPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testPublicationsRegularRolesPermissions() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			CTPortletPermissionPortalInstanceLifecycleListenerTest.class);

		String symbolicName = "com.liferay.change.tracking.web";

		bundle = BundleUtil.getBundle(bundle.getBundleContext(), symbolicName);

		Assert.assertNotNull(
			"Unable to find bundle with symbolic name: " + symbolicName,
			bundle);

		Class<?> clazz = bundle.loadClass(
			"com.liferay.change.tracking.web.internal.util." +
				"PublicationsRegularRolesUtil");

		Constructor<?> constructor = clazz.getConstructor();

		String[] publicationsRegularRoles = ReflectionTestUtil.getFieldValue(
			clazz, "PUBLICATIONS_REGULAR_ROLES");

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		for (String publicationsRegularRole : publicationsRegularRoles) {
			Role role = _roleLocalService.fetchRole(
				company.getCompanyId(), publicationsRegularRole);

			if (role != null) {
				role.setName(
					role.getName() + " " + RandomTestUtil.randomString());

				_roleLocalService.updateRole(role);

				role = _roleLocalService.fetchRole(
					company.getCompanyId(), publicationsRegularRole);
			}

			Assert.assertNull(role);
		}

		_portalInstanceLifecycleListener.portalInstanceRegistered(company);

		for (String publicationsRegularRole : publicationsRegularRoles) {
			Role role = _roleLocalService.fetchRole(
				company.getCompanyId(), publicationsRegularRole);

			Assert.assertNotNull(role);

			Method getModelResourceActionsMethod = clazz.getMethod(
				"getModelResourceActions", String.class);

			Object publicationsRegularRolesUtil = constructor.newInstance();

			String[] modelResourceActions =
				(String[])getModelResourceActionsMethod.invoke(
					publicationsRegularRolesUtil, publicationsRegularRole);

			for (String actionId : modelResourceActions) {
				Assert.assertTrue(
					_resourcePermissionLocalService.hasResourcePermission(
						company.getCompanyId(), CTCollection.class.getName(),
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(company.getCompanyId()),
						role.getRoleId(), actionId));
			}
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.change.tracking.web.internal.instance.lifecycle.CTPortletPermissionPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}