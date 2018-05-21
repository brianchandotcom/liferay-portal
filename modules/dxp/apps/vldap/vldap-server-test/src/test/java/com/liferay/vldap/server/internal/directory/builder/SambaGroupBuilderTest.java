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
 * @author Matthew Tambara
 */
@RunWith(PowerMockRunner.class)
public class SambaGroupBuilderTest extends BaseVLDAPTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		when(
			searchBase.getCompany()
		).thenReturn(
			company
		);

		_sambaGroupBuilder = new SambaGroupBuilder();
	}

	@Test
	public void testBuildDirectoriesNoGIDNumber() throws Exception {
		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "network");
		filterConstraint.addAttribute("sambaSID", "S-1-5-2");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory, false, false, false);
	}

	@Test
	public void testBuildDirectoriesValidDisplayName() throws Exception {
		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("displayName", "root");
		filterConstraint.addAttribute("gidNumber", "0");
		filterConstraint.addAttribute("sambaSID", "S-1-5-32-544");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory, true, true, true);

		Assert.assertTrue(directory.hasAttribute("sambaSID", "S-1-5-32-544"));
	}

	@Test
	public void testBuildDirectoriesValidGIDNumber() throws Exception {
		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "root");
		filterConstraint.addAttribute("gidNumber", "0");
		filterConstraint.addAttribute("sambaSID", "S-1-5-32-544");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory, true, true, true);

		Assert.assertTrue(directory.hasAttribute("sambaSID", "S-1-5-32-544"));
	}

	@Test
	public void testBuildDirectoriesValidSambaSIDList() throws Exception {
		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "root");
		filterConstraint.addAttribute("gidNumber", "0");
		filterConstraint.addAttribute("sambaSIDList", "S-1-5-32-544");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory, true, true, true);

		Assert.assertTrue(directory.hasAttribute("sambaSID", "S-1-5-32-544"));
	}

	@Test
	public void testBuildDirectoriesWithInvalidFilterConstraints()
		throws Exception {

		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithNullFilterConstraints()
		throws Exception {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		List<Directory> directories = _sambaGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesWithNullOrganization() throws Exception {
		Organization organization = mock(Organization.class);

		when(
			organization.getName()
		).thenReturn(
			"testName"
		);

		List<Object> organizations = new ArrayList<>();

		organizations.add(organization);

		when(
			organizationLocalService.dynamicQuery(
				Mockito.any(DynamicQuery.class))
		).thenReturn(
			organizations
		);

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("cn", "network");
		filterConstraint.addAttribute("sambaSID", "S-1-5-2");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaGroupBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory, false, false, false);
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(_sambaGroupBuilder.isValidAttribute("cn", "test"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("displayName", "test"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("gidNumber", "test"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("sambaGroupType", "test"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("sambaSID", "test"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("sambaSIDList", "test"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("objectclass", "liferayRole"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("objectclass", "posixGroup"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute(
				"objectclass", "sambaGroupMapping"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("objectclass", "top"));
		Assert.assertTrue(
			_sambaGroupBuilder.isValidAttribute("objectclass", "*"));
	}

	protected void assertDirectory(
		Directory directory, boolean gid, boolean root, boolean posixGroup) {

		Assert.assertEquals(
			root, directory.hasAttribute("displayName", "root"));
		Assert.assertEquals(gid, directory.hasAttribute("gidNumber", "0"));
		Assert.assertEquals(
			posixGroup, directory.hasAttribute("objectclass", "posixGroup"));
		Assert.assertTrue(
			directory.hasAttribute("objectclass", "sambaGroupMapping"));
		Assert.assertTrue(directory.hasAttribute("sambaGroupType", "4"));
	}

	protected void setUpOrganization() throws Exception {
		Organization organization = mock(Organization.class);

		when(
			organization.getName()
		).thenReturn(
			"testName"
		);

		when(
			searchBase.getOrganization()
		).thenReturn(
			organization
		);
	}

	private SambaGroupBuilder _sambaGroupBuilder;

}