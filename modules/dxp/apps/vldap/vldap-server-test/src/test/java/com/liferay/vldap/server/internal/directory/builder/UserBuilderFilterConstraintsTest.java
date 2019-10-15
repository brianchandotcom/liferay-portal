/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.FilterConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Peter Shin
 */
@RunWith(PowerMockRunner.class)
public class UserBuilderFilterConstraintsTest extends BaseVLDAPTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpUsers();

		setUpExpando();
		setUpFastDateFormat();
		setUpOrganizations();
		setUpPasswordPolicy();
		setUpPortalUtil();
		setUpRoles();
	}

	@Test
	public void testGetUsers() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint organizationFilterConstraint = new FilterConstraint();

		organizationFilterConstraint.addAttribute(
			"mail", "testHasOrganizationUser@email");
		organizationFilterConstraint.addAttribute(
			"member",
			"cn=testOrganizationName,ou=testOrganizationName," +
				"ou=Organizations,ou=liferay.com,o=Liferay");

		filterConstraints.add(organizationFilterConstraint);

		FilterConstraint roleFilterConstraint = new FilterConstraint();

		roleFilterConstraint.addAttribute("mail", "testUserWtihRole@email");
		roleFilterConstraint.addAttribute(
			"member",
			"cn=testRoleName,ou=testRoleName,ou=Roles,ou=liferay.com," +
				"o=Liferay");

		filterConstraints.add(roleFilterConstraint);

		List<User> users = _userBuilder.getUsers(
			company, searchBase, filterConstraints);

		Assert.assertEquals(users.toString(), 2, users.size());
	}

	protected void setUpExpando() {
		ExpandoBridge expandoBridge = mock(ExpandoBridge.class);

		when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaLMPassword"), Mockito.eq(false))
		).thenReturn(
			"testLMPassword"
		);

		when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaNTPassword"), Mockito.eq(false))
		).thenReturn(
			"testNTPassword"
		);

		when(
			_hasOrganizationUser.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);

		when(
			_hasRoleUser.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);
	}

	protected void setUpFastDateFormat() {
		FastDateFormat fastDateFormat = FastDateFormat.getInstance(
			"yyyyMMddHHmmss.SSSZ", null, LocaleUtil.getDefault());

		FastDateFormatFactory fastDateFormatFactory = mock(
			FastDateFormatFactory.class);

		when(
			fastDateFormatFactory.getSimpleDateFormat(Mockito.anyString())
		).thenReturn(
			fastDateFormat
		);

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			fastDateFormatFactory);
	}

	protected void setUpOrganizations() throws Exception {
		Organization organization = mock(Organization.class);

		when(
			organization.getName()
		).thenReturn(
			"testOrganizationName"
		);

		when(
			organization.getOrganizationId()
		).thenReturn(
			PRIMARY_KEY
		);

		List<Organization> organizations = new ArrayList<>();

		organizations.add(organization);

		when(
			organizationLocalService.getOrganization(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			organization
		);

		when(
			_hasOrganizationUser.getOrganizations()
		).thenReturn(
			organizations
		);
	}

	protected void setUpPasswordPolicy() throws Exception {
		PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);

		setUpPasswordPolicy(passwordPolicy);

		when(
			_hasOrganizationUser.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);

		when(
			_hasRoleUser.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);
	}

	protected void setUpRoles() throws Exception {
		Role role = mock(Role.class);

		when(
			role.getName()
		).thenReturn(
			"testRoleName"
		);

		when(
			role.getRoleId()
		).thenReturn(
			PRIMARY_KEY
		);

		List<Role> roles = new ArrayList<>();

		roles.add(role);

		when(
			roleLocalService.getRole(Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			role
		);

		when(
			_hasRoleUser.getRoles()
		).thenReturn(
			roles
		);
	}

	protected void setUpUsers() {
		_hasOrganizationUser = mock(User.class);

		when(
			_hasOrganizationUser.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_hasOrganizationUser.getCreateDate()
		).thenReturn(
			null
		);

		when(
			_hasOrganizationUser.getEmailAddress()
		).thenReturn(
			"testHasOrganizationUser@email"
		);

		when(
			_hasOrganizationUser.getFirstName()
		).thenReturn(
			"testHasOrganizationUserFirstName"
		);

		when(
			_hasOrganizationUser.getFullName()
		).thenReturn(
			"testHasOrganizationUserFullName"
		);

		when(
			_hasOrganizationUser.getLastName()
		).thenReturn(
			"testHasOrganizationUserLastName"
		);

		when(
			_hasOrganizationUser.getModifiedDate()
		).thenReturn(
			null
		);

		when(
			_hasOrganizationUser.getScreenName()
		).thenReturn(
			"testHasOrganizationUserScreenName"
		);

		when(
			_hasOrganizationUser.getUserId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_hasOrganizationUser.getUuid()
		).thenReturn(
			"testHasOrganizationUserUuid"
		);

		_users.add(_hasOrganizationUser);

		_hasRoleUser = mock(User.class);

		when(
			_hasRoleUser.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_hasRoleUser.getCreateDate()
		).thenReturn(
			null
		);

		when(
			_hasRoleUser.getEmailAddress()
		).thenReturn(
			"testUserWtihRole@email"
		);

		when(
			_hasRoleUser.getFirstName()
		).thenReturn(
			"testHasRoleUserFirstName"
		);

		when(
			_hasRoleUser.getFullName()
		).thenReturn(
			"testHasRoleUserFullName"
		);

		when(
			_hasRoleUser.getLastName()
		).thenReturn(
			"testHasRoleUserLastName"
		);

		when(
			_hasRoleUser.getModifiedDate()
		).thenReturn(
			null
		);

		when(
			_hasRoleUser.getScreenName()
		).thenReturn(
			"testHasRoleUserScreenName"
		);

		when(
			_hasRoleUser.getUserId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_hasRoleUser.getUuid()
		).thenReturn(
			"testHasRoleUserUuid"
		);

		_users.add(_hasRoleUser);

		when(
			userLocalService.getCompanyUsers(
				Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			_users
		);

		LinkedHashMap<String, Object> usersRolesParams = new LinkedHashMap<>();

		usersRolesParams.put("usersRoles", PRIMARY_KEY);

		when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.eq(usersRolesParams),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			Arrays.asList(_hasRoleUser)
		);

		LinkedHashMap<String, Object> usersOrgsParams = new LinkedHashMap<>();

		usersOrgsParams.put("usersOrgs", PRIMARY_KEY);

		when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.eq(usersOrgsParams),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			Arrays.asList(_hasOrganizationUser)
		);
	}

	private User _hasOrganizationUser;
	private User _hasRoleUser;
	private final UserBuilder _userBuilder = new UserBuilder();
	private final List<User> _users = new ArrayList<>();

}