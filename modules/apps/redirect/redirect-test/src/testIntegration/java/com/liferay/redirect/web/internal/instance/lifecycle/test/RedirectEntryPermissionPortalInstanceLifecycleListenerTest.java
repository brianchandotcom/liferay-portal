/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @author To Trinh
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

			_company = CompanyTestUtil.addCompany();

			Assert.assertFalse(
				_hasViewRedirectEntryPermission(_company.getCompanyId()));

			_enablePortalInstanceLifecycleListener(componentDescriptionDTO);

			Assert.assertTrue(
				_hasViewRedirectEntryPermission(_company.getCompanyId()));
		}
		finally {
			_enablePortalInstanceLifecycleListener(componentDescriptionDTO);
		}
	}

	@Test
	public void testViewRedirectEntryPermissionsAddedWithNewCompany()
		throws Exception {

		_company = CompanyTestUtil.addCompany();

		Assert.assertTrue(
			_hasViewRedirectEntryPermission(_company.getCompanyId()));
	}

	private void _disablePortalInstanceLifecycleListener(
			ComponentDescriptionDTO componentDescriptionDTO)
		throws Exception {

		Promise<Void> promise = _serviceComponentRuntime.disableComponent(
			componentDescriptionDTO);

		promise.getValue();
	}

	private void _enablePortalInstanceLifecycleListener(
			ComponentDescriptionDTO componentDescriptionDTO)
		throws Exception {

		Promise<Void> promise = _serviceComponentRuntime.enableComponent(
			componentDescriptionDTO);

		promise.getValue();
	}

	private boolean _hasViewRedirectEntryPermission(long companyId)
		throws Exception {

		Group group = _groupLocalService.fetchUserPersonalSiteGroup(companyId);
		Role role = _roleLocalService.fetchRole(
			companyId, RoleConstants.POWER_USER);

		return _resourcePermissionLocalService.hasResourcePermission(
			companyId, RedirectEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			role.getRoleId(), ActionKeys.VIEW);
	}

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "component.name=com.liferay.redirect.web.internal.instance.lifecycle.RedirectEntryPermissionPortalInstanceLifecycleListener"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}