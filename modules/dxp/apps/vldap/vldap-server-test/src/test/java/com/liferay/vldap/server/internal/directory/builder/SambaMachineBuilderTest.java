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
public class SambaMachineBuilderTest extends BaseVLDAPTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		when(
			searchBase.getCompany()
		).thenReturn(
			company
		);
	}

	@Test
	public void testBuildDirectoriesDefaultFilterConstraints()
		throws Exception {

		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("sambaDomainName", "testDomainName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaMachineBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesInvalidFilterConstraints()
		throws Exception {

		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("test", "test");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaMachineBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesInvalidSambaDomain() throws Exception {
		setUpOrganization();

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute("sambaDomainName", "invalidDomainName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaMachineBuilder.buildDirectories(
			searchBase, filterConstraints);

		Assert.assertTrue(directories.isEmpty());
	}

	@Test
	public void testBuildDirectoriesValidOrganizationDomain() throws Exception {
		setUpOrganization();

		List<Directory> directories = _sambaMachineBuilder.buildDirectories(
			searchBase.getTop(), company, _organization, "testDomainName");

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testBuildDirectoriesWithNullFilterConstraints()
		throws Exception {

		List<FilterConstraint> filterConstraints = new ArrayList<>();

		List<Directory> directories = _sambaMachineBuilder.buildDirectories(
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

		filterConstraint.addAttribute("sambaDomainName", "testDomainName");

		filterConstraints.add(filterConstraint);

		List<Directory> directories = _sambaMachineBuilder.buildDirectories(
			searchBase, filterConstraints);

		Directory directory = directories.get(0);

		assertDirectory(directory);
	}

	@Test
	public void testValidAttribute() {
		Assert.assertTrue(
			_sambaMachineBuilder.isValidAttribute("sambaDomainName", "test"));
		Assert.assertTrue(
			_sambaMachineBuilder.isValidAttribute(
				"objectclass", "sambaDomain"));
	}

	protected void assertDirectory(Directory directory) {
		Assert.assertTrue(
			directory.hasAttribute("sambaDomainName", "testDomainName"));
		Assert.assertTrue(directory.hasAttribute("sambaNextUserRid", "1000"));
		Assert.assertTrue(
			directory.hasAttribute(
				"sambaSID", "S-1-5-21-" + company.getCompanyId()));
	}

	protected void setUpOrganization() throws Exception {
		_organization = mock(Organization.class);

		when(
			_organization.getName()
		).thenReturn(
			"testName"
		);

		when(
			searchBase.getOrganization()
		).thenReturn(
			_organization
		);
	}

	private Organization _organization;
	private final SambaMachineBuilder _sambaMachineBuilder =
		new SambaMachineBuilder();

}