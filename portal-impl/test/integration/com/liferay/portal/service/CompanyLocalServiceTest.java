/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service;

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.NoSuchAccountException;
import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.NoSuchPreferencesException;
import com.liferay.portal.NoSuchShardException;
import com.liferay.portal.NoSuchVirtualHostException;
import com.liferay.portal.RequiredCompanyException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.persistence.PasswordPolicyUtil;
import com.liferay.portal.service.persistence.PortalPreferencesUtil;
import com.liferay.portal.service.persistence.PortletUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.File;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Mika Koivisto
 * @author Dale Shan
 */
@Sync
public class CompanyLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() {
		_companyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(PortalInstances.getDefaultCompanyId());

		File file = new File("portal-web/docroot");

		_mockServletContext = new MockServletContext(
			"file:" + file.getAbsolutePath(), new FileSystemResourceLoader());
	}

	@After
	public void tearDown() {
		CompanyThreadLocal.setCompanyId(_companyId);
	}

	@Test
	public void testAddAndDeleteCompany() throws Exception {
		Company company = addCompany();

		String companyWebId = company.getWebId();

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

		for (String webId : PortalInstances.getWebIds()) {
			Assert.assertNotEquals(companyWebId, webId);
		}
	}

	@Test
	public void testAddAndDeleteCompanyWithCompanyGroupStaging()
		throws Exception {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group companyGroup = company.getGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("staging", Boolean.TRUE);

		Group companyStagingGroup = GroupLocalServiceUtil.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			companyGroup.getClassName(), companyGroup.getClassPK(),
			companyGroup.getGroupId(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), companyGroup.getType(),
			companyGroup.isManualMembership(),
			companyGroup.getMembershipRestriction(),
			companyGroup.getFriendlyURL(), false, companyGroup.isActive(),
			serviceContext);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

		companyGroup = GroupLocalServiceUtil.fetchGroup(
			companyGroup.getGroupId());

		Assert.assertNull(companyGroup);

		companyStagingGroup = GroupLocalServiceUtil.fetchGroup(
			companyStagingGroup.getGroupId());

		Assert.assertNull(companyStagingGroup);
	}

	@Test
	public void testAddAndDeleteCompanyWithDLFileEntryTypes() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		Group companyGroup = company.getGroup();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				companyGroup.getGroupId(),
				DLFileEntryTypeConstants.NAME_CONTRACT);

		ServiceContext serviceContext = getServiceContext(companyId);

		serviceContext.setAttribute(
			"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());
		serviceContext.setScopeGroupId(guestGroup.getGroupId());
		serviceContext.setUserId(userId);

		DLAppLocalServiceUtil.addFileEntry(
			userId, guestGroup.getGroupId(), 0, "test.xml", "text/xml",
			"test.xml", "", "", "test".getBytes(), serviceContext);

		CompanyLocalServiceUtil.deleteCompany(companyId);
	}

	@Test
	public void testAddAndDeleteCompanyWithLayoutSetPrototype()
		throws Exception {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group group = GroupTestUtil.addGroup(
			companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID);

		LayoutSetPrototype layoutSetPrototype = addLayoutSetPrototype(
			companyId, userId, RandomTestUtil.randomString());

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, layoutSetPrototype.getLayoutSetPrototypeId(), true);

		addUser(
			companyId, userId, group.getGroupId(),
			getServiceContext(companyId));

		CompanyLocalServiceUtil.deleteCompany(companyId);

		layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
				layoutSetPrototype.getLayoutSetPrototypeId());

		Assert.assertNull(layoutSetPrototype);
	}

	@Test
	public void testAddAndDeleteCompanyWithParentGroup() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group parentGroup = GroupTestUtil.addGroup(
			companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID);

		Group group = GroupTestUtil.addGroup(
			companyId, userId, parentGroup.getGroupId());

		addUser(
			companyId, userId, group.getGroupId(),
			getServiceContext(companyId));

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

		parentGroup = GroupLocalServiceUtil.fetchGroup(
			parentGroup.getGroupId());

		Assert.assertNull(parentGroup);

		group = GroupLocalServiceUtil.fetchGroup(group.getGroupId());

		Assert.assertNull(group);
	}

	@Test(expected = NoSuchAccountException.class)
	public void testDeleteCompanyDeletesAccount() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		AccountLocalServiceUtil.getAccount(company.getAccountId());
	}

	@Test(expected = NoSuchPasswordPolicyException.class)
	public void testDeleteCompanyDeletesDefaultPasswordPolicy()
		throws Exception {

		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		PasswordPolicyLocalServiceUtil.getDefaultPasswordPolicy(
			company.getCompanyId());
	}

	@Test
	public void testDeleteCompanyDeletesGroups() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		int count = GroupLocalServiceUtil.getGroupsCount(
			company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID, true);

		Assert.assertEquals(0, count);

		count = GroupLocalServiceUtil.getGroupsCount(
			company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID, false);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testDeleteCompanyDeletesLayoutPrototypes() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		int count = LayoutPrototypeLocalServiceUtil.searchCount(
			company.getCompanyId(), true);

		Assert.assertEquals(0, count);

		count = LayoutPrototypeLocalServiceUtil.searchCount(
			company.getCompanyId(), false);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testDeleteCompanyDeletesLayoutSetPrototypes() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		List<LayoutSetPrototype> layoutSetPrototypes =
			LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(
				company.getCompanyId());

		Assert.assertEquals(0, layoutSetPrototypes.size());
	}

	@Test
	public void testDeleteCompanyDeletesNonDefaultPasswordPolicies()
		throws Throwable {

		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		int count = PasswordPolicyUtil.countByC_DP(
			company.getCompanyId(), false);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testDeleteCompanyDeletesOrganizations() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		int count = OrganizationLocalServiceUtil.getOrganizationsCount(
			company.getCompanyId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

		Assert.assertEquals(0, count);
	}

	@Test
	public void testDeleteCompanyDeletesPortalInstance() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		for (long companyId : PortalInstances.getCompanyIds()) {
			if (companyId == company.getCompanyId()) {
				Assert.fail("Company instance was not deleted");
			}
		}
	}

	@Test(expected = NoSuchPreferencesException.class)
	public void testDeleteCompanyDeletesPortalPreferences() throws Throwable {
		final Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		PortalPreferencesUtil.findByO_O(
			company.getCompanyId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	@Test
	public void testDeleteCompanyDeletesPortlets() throws Throwable {
		final Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		int count = PortletUtil.countByCompanyId(company.getCompanyId());

		Assert.assertEquals(0, count);
	}

	@Test
	public void testDeleteCompanyDeletesRoles() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		List<Role> roles = RoleLocalServiceUtil.getRoles(
			company.getCompanyId());

		Assert.assertEquals(0, roles.size());
	}

	@Test(expected = NoSuchShardException.class)
	public void testDeleteCompanyDeletesShard() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		ShardLocalServiceUtil.getShard(
			Company.class.getName(), company.getCompanyId());
	}

	@Test
	public void testDeleteCompanyDeletesUsers() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		List<User> users = UserLocalServiceUtil.getCompanyUsers(
			company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(0, users.size());
	}

	@Test(expected = NoSuchVirtualHostException.class)
	public void testDeleteCompanyDeletesVirtualHost() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company);

		VirtualHostLocalServiceUtil.getVirtualHost(company.getWebId());
	}

	@Test
	public void testDeleteCompanyWithLDAPPasswordPolicyEnabled()
		throws Exception {

		Company company = addCompany();

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			company.getCompanyId(), true);

		portletPreferences.setValue(
			PropsKeys.LDAP_PASSWORD_POLICY_ENABLED, "true");

		portletPreferences.store();

		CompanyLocalServiceUtil.deleteCompany(company);
	}

	@Test(expected = RequiredCompanyException.class)
	public void testDeleteDefaultCompany() throws Exception {
		long companyId = PortalInstances.getDefaultCompanyId();

		CompanyLocalServiceUtil.deleteCompany(companyId);
	}

	@Test
	public void testUpdateDisplay() throws Exception {
		Company company = addCompany();

		User user = UserLocalServiceUtil.getDefaultUser(company.getCompanyId());

		UserLocalServiceUtil.updateUser(user);

		CompanyLocalServiceUtil.updateDisplay(
			company.getCompanyId(), "hu", "CET");

		user = UserLocalServiceUtil.getDefaultUser(company.getCompanyId());

		Assert.assertEquals("hu", user.getLanguageId());
		Assert.assertEquals("CET", user.getTimeZoneId());
	}

	@Test
	public void testUpdateInvalidAccountNames() throws Exception {
		Company company = addCompany();

		Group group = GroupTestUtil.addGroup();

		group.setCompanyId(company.getCompanyId());

		GroupLocalServiceUtil.updateGroup(group);

		testUpdateAccountNames(
			company,
			new String[] {StringPool.BLANK, group.getDescriptiveName()}, true);

		GroupLocalServiceUtil.deleteGroup(group);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	@Test
	public void testUpdateInvalidVirtualHostNames() throws Exception {
		testUpdateVirtualHostNames(
			new String[] {StringPool.BLANK, "localhost", ".abc"}, true);
	}

	@Test
	public void testUpdateMx() throws Exception {
		testUpdateMx("abc.com", true, true);
		testUpdateMx("abc.com", true, false);
		testUpdateMx(StringPool.BLANK, false, true);
		testUpdateMx(StringPool.BLANK, false, false);
	}

	@Test
	public void testUpdateValidAccountNames() throws Exception {
		Company company = addCompany();

		testUpdateAccountNames(
			company, new String[] {RandomTestUtil.randomString()}, false);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	@Test
	public void testUpdateValidVirtualHostNames() throws Exception {
		testUpdateVirtualHostNames(new String[] {"abc.com"}, false);
	}

	protected Company addCompany() throws Exception {
		String webId = RandomTestUtil.randomString() + "test.com";

		Company company = CompanyLocalServiceUtil.addCompany(
			webId, webId, "test.com", PropsValues.SHARD_DEFAULT_NAME, false, 0,
			true);

		PortalInstances.initCompany(_mockServletContext, webId);

		return company;
	}

	protected LayoutSetPrototype addLayoutSetPrototype(
			long companyId, long userId, String name)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				userId, companyId, nameMap, new HashMap<Locale, String>(), true,
				true, getServiceContext(companyId));

		return layoutSetPrototype;
	}

	protected User addUser(
			long companyId, long userId, long groupId,
			ServiceContext serviceContext)
		throws Exception {

		return UserTestUtil.addUser(
			companyId, userId, RandomTestUtil.randomString(), false,
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {groupId},
			serviceContext);
	}

	protected ServiceContext getServiceContext(long companyId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);

		return serviceContext;
	}

	protected void testUpdateAccountNames(
			Company company, String[] accountNames, boolean expectFailure)
		throws Exception {

		Account account = AccountLocalServiceUtil.getAccount(
			company.getAccountId());

		for (String accountName : accountNames) {
			try {
				company = CompanyLocalServiceUtil.updateCompany(
					company.getCompanyId(), company.getVirtualHostname(),
					company.getMx(), company.getHomeURL(), true, null,
					accountName, account.getLegalName(), account.getLegalId(),
					account.getLegalType(), account.getSicCode(),
					account.getTickerSymbol(), account.getIndustry(),
					account.getType(), account.getSize());

				if (expectFailure) {
					Assert.fail();
				}
			}
			catch (AccountNameException ane) {
				if (!expectFailure) {
					Assert.fail();
				}
			}
		}
	}

	protected void testUpdateMx(String mx, boolean valid, boolean mailMxUpdate)
		throws Exception {

		Company company = addCompany();

		String originalMx = company.getMx();

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			if (mailMxUpdate) {
				field.set(null, Boolean.TRUE);
			}
			else {
				field.set(null, Boolean.FALSE);
			}

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), mx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			if (valid && mailMxUpdate) {
				Assert.assertNotEquals(originalMx, updatedMx);
			}
			else {
				Assert.assertEquals(originalMx, updatedMx);
			}
		}
		catch (CompanyMxException cme) {
			if (valid || !mailMxUpdate) {
				Assert.fail();
			}
		}
		finally {
			CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

			field.set(null, value);
		}
	}

	protected void testUpdateVirtualHostNames(
			String[] virtualHostNames, boolean expectFailure)
		throws Exception {

		Company company = addCompany();

		for (String virtualHostName : virtualHostNames) {
			try {
				CompanyLocalServiceUtil.updateCompany(
					company.getCompanyId(), virtualHostName, company.getMx(),
					company.getMaxUsers(), company.getActive());

				if (expectFailure) {
					Assert.fail();
				}
			}
			catch (CompanyVirtualHostException cvhe) {
				if (!expectFailure) {
					Assert.fail();
				}
			}
		}

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	private long _companyId;
	private MockServletContext _mockServletContext;

}