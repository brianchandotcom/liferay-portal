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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.Role;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class RoleResourceTest extends BaseRoleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		for (Role role : _roles) {
			RoleLocalServiceUtil.deleteRole(role.getId());
		}

		_roles = new ArrayList<>();
	}

	@Override
	@Test
	public void testGetRolesPage() throws Exception {
		Page<Role> page = roleResource.getRolesPage(Pagination.of(1, 100));

		Collection<Role> defaultRoles = page.getItems();

		long totalCount = page.getTotalCount();

		_roles.add(_addRole(randomRole()));

		_roles.add(_addRole(randomRole()));

		page = roleResource.getRolesPage(
			Pagination.of(1, (int)totalCount + _roles.size()));

		Assert.assertEquals(totalCount + _roles.size(), page.getTotalCount());

		List<Role> allRoles = new ArrayList<>();

		allRoles.addAll(defaultRoles);
		allRoles.addAll(_roles);

		assertEqualsIgnoringOrder(allRoles, (List<Role>)page.getItems());

		assertValid(page);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Role randomRole() throws Exception {
		Role role = super.randomRole();

		role.setRoleType(
			RoleConstants.getTypeLabel(
				RoleConstants.TYPES_ORGANIZATION_AND_REGULAR_AND_SITE
					[RandomTestUtil.randomInt(0, 2)]));

		return role;
	}

	@Override
	protected Role testGetRole_addRole() throws Exception {
		Role role = _addRole(randomRole());

		_roles.add(role);

		return role;
	}

	private Role _addRole(Role role) throws Exception {
		RoleLocalServiceUtil.deleteUserRole(
			_user.getUserId(),
			RoleLocalServiceUtil.getRole(
				testGroup.getCompanyId(), RoleConstants.USER));

		HashMap<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.getDefault(), role.getName());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(testCompany.getCompanyId());
		serviceContext.setUserId(_user.getUserId());

		com.liferay.portal.kernel.model.Role serviceBuilderRole =
			RoleLocalServiceUtil.addRole(
				_user.getUserId(), null, 0, role.getName(), titleMap, null,
				_toRoleType(role.getRoleType()), null, serviceContext);

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), serviceBuilderRole);

		return _toRole(serviceBuilderRole);
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role) {
		return new Role() {
			{
				description = role.getDescription();
				id = role.getRoleId();
				name = role.getName();
			}
		};
	}

	private int _toRoleType(String roleTypeLabel) {
		if (roleTypeLabel.equals(RoleConstants.TYPE_ORGANIZATION_LABEL)) {
			return RoleConstants.TYPE_ORGANIZATION;
		}
		else if (roleTypeLabel.equals(RoleConstants.TYPE_SITE_LABEL)) {
			return RoleConstants.TYPE_SITE;
		}
		else if (roleTypeLabel.equals(RoleConstants.TYPE_REGULAR_LABEL)) {
			return RoleConstants.TYPE_REGULAR;
		}

		throw new IllegalArgumentException(
			"Invalid roleTypeLabel " + roleTypeLabel);
	}

	private List<Role> _roles = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

}