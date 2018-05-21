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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.ldap.Directory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 */
@RunWith(PowerMockRunner.class)
public class RoleBuilderTest extends BaseVLDAPTestCase {

	@Test
	public void testBuildDirectoriesWithInvalidFilterConstraints()
		throws Exception {

		setUpUsers();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _roleBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithInvalidRoleDescription()
		throws Exception {

		setUpUsers();

		setUpRoles();

		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("description", "invalidDescription");
		filterConstraint.addAttribute(
			"member", "screenName=testScreenName,ou=test,cn=test,blah=test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _roleBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithInvalidRoleName() throws Exception {
		setUpUsers();

		setUpRoles();

		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("description", "testDescription");
		filterConstraint.addAttribute(
			"member", "screenName=testScreenName,ou=test,cn=test,blah=test");
		filterConstraint.addAttribute("ou", "invalidName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _roleBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithNoFilterConstraints() throws Exception {
		setUpUsers();

		setUpRoles();

		List<Directory> directories = _roleBuilder.buildDirectories(
			searchBase, new ArrayList<FilterConstraint>());

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesWithNoScreenName() throws Exception {
		setUpUsers();

		setUpRoles();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("description", "testDescription");
		filterConstraint.addAttribute("ou", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _roleBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesWithNullUser() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute(
			"member", "screenName=testScreenName,ou=test,cn=test,blah=test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _roleBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithValidScreenName() throws Exception {
		setUpUsers();

		setUpRoles();

		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("description", "testDescription");
		filterConstraint.addAttribute(
			"member", "screenName=testScreenName,ou=test,cn=test,blah=test");
		filterConstraint.addAttribute("ou", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _roleBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(_roleBuilder.isValidAttribute("cn", "test"));
		Assert.assertTrue(_roleBuilder.isValidAttribute("description", "test"));
		Assert.assertTrue(_roleBuilder.isValidAttribute("member", "test"));
		Assert.assertTrue(_roleBuilder.isValidAttribute("ou", "test"));
		Assert.assertTrue(
			_roleBuilder.isValidAttribute("objectclass", "groupOfNames"));
		Assert.assertTrue(
			_roleBuilder.isValidAttribute("objectclass", "liferayRole"));
		Assert.assertTrue(
			_roleBuilder.isValidAttribute("objectclass", "organizationalRole"));
		Assert.assertTrue(
			_roleBuilder.isValidAttribute("objectclass", "organizationalUnit"));
		Assert.assertTrue(_roleBuilder.isValidAttribute("objectclass", "top"));
		Assert.assertTrue(_roleBuilder.isValidAttribute("objectclass", "*"));
	}

	protected void assertDirectory(Directory directory) {
		Assert.assertTrue(directory.hasAttribute("cn", "testName"));
		Assert.assertTrue(
			directory.hasAttribute("description", "testDescription"));
		Assert.assertTrue(directory.hasAttribute("ou", "testName"));
	}

	protected void setUpRoles() {
		Role role = mock(Role.class);

		when(
			role.getDescription()
		).thenReturn(
			"testDescription"
		);

		when(
			role.getName()
		).thenReturn(
			"testName"
		);

		when(
			role.getRoleId()
		).thenReturn(
			PRIMARY_KEY
		);

		@SuppressWarnings("rawtypes")
		List roles = new ArrayList<>();

		roles.add(role);

		when(
			roleLocalService.dynamicQuery(Mockito.any(DynamicQuery.class))
		).thenReturn(
			roles
		);

		when(
			_user.getRoles()
		).thenReturn(
			roles
		);
	}

	protected void setUpUsers() {
		_user = mock(User.class);

		when(
			_user.getScreenName()
		).thenReturn(
			"testScreenName"
		);
	}

	private final RoleBuilder _roleBuilder = new RoleBuilder();
	private User _user;

}