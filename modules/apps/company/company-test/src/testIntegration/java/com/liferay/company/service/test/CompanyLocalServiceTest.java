/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.company.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.link.model.adapter.StagedAssetLink;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.expando.kernel.model.adapter.StagedExpandoColumn;
import com.liferay.expando.model.adapter.StagedExpandoTable;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyNameException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.NoSuchPasswordPolicyException;
import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.exception.RequiredCompanyException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.adapter.StagedTheme;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.SybaseDump;
import com.liferay.portal.test.rule.SybaseDumpTransactionLog;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.site.model.adapter.StagedGroup;
import com.liferay.sites.kernel.util.Sites;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Mika Koivisto
 * @author Dale Shan
 */
@DataGuard(autoDelete = false, scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
@SybaseDumpTransactionLog(dumpBefore = {SybaseDump.CLASS, SybaseDump.METHOD})
public class CompanyLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	public void resetBackgroundTaskThreadLocal() throws Exception {
		Class<?> backgroundTaskThreadLocalClass =
			BackgroundTaskThreadLocal.class;

		Field backgroundTaskIdField =
			backgroundTaskThreadLocalClass.getDeclaredField(
				"_backgroundTaskId");

		backgroundTaskIdField.setAccessible(true);

		Method setMethod = ThreadLocal.class.getDeclaredMethod(
			"set", Object.class);

		setMethod.invoke(backgroundTaskIdField.get(null), 0L);
	}

	@Before
	public void setUp() {
		_companyId = CompanyThreadLocal.getCompanyId();
	}

	@After
	public void tearDown() throws Exception {
		CompanyThreadLocal.setCompanyId(_companyId);

		resetBackgroundTaskThreadLocal();

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		deleteClassName(_layoutFriendlyURLEntryHelper.getClassName(true));
		deleteStagingClassNameEntries();
	}

	@Test
	public void testAddAndDeleteCompany() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company.getCompanyId());

		for (String webId : PortalInstances.getWebIds()) {
			Assert.assertNotEquals(company.getWebId(), webId);
		}
	}

	@Test
	public void testAddAndDeleteCompanyWithChildOrganizationSite()
		throws Exception {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		Organization companyOrganization = null;
		Group companyOrganizationGroup = null;
		Group group = null;

		try {
			long userId = _userLocalService.getGuestUserId(companyId);

			companyOrganization = _organizationLocalService.addOrganization(
				userId, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				RandomTestUtil.randomString(), true);

			companyOrganizationGroup = companyOrganization.getGroup();

			group = GroupTestUtil.addGroup(
				companyId, userId, companyOrganizationGroup.getGroupId());
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}

		Assert.assertNull(
			"The company organization should delete with the company",
			_organizationLocalService.fetchOrganization(
				companyOrganization.getOrganizationId()));
		Assert.assertNull(
			"The company organization group should delete with the company",
			_groupLocalService.fetchGroup(
				companyOrganizationGroup.getGroupId()));
		Assert.assertNull(
			"The company organization child group should delete with the " +
				"company",
			_groupLocalService.fetchGroup(group.getGroupId()));
	}

	@Test
	public void testAddAndDeleteCompanyWithCompanyGroupStaging()
		throws Exception {

		Company company = addCompany();

		Group companyGroup = null;
		Group companyStagingGroup = null;

		try {
			companyGroup = company.getGroup();

			_stagingLocalService.enableLocalStaging(
				_userLocalService.getGuestUserId(company.getCompanyId()),
				companyGroup, false, false, new ServiceContext());

			companyStagingGroup = companyGroup.getStagingGroup();
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}

		Assert.assertNull(
			_groupLocalService.fetchGroup(companyGroup.getGroupId()));
		Assert.assertNull(
			_groupLocalService.fetchGroup(companyStagingGroup.getGroupId()));
	}

	@Test
	public void testAddAndDeleteCompanyWithDLFileEntryTypes() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		DDMStructure ddmStructure = null;
		DLFileEntryType dlFileEntryType = null;

		try {
			long userId = _userLocalService.getGuestUserId(companyId);

			Group guestGroup = _groupLocalService.getGroup(
				companyId, GroupConstants.GUEST);

			ServiceContext serviceContext = getServiceContext(companyId);

			serviceContext.setScopeGroupId(guestGroup.getGroupId());
			serviceContext.setUserId(userId);

			ddmStructure = _ddmStructureLocalService.addStructure(
				userId, guestGroup.getGroupId(),
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				_portal.getClassNameId(DLFileEntryMetadata.class),
				StringPool.BLANK,
				HashMapBuilder.put(
					LocaleUtil.getDefault(),
					DLFileEntryMetadata.class.getSimpleName()
				).build(),
				new HashMap<>(), StringPool.BLANK,
				StorageType.DEFAULT.toString(), serviceContext);

			dlFileEntryType = _dlFileEntryTypeLocalService.addFileEntryType(
				userId, guestGroup.getGroupId(), ddmStructure.getStructureId(),
				CompanyLocalServiceTest.class.getSimpleName(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(),
					CompanyLocalServiceTest.class.getSimpleName()
				).build(),
				new HashMap<>(),
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
				serviceContext);

			serviceContext.setAttribute(
				"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

			_dlAppLocalService.addFileEntry(
				null, userId, guestGroup.getGroupId(), 0, "test.xml",
				"text/xml", "test.xml", "", "", "", "test".getBytes(), null,
				null, serviceContext);
		}
		finally {
			_companyLocalService.deleteCompany(companyId);
		}

		Assert.assertNull(
			_ddmStructureLocalService.fetchStructure(
				ddmStructure.getStructureId()));
		Assert.assertNull(
			_dlFileEntryTypeLocalService.fetchDLFileEntryType(
				dlFileEntryType.getFileEntryTypeId()));
	}

	@Test
	public void testAddAndDeleteCompanyWithLayoutSetPrototype()
		throws Throwable {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		LayoutSetPrototype layoutSetPrototype = null;

		try {
			long userId = _userLocalService.getGuestUserId(companyId);

			Group group = GroupTestUtil.addGroup(
				companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID);

			layoutSetPrototype = addLayoutSetPrototype(
				companyId, userId, RandomTestUtil.randomString());

			long layoutSetPrototypeId =
				layoutSetPrototype.getLayoutSetPrototypeId();

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_sites.updateLayoutSetPrototypesLinks(
						group, layoutSetPrototypeId, 0, true, false);

					return null;
				});
		}
		finally {
			_companyLocalService.deleteCompany(companyId);
		}

		Assert.assertNull(
			_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
				layoutSetPrototype.getLayoutSetPrototypeId()));
	}

	@Test
	public void testAddAndDeleteCompanyWithLayoutSetPrototypeLinkedUserGroup()
		throws Throwable {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		long layoutSetPrototypeId = 0;
		long userGroupId = 0;

		try {
			long userId = _userLocalService.getGuestUserId(companyId);

			Group group = GroupTestUtil.addGroup(
				companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID);

			UserGroup userGroup = UserGroupTestUtil.addUserGroup(
				group.getGroupId());

			LayoutSetPrototype layoutSetPrototype = addLayoutSetPrototype(
				companyId, userId, RandomTestUtil.randomString());

			layoutSetPrototypeId = layoutSetPrototype.getLayoutSetPrototypeId();

			userGroupId = userGroup.getUserGroupId();

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_sites.updateLayoutSetPrototypesLinks(
						userGroup.getGroup(),
						layoutSetPrototype.getLayoutSetPrototypeId(), 0, true,
						false);

					return null;
				});
		}
		finally {
			_companyLocalService.deleteCompany(companyId);
		}

		Assert.assertNull(
			_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
				layoutSetPrototypeId));
		Assert.assertNull(_userGroupLocalService.fetchUserGroup(userGroupId));
	}

	@Test
	public void testAddAndDeleteCompanyWithParentGroup() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		Group group = null;
		Group parentGroup = null;

		try {
			long userId = _userLocalService.getGuestUserId(companyId);

			parentGroup = GroupTestUtil.addGroup(
				companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID);

			group = GroupTestUtil.addGroup(
				companyId, userId, parentGroup.getGroupId());
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}

		Assert.assertNull(
			_groupLocalService.fetchGroup(parentGroup.getGroupId()));
		Assert.assertNull(_groupLocalService.fetchGroup(group.getGroupId()));
	}

	@Test
	public void testAddAndDeleteCompanyWithStagedOrganizationSite()
		throws Exception {

		Company company = addCompany();

		Organization companyOrganization = null;
		Group companyOrganizationGroup = null;

		try {
			User companyAdminUser = UserTestUtil.addCompanyAdminUser(company);

			companyOrganization = _organizationLocalService.addOrganization(
				companyAdminUser.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				RandomTestUtil.randomString(), true);

			companyOrganizationGroup = companyOrganization.getGroup();

			GroupTestUtil.enableLocalStaging(
				companyOrganizationGroup, companyAdminUser.getUserId());
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}

		Assert.assertNull(
			_organizationLocalService.fetchOrganization(
				companyOrganization.getOrganizationId()));
		Assert.assertNull(
			_groupLocalService.fetchGroup(
				companyOrganizationGroup.getGroupId()));
	}

	@Test
	public void testAddAndDeleteCompanyWithUserGroup() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		User user = null;
		UserGroup userGroup = null;

		try {
			long userId = _userLocalService.getGuestUserId(companyId);

			Group group = GroupTestUtil.addGroup(
				companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID);

			userGroup = UserGroupTestUtil.addUserGroup(group.getGroupId());

			user = addUser(
				companyId, userId, group.getGroupId(),
				getServiceContext(companyId));

			_userGroupLocalService.addUserUserGroup(
				user.getUserId(), userGroup);
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}

		Assert.assertNull(
			_userGroupLocalService.fetchUserGroup(userGroup.getUserGroupId()));
		Assert.assertNull(_userLocalService.fetchUser(user.getUserId()));
	}

	@Test
	public void testAddAndDeleteCompanyWithUserGroupAndUserGroupRole()
		throws Exception {

		Company company = addCompany();

		Group group = null;
		Role role = null;
		User user = null;
		UserGroup userGroup = null;

		try {
			long userId = _userLocalService.getGuestUserId(
				company.getCompanyId());

			group = GroupTestUtil.addGroup(
				company.getCompanyId(), userId,
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			userGroup = UserGroupTestUtil.addUserGroup(group.getGroupId());

			user = addUser(
				company.getCompanyId(), userId, group.getGroupId(),
				getServiceContext(company.getCompanyId()));

			_userGroupLocalService.addUserUserGroup(
				user.getUserId(), userGroup);

			role = _roleLocalService.addRole(
				userId, Group.class.getName(), group.getClassPK(),
				StringUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), StringUtil.randomString()),
				Collections.emptyMap(), RoleConstants.TYPE_SITE,
				StringPool.BLANK, getServiceContext(company.getCompanyId()));

			_userGroupRoleLocalService.addUserGroupRole(
				user.getUserId(), group.getGroupId(), role.getRoleId());
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}

		Assert.assertNull(_roleLocalService.fetchRole(role.getRoleId()));
		Assert.assertNull(_userLocalService.fetchUser(user.getUserId()));
		Assert.assertNull(
			_userGroupLocalService.fetchUserGroup(userGroup.getUserGroupId()));
		Assert.assertNull(
			_userGroupRoleLocalService.fetchUserGroupRole(
				user.getUserId(), group.getGroupId(), role.getRoleId()));
	}

	@Test(expected = NoSuchPasswordPolicyException.class)
	public void testDeleteCompanyDeletesDefaultPasswordPolicy()
		throws Exception {

		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		_passwordPolicyLocalService.getDefaultPasswordPolicy(
			company.getCompanyId());
	}

	@Test
	public void testDeleteCompanyDeletesGroups() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		Assert.assertEquals(
			0,
			_groupLocalService.getGroupsCount(
				company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
				true));
		Assert.assertEquals(
			0,
			_groupLocalService.getGroupsCount(
				company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
				false));
	}

	@Test
	public void testDeleteCompanyDeletesLayoutPrototypes() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		Assert.assertEquals(
			0,
			_layoutPrototypeLocalService.searchCount(
				company.getCompanyId(), true));
		Assert.assertEquals(
			0,
			_layoutPrototypeLocalService.searchCount(
				company.getCompanyId(), false));
	}

	@Test
	public void testDeleteCompanyDeletesLayoutSetPrototypes() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		List<LayoutSetPrototype> layoutSetPrototypes =
			_layoutSetPrototypeLocalService.getLayoutSetPrototypes(
				company.getCompanyId());

		Assert.assertEquals(
			layoutSetPrototypes.toString(), 0, layoutSetPrototypes.size());
	}

	@Test(expected = NoSuchPasswordPolicyException.class)
	public void testDeleteCompanyDeletesNondefaultPasswordPolicies()
		throws Throwable {

		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_passwordPolicyLocalService.getPasswordPolicy(
					company.getCompanyId(), false);

				return null;
			});
	}

	@Test
	public void testDeleteCompanyDeletesOrganizations() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		Assert.assertEquals(
			0,
			_organizationLocalService.getOrganizationsCount(
				company.getCompanyId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID));
	}

	@Test
	public void testDeleteCompanyDeletesPortalInstance() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		_companyLocalService.forEachCompanyId(
			companyId -> Assert.assertNotEquals(
				"Company instance was not deleted", company.getCompanyId(),
				(long)companyId));
	}

	@Test
	public void testDeleteCompanyDeletesPortalPreferences() throws Throwable {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				Assert.assertNull(
					_portalPreferencesLocalService.fetchPortalPreferences(
						company.getCompanyId(),
						PortletKeys.PREFS_OWNER_TYPE_COMPANY));

				return null;
			});
	}

	@Test
	public void testDeleteCompanyDeletesPortlets() throws Throwable {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				Assert.assertEquals(
					0,
					_portletLocalService.getPortletsCount(
						company.getCompanyId()));

				return null;
			});
	}

	@Test
	public void testDeleteCompanyDeletesRoles() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		List<Role> roles = _roleLocalService.getRoles(company.getCompanyId());

		Assert.assertEquals(roles.toString(), 0, roles.size());
	}

	@Test
	public void testDeleteCompanyDeletesUserGroupRoleBeforeRole()
		throws Exception {

		List<String> list = _registerModelListeners();

		Company company = addCompany();

		try {
			long userId = _userLocalService.getGuestUserId(
				company.getCompanyId());

			Group group = GroupTestUtil.addGroup(
				company.getCompanyId(), userId,
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			UserGroup userGroup = UserGroupTestUtil.addUserGroup(
				group.getGroupId());

			User user = addUser(
				company.getCompanyId(), userId, group.getGroupId(),
				getServiceContext(company.getCompanyId()));

			_userGroupLocalService.addUserUserGroup(
				user.getUserId(), userGroup);

			Role role = _roleLocalService.addRole(
				userId, Group.class.getName(), group.getClassPK(),
				StringUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), StringUtil.randomString()),
				Collections.emptyMap(), RoleConstants.TYPE_SITE,
				StringPool.BLANK, getServiceContext(company.getCompanyId()));

			_userGroupRoleLocalService.addUserGroupRole(
				user.getUserId(), group.getGroupId(), role.getRoleId());
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}

		Assert.assertEquals(UserGroupRole.class.getName(), list.get(0));
		Assert.assertEquals(Role.class.getName(), list.get(1));
	}

	@Test
	public void testDeleteCompanyDeletesUsers() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		List<User> users = _userLocalService.getCompanyUsers(
			company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(users.toString(), 0, users.size());
	}

	@Test(expected = NoSuchVirtualHostException.class)
	public void testDeleteCompanyDeletesVirtualHost() throws Exception {
		Company company = addCompany();

		_companyLocalService.deleteCompany(company);

		_virtualHostLocalService.getVirtualHost(company.getWebId());
	}

	@Test(expected = RequiredCompanyException.class)
	public void testDeleteDefaultCompany() throws Exception {
		long companyId = PortalInstances.getDefaultCompanyId();

		_companyLocalService.deleteCompany(companyId);
	}

	@Test
	public void testGetCompanyByVirtualHost() throws Exception {
		String virtualHostName = "::1";

		Company company = addCompany(virtualHostName);

		try {
			Assert.assertEquals(
				company,
				_companyLocalService.getCompanyByVirtualHost(virtualHostName));
			Assert.assertEquals(
				company,
				_companyLocalService.getCompanyByVirtualHost(
					"0:0:0:0:0:0:0:1"));
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Test
	public void testUpdateCompanyLocales() throws Exception {
		Company company = addCompany();

		String languageId = "ca_ES";

		try {
			TimeZone timeZone = company.getTimeZone();

			_companyLocalService.updateDisplay(
				company.getCompanyId(), languageId, timeZone.getID());

			_companyLocalService.updatePreferences(
				company.getCompanyId(),
				UnicodePropertiesBuilder.put(
					PropsKeys.LOCALES, languageId
				).build());

			Assert.assertEquals(
				Collections.singleton(LocaleUtil.fromLanguageId(languageId)),
				_language.getAvailableLocales());
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Test
	public void testUpdateCompanyLocalesUpdateGroupLocales() throws Exception {
		Company company = addCompany();

		try {
			String[] companyLanguageIds = _prefsProps.getStringArray(
				company.getCompanyId(), PropsKeys.LOCALES, StringPool.COMMA,
				PropsValues.LOCALES_ENABLED);

			User user = UserTestUtil.getAdminUser(company.getCompanyId());

			Group group = GroupTestUtil.addGroup(
				company.getCompanyId(), user.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			group = GroupTestUtil.updateDisplaySettings(
				group.getGroupId(),
				ListUtil.fromArray(
					LocaleUtil.fromLanguageIds(companyLanguageIds)),
				LocaleUtil.getDefault());

			UnicodeProperties groupTypeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			Assert.assertEquals(
				StringUtil.merge(companyLanguageIds),
				groupTypeSettingsUnicodeProperties.getProperty(
					PropsKeys.LOCALES));

			String languageIds = "ca_ES,en_US";

			_companyLocalService.updatePreferences(
				company.getCompanyId(),
				UnicodePropertiesBuilder.put(
					PropsKeys.LOCALES, languageIds
				).build());

			Assert.assertEquals(
				languageIds,
				_prefsProps.getString(
					company.getCompanyId(), PropsKeys.LOCALES));

			group = _groupLocalService.getGroup(group.getGroupId());

			groupTypeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			Assert.assertEquals(
				languageIds,
				groupTypeSettingsUnicodeProperties.getProperty(
					PropsKeys.LOCALES));
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Test
	public void testUpdateDisplay() throws Exception {
		Company company = addCompany();

		try {
			User user = _userLocalService.getGuestUser(company.getCompanyId());

			_userLocalService.updateUser(user);

			String languageId = LocaleUtil.toLanguageId(LocaleUtil.HUNGARY);

			_companyLocalService.updateDisplay(
				company.getCompanyId(), languageId, "CET");

			user = _userLocalService.getGuestUser(company.getCompanyId());

			Assert.assertEquals(languageId, user.getLanguageId());
			Assert.assertEquals("CET", user.getTimeZoneId());
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}
	}

	@Test
	public void testUpdateInvalidCompanyNames() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		try {
			Group group = GroupTestUtil.addGroup(
				companyId, _userLocalService.getGuestUserId(companyId),
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			testUpdateCompanyNames(
				company,
				new String[] {StringPool.BLANK, group.getDescriptiveName()},
				true);
		}
		finally {
			_companyLocalService.deleteCompany(companyId);
		}
	}

	@Test
	public void testUpdateInvalidMx() throws Exception {
		testUpdateMx("abc", false, true);
		testUpdateMx(StringPool.BLANK, false, true);
	}

	@Test
	public void testUpdateInvalidVirtualHostnames() throws Exception {
		testUpdateVirtualHostnames(
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
	public void testUpdateValidCompanyNames() throws Exception {
		Company company = addCompany();

		try {
			testUpdateCompanyNames(
				company, new String[] {RandomTestUtil.randomString()}, false);
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}
	}

	@Test
	public void testUpdateValidVirtualHostnames() throws Exception {
		testUpdateVirtualHostnames(
			new String[] {
				"abc.com", "255.0.0.0", "0:0:0:0:0:0:0:1", "::1",
				"0000:0000:0000:0000:0000:0000:0000:0001"
			},
			false);
	}

	protected Company addCompany() throws Exception {
		return addCompany(RandomTestUtil.randomString() + "test.com");
	}

	protected Company addCompany(String webId) throws Exception {
		Company company = _companyLocalService.addCompany(
			null, webId, webId, "test.com", 0, true, null, null, null, null,
			null, null);

		PortalInstances.initCompany(company);

		CompanyThreadLocal.setCompanyId(company.getCompanyId());

		return company;
	}

	protected LayoutSetPrototype addLayoutSetPrototype(
			long companyId, long userId, String name)
		throws Exception {

		return _layoutSetPrototypeLocalService.addLayoutSetPrototype(
			userId, companyId,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), name
			).build(),
			new HashMap<Locale, String>(), true, true,
			getServiceContext(companyId));
	}

	protected User addUser(
			long companyId, long userId, long groupId,
			ServiceContext serviceContext)
		throws Exception {

		return UserTestUtil.addUser(
			companyId, userId,
			RandomTestUtil.randomString(NumericStringRandomizerBumper.INSTANCE),
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {groupId},
			serviceContext);
	}

	protected void deleteClassName(String value) {
		ClassName className = _classNameLocalService.fetchClassName(value);

		if (className == null) {
			return;
		}

		_classNameLocalService.deleteClassName(className);
	}

	protected void deleteStagingClassNameEntries() {
		deleteClassName(Folder.class.getName());
		deleteClassName(StagedAssetLink.class.getName());
		deleteClassName(StagedExpandoColumn.class.getName());
		deleteClassName(StagedExpandoTable.class.getName());
		deleteClassName(StagedLayoutSet.class.getName());
		deleteClassName(StagedGroup.class.getName());
		deleteClassName(StagedTheme.class.getName());
	}

	protected ServiceContext getServiceContext(long companyId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);

		return serviceContext;
	}

	protected void testUpdateCompanyNames(
			Company company, String[] companyNames, boolean expectFailure)
		throws Exception {

		for (String companyName : companyNames) {
			try {
				company = _companyLocalService.updateCompany(
					company.getCompanyId(), company.getVirtualHostname(),
					company.getMx(), company.getHomeURL(), true, null,
					companyName, company.getLegalName(), company.getLegalId(),
					company.getLegalType(), company.getSicCode(),
					company.getTickerSymbol(), company.getIndustry(),
					company.getType(), company.getSize());

				Assert.assertFalse(expectFailure);
			}
			catch (CompanyNameException companyNameException) {
				if (_log.isDebugEnabled()) {
					_log.debug(companyNameException);
				}

				Assert.assertTrue(expectFailure);
			}
		}
	}

	protected void testUpdateMx(String mx, boolean valid, boolean mailMxUpdate)
		throws Exception {

		Company company = addCompany();

		String originalMx = company.getMx();

		Field field = null;

		Object value = null;

		try {
			field = ReflectionUtil.getDeclaredField(
				PropsValues.class, "MAIL_MX_UPDATE");

			value = field.get(null);

			if (mailMxUpdate) {
				field.set(null, Boolean.TRUE);
			}
			else {
				field.set(null, Boolean.FALSE);
			}

			_companyLocalService.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), mx,
				company.getMaxUsers(), company.isActive());

			company = _companyLocalService.getCompany(company.getCompanyId());

			String updatedMx = company.getMx();

			if (valid && mailMxUpdate) {
				Assert.assertNotEquals(originalMx, updatedMx);
			}
			else {
				Assert.assertEquals(originalMx, updatedMx);
			}
		}
		catch (CompanyMxException companyMxException) {
			if (_log.isDebugEnabled()) {
				_log.debug(companyMxException);
			}

			Assert.assertFalse(valid);
			Assert.assertTrue(mailMxUpdate);
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());

			if (field != null) {
				field.set(null, value);
			}
		}
	}

	protected void testUpdateVirtualHostnames(
			String[] virtualHostnames, boolean expectFailure)
		throws Exception {

		Company company = addCompany();

		try {
			for (String virtualHostname : virtualHostnames) {
				try {
					_companyLocalService.updateCompany(
						company.getCompanyId(), virtualHostname,
						company.getMx(), company.getMaxUsers(),
						company.isActive());

					Assert.assertFalse(expectFailure);
				}
				catch (CompanyVirtualHostException
							companyVirtualHostException) {

					if (_log.isDebugEnabled()) {
						_log.debug(companyVirtualHostException);
					}

					Assert.assertTrue(expectFailure);
				}
			}
		}
		finally {
			_companyLocalService.deleteCompany(company.getCompanyId());
		}
	}

	private List<String> _registerModelListeners() {
		List<String> list = new CopyOnWriteArrayList<>();

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistrations.add(
			bundleContext.registerService(
				ModelListener.class,
				new BaseModelListener<Role>() {

					@Override
					public void onBeforeRemove(Role role)
						throws ModelListenerException {

						list.add(Role.class.getName());
					}

				},
				new HashMapDictionary<>()));
		_serviceRegistrations.add(
			bundleContext.registerService(
				ModelListener.class,
				new BaseModelListener<UserGroupRole>() {

					@Override
					public void onBeforeRemove(UserGroupRole userGroupRole)
						throws ModelListenerException {

						list.add(UserGroupRole.class.getName());
					}

				},
				new HashMapDictionary<>()));

		return list;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyLocalServiceTest.class);

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.SUPPORTS);
		builder.setReadOnly(true);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private long _companyId;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Language _language;

	@Inject
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Inject
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Inject
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private PrefsProps _prefsProps;

	@Inject
	private RoleLocalService _roleLocalService;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new CopyOnWriteArrayList<>();

	@Inject
	private Sites _sites;

	@Inject
	private StagingLocalService _stagingLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}