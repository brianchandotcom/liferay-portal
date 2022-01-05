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
import com.liferay.portal.kernel.model.Company;
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
import com.liferay.vldap.server.internal.util.LdapUtil;

import java.lang.reflect.Method;

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Jonathan McCann
 */
@PrepareForTest(LdapUtil.class)
public class DirectoryTreeTest extends BaseVLDAPTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_clazz = Class.forName(DirectoryTree.class.getName());

		_classInstance = _clazz.newInstance();
	}

	@Test
	public void testGetCommunitiesSearchBaseWithEmptyIdentifiers()
		throws Exception {

		_setUpGroup();

		Method getCommunitiesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getCommunitiesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getCommunitiesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getCommunitiesSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testGroupName", 0, company,
				new ArrayList<>());

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

		Method getCommunitiesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getCommunitiesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getCommunitiesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getCommunitiesSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testGroupName", 0, company,
				Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetCommunitiesSearchBaseWithNullGroup() throws Exception {
		Method getCommunitiesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getCommunitiesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getCommunitiesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getCommunitiesSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testGroupName", 0, company,
				new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetCommunitiesSearchBaseWithNullTypeValue()
		throws Exception {

		_setUpGroup();

		Method getCommunitiesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getCommunitiesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getCommunitiesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getCommunitiesSearchBaseMethod.invoke(
				_classInstance, "Liferay", null, 0, company, new ArrayList<>());

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

		Method getCommunitiesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getCommunitiesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getCommunitiesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getCommunitiesSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testOrganizationName", 0, company,
				new ArrayList<>());

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
		Method getIdentifiersMethod = _clazz.getDeclaredMethod(
			"getIdentifiers", Dn.class);

		getIdentifiersMethod.setAccessible(true);

		Dn dn = new Dn("");

		List<Identifier> identifiers =
			(List<Identifier>)getIdentifiersMethod.invoke(_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("o=Liferay");

		identifiers = (List<Identifier>)getIdentifiersMethod.invoke(
			_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("ou=liferay.com,o=Liferay");

		identifiers = (List<Identifier>)getIdentifiersMethod.invoke(
			_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("ou=Users,ou=liferay.com,o=Liferay");

		identifiers = (List<Identifier>)getIdentifiersMethod.invoke(
			_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("cn=test,ou=Users,ou=liferay.com,o=Liferay");

		identifiers = (List<Identifier>)getIdentifiersMethod.invoke(
			_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());

		dn = new Dn("cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay");

		identifiers = (List<Identifier>)getIdentifiersMethod.invoke(
			_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 1, identifiers.size());

		Identifier identifier = identifiers.get(0);

		Assert.assertEquals("cn", identifier.getRdnType());
		Assert.assertEquals("test", identifier.getRdnValue());

		dn = new Dn(
			"uid=test,cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay");

		identifiers = (List<Identifier>)getIdentifiersMethod.invoke(
			_classInstance, dn);

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
		Dn dn = new Dn("cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay");

		spy(LdapUtil.class);

		doReturn(
			null
		).when(
			LdapUtil.class, "getRdnType", dn, 4
		);

		Method getIdentifiersMethod = _clazz.getDeclaredMethod(
			"getIdentifiers", Dn.class);

		getIdentifiersMethod.setAccessible(true);

		List<Identifier> identifiers =
			(List<Identifier>)getIdentifiersMethod.invoke(_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());
	}

	@Test
	public void testGetIdentifiersWithNullRdnValue() throws Exception {
		Dn dn = new Dn("cn=test,ou=test,ou=Users,ou=liferay.com,o=Liferay");

		spy(LdapUtil.class);

		doReturn(
			null
		).when(
			LdapUtil.class, "getRdnValue", dn, 4
		);

		Method getIdentifiersMethod = _clazz.getDeclaredMethod(
			"getIdentifiers", Dn.class);

		getIdentifiersMethod.setAccessible(true);

		List<Identifier> identifiers =
			(List<Identifier>)getIdentifiersMethod.invoke(_classInstance, dn);

		Assert.assertEquals(identifiers.toString(), 0, identifiers.size());
	}

	@Test
	public void testGetOrganizationsSearchBaseWithEmptyIdentifiers()
		throws Exception {

		_setUpOrganization();

		Method getOrganizationsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getOrganizationsSearchBase", String.class, String.class,
			long.class, Company.class, List.class);

		getOrganizationsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getOrganizationsSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testOrganizationName", 0, company,
				new ArrayList<>());

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

		Method getOrganizationsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getOrganizationsSearchBase", String.class, String.class,
			long.class, Company.class, List.class);

		getOrganizationsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getOrganizationsSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testOrganizationName", 0, company,
				Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetOrganizationsSearchBaseWithNullOrganization()
		throws Exception {

		Method getOrganizationsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getOrganizationsSearchBase", String.class, String.class,
			long.class, Company.class, List.class);

		getOrganizationsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getOrganizationsSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testOrganizationName", 0, company,
				new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetOrganizationsSearchBaseWithNullTypeValue()
		throws Exception {

		_setUpOrganization();

		Method getOrganizationsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getOrganizationsSearchBase", String.class, String.class,
			long.class, Company.class, List.class);

		getOrganizationsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getOrganizationsSearchBaseMethod.invoke(
				_classInstance, "Liferay", null, 0, company, new ArrayList<>());

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

		Method getRolesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getRolesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getRolesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getRolesSearchBaseMethod.invoke(
			_classInstance, "Liferay", "testRoleName", 0, company,
			new ArrayList<>());

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

		Method getRolesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getRolesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getRolesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getRolesSearchBaseMethod.invoke(
			_classInstance, "Liferay", "testRoleName", 0, company,
			Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetRolesSearchBaseWithNullRole() throws Exception {
		Method getRolesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getRolesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getRolesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getRolesSearchBaseMethod.invoke(
			_classInstance, "Liferay", "testRoleName", 0, company,
			new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetRolesSearchBaseWithNullTypeValue() throws Exception {
		_setUpRole();

		Method getRolesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getRolesSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getRolesSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getRolesSearchBaseMethod.invoke(
			_classInstance, "Liferay", null, 0, company, new ArrayList<>());

		Assert.assertTrue(searchBase.getDirectory() instanceof RolesDirectory);
		Assert.assertTrue(
			searchBase.getDirectoryBuilder() instanceof RolesBuilder);
		Assert.assertEquals("Liferay", searchBase.getTop());
		Assert.assertEquals(company.getCompanyId(), searchBase.getCompanyId());
	}

	@Test
	public void testGetSambaMachinesSearchBase() throws Exception {
		_setUpOrganization();

		Method getSambaMachinesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSambaMachinesSearchBase", String.class, Company.class,
			Organization.class, List.class);

		getSambaMachinesSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("sambaDomainName", "testDomainName"));

		SearchBase searchBase =
			(SearchBase)getSambaMachinesSearchBaseMethod.invoke(
				_classInstance, "Liferay", company, _organization, identifiers);

		Assert.assertTrue(
			searchBase.getDirectory() instanceof SambaMachineDirectory);
		Assert.assertNull(searchBase.getDirectoryBuilder());
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithInvalidIdentifier()
		throws Exception {

		_setUpOrganization();

		Method getSambaMachinesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSambaMachinesSearchBase", String.class, Company.class,
			Organization.class, List.class);

		getSambaMachinesSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("ou", "sambaDomain"));

		SearchBase searchBase =
			(SearchBase)getSambaMachinesSearchBaseMethod.invoke(
				_classInstance, "Liferay", company, _organization, identifiers);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithMultipleIdentifiers()
		throws Exception {

		_setUpOrganization();

		Method getSambaMachinesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSambaMachinesSearchBase", String.class, Company.class,
			Organization.class, List.class);

		getSambaMachinesSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("ou", "sambaDomain"));
		identifiers.add(new Identifier("cn", "test"));

		SearchBase searchBase =
			(SearchBase)getSambaMachinesSearchBaseMethod.invoke(
				_classInstance, "Liferay", company, _organization, identifiers);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithNullDirectories()
		throws Exception {

		_setUpOrganization();

		Method getSambaMachinesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSambaMachinesSearchBase", String.class, Company.class,
			Organization.class, List.class);

		getSambaMachinesSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "test"));
		identifiers.add(new Identifier("sambaDomainName", "invalidDomainName"));

		SearchBase searchBase =
			(SearchBase)getSambaMachinesSearchBaseMethod.invoke(
				_classInstance, "Liferay", company, _organization, identifiers);

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetSambaMachinesSearchBaseWithNullOrganization()
		throws Exception {

		Method getSambaMachinesSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSambaMachinesSearchBase", String.class, Company.class,
			Organization.class, List.class);

		getSambaMachinesSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		SearchBase searchBase =
			(SearchBase)getSambaMachinesSearchBaseMethod.invoke(
				_classInstance, "Liferay", company, null, identifiers);

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
		Method getSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSearchBase", String.class, long.class, LinkedHashMap.class,
			List.class, Organization.class, Company.class);

		getSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "invalidIdentifier"));
		identifiers.add(new Identifier("sambaDomainName", "testDomainName"));

		SearchBase searchBase = (SearchBase)getSearchBaseMethod.invoke(
			_classInstance, "Liferay", 0, new LinkedHashMap<String, Object>(),
			identifiers, _organization, company);

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

		Method getSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSearchBase", String.class, long.class, LinkedHashMap.class,
			List.class, Organization.class, Company.class);

		getSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		identifiers.add(new Identifier("ou", "Samba Machines"));
		identifiers.add(new Identifier("sambaDomainName", "testDomainName"));

		SearchBase searchBase = (SearchBase)getSearchBaseMethod.invoke(
			_classInstance, "Liferay", 0, new LinkedHashMap<String, Object>(),
			identifiers, _organization, company);

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

		when(
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
		Method getSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSearchBase", String.class, long.class, LinkedHashMap.class,
			List.class, Organization.class, Company.class);

		getSearchBaseMethod.setAccessible(true);

		List<Identifier> identifiers = new ArrayList<>();

		getSearchBaseMethod.invoke(
			_classInstance, "Liferay", 0, new LinkedHashMap<String, Object>(),
			identifiers, _organization, company);
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

		Method getSearchBaseMethod = _clazz.getDeclaredMethod(
			"getSearchBase", String.class, long.class, LinkedHashMap.class,
			List.class, Organization.class, Company.class);

		getSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getSearchBaseMethod.invoke(
			_classInstance, "Liferay", 0, new LinkedHashMap<String, Object>(),
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

		Method getUserGroupsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUserGroupsSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUserGroupsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getUserGroupsSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testUserGroupName", 0, company,
				new ArrayList<>());

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

		Method getUserGroupsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUserGroupsSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUserGroupsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getUserGroupsSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testUserGroupName", 0, company,
				Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetUserGroupsSearchBaseWithNullTypeValue()
		throws Exception {

		_setUpGroup();
		_setUpUserGroups();

		Method getUserGroupsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUserGroupsSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUserGroupsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getUserGroupsSearchBaseMethod.invoke(
				_classInstance, "Liferay", null, 0, company, new ArrayList<>());

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

		Method getUserGroupsSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUserGroupsSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUserGroupsSearchBaseMethod.setAccessible(true);

		SearchBase searchBase =
			(SearchBase)getUserGroupsSearchBaseMethod.invoke(
				_classInstance, "Liferay", "testUserGroupName", 0, company,
				new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithEmptyIdentifiers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		Method getUsersSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUsersSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUsersSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getUsersSearchBaseMethod.invoke(
			_classInstance, "Liferay", "testScreenName", 0, company,
			new ArrayList<>());

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetUsersSearchBaseWithIdentifiers() throws Exception {
		_setUpUsers();

		_setUpExpando();
		_setUpFastDateFormat();
		_setUpPasswordPolicy();
		setUpPortalUtil();

		Method getUsersSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUsersSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUsersSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getUsersSearchBaseMethod.invoke(
			_classInstance, "Liferay", "testScreenName", 0, company,
			Arrays.asList(new Identifier("cn", "testScreenName")));

		_assertUserSearchBase(searchBase, true);
	}

	@Test
	public void testGetUsersSearchBaseWithInvalidRdnType() throws Exception {
		_setUpGroup();

		Method getUsersSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUsersSearchBase", String.class, String.class, String.class,
			LinkedHashMap.class, long.class, Company.class);

		getUsersSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getUsersSearchBaseMethod.invoke(
			_classInstance, "o=Liferay", "ou", "testScreenName",
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

		Method getUsersSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUsersSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUsersSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getUsersSearchBaseMethod.invoke(
			_classInstance, "Liferay", null, 0, company, new ArrayList<>());

		_assertUsersSearchBase(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithNullUser() throws Exception {
		_setUpGroup();

		Method getUsersSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUsersSearchBase", String.class, String.class, long.class,
			Company.class, List.class);

		getUsersSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getUsersSearchBaseMethod.invoke(
			_classInstance, "Liferay", "testScreenName", 0, company,
			new ArrayList<>());

		Assert.assertNull(searchBase);
	}

	@Test
	public void testGetUsersSearchBaseWithNullUsers() throws Exception {
		_setUpGroup();

		Method getUsersSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUsersSearchBase", String.class, String.class, String.class,
			LinkedHashMap.class, long.class, Company.class);

		getUsersSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getUsersSearchBaseMethod.invoke(
			_classInstance, "o=Liferay", "cn", "testScreenName",
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

		Method getUsersSearchBaseMethod = _clazz.getDeclaredMethod(
			"getUsersSearchBase", String.class, String.class, String.class,
			LinkedHashMap.class, long.class, Company.class);

		getUsersSearchBaseMethod.setAccessible(true);

		SearchBase searchBase = (SearchBase)getUsersSearchBaseMethod.invoke(
			_classInstance, "Liferay", "cn", "testScreenName",
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

		Method toFilterConstraintsFromBranchNodeMethod =
			_clazz.getDeclaredMethod(
				"toFilterConstraintsFromBranchNode", BranchNode.class);

		toFilterConstraintsFromBranchNodeMethod.setAccessible(true);

		BranchNode branchNode = new AndNode();

		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		branchNode.addNode(exprNode);

		exprNode = new EqualityNode("cn", "newTestScreenName");

		branchNode.addNode(exprNode);

		List<FilterConstraint> filterConstraints =
			(List<FilterConstraint>)
				toFilterConstraintsFromBranchNodeMethod.invoke(
					_classInstance, branchNode);

		Assert.assertTrue(ListUtil.isEmpty(filterConstraints));
	}

	@Test
	public void testToFilterConstraintsFromBranchNodeWithAndNodes()
		throws Exception {

		Method toFilterConstraintsFromBranchNodeMethod =
			_clazz.getDeclaredMethod(
				"toFilterConstraintsFromBranchNode", BranchNode.class);

		toFilterConstraintsFromBranchNodeMethod.setAccessible(true);

		BranchNode branchNode = new AndNode();

		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		branchNode.addNode(exprNode);

		exprNode = new EqualityNode("ou", "test");

		branchNode.addNode(exprNode);

		List<FilterConstraint> filterConstraints =
			(List<FilterConstraint>)
				toFilterConstraintsFromBranchNodeMethod.invoke(
					_classInstance, branchNode);

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
		Method toFilterConstraintsMethod = _clazz.getDeclaredMethod(
			"toFilterConstraints", ExprNode.class);

		toFilterConstraintsMethod.setAccessible(true);

		BranchNode branchNode = new AndNode();

		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		branchNode.addNode(exprNode);

		List<FilterConstraint> filterConstraints =
			(List<FilterConstraint>)toFilterConstraintsMethod.invoke(
				_classInstance, branchNode);

		_assertFilterConstraints(filterConstraints);
	}

	@Test
	public void testToFilterConstraintsWithLeafExprNode() throws Exception {
		Method toFilterConstraintsMethod = _clazz.getDeclaredMethod(
			"toFilterConstraints", ExprNode.class);

		toFilterConstraintsMethod.setAccessible(true);

		ExprNode exprNode = new EqualityNode("cn", "testScreenName");

		List<FilterConstraint> filterConstraints =
			(List<FilterConstraint>)toFilterConstraintsMethod.invoke(
				_classInstance, exprNode);

		_assertFilterConstraints(filterConstraints);
	}

	@Test
	public void testToFilterConstraintsWithNullExprNode() throws Exception {
		Method toFilterConstraintsMethod = _clazz.getDeclaredMethod(
			"toFilterConstraints", ExprNode.class);

		toFilterConstraintsMethod.setAccessible(true);

		ExprNode exprNode = null;

		List<FilterConstraint> filterConstraints =
			(List<FilterConstraint>)toFilterConstraintsMethod.invoke(
				_classInstance, exprNode);

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

		Method toFilterConstraintsFromBranchNodeMethod =
			_clazz.getDeclaredMethod(
				"toFilterConstraintsFromBranchNode", BranchNode.class);

		toFilterConstraintsFromBranchNodeMethod.setAccessible(true);

		if (addExprNode) {
			ExprNode exprNode = new EqualityNode("cn", "testScreenName");

			branchNode.addNode(exprNode);
		}

		return (List<FilterConstraint>)
			toFilterConstraintsFromBranchNodeMethod.invoke(
				_classInstance, branchNode);
	}

	private List<FilterConstraint> _getFilterConstraintsFromLeafNode(
			LeafNode leafNode)
		throws Exception {

		Method toFilterConstraintsFromLeafNodeMethod = _clazz.getDeclaredMethod(
			"toFilterConstraintsFromLeafNode", LeafNode.class);

		toFilterConstraintsFromLeafNodeMethod.setAccessible(true);

		return (List<FilterConstraint>)
			toFilterConstraintsFromLeafNodeMethod.invoke(
				_classInstance, leafNode);
	}

	private void _setUpExpando() {
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
			_user.getExpandoBridge()
		).thenReturn(
			expandoBridge
		);
	}

	private void _setUpFastDateFormat() {
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

	private void _setUpGroup() throws Exception {
		Group group = mock(Group.class);

		when(
			groupLocalService.fetchGroup(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testGroupName"))
		).thenReturn(
			group
		);

		when(
			groupLocalService.fetchGroup(Mockito.eq(PRIMARY_KEY))
		).thenReturn(
			group
		);

		when(
			group.getName(LocaleUtil.getDefault())
		).thenReturn(
			"testGroupName"
		);
	}

	private void _setUpOrganization() throws Exception {
		_organization = mock(Organization.class);

		when(
			organizationLocalService.fetchOrganization(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testOrganizationName"))
		).thenReturn(
			_organization
		);

		when(
			_organization.getGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			_organization.getName()
		).thenReturn(
			"testOrganizationName"
		);
	}

	private void _setUpPasswordPolicy() throws Exception {
		PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);

		setUpPasswordPolicy(passwordPolicy);

		when(
			_user.getPasswordPolicy()
		).thenReturn(
			passwordPolicy
		);
	}

	private void _setUpRole() throws Exception {
		Role role = mock(Role.class);

		when(
			role.getName()
		).thenReturn(
			"testRoleName"
		);

		when(
			roleLocalService.fetchRole(
				Mockito.eq(PRIMARY_KEY), Mockito.eq("testRoleName"))
		).thenReturn(
			role
		);
	}

	private void _setUpUserGroups() throws Exception {
		UserGroup userGroup = mock(UserGroup.class);

		when(
			userGroup.getName()
		).thenReturn(
			"testUserGroupName"
		);

		when(
			userGroup.getUserGroupId()
		).thenReturn(
			PRIMARY_KEY
		);

		when(
			userGroupLocalService.fetchUserGroup(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			userGroup
		);
	}

	private void _setUpUsers() {
		_user = mock(User.class);

		when(
			_user.getScreenName()
		).thenReturn(
			"testScreenName"
		);

		when(
			userLocalService.fetchUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_user
		);

		when(
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

	private static Object _classInstance;
	private static Class<?> _clazz;
	private static Organization _organization;
	private static User _user;

}