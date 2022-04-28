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

package com.liferay.vldap.server.internal.directory;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;
import com.liferay.vldap.server.internal.directory.builder.CommunitiesBuilder;
import com.liferay.vldap.server.internal.directory.builder.CommunityBuilder;
import com.liferay.vldap.server.internal.directory.builder.CompanyBuilder;
import com.liferay.vldap.server.internal.directory.builder.OrganizationBuilder;
import com.liferay.vldap.server.internal.directory.builder.OrganizationsBuilder;
import com.liferay.vldap.server.internal.directory.builder.RoleBuilder;
import com.liferay.vldap.server.internal.directory.builder.RolesBuilder;
import com.liferay.vldap.server.internal.directory.builder.RootBuilder;
import com.liferay.vldap.server.internal.directory.builder.SchemaBuilder;
import com.liferay.vldap.server.internal.directory.builder.TopBuilder;
import com.liferay.vldap.server.internal.directory.builder.UserBuilder;
import com.liferay.vldap.server.internal.directory.builder.UserGroupBuilder;
import com.liferay.vldap.server.internal.directory.builder.UserGroupsBuilder;
import com.liferay.vldap.server.internal.directory.builder.UsersBuilder;
import com.liferay.vldap.server.internal.directory.ldap.Attribute;
import com.liferay.vldap.server.internal.directory.ldap.CommunitiesDirectory;
import com.liferay.vldap.server.internal.directory.ldap.CommunityDirectory;
import com.liferay.vldap.server.internal.directory.ldap.CompanyDirectory;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.directory.ldap.OrganizationDirectory;
import com.liferay.vldap.server.internal.directory.ldap.OrganizationsDirectory;
import com.liferay.vldap.server.internal.directory.ldap.RoleDirectory;
import com.liferay.vldap.server.internal.directory.ldap.RolesDirectory;
import com.liferay.vldap.server.internal.directory.ldap.RootDirectory;
import com.liferay.vldap.server.internal.directory.ldap.SambaMachineDirectory;
import com.liferay.vldap.server.internal.directory.ldap.SchemaDirectory;
import com.liferay.vldap.server.internal.directory.ldap.TopDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UserDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UserGroupDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UserGroupsDirectory;
import com.liferay.vldap.server.internal.directory.ldap.UsersDirectory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.directory.api.ldap.model.filter.AndNode;
import org.apache.directory.api.ldap.model.filter.BranchNode;
import org.apache.directory.api.ldap.model.filter.EqualityNode;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.GreaterEqNode;
import org.apache.directory.api.ldap.model.filter.LeafNode;
import org.apache.directory.api.ldap.model.filter.LessEqNode;
import org.apache.directory.api.ldap.model.filter.NotNode;
import org.apache.directory.api.ldap.model.filter.OrNode;
import org.apache.directory.api.ldap.model.filter.PresenceNode;
import org.apache.directory.api.ldap.model.filter.SubstringNode;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.name.Rdn;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Jonathan McCann
 */
public class DirectoryTreeTest extends BaseVLDAPTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGetCommunitiesSearchBaseWithEmptyIdentifiers()
		throws Exception {

		_setUpGroup();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getCommunitiesSearchBase(
			"Liferay", "testGroupName", 0, company, new ArrayList<>());

		Assert.assertTrue(
			searchBase.getDirectory() instanceof CommunityDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof CommunityBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetCommunitiesSearchBaseWithIdentifiers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpGroup();
		_setUpExpando();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getCommunitiesSearchBase(
			"Liferay", "testGroupName", 0, company,
			Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetCommunitiesSearchBaseWithNullGroup() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getCommunitiesSearchBase(
			"Liferay", "testGroupName", 0, company, new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetCommunitiesSearchBaseWithNullTypeValue()
		throws Exception {

		_setUpGroup();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getCommunitiesSearchBase(
			"Liferay", null, 0, company, new ArrayList<>());

		Assert.assertTrue(
			searchBase.getDirectory() instanceof CommunitiesDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof CommunitiesBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetCommunitiesSearchBaseWithOrganization()
		throws Exception {

		_setUpGroup();
		_setUpOrganization();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getCommunitiesSearchBase(
			"Liferay", "testOrganizationName", 0, company, new ArrayList<>());

		Assert.assertTrue(
			searchBase.getDirectory() instanceof CommunityDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof CommunityBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetDirectories() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		CompanyBuilder companyBuilder = new CompanyBuilder();

		companyBuilder.addDirectoryBuilder(new CompanyBuilder());

		SearchBase searchBase = new SearchBase(
			new CompanyDirectory("Liferay", company), companyBuilder,
			"Liferay");

		List<Directory> directories = directoryTree.getDirectories(
			searchBase, null, SearchScope.OBJECT);

		Assert.assertEquals(directories.toString(), 1, directories.size());
		Assert.assertTrue(directories.get(0) instanceof CompanyDirectory);
	}

	@Test
	public void testGetDirectoriesWithNullSearchBase() {
		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = new SearchBase(null, null);

		List<Directory> directories = directoryTree.getDirectories(
			searchBase, null, SearchScope.OBJECT);

		Assert.assertEquals(directories.toString(), 0, directories.size());
	}

	@Test
	public void testGetIdentifiers() throws Exception {
		Dn dn = new Dn("");

		DirectoryTree directoryTree = new DirectoryTree();

		List<Identifier> identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("o=Liferay");

		identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("ou=liferay.com,o=Liferay");

		identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("ou=Users,ou=liferay.com,o=Liferay");

		identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("cn=test,ou=Users,ou=liferay.com,o=Liferay");

		identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay");

		identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 1, identifiers.size());

		Identifier identifier = identifiers.get(0);

		Assert.assertEquals("cn", identifier.getRdnType());
		Assert.assertEquals("test", identifier.getRdnValue());

		dn = new Dn(
			"uid=test,cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay");

		identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 2, identifiers.size());

		identifier = identifiers.get(0);

		Assert.assertEquals("cn", identifier.getRdnType());
		Assert.assertEquals("test", identifier.getRdnValue());

		identifier = identifiers.get(1);

		Assert.assertEquals("uid", identifier.getRdnType());
		Assert.assertEquals("test", identifier.getRdnValue());
	}

	@Test
	public void testGetIdentifiersWithNullRdnType() throws Exception {
		Dn dn = Mockito.spy(
			new Dn("cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay"));

		Rdn rdn = Mockito.mock(Rdn.class);

		Mockito.when(
			dn.getRdn(Mockito.anyInt())
		).thenReturn(
			rdn
		);

		Mockito.when(
			rdn.getNormType()
		).thenReturn(
			null
		);

		Mockito.when(
			rdn.getValue(Mockito.any())
		).thenReturn(
			""
		);

		DirectoryTree directoryTree = new DirectoryTree();

		List<Identifier> identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());
	}

	@Test
	public void testGetIdentifiersWithNullRdnValue() throws Exception {
		Dn dn = Mockito.spy(
			new Dn("cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay"));

		Rdn rdn = Mockito.mock(Rdn.class);

		Mockito.when(
			dn.getRdn(Mockito.anyInt())
		).thenReturn(
			rdn
		);

		Mockito.when(
			rdn.getNormType()
		).thenReturn(
			""
		);

		Object valueObject = Mockito.mock(Object.class);

		Mockito.when(
			rdn.getValue(Mockito.anyString())
		).thenReturn(
			valueObject
		);

		Mockito.when(
			valueObject.toString()
		).thenReturn(
			null
		);

		DirectoryTree directoryTree = new DirectoryTree();

		List<Identifier> identifiers = directoryTree.getIdentifiers(dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());
	}

	@Test
	public void testGetOrganizationsSearchBaseWithEmptyIdentifiers()
		throws Exception {

		_setUpOrganization();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getOrganizationsSearchBase(
			"Liferay", "testOrganizationName", 0, company, new ArrayList<>());

		Assert.assertTrue(
			searchBase.getDirectory() instanceof OrganizationDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof OrganizationBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetOrganizationsSearchBaseWithIdentifiers()
		throws Exception {

		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpOrganization();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getOrganizationsSearchBase(
			"Liferay", "testOrganizationName", 0, company,
			Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetOrganizationsSearchBaseWithNullOrganization()
		throws Exception {

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getOrganizationsSearchBase(
			"Liferay", "testOrganizationName", 0, company, new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetOrganizationsSearchBaseWithNullTypeValue()
		throws Exception {

		_setUpOrganization();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getOrganizationsSearchBase(
			"Liferay", null, 0, company, new ArrayList<>());

		Assert.assertTrue(
			searchBase.getDirectory() instanceof OrganizationsDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof OrganizationsBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetRolesSearchBaseWithEmptyIdentifiers() throws Exception {
		_setUpRole();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getRolesSearchBase(
			"Liferay", "testRoleName", 0, company, new ArrayList<>());

		Assert.assertTrue(searchBase.getDirectory() instanceof RoleDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof RoleBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetRolesSearchBaseWithIdentifiers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();
		_setUpRole();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getRolesSearchBase(
			"Liferay", "testRoleName", 0, company,
			Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetRolesSearchBaseWithNullRole() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getRolesSearchBase(
			"Liferay", "testRoleName", 0, company, new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetRolesSearchBaseWithNullTypeValue() throws Exception {
		_setUpRole();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getRolesSearchBase(
			"Liferay", null, 0, company, new ArrayList<>());

		Assert.assertTrue(searchBase.getDirectory() instanceof RolesDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof RolesBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetSambaMachinesSearchBase() throws Exception {
		_setUpOrganization();

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("sambaDomainName", "testDomainName"));

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSambaMachinesSearchBase(
			"Liferay", company, _organization, identifiers);

		Assert.assertTrue(
			searchBase.getDirectory() instanceof SambaMachineDirectory);
		Assert.assertNull(searchBase.getDirectoryBuilder());
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithInvalidIdentifier()
		throws Exception {

		_setUpOrganization();

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("ou", "sambaDomain"));

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSambaMachinesSearchBase(
			"Liferay", company, _organization, identifiers);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithMultipleIdentifiers()
		throws Exception {

		_setUpOrganization();

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("ou", "sambaDomain"));
		identifiers.add(new Identifier("cn", "test"));

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSambaMachinesSearchBase(
			"Liferay", company, _organization, identifiers);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithNullDirectories()
		throws Exception {

		_setUpOrganization();

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("sambaDomainName", "invalidDomainName"));

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSambaMachinesSearchBase(
			"Liferay", company, _organization, identifiers);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithNullOrganization()
		throws Exception {

		List<Identifier> identifiers = new ArrayList<>();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSambaMachinesSearchBase(
			"Liferay", company, null, identifiers);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSearchBaseWithCommonName() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("o=subschema");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		Assert.assertTrue(searchBase.getDirectory() instanceof SchemaDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof SchemaBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
	}

	@Test
	public void testGetSearchBaseWithCommunitiesType() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=Communities,ou=liferay.com,o=Liferay");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		Assert.assertTrue(
			searchBase.getDirectory() instanceof CommunitiesDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof CommunitiesBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());

		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetSearchBaseWithInvalidIdentifiers() throws Exception {
		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "invalidIdentifier"));
		identifiers.add(new Identifier("sambaDomainName", "testDomainName"));

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSearchBase(
			"Liferay", 0, new LinkedHashMap<String, Object>(), identifiers,
			_organization, company);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSearchBaseWithInvalidType() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=test,ou=liferay.com,o=Liferay");

		Assert.assertNull(directoryTree.getSearchBase(dn, 0));
	}

	@Test
	public void testGetSearchBaseWithMultipleIdentifiers() throws Exception {
		_setUpOrganization();

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "Samba Machines"));
		identifiers.add(new Identifier("sambaDomainName", "testDomainName"));

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSearchBase(
			"Liferay", 0, new LinkedHashMap<>(), identifiers, _organization,
			company);

		Assert.assertTrue(
			searchBase.getDirectory() instanceof SambaMachineDirectory);
		Assert.assertNull(searchBase.getDirectoryBuilder());
	}

	@Test
	public void testGetSearchBaseWithNonliferayTop() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("o=test");

		Assert.assertNull(directoryTree.getSearchBase(dn, 0));
	}

	@Test
	public void testGetSearchBaseWithNullCompany() throws Exception {
		CompanyLocalService companyLocalService = getMockPortalService(
			CompanyLocalServiceUtil.class, CompanyLocalService.class);

		Mockito.when(
			companyLocalService.getCompanyByWebId(Mockito.eq("test"))
		).thenThrow(
			new NoSuchCompanyException()
		);

		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=test,o=Liferay");

		Assert.assertNull(directoryTree.getSearchBase(dn, 0));
	}

	@Test
	public void testGetSearchBaseWithNullCompanyWebId() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("o=Liferay");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		TopDirectory topDirectory = (TopDirectory)searchBase.getDirectory();

		List<Attribute> attributes = topDirectory.getAttributes("o");

		Attribute topAttribute = attributes.get(0);

		Assert.assertEquals("Liferay", topAttribute.getValue());

		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof TopBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
	}

	@Test
	public void testGetSearchBaseWithNullDn() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Assert.assertNull(directoryTree.getSearchBase(null, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSearchBaseWithNullIdentifiers() throws Exception {
		List<Identifier> identifiers = new ArrayList<>();

		DirectoryTree directoryTree = new DirectoryTree();

		directoryTree.getSearchBase(
			"Liferay", 0, new LinkedHashMap<>(), identifiers, _organization,
			company);
	}

	@Test
	public void testGetSearchBaseWithNullTop() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		Assert.assertTrue(searchBase.getDirectory() instanceof RootDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof RootBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
	}

	@Test
	public void testGetSearchBaseWithNullType() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=liferay.com,o=Liferay");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		CompanyDirectory companyDirectory =
			(CompanyDirectory)searchBase.getDirectory();

		List<Attribute> attributes = companyDirectory.getAttributes("ou");

		Attribute webIdAttribute = attributes.get(0);

		Assert.assertEquals("liferay.com", webIdAttribute.getValue());

		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof CompanyBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetSearchBaseWithOneIdentifier() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSearchBase(
			"Liferay", 0, new LinkedHashMap<String, Object>(),
			Arrays.asList(new Identifier("cn", "testScreenName")),
			_organization, company);

		_assertUserSearchBase(searchBase, false);
	}

	@Test
	public void testGetSearchBaseWithOrganizationsType() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=Organizations,ou=liferay.com,o=Liferay");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		Assert.assertTrue(
			searchBase.getDirectory() instanceof OrganizationsDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof OrganizationsBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetSearchBaseWithRolesType() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=Roles,ou=liferay.com,o=Liferay");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		Assert.assertTrue(searchBase.getDirectory() instanceof RolesDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof RolesBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetSearchBaseWithUserGroupsType() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=User Groups,ou=liferay.com,o=Liferay");

		SearchBase searchBase = directoryTree.getSearchBase(dn, 0);

		Assert.assertTrue(
			searchBase.getDirectory() instanceof UserGroupsDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof UserGroupsBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetSearchBaseWithUsersType() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		Dn dn = new Dn("ou=Users,ou=liferay.com,o=Liferay");

		_assertUsersSearchBase(directoryTree.getSearchBase(dn, 0));
	}

	@Test
	public void testGetUserGroupsSearchBaseWithEmptyIdentifiers()
		throws Exception {

		_setUpGroup();
		_setUpUserGroups();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUserGroupsSearchBase(
			"Liferay", "testUserGroupName", 0, company, new ArrayList<>());

		Assert.assertTrue(
			searchBase.getDirectory() instanceof UserGroupDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof UserGroupBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetUserGroupsSearchBaseWithIdentifiers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpGroup();
		_setUpPasswordPolicy();
		setUpPortalUtil();
		_setUpUserGroups();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUserGroupsSearchBase(
			"Liferay", "testUserGroupName", 0, company,
			Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetUserGroupsSearchBaseWithNullTypeValue()
		throws Exception {

		_setUpGroup();
		_setUpUserGroups();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUserGroupsSearchBase(
			"Liferay", null, 0, company, new ArrayList<>());

		Assert.assertTrue(
			searchBase.getDirectory() instanceof UserGroupsDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof UserGroupsBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetUserGroupsSearchBaseWithNullUserGroup()
		throws Exception {

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUserGroupsSearchBase(
			"Liferay", "testUserGroupName", 0, company, new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithEmptyIdentifiers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUsersSearchBase(
			"Liferay", "testScreenName", 0, company, new ArrayList<>());

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetUsersSearchBaseWithIdentifiers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUsersSearchBase(
			"Liferay", "testScreenName", 0, company,
			Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetUsersSearchBaseWithInvalidRdnType() throws Exception {
		_setUpGroup();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUsersSearchBase(
			"o=Liferay", "ou", "testScreenName",
			new LinkedHashMap<String, Object>(), 0, company);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithNullTypeValue() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUsersSearchBase(
			"Liferay", null, 0, company, new ArrayList<>());

		_assertUsersSearchBase(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithNullUser() throws Exception {
		_setUpGroup();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUsersSearchBase(
			"Liferay", "testScreenName", 0, company, new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithNullUsers() throws Exception {
		_setUpGroup();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUsersSearchBase(
			"o=Liferay", "cn", "testScreenName",
			new LinkedHashMap<String, Object>(), 0, company);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithUsers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getUsersSearchBase(
			"Liferay", "cn", "testScreenName",
			new LinkedHashMap<String, Object>(), 0, company);

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithAndNode()
		throws Exception {

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromBranchNode(new AndNode(), true);

		_assertFilterConstraints(filterConstraints);
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithAndNodeCollision()
		throws Exception {

		DirectoryTree directoryTree = new DirectoryTree();

		BranchNode branchNode = new AndNode();

		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		branchNode.addNode(exprNode);

		exprNode = new EqualityNode("cn", "newTestScreenName");

		branchNode.addNode(exprNode);

		List<FilterConstraint> filterConstraints =
			directoryTree.toFilterConstraintsFromBranchNode(branchNode);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithAndNodes()
		throws Exception {

		DirectoryTree directoryTree = new DirectoryTree();

		BranchNode branchNode = new AndNode();

		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		branchNode.addNode(exprNode);

		exprNode = new EqualityNode("ou", "test");

		branchNode.addNode(exprNode);

		List<FilterConstraint> filterConstraints =
			directoryTree.toFilterConstraintsFromBranchNode(branchNode);

		Assert.assertEquals(
			filterConstraints.toString(), 1, filterConstraints.size());

		FilterConstraint filterConstraint = filterConstraints.get(0);

		Assert.assertEquals("testScreenName", filterConstraint.getValue("cn"));
		Assert.assertEquals("test", filterConstraint.getValue("ou"));
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithNotNode()
		throws Exception {

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromBranchNode(new NotNode(), true);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithNullExprNode()
		throws Exception {

		BranchNode branchNode = new AndNode();

		branchNode.addNode(null);

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromBranchNode(branchNode, false);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));

		branchNode = new OrNode();

		branchNode.addNode(null);

		filterConstraints = _getFilterConstraintsFromBranchNode(
			branchNode, false);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithNullNode()
		throws Exception {

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromBranchNode(null, false);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithOrNode()
		throws Exception {

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromBranchNode(new OrNode(), true);

		_assertFilterConstraints(filterConstraints);
	}

	@Test
	public void testToFilterConstraintsFromLeafNodeSubstringNode()
		throws Exception {

		LeafNode leafNode = new SubstringNode("cn");

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromLeafNode(leafNode);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromLeafNodeWithEqualityNode()
		throws Exception {

		LeafNode leafNode = new EqualityNode("cn", "testScreenName");

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromLeafNode(leafNode);

		_assertFilterConstraints(filterConstraints);
	}

	@Test
	public void testToFilterConstraintsFromLeafNodeWithGreaterEqNode()
		throws Exception {

		LeafNode leafNode = new GreaterEqNode("cn", "testScreenName");

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromLeafNode(leafNode);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromLeafNodeWithLessEqNode()
		throws Exception {

		LeafNode leafNode = new LessEqNode("cn", "testScreenName");

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromLeafNode(leafNode);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromLeafNodeWithNullNode()
		throws Exception {

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromLeafNode(null);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromLeafNodeWithPresenceNode()
		throws Exception {

		LeafNode leafNode = new PresenceNode("cn");

		List<FilterConstraint> filterConstraints =
			_getFilterConstraintsFromLeafNode(leafNode);

		Assert.assertEquals(
			filterConstraints.toString(), 1, filterConstraints.size());

		FilterConstraint filterConstraint = filterConstraints.get(0);

		Assert.assertEquals("*", filterConstraint.getValue("cn"));
	}

	@Test
	public void testToFilterConstraintsWithBranchExprNode() throws Exception {
		BranchNode branchNode = new AndNode();

		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		branchNode.addNode(exprNode);

		DirectoryTree directoryTree = new DirectoryTree();

		List<FilterConstraint> filterConstraints =
			directoryTree.toFilterConstraints(branchNode);

		_assertFilterConstraints(filterConstraints);
	}

	@Test
	public void testToFilterConstraintsWithLeafExprNode() throws Exception {
		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		DirectoryTree directoryTree = new DirectoryTree();

		List<FilterConstraint> filterConstraints =
			directoryTree.toFilterConstraints(exprNode);

		_assertFilterConstraints(filterConstraints);
	}

	@Test
	public void testToFilterConstraintsWithNullExprNode() throws Exception {
		DirectoryTree directoryTree = new DirectoryTree();

		ExprNode exprNode = null;

		List<FilterConstraint> filterConstraints =
			directoryTree.toFilterConstraints(exprNode);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	private void _assertFilterConstraints(
		List<FilterConstraint> filterConstraints) {

		Assert.assertEquals(
			filterConstraints.toString(), 1, filterConstraints.size());

		FilterConstraint filterConstraint = filterConstraints.get(0);

		Assert.assertEquals("testScreenName", filterConstraint.getValue("cn"));
	}

	private void _assertUserSearchBase(
		SearchBase searchBase, boolean assertUser) {

		Assert.assertTrue(searchBase.getDirectory() instanceof UserDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof UserBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());

		if (assertUser) {
			User user = searchBase.getUser();

			Assert.assertEquals("testScreenName", user.getScreenName());
		}
	}

	private void _assertUsersSearchBase(SearchBase searchBase) {
		Assert.assertTrue(searchBase.getDirectory() instanceof UsersDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof UsersBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	private List<FilterConstraint> _getFilterConstraintsFromBranchNode(
			BranchNode branchNode, boolean addExprNode)
		throws Exception {

		if (addExprNode) {
			ExprNode exprNode = new EqualityNode("cn", "testScreenName");

			branchNode.addNode(exprNode);
		}

		DirectoryTree directoryTree = new DirectoryTree();

		return directoryTree.toFilterConstraintsFromBranchNode(branchNode);
	}

	private List<FilterConstraint> _getFilterConstraintsFromLeafNode(
			LeafNode leafNode)
		throws Exception {

		DirectoryTree directoryTree = new DirectoryTree();

		return directoryTree.toFilterConstraintsFromLeafNode(leafNode);
	}

	private void _setUpExpando() {
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
			_user.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);
	}

	private void _setUpFastDateFormat() {
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

	private void _setUpGroup() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			groupLocalService.fetchGroup(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testGroupName"))
		).thenReturn(
			group
		);

		Mockito.when(
			groupLocalService.fetchGroup(Mockito.eq(PRIMARY_KEY))
		).thenReturn(
			group
		);

		Mockito.when(
			group.getName(LocaleUtil.getDefault())
		).thenReturn(
			"testGroupName"
		);
	}

	private void _setUpOrganization() throws Exception {
		_organization = Mockito.mock(Organization.class);

		Mockito.when(
			organizationLocalService.fetchOrganization(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testOrganizationName"))
		).thenReturn(
			_organization
		);

		Mockito.when(
			_organization.getGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			_organization.getName()
		).thenReturn(
			"testOrganizationName"
		);
	}

	private void _setUpPasswordPolicy() throws Exception {
		PasswordPolicy passwordPolicy = Mockito.mock(PasswordPolicy.class);

		setUpPasswordPolicy(passwordPolicy);

		Mockito.when(
			_user.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);
	}

	private void _setUpRole() throws Exception {
		Role role = Mockito.mock(Role.class);

		Mockito.when(
			role.getName()
		).thenReturn(
			"testRoleName"
		);

		Mockito.when(
			roleLocalService.fetchRole(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testRoleName"))
		).thenReturn(
			role
		);
	}

	private void _setUpUserGroups() throws Exception {
		UserGroup userGroup = Mockito.mock(UserGroup.class);

		Mockito.when(
			userGroup.getName()
		).thenReturn(
			"testUserGroupName"
		);

		Mockito.when(
			userGroup.getUserGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			userGroupLocalService.fetchUserGroup(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			userGroup
		);
	}

	private void _setUpUsers() {
		_user = Mockito.mock(User.class);

		Mockito.when(
			_user.getScreenName()
		).thenReturn(
			"testScreenName"
		);

		Mockito.when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_user
		);

		Mockito.when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			Arrays.asList(_user)
		);
	}

	private static Organization _organization;
	private static User _user;

}