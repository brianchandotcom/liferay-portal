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
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.FilterConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Peter Shin
 */
public class UserBuilderFilterConstraintsTest extends BaseVLDAPTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
		ExpandoBridge expandoBridge = Mockito.mock(ExpandoBridge.class);

		Mockito.when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaLMPassword"), Mockito.eq(false))
		).thenReturn(
			"testLMPassword"
		);

		Mockito.when(
			expandoBridge.getAttribute(
				Mockito.eq("sambaNTPassword"), Mockito.eq(false))
		).thenReturn(
			"testNTPassword"
		);

		Mockito.when(
			_hasOrganizationUser.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);

		Mockito.when(
			_hasRoleUser.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);
	}

	protected void setUpFastDateFormat() {
		FastDateFormat fastDateFormat = FastDateFormat.getInstance(
			"yyyyMMddHHmmss.SSSZ", null, LocaleUtil.getDefault());

		FastDateFormatFactory fastDateFormatFactory = Mockito.mock(
			FastDateFormatFactory.class);

		Mockito.when(
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
		Organization organization = Mockito.mock(Organization.class);

		Mockito.when(
			organization.getName()
		).thenReturn(
			"testOrganizationName"
		);

		Mockito.when(
			organization.getOrganizationId()
		).thenReturn(
			PRIMARY_KEY
		);

		List<Organization> organizations = new ArrayList<>();

		organizations.add(organization);

		Mockito.when(
			organizationLocalService.getOrganization(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			organization
		);

		Mockito.when(
			_hasOrganizationUser.getOrganizations()
		).thenReturn(
			organizations
		);
	}

	protected void setUpPasswordPolicy() throws Exception {
		PasswordPolicy passwordPolicy = Mockito.mock(PasswordPolicy.class);

		setUpPasswordPolicy(passwordPolicy);

		Mockito.when(
			_hasOrganizationUser.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);

		Mockito.when(
			_hasRoleUser.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);
	}

	protected void setUpRoles() throws Exception {
		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			"testRoleName"
		);

		Mockito.when(
			role.getRoleId()
		).thenReturn(
			PRIMARY_KEY
		);

		List<Role> roles = new ArrayList<>();

		roles.add(role);

		Mockito.when(
			roleLocalService.getRole(Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			role
		);

		Mockito.when(
			_hasRoleUser.getRoles()
		).thenReturn(
			roles
		);
	}

	protected void setUpUsers() {
		_hasOrganizationUser = Mockito.mock(User.class);

		Mockito.when(
			_hasOrganizationUser.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_hasOrganizationUser.getCreateDate()
		).thenReturn(
			null
		);

		Mockito.when(
			_hasOrganizationUser.getEmailAddress()
		).thenReturn(
			"testHasOrganizationUser@email"
		);

		Mockito.when(
			_hasOrganizationUser.getFirstName()
		).thenReturn(
			"testHasOrganizationUserFirstName"
		);

		Mockito.when(
			_hasOrganizationUser.getFullName()
		).thenReturn(
			"testHasOrganizationUserFullName"
		);

		Mockito.when(
			_hasOrganizationUser.getLastName()
		).thenReturn(
			"testHasOrganizationUserLastName"
		);

		Mockito.when(
			_hasOrganizationUser.getModifiedDate()
		).thenReturn(
			null
		);

		Mockito.when(
			_hasOrganizationUser.getScreenName()
		).thenReturn(
			"testHasOrganizationUserScreenName"
		);

		Mockito.when(
			_hasOrganizationUser.getUserId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_hasOrganizationUser.getUuid()
		).thenReturn(
			"testHasOrganizationUserUuid"
		);

		_users.add(_hasOrganizationUser);

		_hasRoleUser = Mockito.mock(User.class);

		Mockito.when(
			_hasRoleUser.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_hasRoleUser.getCreateDate()
		).thenReturn(
			null
		);

		Mockito.when(
			_hasRoleUser.getEmailAddress()
		).thenReturn(
			"testUserWtihRole@email"
		);

		Mockito.when(
			_hasRoleUser.getFirstName()
		).thenReturn(
			"testHasRoleUserFirstName"
		);

		Mockito.when(
			_hasRoleUser.getFullName()
		).thenReturn(
			"testHasRoleUserFullName"
		);

		Mockito.when(
			_hasRoleUser.getLastName()
		).thenReturn(
			"testHasRoleUserLastName"
		);

		Mockito.when(
			_hasRoleUser.getModifiedDate()
		).thenReturn(
			null
		);

		Mockito.when(
			_hasRoleUser.getScreenName()
		).thenReturn(
			"testHasRoleUserScreenName"
		);

		Mockito.when(
			_hasRoleUser.getUserId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_hasRoleUser.getUuid()
		).thenReturn(
			"testHasRoleUserUuid"
		);

		_users.add(_hasRoleUser);

		Mockito.when(
			userLocalService.getCompanyUsers(
				Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			_users
		);

		Mockito.when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(),
				Mockito.eq(
					LinkedHashMapBuilder.<String, Object>put(
						"usersRoles", PRIMARY_KEY
					).build()),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			Arrays.asList(_hasRoleUser)
		);

		Mockito.when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(),
				Mockito.eq(
					LinkedHashMapBuilder.<String, Object>put(
						"usersOrgs", PRIMARY_KEY
					).build()),
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