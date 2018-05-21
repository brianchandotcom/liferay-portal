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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.ldap.Directory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author William Newbury
 */
@RunWith(PowerMockRunner.class)
public class UserGroupBuilderTest extends BaseVLDAPTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpUsers();

		setUpUserGroups();
	}

	@Test
	public void testBuildDirectoriesInvalidUserGroupDescription()
		throws Exception {

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
			"member", "screenName=testScreenName,ou=test,cn=test,test=test");
		filterConstraint.addAttribute("ou", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesInvalidUserGroupName() throws Exception {
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
			"member", "screenName=testScreenName,ou=test,cn=test,test=test");
		filterConstraint.addAttribute("ou", "invalidName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesNoFilter() throws Exception {
		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, new ArrayList<FilterConstraint>());

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesNoScreenName() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("description", "testDescription");
		filterConstraint.addAttribute("ou", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesNullOu() throws Exception {
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
			"member", "screenName=testScreenName,ou=test,cn=test,test=test");
		filterConstraint.addAttribute("cn", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesNullUser() throws Exception {
		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			null
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("description", "testDescription");
		filterConstraint.addAttribute(
			"member", "screenName=testScreenName,ou=test,cn=test,test=test");
		filterConstraint.addAttribute("cn", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesValidScreenName() throws Exception {
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
			"member", "screenName=testScreenName,ou=test,cn=test,test=test");
		filterConstraint.addAttribute("ou", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesWithInvalidFilter() throws Exception {
		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _userGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(_userGroupBuilder.isValidAttribute("cn", "test"));
		Assert.assertTrue(
			_userGroupBuilder.isValidAttribute("description", "test"));
		Assert.assertTrue(_userGroupBuilder.isValidAttribute("member", "test"));
		Assert.assertTrue(_userGroupBuilder.isValidAttribute("ou", "test"));
		Assert.assertTrue(
			_userGroupBuilder.isValidAttribute("objectclass", "groupOfNames"));
		Assert.assertTrue(
			_userGroupBuilder.isValidAttribute(
				"objectclass", "liferayUserGroup"));
		Assert.assertTrue(
			_userGroupBuilder.isValidAttribute(
				"objectclass", "organizationalUnit"));
		Assert.assertTrue(
			_userGroupBuilder.isValidAttribute("objectclass", "top"));
		Assert.assertTrue(
			_userGroupBuilder.isValidAttribute("objectclass", "*"));
	}

	protected void assertDirectory(Directory directory) {
		Assert.assertTrue(directory.hasAttribute("cn", "testName"));
		Assert.assertTrue(
			directory.hasAttribute("description", "testDescription"));
		Assert.assertTrue(
			directory.hasAttribute("objectclass", "groupOfNames"));
		Assert.assertTrue(
			directory.hasAttribute("objectclass", "liferayUserGroup"));
		Assert.assertTrue(
			directory.hasAttribute("objectclass", "organizationalUnit"));
		Assert.assertTrue(directory.hasAttribute("ou", "testName"));
	}

	protected void setUpUserGroups() {
		UserGroup userGroup = mock(UserGroup.class);

		when(
			userGroup.getDescription()
		).thenReturn(
			"testDescription"
		);

		when(
			userGroup.getName()
		).thenReturn(
			"testName"
		);

		when(
			userGroup.getUserGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		@SuppressWarnings("rawtypes")
		List userGroups = new ArrayList<>();

		userGroups.add(userGroup);

		when(
			userGroupLocalService.dynamicQuery(Mockito.any(DynamicQuery.class))
		).thenReturn(
			userGroups
		);

		when(
			_user.getUserGroups()
		).thenReturn(
			userGroups
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

	private User _user;
	private final UserGroupBuilder _userGroupBuilder = new UserGroupBuilder();

}