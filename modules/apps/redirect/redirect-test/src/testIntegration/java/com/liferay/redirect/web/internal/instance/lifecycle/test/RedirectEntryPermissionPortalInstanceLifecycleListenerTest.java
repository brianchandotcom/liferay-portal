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

package com.liferay.redirect.web.internal.instance.lifecycle.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectEntry;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author tototrinh
 */
@RunWith(Arquillian.class)
public class RedirectEntryPermissionPortalInstanceLifecycleListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testViewRedirectEntryPermissionsAddedWithExistingCompany()
		throws Exception {

		Class<?> clazz = _portalInstanceLifecycleListener.getClass();

		ComponentDescriptionDTO componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(clazz), clazz.getName());

		try {
			_disablePortalInstanceLifecycleListener(componentDescriptionDTO);

			_testCompany = CompanyTestUtil.addCompany();

			Assert.assertFalse(
				_hasViewRedirectEntryPermission(_testCompany.getCompanyId()));

			_enablePortalInstanceLifecycleListener(componentDescriptionDTO);

			Assert.assertTrue(
				_hasViewRedirectEntryPermission(_testCompany.getCompanyId()));
		}
		finally {
			_enablePortalInstanceLifecycleListener(componentDescriptionDTO);
		}
	}

	@Test
	public void testViewRedirectEntryPermissionsAddedWithNewCompany()
		throws Exception {

		_testCompany = CompanyTestUtil.addCompany();

		Assert.assertTrue(
			_hasViewRedirectEntryPermission(_testCompany.getCompanyId()));
	}

	private void _disablePortalInstanceLifecycleListener(
			ComponentDescriptionDTO componentDescriptionDTO)
		throws Exception {

		Promise<Void> voidPromise = _serviceComponentRuntime.disableComponent(
			componentDescriptionDTO);

		voidPromise.getValue();
	}

	private void _enablePortalInstanceLifecycleListener(
			ComponentDescriptionDTO componentDescriptionDTO)
		throws Exception {

		Promise<Void> voidPromise = _serviceComponentRuntime.enableComponent(
			componentDescriptionDTO);

		voidPromise.getValue();
	}

	private boolean _hasViewRedirectEntryPermission(long companyId)
		throws Exception {

		Role powerUserRole = _roleLocalService.fetchRole(
			companyId, RoleConstants.POWER_USER);

		Group userPersonalSiteGroup =
			_groupLocalService.fetchUserPersonalSiteGroup(companyId);

		return _resourcePermissionLocalService.hasResourcePermission(
			companyId, RedirectEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(userPersonalSiteGroup.getGroupId()),
			powerUserRole.getRoleId(), ActionKeys.VIEW);
	}

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "component.name=*.RedirectEntryPermissionPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private Company _testCompany;

}