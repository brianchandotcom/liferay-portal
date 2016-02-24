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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 * @author Tomas Polesovsky
 */
@Sync
public class PermissionCheckerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		registerResourceActions();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		removeResourceActions();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testHasPermissionOnDefaultPortletResourcesWhenPortletDeploys()
		throws Exception {

		_user = UserTestUtil.addUser();

		UserLocalServiceUtil.setGroupUsers(
			_group.getGroupId(), new long[] {_user.getUserId()});

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Portlet portlet = new PortletImpl(
			_user.getCompanyId(), _PORTLET_RESOURCE_NAME);

		PortletLocalServiceUtil.deployRemotePortlet(portlet, "category.hidden");

		try {
			boolean hasPermission = permissionChecker.hasPermission(
				_group.getGroupId(), _PORTLET_RESOURCE_NAME,
				_PORTLET_RESOURCE_NAME, ActionKeys.VIEW);

			Assert.assertTrue(hasPermission);

			hasPermission = permissionChecker.hasPermission(
				_group.getGroupId(), _PORTLET_RESOURCE_NAME,
				_PORTLET_RESOURCE_NAME, ActionKeys.CONFIGURATION);

			Assert.assertTrue(hasPermission);

			hasPermission = permissionChecker.hasPermission(
				_group.getGroupId(), _PORTLET_RESOURCE_NAME,
				_PORTLET_RESOURCE_NAME, ActionKeys.ACCESS_IN_CONTROL_PANEL);

			Assert.assertFalse(hasPermission);
		}
		finally {
			ResourceLocalServiceUtil.deleteResource(
				_user.getCompanyId(), _PORTLET_RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL, _PORTLET_RESOURCE_NAME);

			ResourceLocalServiceUtil.deleteResource(
				_user.getCompanyId(), _ROOT_MODEL_RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL, _ROOT_MODEL_RESOURCE_NAME);

			PortletLocalServiceUtil.destroyRemotePortlet(portlet);
		}
	}

	@Test
	public void testHasPermissionOnRootModelResource() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		ResourceLocalServiceUtil.addResources(
			permissionChecker.getCompanyId(), _group.getGroupId(), 0,
			_ROOT_MODEL_RESOURCE_NAME, _group.getGroupId(), false, true, false);

		try {
			boolean hasPermission = permissionChecker.hasPermission(
				_group.getGroupId(), _ROOT_MODEL_RESOURCE_NAME,
				_group.getGroupId(), _ADD_SITE_TEST_ACTION);

			Assert.assertFalse(hasPermission);

			UserLocalServiceUtil.setGroupUsers(
				_group.getGroupId(), new long[] {_user.getUserId()});

			permissionChecker = _getPermissionChecker(_user);

			hasPermission = permissionChecker.hasPermission(
				_group.getGroupId(), _ROOT_MODEL_RESOURCE_NAME,
				_group.getGroupId(), _ADD_SITE_TEST_ACTION);

			Assert.assertTrue(hasPermission);
		}
		finally {
			ResourceLocalServiceUtil.deleteResource(
				_user.getCompanyId(), _ROOT_MODEL_RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL, _group.getGroupId());
		}
	}

	@Test
	public void testIsCompanyAdminWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isCompanyAdmin());
	}

	@Test
	public void testIsCompanyAdminWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isCompanyAdmin());
	}

	@Test
	public void testIsContentReviewerWithCompanyAdminUser() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				TestPropsValues.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsContentReviewerWithReviewerUser() throws Exception {
		_user = UserTestUtil.addUser();

		_role = RoleTestUtil.addRole(
			RoleConstants.PORTAL_CONTENT_REVIEWER, RoleConstants.TYPE_REGULAR);

		UserLocalServiceUtil.setRoleUsers(
			_role.getRoleId(), new long[] {_user.getUserId()});

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				_user.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsContentReviewerWithSiteContentReviewer()
		throws Exception {

		_role = RoleTestUtil.addRole(
			RoleConstants.SITE_CONTENT_REVIEWER, RoleConstants.TYPE_SITE);

		_user = UserTestUtil.addGroupUser(
			_group, RoleConstants.SITE_CONTENT_REVIEWER);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(
			permissionChecker.isContentReviewer(
				_user.getCompanyId(), _group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithGroupAdmin() throws Exception {
		_user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupAdminWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isGroupAdmin(_group.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithGroupMember() throws Exception {
		_user = UserTestUtil.addUser();

		UserLocalServiceUtil.addGroupUser(
			_group.getGroupId(), _user.getUserId());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isGroupMember(_group.getGroupId()));
	}

	@Test
	public void testIsGroupMemberWithNonGroupMember() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isGroupMember(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithCompanyAdmin() throws Exception {
		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithGroupAdmin() throws Exception {
		_user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithOwnerUser() throws Exception {
		_user = UserTestUtil.addGroupOwnerUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsGroupOwnerWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser(
			_group.getGroupId(), LocaleUtil.getDefault());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isGroupOwner(_group.getGroupId()));
	}

	@Test
	public void testIsOmniAdminWithAdministratorRoleUser() throws Exception {
		_user = UserTestUtil.addOmniAdminUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithCompanyAdmin() throws Exception {
		long companyId = CompanyThreadLocal.getCompanyId();

		_company = CompanyTestUtil.addCompany();

		CompanyThreadLocal.setCompanyId(_company.getCompanyId());

		_user = UserTestUtil.addCompanyAdminUser(_company);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isOmniadmin());

		CompanyThreadLocal.setCompanyId(companyId);
	}

	@Test
	public void testIsOmniAdminWithGroupAdmin() throws Exception {
		_user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOmniAdminWithRegularUser() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(permissionChecker.isOmniadmin());
	}

	@Test
	public void testIsOrganizationAdminWithCompanyAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithGroupAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithOrganizationAdmin()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addOrganizationAdminUser(_organization);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertTrue(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationAdminWithRegularUser() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationAdmin(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithCompanyAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		PermissionChecker permissionChecker = _getPermissionChecker(
			TestPropsValues.getUser());

		Assert.assertTrue(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithGroupAdmin() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addGroupAdminUser(_organization.getGroup());

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithOrganizationAdmin()
		throws Exception {

		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addOrganizationAdminUser(_organization);

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	@Test
	public void testIsOrganizationOwnerWithRegularUser() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(_user);

		Assert.assertFalse(
			permissionChecker.isOrganizationOwner(
				_organization.getOrganizationId()));
	}

	protected static void registerResourceActions() throws Exception {
		String packageName = PermissionCheckerTest.class.getPackage().getName();
		String packagePath = packageName.replace('.', '/');

		ResourceActionsUtil.read(
			null, PermissionCheckerTest.class.getClassLoader(),
			packagePath + "/dependencies/resource-actions.xml");

		List<String> portletActions =
			ResourceActionsUtil.getPortletResourceActions(
				_PORTLET_RESOURCE_NAME);

		ResourceActionLocalServiceUtil.checkResourceActions(
			_PORTLET_RESOURCE_NAME, portletActions);

		List<String> modelNames = ResourceActionsUtil.getPortletModelResources(
			_PORTLET_RESOURCE_NAME);

		for (String modelName : modelNames) {
			List<String> modelActions =
				ResourceActionsUtil.getModelResourceActions(modelName);

			ResourceActionLocalServiceUtil.checkResourceActions(
				modelName, modelActions);
		}
	}

	protected static void removeResourceActions() {
		List<ResourceAction> portletResourceActions =
			ResourceActionLocalServiceUtil.getResourceActions(
				_PORTLET_RESOURCE_NAME);

		for (ResourceAction portletResourceAction : portletResourceActions) {
			ResourceActionLocalServiceUtil.deleteResourceAction(
				portletResourceAction);
		}

		List<String> modelNames = ResourceActionsUtil.getPortletModelResources(
			_PORTLET_RESOURCE_NAME);

		for (String modelName : modelNames) {
			List<ResourceAction> modelResourceActions =
				ResourceActionLocalServiceUtil.getResourceActions(modelName);

			for (ResourceAction modelResourceAction : modelResourceActions) {
				ResourceActionLocalServiceUtil.deleteResourceAction(
					modelResourceAction);
			}
		}
	}

	private PermissionChecker _getPermissionChecker(User user)
		throws Exception {

		PermissionCacheUtil.clearCache(user.getUserId());

		return PermissionCheckerFactoryUtil.create(user);
	}

	private static final String _ADD_SITE_TEST_ACTION = "ADD_SITE_TEST";

	private static final String _PORTLET_RESOURCE_NAME =
		"com_liferay_portal_security_PermissionCheckerTestSiteRelatedPortlet";

	private static final String _ROOT_MODEL_RESOURCE_NAME =
		"com.liferay.portal.security.permission.site";

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}