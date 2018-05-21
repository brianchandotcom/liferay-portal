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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.ldap.Directory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
public class CommunityBuilderTest extends BaseVLDAPTestCase {

	@Test
	public void testBuildDirectoriesWithInvalidFilterConstraints()
		throws Exception {

		setUpGroups();
		setUpUsers();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _communityBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithInvalidGroupDescription()
		throws Exception {

		setUpGroups();
		setUpUsers();

		when(
			_user.getGroups()
		).thenReturn(
			_groups
		);

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

		List<Directory> directories = _communityBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithInvalidGroupName() throws Exception {
		setUpGroups();
		setUpUsers();

		when(
			_user.getGroups()
		).thenReturn(
			_groups
		);

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

		List<Directory> directories = _communityBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithNoScreenName() throws Exception {
		setUpGroups();
		setUpUsers();

		when(
			groupLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.any(LinkedHashMap.class), Mockito.anyBoolean(),
				Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			_groups
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("description", "testDescription");
		filterConstraint.addAttribute("ou", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _communityBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesWithNullFilterConstraints()
		throws Exception {

		setUpGroups();
		setUpUsers();

		when(
			groupLocalService.getCompanyGroups(
				Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			_groups
		);

		List<Directory> directories = _communityBuilder.buildDirectories(
			searchBase, new ArrayList<FilterConstraint>());

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

		List<Directory> directories = _communityBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithValidScreenName() throws Exception {
		setUpGroups();
		setUpUsers();

		when(
			_user.getGroups()
		).thenReturn(
			_groups
		);

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

		List<Directory> directories = _communityBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(_communityBuilder.isValidAttribute("cn", "test"));
		Assert.assertTrue(
			_communityBuilder.isValidAttribute("description", "test"));
		Assert.assertTrue(_communityBuilder.isValidAttribute("member", "test"));
		Assert.assertTrue(_communityBuilder.isValidAttribute("ou", "test"));
		Assert.assertTrue(
			_communityBuilder.isValidAttribute("objectclass", "groupOfNames"));
		Assert.assertTrue(
			_communityBuilder.isValidAttribute(
				"objectclass", "liferayCommunity"));
		Assert.assertTrue(
			_communityBuilder.isValidAttribute(
				"objectclass", "organizationalUnit"));
		Assert.assertTrue(
			_communityBuilder.isValidAttribute("objectclass", "top"));
		Assert.assertTrue(
			_communityBuilder.isValidAttribute("objectclass", "*"));
	}

	protected void assertDirectory(Directory directory) {
		Assert.assertTrue(directory.hasAttribute("cn", "testName"));
		Assert.assertTrue(
			directory.hasAttribute("description", "testDescription"));
		Assert.assertTrue(
			directory.hasAttribute(
				"member",
				"cn=testScreenName,ou=Users,ou=liferay.com,o=Liferay"));
		Assert.assertTrue(
			directory.hasAttribute("objectclass", "liferayCommunity"));
		Assert.assertTrue(directory.hasAttribute("ou", "testName"));
	}

	protected void setUpGroups() {
		Group group = mock(Group.class);

		when(
			group.getDescription()
		).thenReturn(
			"testDescription"
		);

		when(
			group.getDescription(LocaleUtil.getDefault())
		).thenReturn(
			"testDescription"
		);

		when(
			group.getGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			group.getName()
		).thenReturn(
			"testName"
		);

		when(
			group.getName(LocaleUtil.getDefault())
		).thenReturn(
			"testName"
		);

		_groups.add(group);
	}

	protected void setUpUsers() {
		_user = mock(User.class);

		when(
			_user.getScreenName()
		).thenReturn(
			"testScreenName"
		);

		List<User> users = new ArrayList<>();

		users.add(_user);

		when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			users
		);
	}

	private final CommunityBuilder _communityBuilder = new CommunityBuilder();
	private final List<Group> _groups = new ArrayList<>();
	private User _user;

}