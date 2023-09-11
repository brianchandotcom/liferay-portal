/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.service.test;

import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountEntryDomainsException;
import com.liferay.account.exception.AccountEntryNameException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.service.test.util.AccountGroupTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAccountEntryAssetTags() throws Exception {
		String[] assetTagNames = {"tag1", "tag2"};

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			accountEntryArgs -> accountEntryArgs.assetTagNames = assetTagNames);

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			AccountEntry.class.getName(), accountEntry.getAccountEntryId());

		Assert.assertArrayEquals(
			assetTagNames, ListUtil.toArray(assetTags, AssetTag.NAME_ACCESSOR));
	}

	@Test
	public void testAccountEntryGroup() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		Group group = accountEntry.getAccountEntryGroup();

		Assert.assertNotNull(group);
		Assert.assertEquals(
			_classNameLocalService.getClassNameId(AccountEntry.class),
			group.getClassNameId());
		Assert.assertEquals(
			accountEntry.getAccountEntryId(), group.getClassPK());

		long accountEntryGroupId = accountEntry.getAccountEntryGroupId();

		Assert.assertNotEquals(
			GroupConstants.DEFAULT_LIVE_GROUP_ID, accountEntryGroupId);

		Assert.assertEquals(group.getGroupId(), accountEntryGroupId);

		_accountEntryLocalService.deleteAccountEntry(accountEntry);

		Assert.assertNull(accountEntry.getAccountEntryGroup());
	}

	@Test
	public void testAccountEntryName() throws Exception {
		try {
			AccountEntryTestUtil.addAccountEntry(AccountEntryArgs.withName(""));

			Assert.fail();
		}
		catch (AccountEntryNameException accountEntryNameException) {
			String message = accountEntryNameException.getMessage();

			Assert.assertTrue(message.contains("Name is null"));
		}

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		try {
			_accountEntryLocalService.updateAccountEntry(
				accountEntry.getAccountEntryId(),
				accountEntry.getParentAccountEntryId(), "", null, false, null,
				null, null, null, accountEntry.getStatus(),
				ServiceContextTestUtil.getServiceContext());

			Assert.fail();
		}
		catch (AccountEntryNameException accountEntryNameException) {
			String message = accountEntryNameException.getMessage();

			Assert.assertTrue(message.contains("Name is null"));
		}
	}

	@Test
	public void testAccountEntryObjectValidationRule() throws Exception {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName());

		Class<?> clazz = getClass();

		_objectValidationRuleLocalService.addObjectValidationRule(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), true,
			ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY,
			LocalizedMapUtil.getLocalizedMap("This name is invalid."),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION,
			StringUtil.read(
				clazz,
				StringBundler.concat(
					"dependencies/", clazz.getSimpleName(), StringPool.PERIOD,
					testName.getMethodName(), ".groovy")),
			false, Collections.emptyList());

		try {
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withName("Invalid Name"));

			Assert.fail();
		}
		catch (ModelListenerException modelListenerException) {
			String message = modelListenerException.getMessage();

			Assert.assertTrue(message.contains("This name is invalid."));

			Assert.assertTrue(
				modelListenerException.getCause() instanceof
					ObjectValidationRuleEngineException.InvalidFields);
		}

		try {
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withName("Invalid Name"));

			Assert.fail();
		}
		catch (ModelListenerException modelListenerException) {
			String message = modelListenerException.getMessage();

			Assert.assertTrue(message.contains("This name is invalid."));

			Assert.assertTrue(
				modelListenerException.getCause() instanceof
					ObjectValidationRuleEngineException.InvalidFields);
		}
	}

	@Test
	public void testActivateAccountEntries() throws Exception {
		List<AccountEntry> accountEntries =
			AccountEntryTestUtil.addAccountEntries(
				2, AccountEntryArgs.STATUS_INACTIVE);

		for (AccountEntry accountEntry : accountEntries) {
			_assertStatus(
				accountEntry.getAccountEntryId(),
				WorkflowConstants.STATUS_INACTIVE);
		}

		_accountEntryLocalService.activateAccountEntries(
			ListUtil.toLongArray(
				accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR));

		for (AccountEntry accountEntry : accountEntries) {
			_assertStatus(
				accountEntry.getAccountEntryId(),
				WorkflowConstants.STATUS_APPROVED);
		}
	}

	@Test
	public void testActivateAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.STATUS_INACTIVE);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);

		_accountEntryLocalService.activateAccountEntry(accountEntry);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testActivateAccountEntryByPrimaryKey() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.STATUS_INACTIVE);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);

		_accountEntryLocalService.activateAccountEntry(
			accountEntry.getAccountEntryId());

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testAddAccountEntry() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		Assert.assertNotNull(
			_accountEntryLocalService.fetchAccountEntry(
				accountEntry.getAccountEntryId()));
		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(accountEntry.getAccountEntryId())));
		Assert.assertTrue(accountEntry.isRestrictMembership());

		_assertStatus(
			accountEntry, WorkflowConstants.STATUS_APPROVED,
			TestPropsValues.getUser());
		Assert.assertFalse(_hasWorkflowInstance(accountEntry));
	}

	@Test
	public void testAddAccountEntryWithDomains() throws Exception {
		String[] domains = {
			"test1.com", "test.1.com", "test-1.com", "UPPER.COM", " trim.com "
		};
		String[] expectedDomains = {
			"test1.com", "test.1.com", "test-1.com", "upper.com", "trim.com"
		};

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withDomains(domains));

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(expectedDomains),
			ArrayUtil.sortedUnique(accountEntry.getDomainsArray()));

		accountEntry = AccountEntryTestUtil.addAccountEntry();

		Assert.assertArrayEquals(new String[0], accountEntry.getDomainsArray());
	}

	@Test
	public void testAddAccountEntryWithInvalidDomains() throws Exception {
		String blockedEmailAddressDomain = "blocked.com";

		String[] invalidDomains = {
			"invalid", ".invalid", "-invalid", "invalid-", "_invalid",
			"invalid_", "@invalid.com", "invalid#domain", "invalid&domain",
			"invalid!.com", "invalid$domain.com", "invalid%.com", "*invalid",
			"invalid*", "invalid.*.com", "invalid+domain", ">", "<",
			"invalid@domain.com", blockedEmailAddressDomain
		};

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						AccountEntryEmailDomainsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"blockedEmailDomains", blockedEmailAddressDomain
						).build())) {

			for (String domain : invalidDomains) {
				try {
					AccountEntryTestUtil.addAccountEntry(
						AccountEntryArgs.withDomains(domain));

					Assert.fail(
						"Created an account entry with invalid domain " +
							domain);
				}
				catch (AccountEntryDomainsException
							accountEntryDomainsException) {
				}
			}
		}
	}

	@Test
	public void testAddAccountEntryWithWorkflowEnabled() throws Exception {
		_enableWorkflow();

		User user = UserTestUtil.addUser();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOwner(user));

		_assertStatus(accountEntry, WorkflowConstants.STATUS_PENDING, user);
		Assert.assertTrue(_hasWorkflowInstance(accountEntry));
	}

	@Test
	public void testDeactivateAccountEntries() throws Exception {
		List<AccountEntry> accountEntries =
			AccountEntryTestUtil.addAccountEntries(2);

		for (AccountEntry accountEntry : accountEntries) {
			_assertStatus(
				accountEntry.getAccountEntryId(),
				WorkflowConstants.STATUS_APPROVED);
		}

		_accountEntryLocalService.deactivateAccountEntries(
			ListUtil.toLongArray(
				accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR));

		for (AccountEntry accountEntry : accountEntries) {
			_assertStatus(
				accountEntry.getAccountEntryId(),
				WorkflowConstants.STATUS_INACTIVE);
		}
	}

	@Test
	public void testDeactivateAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		_accountEntryLocalService.deactivateAccountEntry(accountEntry);

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);
	}

	@Test
	public void testDeactivateAccountEntryByPrimaryKey() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_APPROVED);

		_accountEntryLocalService.deactivateAccountEntry(
			accountEntry.getAccountEntryId());

		_assertStatus(
			accountEntry.getAccountEntryId(),
			WorkflowConstants.STATUS_INACTIVE);
	}

	@Test
	public void testDeleteAccountEntries() throws Exception {
		List<AccountEntry> accountEntries =
			AccountEntryTestUtil.addAccountEntries(2);

		_accountEntryLocalService.deleteAccountEntries(
			ListUtil.toLongArray(
				accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR));

		for (AccountEntry accountEntry : accountEntries) {
			_assertDeleted(accountEntry);
		}
	}

	@Test
	public void testDeleteAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		accountEntry = _accountEntryLocalService.deleteAccountEntry(
			accountEntry);

		_assertDeleted(accountEntry);
	}

	@Test
	public void testDeleteAccountEntryByPrimaryKey() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_accountEntryLocalService.deleteAccountEntry(
			accountEntry.getAccountEntryId());

		_assertDeleted(accountEntry);
	}

	@Test
	public void testDeleteAccountEntryWithAddress() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		ListType listType = _listTypeLocalService.getListType(
			"personal", ListTypeConstants.CONTACT_ADDRESS);

		Address address = _addressLocalService.addAddress(
			null, accountEntry.getUserId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, RandomTestUtil.randomString(), null, 0, 0,
			listType.getListTypeId(), false, false, "1234567890",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(address);

		_accountEntryLocalService.deleteAccountEntry(
			accountEntry.getAccountEntryId());

		_assertDeleted(accountEntry);
	}

	@Test
	public void testGetGuestAccountEntry() throws Exception {
		AccountEntry guestAccountEntry =
			_accountEntryLocalService.getGuestAccountEntry(
				TestPropsValues.getCompanyId());

		Assert.assertEquals(
			AccountConstants.ACCOUNT_ENTRY_ID_GUEST,
			guestAccountEntry.getAccountEntryId());
		Assert.assertEquals(
			TestPropsValues.getCompanyId(), guestAccountEntry.getCompanyId());
		Assert.assertEquals(
			AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST,
			guestAccountEntry.getType());

		Assert.assertNull(
			_accountEntryLocalService.fetchAccountEntry(
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST));
	}

	@Test
	public void testGetUserAccountEntriesByAccountEntryMembership()
		throws Exception {

		User user = UserTestUtil.addUser();

		_testGetUserAccountEntries(
			user,
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withUsers(user)));
	}

	@Test
	public void testGetUserAccountEntriesByAccountEntryMembershipAndOrganizationMembership()
		throws Exception {

		User accountEntryOwner = UserTestUtil.addUser();

		User user1 = UserTestUtil.addUser();

		Organization organizationA = _addOrganization(
			accountEntryOwner,
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

		Organization organizationAA = _addOrganization(
			accountEntryOwner, organizationA.getOrganizationId());

		_organizationLocalService.addUserOrganization(
			user1.getUserId(), organizationAA);

		Organization organizationAB = _addOrganization(
			accountEntryOwner, organizationA.getOrganizationId());

		Organization organizationABA = _addOrganization(
			accountEntryOwner, organizationAB.getOrganizationId());

		_organizationLocalService.addUserOrganization(
			user1.getUserId(), organizationABA);

		AccountEntry accountEntryAA = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOrganizations(organizationAA),
			AccountEntryArgs.withOwner(accountEntryOwner),
			AccountEntryArgs.withUsers(user1));

		User user2 = UserTestUtil.addUser();

		Organization organizationABB = _addOrganization(
			accountEntryOwner, organizationAB.getOrganizationId());

		_organizationLocalService.addUserOrganization(
			user2.getUserId(), organizationABB);

		AccountEntry accountEntryABA = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOrganizations(organizationABA),
			AccountEntryArgs.withOwner(accountEntryOwner),
			AccountEntryArgs.withUsers(user2));

		_testGetUserAccountEntries(user1, accountEntryAA, accountEntryABA);

		AccountEntry accountEntryABB1 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOwner(accountEntryOwner),
			AccountEntryArgs.withOrganizations(organizationABB));
		AccountEntry accountEntryABB2 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOwner(accountEntryOwner),
			AccountEntryArgs.withOrganizations(organizationABB));

		_testGetUserAccountEntries(
			user2, accountEntryABA, accountEntryABB1, accountEntryABB2);

		User user3 = UserTestUtil.addUser();

		_organizationLocalService.addUserOrganization(
			user3.getUserId(), organizationABA);

		_testGetUserAccountEntries(user3, accountEntryABA);

		User user4 = UserTestUtil.addUser();

		_organizationLocalService.addUserOrganization(
			user4.getUserId(), organizationA);

		_testGetUserAccountEntries(
			user4, accountEntryAA, accountEntryABA, accountEntryABB1,
			accountEntryABB2);
	}

	@Test
	public void testGetUserAccountEntriesByAccountEntryOwnership()
		throws Exception {

		User accountEntryOwner = UserTestUtil.addUser();

		_testGetUserAccountEntries(
			accountEntryOwner,
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withOwner(accountEntryOwner)));
	}

	@Test
	public void testGetUserAccountEntriesByOrganizationMembership()
		throws Exception {

		User accountEntryOwner = UserTestUtil.addUser();

		List<AccountEntry> accountEntries = new ArrayList<>();
		List<User> users = new ArrayList<>();

		for (int i = 1; i < 5; i++) {
			Organization organization =
				_organizationLocalService.addOrganization(
					accountEntryOwner.getUserId(),
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
					RandomTestUtil.randomString(), false);

			users.add(
				UserTestUtil.addOrganizationUser(
					organization, RoleConstants.ORGANIZATION_USER));

			accountEntries.add(
				AccountEntryTestUtil.addAccountEntry(
					AccountEntryArgs.withOrganizations(organization),
					AccountEntryArgs.withOwner(accountEntryOwner)));
		}

		for (int i = 0; i < accountEntries.size(); i++) {
			_testGetUserAccountEntries(users.get(i), accountEntries.get(i));
		}
	}

	@Test
	public void testGetUserAccountEntriesByType() throws Exception {
		User accountEntryMember = UserTestUtil.addUser();
		User accountEntryOwner = UserTestUtil.addUser();

		for (String type :
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				}) {

			_testGetUserAccountEntries(
				accountEntryMember, type,
				AccountEntryTestUtil.addAccountEntry(
					AccountEntryArgs.withOwner(accountEntryOwner),
					AccountEntryArgs.withUsers(accountEntryMember),
					accountEntryArgs -> accountEntryArgs.type = type));
		}
	}

	@Test
	public void testGetUserAccountEntriesOrderByComparator() throws Exception {
		User user = UserTestUtil.addUser();

		List<AccountEntry> accountEntries =
			AccountEntryTestUtil.addAccountEntries(
				2, AccountEntryArgs.withUsers(user));

		accountEntries.sort(
			Comparator.comparing(
				AccountEntry::getName, String.CASE_INSENSITIVE_ORDER));

		Assert.assertEquals(
			accountEntries,
			_accountEntryLocalService.getUserAccountEntries(
				user.getUserId(), null, null,
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS},
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				OrderByComparatorFactoryUtil.create(
					"AccountEntry", "name", true)));
	}

	@Test
	public void testGetUserAccountEntriesWithKeywords() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(user));

		_assertGetUserAccountEntriesWithKeywords(
			accountEntry, user.getUserId(),
			String.valueOf(accountEntry.getAccountEntryId()));
		_assertGetUserAccountEntriesWithKeywords(
			accountEntry, user.getUserId(),
			accountEntry.getExternalReferenceCode());
	}

	@Test
	public void testSearchByAccountEntryId() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_assertKeywordSearch(
			accountEntry, String.valueOf(accountEntry.getAccountEntryId()));
	}

	@Test
	public void testSearchByAccountGroupIds() throws Exception {
		AccountEntryTestUtil.addAccountEntry();

		AccountGroup accountGroup = AccountGroupTestUtil.addAccountGroup(
			_accountGroupLocalService, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		LinkedHashMap<String, Object> params = _getLinkedHashMap(
			"accountGroupIds", new long[] {accountGroup.getAccountGroupId()});

		_assertSearchWithParams(
			params,
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withAccountGroups(accountGroup)));

		_accountGroupLocalService.deleteAccountGroup(accountGroup);

		_assertSearchWithParams(params);
	}

	@Test
	public void testSearchByAccountUserIds() throws Exception {
		AccountEntryTestUtil.addAccountEntry();

		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		_assertSearchWithParams(
			_getLinkedHashMap(
				"accountUserIds",
				new long[] {user1.getUserId(), user2.getUserId()}),
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withUsers(user1)),
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withUsers(user2)));
	}

	@Test
	public void testSearchByAllowNewUserMembership() throws Exception {
		AccountEntry businessAccountEntry1 =
			AccountEntryTestUtil.addAccountEntry();
		AccountEntry businessAccountEntry2 =
			AccountEntryTestUtil.addAccountEntry();
		AccountEntry personAccountEntry1 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.TYPE_PERSON);
		AccountEntry personAccountEntry2 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.TYPE_PERSON);

		User user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			businessAccountEntry2.getAccountEntryId(), user.getUserId());
		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			personAccountEntry2.getAccountEntryId(), user.getUserId());

		_assertSearchWithParams(
			null, businessAccountEntry1, businessAccountEntry2,
			personAccountEntry1, personAccountEntry2);
		_assertSearchWithParams(
			_getLinkedHashMap("allowNewUserMembership", Boolean.TRUE),
			businessAccountEntry1, businessAccountEntry2, personAccountEntry1);
		_assertSearchWithParams(
			_getLinkedHashMap("allowNewUserMembership", Boolean.FALSE),
			personAccountEntry2);
	}

	@Test
	public void testSearchByDomains() throws Exception {
		AccountEntryTestUtil.addAccountEntry();

		String emailDomain1 = "foo.com";
		String emailDomain2 = "bar.com";

		_assertSearchWithParams(
			_getLinkedHashMap(
				"domains", new String[] {emailDomain1, emailDomain2}),
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withDomains(emailDomain1)),
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withDomains(emailDomain2)));
	}

	@Test
	public void testSearchByExternalReferenceCode() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_assertKeywordSearch(
			accountEntry, accountEntry.getExternalReferenceCode());
	}

	@Test
	public void testSearchByOrganizations() throws Exception {
		AccountEntryTestUtil.addAccountEntry();

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOrganizations(parentOrganization));

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOrganizations(organization));

		_assertSearchWithParams(
			_getLinkedHashMap(
				"organizationIds",
				new long[] {parentOrganization.getOrganizationId()}),
			accountEntry1);
		_assertSearchWithParams(
			_getLinkedHashMap(
				"organizationIds",
				new long[] {organization.getOrganizationId()}),
			accountEntry2);
		_assertSearchWithParams(
			_getLinkedHashMap(
				"organizationIds",
				new long[] {
					parentOrganization.getOrganizationId(),
					organization.getOrganizationId()
				}),
			accountEntry1, accountEntry2);
	}

	@Test
	public void testSearchByParentAccountEntryId() throws Exception {
		AccountEntryTestUtil.addAccountEntry();

		AccountEntry parentAccountEntry =
			AccountEntryTestUtil.addAccountEntry();

		_assertSearchWithParams(
			_getLinkedHashMap(
				"parentAccountEntryId", parentAccountEntry.getAccountEntryId()),
			AccountEntryTestUtil.addAccountEntry(
				accountEntryArgs ->
					accountEntryArgs.parentAccountEntry = parentAccountEntry));
	}

	@Test
	public void testSearchByStatus() throws Exception {
		AccountEntry activeAccountEntry =
			AccountEntryTestUtil.addAccountEntry();
		AccountEntry inactiveAccountEntry =
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.STATUS_INACTIVE);

		_assertSearchWithParams(
			_getLinkedHashMap("status", WorkflowConstants.STATUS_APPROVED),
			activeAccountEntry);
		_assertSearchWithParams(null, activeAccountEntry);
		_assertSearchWithParams(
			_getLinkedHashMap("status", WorkflowConstants.STATUS_INACTIVE),
			inactiveAccountEntry);
		_assertSearchWithParams(
			_getLinkedHashMap("status", WorkflowConstants.STATUS_ANY),
			activeAccountEntry, inactiveAccountEntry);
	}

	@Test
	public void testSearchByType() throws Exception {
		AccountEntry businessAccountEntry =
			AccountEntryTestUtil.addAccountEntry();
		AccountEntry personAccountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.TYPE_PERSON);

		_assertSearchWithParams(null, businessAccountEntry, personAccountEntry);

		_assertSearchWithParams(
			_getLinkedHashMap(
				"types",
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS}),
			businessAccountEntry);
		_assertSearchWithParams(
			_getLinkedHashMap(
				"types",
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON}),
			personAccountEntry);
		_assertSearchWithParams(
			_getLinkedHashMap(
				"types",
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				}),
			businessAccountEntry, personAccountEntry);
		_assertSearchWithParams(
			_getLinkedHashMap("types", new String[] {"invalidType"}));
	}

	@Test
	public void testSearchByUserName() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntryTestUtil.addAccountEntry(AccountEntryArgs.withOwner(user));

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_keywordSearch(user.getFullName());

		Assert.assertEquals(0, baseModelSearchResult.getLength());
	}

	@Test
	public void testSearchIndexerDocument() throws Exception {
		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withDomains("one.com", "two.com", "three.com"),
			AccountEntryArgs.withUsers(user1, user2));

		Indexer<AccountEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			AccountEntry.class);

		Document document = indexer.getDocument(accountEntry);

		Assert.assertEquals(
			accountEntry.getDescription(), document.get(Field.DESCRIPTION));
		Assert.assertEquals(accountEntry.getName(), document.get(Field.NAME));
		Assert.assertEquals(
			accountEntry.getStatus(),
			GetterUtil.getInteger(document.get(Field.STATUS), -1));
		Assert.assertArrayEquals(
			_getAccountUserIds(accountEntry),
			GetterUtil.getLongValues(document.getValues("accountUserIds")));
		Assert.assertArrayEquals(
			accountEntry.getDomainsArray(), document.getValues("domains"));
		Assert.assertEquals(
			accountEntry.getParentAccountEntryId(),
			GetterUtil.getLong(document.get("parentAccountEntryId"), -1L));
	}

	@Test
	public void testSearchIndexerExists() throws Exception {
		Assert.assertNotNull(
			IndexerRegistryUtil.getIndexer(AccountEntry.class));
	}

	@Test
	public void testSearchKeywords() throws Exception {
		AccountEntryTestUtil.addAccountEntry();

		String keywords = RandomTestUtil.randomString();

		List<AccountEntry> expectedAccountEntries = Arrays.asList(
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withName(keywords)),
			AccountEntryTestUtil.addAccountEntry(
				AccountEntryArgs.withDescription(keywords)));

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_keywordSearch(keywords);

		Assert.assertEquals(
			expectedAccountEntries.size(), baseModelSearchResult.getLength());
	}

	@Test
	public void testSearchNoKeywords() throws Exception {
		AccountEntryTestUtil.addAccountEntry();

		List<AccountEntry> expectedAccountEntries =
			_accountEntryLocalService.getAccountEntries(
				TestPropsValues.getCompanyId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_keywordSearch(null);

		Assert.assertEquals(
			expectedAccountEntries.size(), baseModelSearchResult.getLength());
	}

	@Test
	public void testSearchPagination() throws Exception {
		String keywords = RandomTestUtil.randomString();

		List<AccountEntry> expectedAccountEntries =
			AccountEntryTestUtil.addAccountEntries(
				5, AccountEntryArgs.withDescription(keywords));

		_assertPaginationSort(expectedAccountEntries, keywords, false);
		_assertPaginationSort(expectedAccountEntries, keywords, true);
	}

	@Test
	public void testUpdateAccountEntry() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		String[] expectedDomains = {"update1.com", "update2.com"};

		accountEntry = _accountEntryLocalService.updateAccountEntry(
			accountEntry.getAccountEntryId(),
			accountEntry.getParentAccountEntryId(), accountEntry.getName(),
			accountEntry.getDescription(), false, expectedDomains,
			accountEntry.getEmailAddress(), null, accountEntry.getTaxIdNumber(),
			accountEntry.getStatus(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertArrayEquals(
			expectedDomains, accountEntry.getDomainsArray());

		accountEntry = _accountEntryLocalService.updateAccountEntry(
			accountEntry.getAccountEntryId(),
			accountEntry.getParentAccountEntryId(), accountEntry.getName(),
			accountEntry.getDescription(), false, null,
			accountEntry.getEmailAddress(), null, accountEntry.getTaxIdNumber(),
			accountEntry.getStatus(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertArrayEquals(
			expectedDomains, accountEntry.getDomainsArray());

		_assertStatus(
			accountEntry, WorkflowConstants.STATUS_APPROVED,
			TestPropsValues.getUser());
	}

	@Test
	public void testUpdateDomains() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		Assert.assertArrayEquals(new String[0], accountEntry.getDomainsArray());

		String[] expectedDomains = {"foo.com", "bar.com"};

		accountEntry = _accountEntryLocalService.updateDomains(
			accountEntry.getAccountEntryId(), expectedDomains);

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(expectedDomains),
			ArrayUtil.sortedUnique(accountEntry.getDomainsArray()));

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.searchAccountEntries(
				accountEntry.getCompanyId(), null,
				LinkedHashMapBuilder.<String, Object>put(
					"domains", expectedDomains
				).build(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null, false);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<AccountEntry> accountEntries =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(accountEntry, accountEntries.get(0));
	}

	@Test
	public void testUpdateRestrictMembership() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		long mvccVersion = accountEntry.getMvccVersion();

		boolean expectedRestrictMembership =
			!accountEntry.isRestrictMembership();

		accountEntry = _accountEntryLocalService.updateRestrictMembership(
			accountEntry.getAccountEntryId(), expectedRestrictMembership);

		Assert.assertNotEquals(mvccVersion, accountEntry.getMvccVersion());
		Assert.assertEquals(
			expectedRestrictMembership, accountEntry.isRestrictMembership());

		mvccVersion = accountEntry.getMvccVersion();

		accountEntry = _accountEntryLocalService.updateRestrictMembership(
			accountEntry.getAccountEntryId(),
			accountEntry.isRestrictMembership());

		Assert.assertEquals(mvccVersion, accountEntry.getMvccVersion());
	}

	@Test
	public void testUpdateStatus() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_assertStatus(
			accountEntry, WorkflowConstants.STATUS_APPROVED,
			TestPropsValues.getUser());

		long mvccVersion = accountEntry.getMvccVersion();

		accountEntry = _accountEntryLocalService.updateStatus(
			TestPropsValues.getUserId(), accountEntry.getAccountEntryId(),
			accountEntry.getStatus(), null, null);

		Assert.assertEquals(mvccVersion, accountEntry.getMvccVersion());

		int expectedStatus = WorkflowConstants.STATUS_DENIED;
		User user = UserTestUtil.addUser();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		Date modifiedDate = new Date();

		serviceContext.setModifiedDate(modifiedDate);

		accountEntry = _accountEntryLocalService.updateStatus(
			user.getUserId(), accountEntry.getAccountEntryId(), expectedStatus,
			serviceContext, null);

		_assertStatus(accountEntry, expectedStatus, user);
		Assert.assertEquals(modifiedDate, accountEntry.getModifiedDate());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Rule
	public TestName testName = new TestName();

	private Organization _addOrganization(User user, long parentOrganizationId)
		throws Exception {

		return _organizationLocalService.addOrganization(
			user.getUserId(), parentOrganizationId,
			RandomTestUtil.randomString(), false);
	}

	private void _assertDeleted(AccountEntry accountEntry) throws Exception {
		Assert.assertNull(
			_accountEntryLocalService.fetchAccountEntry(
				accountEntry.getAccountEntryId()));
		Assert.assertEquals(
			0,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(accountEntry.getAccountEntryId())));
		Assert.assertFalse(_hasWorkflowInstance(accountEntry));
		Assert.assertFalse(_hasAddresses(accountEntry));
	}

	private void _assertGetUserAccountEntriesWithKeywords(
			AccountEntry expectedAccountEntry, long userId, String keywords)
		throws Exception {

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
				keywords,
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			accountEntries.toString(), 1, accountEntries.size());
		Assert.assertEquals(expectedAccountEntry, accountEntries.get(0));
	}

	private void _assertKeywordSearch(
			AccountEntry expectedAccountEntry, String keywords)
		throws Exception {

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_keywordSearch(keywords);

		Assert.assertEquals(1, baseModelSearchResult.getLength());

		List<AccountEntry> accountEntries =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(expectedAccountEntry, accountEntries.get(0));
	}

	private void _assertPaginationSort(
			List<AccountEntry> expectedAccountEntries, String keywords,
			boolean reversed)
		throws Exception {

		int delta = 3;
		int start = 1;

		if (reversed) {
			expectedAccountEntries.sort(_accountEntryNameComparator.reversed());
		}
		else {
			expectedAccountEntries.sort(_accountEntryNameComparator);
		}

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.searchAccountEntries(
				TestPropsValues.getCompanyId(), keywords, null, start, delta,
				"name", reversed);

		List<AccountEntry> actualAccountEntries =
			baseModelSearchResult.getBaseModels();

		Assert.assertEquals(
			actualAccountEntries.toString(), delta,
			actualAccountEntries.size());

		for (int i = 0; i < delta; i++) {
			Assert.assertEquals(
				expectedAccountEntries.get(start + i),
				actualAccountEntries.get(i));
		}
	}

	private void _assertSearchWithParams(
			LinkedHashMap<String, Object> params,
			AccountEntry... expectedAccountEntries)
		throws Exception {

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.searchAccountEntries(
				TestPropsValues.getCompanyId(), null, params, 0, 10, null,
				false);

		Assert.assertEquals(
			expectedAccountEntries.length, baseModelSearchResult.getLength());

		List<AccountEntry> expectedAccountEntriesList = Arrays.asList(
			expectedAccountEntries);

		Assert.assertTrue(
			expectedAccountEntriesList.containsAll(
				baseModelSearchResult.getBaseModels()));
	}

	private void _assertStatus(
		AccountEntry accountEntry, int expectedStatus, User expectedUser) {

		Assert.assertEquals(expectedStatus, accountEntry.getStatus());
		Assert.assertEquals(
			expectedUser.getUserId(), accountEntry.getStatusByUserId());
		Assert.assertEquals(
			expectedUser.getFullName(), accountEntry.getStatusByUserName());
	}

	private void _assertStatus(long accountEntryId, int expectedStatus) {
		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			accountEntryId);

		Assert.assertEquals(expectedStatus, accountEntry.getStatus());
	}

	private void _enableWorkflow() throws Exception {
		_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, AccountEntry.class.getName(),
			0, 0, "Single Approver", 1);
	}

	private long[] _getAccountUserIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountUserRetriever.getAccountUsers(
				accountEntry.getAccountEntryId()),
			User.USER_ID_ACCESSOR);
	}

	private LinkedHashMap<String, Object> _getLinkedHashMap(
		String key, Object value) {

		return LinkedHashMapBuilder.<String, Object>put(
			key, value
		).build();
	}

	private boolean _hasAddresses(AccountEntry accountEntry) throws Exception {
		List<Address> addresses = _addressLocalService.getAddresses(
			accountEntry.getCompanyId(), AccountEntry.class.getName(),
			accountEntry.getAccountEntryId());

		if (addresses.isEmpty()) {
			return false;
		}

		return true;
	}

	private boolean _hasWorkflowInstance(AccountEntry accountEntry)
		throws Exception {

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				accountEntry.getCompanyId(),
				GroupConstants.DEFAULT_LIVE_GROUP_ID,
				AccountEntry.class.getName(), accountEntry.getAccountEntryId());

		if (workflowInstanceLink != null) {
			return true;
		}

		return false;
	}

	private BaseModelSearchResult<AccountEntry> _keywordSearch(String keywords)
		throws Exception {

		return _accountEntryLocalService.searchAccountEntries(
			TestPropsValues.getCompanyId(), keywords, null, 0, 10, null, false);
	}

	private void _testGetUserAccountEntries(
			User user, AccountEntry... expectedAccountEntries)
		throws Exception {

		_testGetUserAccountEntries(
			user, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			expectedAccountEntries);
	}

	private void _testGetUserAccountEntries(
			User user, String type, AccountEntry... expectedAccountEntries)
		throws Exception {

		Arrays.sort(expectedAccountEntries);

		List<AccountEntry> userAccountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				user.getUserId(), AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
				null, new String[] {type}, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			userAccountEntries.toString(), expectedAccountEntries.length,
			userAccountEntries.size());

		Assert.assertEquals(
			expectedAccountEntries.length,
			_accountEntryLocalService.getUserAccountEntriesCount(
				user.getUserId(), AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
				null, new String[] {type}));

		userAccountEntries = ListUtil.sort(userAccountEntries);

		for (int i = 0; i < expectedAccountEntries.length; i++) {
			AccountEntry expectedAccountEntry = expectedAccountEntries[i];
			AccountEntry userAccountEntry = userAccountEntries.get(i);

			Assert.assertEquals(
				expectedAccountEntry.getAccountEntryId(),
				userAccountEntry.getAccountEntryId());
			Assert.assertEquals(
				expectedAccountEntry.getType(), userAccountEntry.getType());
			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED,
				userAccountEntry.getStatus());
		}
	}

	private static final Comparator<AccountEntry> _accountEntryNameComparator =
		(accountEntry1, accountEntry2) -> {
			String name1 = accountEntry1.getName();
			String name2 = accountEntry2.getName();

			return name1.compareToIgnoreCase(name2);
		};

	@Inject
	private static ListTypeLocalService _listTypeLocalService;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private AccountUserRetriever _accountUserRetriever;

	@Inject
	private AddressLocalService _addressLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}