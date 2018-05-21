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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
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
 * @author Matthew Tambara
 */
@RunWith(PowerMockRunner.class)
public class OrganizationBuilderTest extends BaseVLDAPTestCase {

	@Test
	public void testBuildDirectoriesWithInvalidFilterConstraints()
		throws Exception {

		setUpUsers();

		setUpOrganizations();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _organizationBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithInvalidOrganizationName()
		throws Exception {

		setUpUsers();

		setUpOrganizations();

		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("ou", "invalidName");
		filterConstraint.addAttribute(
			"member", "screenName=testScreenName,ou=test,cn=test,blah=test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _organizationBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithNoFilterConstraints() throws Exception {
		setUpUsers();

		setUpOrganizations();

		List<Directory> directories = _organizationBuilder.buildDirectories(
			searchBase, new ArrayList<FilterConstraint>());

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesWithNoScreenName() throws Exception {
		setUpUsers();

		setUpOrganizations();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("ou", "testName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _organizationBuilder.buildDirectories(
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

		List<Directory> directories = _organizationBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithValidScreenName() throws Exception {
		setUpUsers();

		setUpOrganizations();

		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_user
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("ou", "testName");
		filterConstraint.addAttribute(
			"member", "screenName=testScreenName,ou=test,cn=test,blah=test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _organizationBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(_organizationBuilder.isValidAttribute("cn", "test"));
		Assert.assertTrue(
			_organizationBuilder.isValidAttribute("member", "test"));
		Assert.assertTrue(_organizationBuilder.isValidAttribute("ou", "test"));
		Assert.assertTrue(
			_organizationBuilder.isValidAttribute(
				"objectclass", "groupOfNames"));
		Assert.assertTrue(
			_organizationBuilder.isValidAttribute(
				"objectclass", "liferayOrganization"));
		Assert.assertTrue(
			_organizationBuilder.isValidAttribute(
				"objectclass", "organizationalUnit"));
		Assert.assertTrue(
			_organizationBuilder.isValidAttribute("objectclass", "top"));
		Assert.assertTrue(
			_organizationBuilder.isValidAttribute("objectclass", "*"));
	}

	protected void assertDirectory(Directory directory) {
		Assert.assertTrue(directory.hasAttribute("cn", "testName"));
		Assert.assertTrue(
			directory.hasAttribute(
				"member",
				"cn=testScreenName,ou=Users,ou=liferay.com,o=Liferay"));
		Assert.assertTrue(
			directory.hasAttribute("objectclass", "liferayOrganization"));
		Assert.assertTrue(directory.hasAttribute("ou", "testName"));
	}

	protected void setUpOrganizations() throws Exception {
		Organization organization = mock(Organization.class);

		when(
			organization.getName()
		).thenReturn(
			"testName"
		);

		when(
			organization.getOrganizationId()
		).thenReturn(
			PRIMARY_KEY
		);

		@SuppressWarnings("rawtypes")
		List organizations = new ArrayList<>();

		organizations.add(organization);

		when(
			organizationLocalService.dynamicQuery(
				Mockito.any(DynamicQuery.class))
		).thenReturn(
			organizations
		);

		when(
			_user.getOrganizations()
		).thenReturn(
			organizations
		);
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

	private final OrganizationBuilder _organizationBuilder =
		new OrganizationBuilder();
	private User _user;

}