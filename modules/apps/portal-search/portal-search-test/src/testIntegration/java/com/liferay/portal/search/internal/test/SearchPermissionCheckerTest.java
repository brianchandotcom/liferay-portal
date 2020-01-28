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

package com.liferay.portal.search.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.internal.test.util.BaseTestFilterVisitor;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class SearchPermissionCheckerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();

		_serviceReference = _bundleContext.getServiceReference(
			SearchPermissionChecker.class);

		_searchPermissionChecker = _bundleContext.getService(_serviceReference);

		_group = GroupTestUtil.addGroup();

		_organization = OrganizationTestUtil.addOrganization();
	}

	@After
	public void tearDown() throws Exception {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testAdministratorRolePermissionFilter() throws Exception {
		_user = UserTestUtil.addOmniAdminUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		BooleanFilter booleanFilter = getBooleanFilter(null);

		Assert.assertFalse(booleanFilter.hasClauses());
	}

	@Test
	public void testCompanyPermissionFilter() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		UserLocalServiceUtil.addRoleUser(_role.getRoleId(), _user.getUserId());

		addViewPermission(
			ResourceConstants.SCOPE_COMPANY, TestPropsValues.getCompanyId(),
			_role.getRoleId());

		BooleanFilter booleanFilter = getBooleanFilter(null);

		Assert.assertFalse(booleanFilter.hasClauses());
	}

	@Test
	public void testGroupIdsPermissionFilter() throws Exception {
		_user = UserTestUtil.addOrganizationAdminUser(_organization);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		assertFieldValue(
			new long[] {_group.getGroupId()}, Field.GROUP_ROLE_ID,
			_group.getGroupId() + StringPool.DASH + role.getRoleId(), false);
	}

	@Test
	public void testGroupPermissionFilter() throws Exception {
		_user = UserTestUtil.addGroupAdminUser(_group);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		addViewPermission(
			ResourceConstants.SCOPE_GROUP, _group.getGroupId(),
			role.getRoleId());

		assertFieldValue(
			null, Field.GROUP_ID, String.valueOf(_group.getGroupId()));
		assertFieldValue(
			null, Field.ROLE_IDS, String.valueOf(role.getRoleId()));
	}

	@Test
	public void testGroupTemplatePermissionFilter() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		UserLocalServiceUtil.addRoleUser(_role.getRoleId(), _user.getUserId());

		addViewPermission(
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			GroupConstants.DEFAULT_PARENT_GROUP_ID, _role.getRoleId());

		BooleanFilter booleanFilter = getBooleanFilter(null);

		Assert.assertFalse(booleanFilter.hasClauses());
	}

	@Test
	public void testGuestPermissionFilter() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.GUEST);

		addViewPermission(
			ResourceConstants.SCOPE_GROUP, _group.getGroupId(),
			role.getRoleId());

		assertFieldValue(
			new long[] {_group.getGroupId()}, Field.GROUP_ID,
			String.valueOf(_group.getGroupId()));
		assertFieldValue(
			new long[] {_group.getGroupId()}, Field.ROLE_IDS,
			String.valueOf(role.getRoleId()));
	}

	@Test
	public void testOrganizationRolePermissionFilter() throws Exception {
		_user = UserTestUtil.addOrganizationAdminUser(_organization);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(),
			RoleConstants.ORGANIZATION_ADMINISTRATOR);

		addViewPermission(
			ResourceConstants.SCOPE_GROUP, _organization.getGroupId(),
			role.getRoleId());

		assertFieldValue(
			null, Field.GROUP_ID, String.valueOf(_organization.getGroupId()));
		assertFieldValue(
			null, Field.ROLE_IDS, String.valueOf(role.getRoleId()));
	}

	@Test
	public void testUserGroupRolePermissionFilter() throws Exception {
		_user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		RoleLocalServiceUtil.addGroupRole(
			_group.getGroupId(), _role.getRoleId());

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			_user.getUserId(), _group.getGroupId(),
			new long[] {_role.getRoleId()});

		assertFieldValue(
			null, Field.GROUP_ROLE_ID,
			_group.getGroupId() + StringPool.DASH + _role.getRoleId());
	}

	protected void addViewPermission(int scope, long primKey, long roleId)
		throws Exception {

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			TestPropsValues.getCompanyId(), getClassName(), scope,
			String.valueOf(primKey), roleId, ActionKeys.VIEW);

		_resourcePermission =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				TestPropsValues.getCompanyId(), getClassName(), scope,
				String.valueOf(primKey), roleId);
	}

	protected void assertFieldValue(long[] groupIds, String field, String value)
		throws Exception {

		assertFieldValue(groupIds, field, value, true);
	}

	protected void assertFieldValue(
			long[] groupIds, String field, String value, boolean expected)
		throws Exception {

		BooleanFilter booleanFilter = getBooleanFilter(groupIds);

		TestFilterVisitor testFilterVisitor = new TestFilterVisitor(
			expected, field, value);

		booleanFilter.accept(testFilterVisitor);

		testFilterVisitor.assertField();
	}

	protected BooleanFilter getBooleanFilter(long[] groupIds) throws Exception {
		return _searchPermissionChecker.getPermissionBooleanFilter(
			TestPropsValues.getCompanyId(), groupIds, _user.getUserId(),
			getClassName(), new BooleanFilter(), new SearchContext());
	}

	protected String getClassName() {
		return DLFileEntry.class.getName();
	}

	private BundleContext _bundleContext;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private ResourcePermission _resourcePermission;

	@DeleteAfterTestRun
	private Role _role;

	private SearchPermissionChecker _searchPermissionChecker;
	private ServiceReference<SearchPermissionChecker> _serviceReference;

	@DeleteAfterTestRun
	private User _user;

	private static class TestFilterVisitor extends BaseTestFilterVisitor<Void> {

		public TestFilterVisitor(boolean expected, String field, String value) {
			_expected = expected;
			_field = field;
			_value = value;
		}

		public void assertField() {
			Assert.assertEquals(_expected, _found);
		}

		@Override
		public Void visit(BooleanFilter booleanFilter) {
			for (BooleanClause<Filter> booleanClause :
					booleanFilter.getMustBooleanClauses()) {

				Filter filter = booleanClause.getClause();

				filter.accept(this);
			}

			for (BooleanClause<Filter> booleanClause :
					booleanFilter.getShouldBooleanClauses()) {

				Filter filter = booleanClause.getClause();

				filter.accept(this);
			}

			return null;
		}

		@Override
		public Void visit(TermsFilter termsFilter) {
			if (_field.equals(termsFilter.getField()) &&
				ArrayUtil.contains(termsFilter.getValues(), _value)) {

				_found = true;
			}

			return null;
		}

		private final boolean _expected;
		private final String _field;
		private boolean _found;
		private final String _value;

	}

}