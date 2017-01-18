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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christopher Kian
 * @author Preston Crary
 */
public class InlineSQLHelperImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_groupOne = GroupTestUtil.addGroup();
		_groupTwo = GroupTestUtil.addGroup();

		_groupIds = new long[] {_groupOne.getGroupId(), _groupTwo.getGroupId()};

		_user = UserTestUtil.addUser();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testClauseOrdering() throws Exception {
		_addGroupRole(_groupOne, RoleConstants.SITE_MEMBER);
		_addGroupRole(_groupTwo, RoleConstants.SITE_MEMBER);

		_setPermissionChecker();

		_assertClauseOrdering(_SQL_PLAIN + _SQL_WHERE, _WHERE_CLAUSE);
		_assertClauseOrdering(_SQL_PLAIN + _SQL_GROUP_BY, _GROUP_BY_CLAUSE);
		_assertClauseOrdering(_SQL_PLAIN + _SQL_ORDER_BY, _ORDER_BY_CLAUSE);
		_assertClauseOrdering(
			_SQL_PLAIN + _SQL_WHERE + _SQL_GROUP_BY, _GROUP_BY_CLAUSE);
		_assertClauseOrdering(
			_SQL_PLAIN + _SQL_WHERE + _SQL_ORDER_BY, _ORDER_BY_CLAUSE);
		_assertClauseOrdering(
			_SQL_PLAIN + _SQL_GROUP_BY + _SQL_ORDER_BY, _ORDER_BY_CLAUSE);
		_assertClauseOrdering(
			_SQL_PLAIN + _SQL_WHERE + _SQL_GROUP_BY + _SQL_ORDER_BY,
			_ORDER_BY_CLAUSE);
	}

	@Test
	public void testCompanyScope() throws Exception {
		_role = RoleTestUtil.addRole(
			"scopeCompanyRole", RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), _role);

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			CompanyThreadLocal.getCompanyId(), _CLASS_NAME,
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_role.getCompanyId()), _role.getRoleId(),
			ActionKeys.VIEW);

		_setPermissionChecker();

		String sql = _inlineSQLHelperImpl.replacePermissionCheckJoin(
			_SQL_PLAIN, _CLASS_NAME, _CLASS_PK_FIELD, _USER_ID_FIELD,
			_GROUP_ID_FIELD, new long[] {_groupOne.getGroupId()}, null);

		Assert.assertSame(_SQL_PLAIN, sql);

		Assert.assertTrue(
			_inlineSQLHelperImpl.isEnabled(_groupOne.getGroupId()));
	}

	@Test
	public void testGetRoles() throws Exception {
		_groupThree = GroupTestUtil.addGroup();

		_role = RoleTestUtil.addRole("testRole", RoleConstants.TYPE_SITE);

		_addGroupRole(_groupThree, "testRole");

		_setPermissionChecker();

		Role guestRole = RoleLocalServiceUtil.getRole(
			_groupThree.getCompanyId(), RoleConstants.GUEST);
		Role siteMemberRole = RoleLocalServiceUtil.getRole(
			_groupThree.getCompanyId(), RoleConstants.SITE_MEMBER);
		Role userRole = RoleLocalServiceUtil.getRole(
			_groupThree.getCompanyId(), RoleConstants.USER);

		long[] roleIds = _inlineSQLHelperImpl.getRoleIds(
			_groupThree.getGroupId());

		String msg = StringUtil.merge(roleIds);

		Assert.assertTrue(msg, ArrayUtil.contains(roleIds, _role.getRoleId()));
		Assert.assertTrue(
			msg, ArrayUtil.contains(roleIds, guestRole.getRoleId()));
		Assert.assertTrue(
			msg, ArrayUtil.contains(roleIds, siteMemberRole.getRoleId()));
		Assert.assertTrue(
			msg, ArrayUtil.contains(roleIds, userRole.getRoleId()));
	}

	@Test
	public void testGroupAdminResourcePermission() throws Exception {
		_addGroupRole(_groupOne, RoleConstants.SITE_ADMINISTRATOR);
		_addGroupRole(_groupTwo, RoleConstants.SITE_MEMBER);

		_setPermissionChecker();

		String sql = _replacePermissionCheckJoin(_SQL_PLAIN);

		Role ownerRole = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.OWNER);

		StringBundler sb = new StringBundler(3);

		sb.append(" OR ((ResourcePermission.primKeyId = 0) AND ");
		sb.append("(ResourcePermission.roleId = ");
		sb.append(ownerRole.getRoleId());

		Assert.assertTrue(sql, sql.contains(sb.toString()));
	}

	@Test
	public void testGroupScope() throws Exception {
		_role = RoleTestUtil.addRole("scopeGroupRole", RoleConstants.TYPE_SITE);

		_addGroupRole(_groupOne, "scopeGroupRole");

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			CompanyThreadLocal.getCompanyId(), _CLASS_NAME,
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(_groupOne.getGroupId()), _role.getRoleId(),
			ActionKeys.VIEW);

		_setPermissionChecker();

		String sql = _inlineSQLHelperImpl.replacePermissionCheck(
			_SQL_PLAIN, _CLASS_NAME, _CLASS_PK_FIELD, _USER_ID_FIELD,
			_GROUP_ID_FIELD, new long[] {_groupOne.getGroupId()}, null);

		Assert.assertSame(_SQL_PLAIN, sql);

		Assert.assertTrue(
			_inlineSQLHelperImpl.isEnabled(_groupOne.getGroupId()));
	}

	@Test
	public void testGroupTemplateScope() throws Exception {
		_role = RoleTestUtil.addRole(
			"scopeGroupTemplateRole", RoleConstants.TYPE_SITE);

		_addGroupRole(_groupOne, "scopeGroupTemplateRole");

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			CompanyThreadLocal.getCompanyId(), _CLASS_NAME,
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			_role.getRoleId(), ActionKeys.VIEW);

		_setPermissionChecker();

		String sql = _inlineSQLHelperImpl.replacePermissionCheckJoin(
			_SQL_PLAIN, _CLASS_NAME, _CLASS_PK_FIELD, _USER_ID_FIELD,
			_GROUP_ID_FIELD, new long[] {_groupOne.getGroupId()}, null);

		Assert.assertSame(_SQL_PLAIN, sql);

		Assert.assertTrue(
			_inlineSQLHelperImpl.isEnabled(_groupOne.getGroupId()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		_groupThree = GroupTestUtil.addGroup();

		_groupThree.setCompanyId(company.getCompanyId());

		GroupLocalServiceUtil.updateGroup(_groupThree);

		_addGroupRole(_groupThree, RoleConstants.SITE_MEMBER);

		_addGroupRole(_groupOne, RoleConstants.SITE_MEMBER);

		_setPermissionChecker();

		_inlineSQLHelperImpl.replacePermissionCheck(
			_SQL_PLAIN, _CLASS_NAME, _CLASS_PK_FIELD, _USER_ID_FIELD,
			_GROUP_ID_FIELD,
			new long[] {_groupOne.getGroupId(), _groupThree.getGroupId()},
			null);
	}

	@Test
	public void testIsNotEnabledForOmniAdmin() throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			_user.getCompanyId(), RoleConstants.ADMINISTRATOR);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), _user);

		_setPermissionChecker();

		Assert.assertFalse(_inlineSQLHelperImpl.isEnabled(_groupIds));
	}

	@Test
	public void testIsNotEnabledSiteAdmin() throws Exception {
		_addGroupRole(_groupOne, RoleConstants.SITE_ADMINISTRATOR);

		_setPermissionChecker();

		Assert.assertFalse(
			_inlineSQLHelperImpl.isEnabled(_groupOne.getGroupId()));
		Assert.assertTrue(
			_inlineSQLHelperImpl.isEnabled(_groupTwo.getGroupId()));
	}

	@Test
	public void testSQLComposition() throws Exception {
		_addGroupRole(_groupOne, RoleConstants.SITE_MEMBER);
		_addGroupRole(_groupTwo, RoleConstants.SITE_MEMBER);

		_setPermissionChecker();

		String sql = _replacePermissionCheckJoin(_SQL_PLAIN);

		_assertWhereClause(sql, _CLASS_PK_FIELD);

		StringBundler sb = new StringBundler(4);

		sb.append(_RESOURCE_PERMISSION);
		sb.append(".name = '");
		sb.append(_CLASS_NAME);
		sb.append("'");

		Assert.assertTrue(sql, sql.contains(sb.toString()));

		sb = new StringBundler(3);

		sb.append(_RESOURCE_PERMISSION);
		sb.append(".companyId = ");
		sb.append(CompanyThreadLocal.getCompanyId());

		Assert.assertTrue(sql, sql.contains(sb.toString()));

		sb = new StringBundler(3);

		sb.append(_USER_ID_FIELD);
		sb.append(" = ");
		sb.append(_user.getUserId());

		Assert.assertTrue(sql, sql.contains(sb.toString()));

		_assertValidSql(sql);

		sql = _replacePermissionCheckJoin(_SQL_PLAIN + _SQL_WHERE);

		_assertWhereClause(sql, _CLASS_PK_FIELD);

		Assert.assertTrue(
			sql,
			sql.endsWith(
				" AND " + _SQL_WHERE.substring(_WHERE_CLAUSE.length())));

		_assertValidSql(sql);
	}

	private void _addGroupRole(Group group, String roleName) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			new long[] {_user.getUserId()}, group.getGroupId(),
			role.getRoleId());
	}

	private void _assertClauseOrdering(String sql, String endingClause) {
		String actualSql = _inlineSQLHelperImpl.replacePermissionCheckJoin(
			sql, _CLASS_NAME, _CLASS_PK_FIELD, _USER_ID_FIELD, _GROUP_ID_FIELD,
			_groupIds, null);

		int wherePos = actualSql.lastIndexOf(_WHERE_CLAUSE);
		int groupByPos = actualSql.indexOf(_GROUP_BY_CLAUSE);
		int orderByPos = actualSql.indexOf(_ORDER_BY_CLAUSE);

		Assert.assertNotEquals(actualSql, -1, wherePos);

		if (endingClause.equals(_WHERE_CLAUSE)) {
			Assert.assertEquals(actualSql, -1, groupByPos);
			Assert.assertEquals(actualSql, -1, orderByPos);
		}
		else if (endingClause.equals(_GROUP_BY_CLAUSE)) {
			Assert.assertTrue(actualSql, wherePos < groupByPos);
			Assert.assertEquals(actualSql, -1, orderByPos);
		}
		else {
			Assert.assertTrue(actualSql, wherePos < orderByPos);
		}
	}

	private void _assertValidSql(String sql) throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.execute();
		}
	}

	private void _assertWhereClause(String sql, String classPK) {
		StringBundler sb = new StringBundler(4);

		sb.append(_WHERE_CLAUSE);
		sb.append("(");
		sb.append(classPK);
		sb.append(" IN (");

		Assert.assertTrue(sql, sql.contains(sb.toString()));
	}

	private String _replacePermissionCheckJoin(String sql) throws Exception {
		return _inlineSQLHelperImpl.replacePermissionCheckJoin(
			sql, _CLASS_NAME, _CLASS_PK_FIELD, _USER_ID_FIELD, _GROUP_ID_FIELD,
			_groupIds, null);
	}

	private void _setPermissionChecker() throws Exception {
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	private static final String _CLASS_NAME =
		"com.liferay.journal.model.JournalArticle";

	private static final String _CLASS_PK_FIELD =
		"JournalArticle.resourcePrimKey";

	private static final String _GROUP_BY_CLAUSE = " GROUP BY ";

	private static final String _GROUP_ID_FIELD = "groupIdField";

	private static final String _ORDER_BY_CLAUSE = " ORDER BY ";

	private static final String _RESOURCE_PERMISSION = "ResourcePermission";

	private static final String _SQL_GROUP_BY = " GROUP BY " + _CLASS_PK_FIELD;

	private static final String _SQL_ORDER_BY = " ORDER BY " + _CLASS_PK_FIELD;

	private static final String _SQL_PLAIN =
		"SELECT COUNT(*) FROM JournalArticle";

	private static final String _SQL_WHERE =
		" WHERE " + _CLASS_PK_FIELD + " != 0";

	private static final String _USER_ID_FIELD =
		_RESOURCE_PERMISSION + ".ownerId";

	private static final String _WHERE_CLAUSE = " WHERE ";

	private long[] _groupIds;

	@DeleteAfterTestRun
	private Group _groupOne;

	@DeleteAfterTestRun
	private Group _groupThree;

	@DeleteAfterTestRun
	private Group _groupTwo;

	private final InlineSQLHelperImpl _inlineSQLHelperImpl =
		new InlineSQLHelperImpl();
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}