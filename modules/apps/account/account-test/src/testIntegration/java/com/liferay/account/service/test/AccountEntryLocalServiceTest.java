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

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountEntryDomainsException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.retriever.AccountUserRetriever;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
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
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAssetTagNames(new String[] {"tag1", "tag2"});

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			AccountEntry.class.getName(), accountEntry.getAccountEntryId());

		Assert.assertArrayEquals(
			serviceContext.getAssetTagNames(),
			ListUtil.toArray(assetTags, AssetTag.NAME_ACCESSOR));
	}

	@Test
	public void testAccountEntryGroup() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		Group group = accountEntry.getAccountEntryGroup();

		Assert.assertNotNull(group);

		long classNameId = _classNameLocalService.getClassNameId(
			AccountEntry.class);

		Assert.assertEquals(classNameId, group.getClassNameId());

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
	public void testActivateAccountEntries() throws Exception {
		long[] accountEntryIds = _addAccountEntries(
			WorkflowConstants.STATUS_INACTIVE);

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_INACTIVE);
		}

		_accountEntryLocalService.activateAccountEntries(accountEntryIds);

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_APPROVED);
		}
	}

	@Test
	public void testActivateAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = _addAccountEntry(
			WorkflowConstants.STATUS_INACTIVE);

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
		AccountEntry accountEntry = _addAccountEntry(
			WorkflowConstants.STATUS_INACTIVE);

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
		AccountEntry accountEntry = _addAccountEntry();

		Assert.assertNotNull(
			_accountEntryLocalService.fetchAccountEntry(
				accountEntry.getAccountEntryId()));

		int resourcePermissionsCount =
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(accountEntry.getAccountEntryId()));

		Assert.assertEquals(1, resourcePermissionsCount);
	}

	@Test
	public void testAddAccountEntryWithDomains() throws Exception {
		String[] domains = {"test1.com", "test.1.com", "test-1.com"};

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService, domains);

		Assert.assertEquals(
			StringUtil.merge(ArrayUtil.distinct(domains), ","),
			accountEntry.getDomains());
	}

	@Test
	public void testAddAccountEntryWithInvalidDomains() throws Exception {
		String[] invalidDomains = {
			"invalid", ".invalid", "-invalid", "invalid-", "_invalid",
			"invalid_", "@invalid.com", "invalid#domain", "invalid&domain",
			"invalid!.com", "invalid$domain.com", "invalid%.com", "*invalid",
			"invalid*", "invalid.*.com", "invalid+domain", ">", "<"
		};

		for (String domain : invalidDomains) {
			try {
				AccountEntryTestUtil.addAccountEntry(
					_accountEntryLocalService, new String[] {domain});

				Assert.fail(
					"Created an account entry with invalid domain " + domain);
			}
			catch (AccountEntryDomainsException accountEntryDomainsException) {
			}
		}
	}

	@Test
	public void testDeactivateAccountEntries() throws Exception {
		long[] accountEntryIds = _addAccountEntries();

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_APPROVED);
		}

		_accountEntryLocalService.deactivateAccountEntries(accountEntryIds);

		for (long accountEntryId : accountEntryIds) {
			_assertStatus(accountEntryId, WorkflowConstants.STATUS_INACTIVE);
		}
	}

	@Test
	public void testDeactivateAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

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
		AccountEntry accountEntry = _addAccountEntry();

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
		long[] accountEntryIds = _addAccountEntries();

		_accountEntryLocalService.deleteAccountEntries(accountEntryIds);

		for (long accountEntryId : accountEntryIds) {
			_assertDeleted(accountEntryId);
		}
	}

	@Test
	public void testDeleteAccountEntryByModel() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		_accountEntryLocalService.deleteAccountEntry(accountEntry);

		_assertDeleted(accountEntry.getAccountEntryId());
	}

	@Test
	public void testDeleteAccountEntryByPrimaryKey() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		_accountEntryLocalService.deleteAccountEntry(
			accountEntry.getAccountEntryId());

		_assertDeleted(accountEntry.getAccountEntryId());
	}

	@Test
	public void testSearchByAccountUserIds() throws Exception {
		_addAccountEntries();

		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		_assertSearchWithParams(
			Arrays.asList(
				_addAccountEntryWithUser(user1),
				_addAccountEntryWithUser(user2)),
			_getLinkedHashMap(
				"accountUserIds",
				new long[] {user1.getUserId(), user2.getUserId()}));
	}

	@Test
	public void testSearchByDomains() throws Exception {
		_addAccountEntries();

		String emailDomain1 = "foo.com";
		String emailDomain2 = "bar.com";

		_assertSearchWithParams(
			Arrays.asList(
				_addAccountEntryWithEmailDomain(emailDomain1),
				_addAccountEntryWithEmailDomain(emailDomain2)),
			_getLinkedHashMap(
				"domains", new String[] {emailDomain1, emailDomain2}));
	}

	@Test
	public void testSearchByOrganizations() throws Exception {
		_addAccountEntries();

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		AccountEntry accountEntry1 = _addAccountEntryWithOrganization(
			parentOrganization);

		AccountEntry accountEntry2 = _addAccountEntryWithOrganization(
			organization);

		_assertSearchWithParams(
			Arrays.asList(accountEntry1),
			_getLinkedHashMap(
				"organizationIds",
				new long[] {parentOrganization.getOrganizationId()}));
		_assertSearchWithParams(
			Arrays.asList(accountEntry2),
			_getLinkedHashMap(
				"organizationIds",
				new long[] {organization.getOrganizationId()}));
		_assertSearchWithParams(
			Arrays.asList(accountEntry1, accountEntry2),
			_getLinkedHashMap(
				"organizationIds",
				new long[] {
					parentOrganization.getOrganizationId(),
					organization.getOrganizationId()
				}));
	}

	@Test
	public void testSearchByParentAccountEntryId() throws Exception {
		_addAccountEntries();

		AccountEntry parentAccountEntry = _addAccountEntry();

		_assertSearchWithParams(
			Arrays.asList(
				_addAccountEntryWithParentAccountEntryId(
					parentAccountEntry.getAccountEntryId()),
				_addAccountEntryWithParentAccountEntryId(
					parentAccountEntry.getAccountEntryId())),
			_getLinkedHashMap(
				"parentAccountEntryId",
				parentAccountEntry.getAccountEntryId()));
	}

	@Test
	public void testSearchByStatus() throws Exception {
		List<AccountEntry> activeAccountEntries = Arrays.asList(
			_addAccountEntry(), _addAccountEntry(), _addAccountEntry());
		List<AccountEntry> inactiveAccountEntries = Arrays.asList(
			_addAccountEntry(WorkflowConstants.STATUS_INACTIVE),
			_addAccountEntry(WorkflowConstants.STATUS_INACTIVE),
			_addAccountEntry(WorkflowConstants.STATUS_INACTIVE),
			_addAccountEntry(WorkflowConstants.STATUS_INACTIVE));

		_assertSearchWithParams(
			activeAccountEntries,
			_getLinkedHashMap("status", WorkflowConstants.STATUS_APPROVED));
		_assertSearchWithParams(activeAccountEntries, new LinkedHashMap<>());
		_assertSearchWithParams(
			inactiveAccountEntries,
			_getLinkedHashMap("status", WorkflowConstants.STATUS_INACTIVE));
		_assertSearchWithParams(
			ListUtil.concat(activeAccountEntries, inactiveAccountEntries),
			_getLinkedHashMap("status", WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testSearchByType() throws Exception {
		AccountEntry businessAccountEntry = _addAccountEntry();
		AccountEntry personalAccountEntry = _addPersonalAccountEntry();

		_assertSearchWithParams(
			Arrays.asList(businessAccountEntry, personalAccountEntry), null);

		_assertSearchWithParams(
			Collections.singletonList(businessAccountEntry),
			_getLinkedHashMap(
				"type", AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS));
		_assertSearchWithParams(
			Collections.singletonList(personalAccountEntry),
			_getLinkedHashMap(
				"type", AccountConstants.ACCOUNT_ENTRY_TYPE_PERSONAL));
		_assertSearchWithParams(
			Collections.emptyList(), _getLinkedHashMap("type", "invalidType"));
	}

	@Test
	public void testSearchIndexerDocument() throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		User user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		User user1 = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user1.getUserId());

		accountEntry.setDomains("one,two,three");

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
			_getDomains(accountEntry), document.getValues("domains"));
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
		_addAccountEntries();

		String keywords = RandomTestUtil.randomString();

		List<AccountEntry> expectedAccountEntries = Arrays.asList(
			_addAccountEntry(keywords, RandomTestUtil.randomString()),
			_addAccountEntry(RandomTestUtil.randomString(), keywords));

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_keywordSearch(keywords);

		Assert.assertEquals(
			expectedAccountEntries.size(), baseModelSearchResult.getLength());
	}

	@Test
	public void testSearchNoKeywords() throws Exception {
		_addAccountEntries();

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

		List<AccountEntry> expectedAccountEntries = Arrays.asList(
			_addAccountEntry(RandomTestUtil.randomString(), keywords),
			_addAccountEntry(RandomTestUtil.randomString(), keywords),
			_addAccountEntry(RandomTestUtil.randomString(), keywords),
			_addAccountEntry(RandomTestUtil.randomString(), keywords),
			_addAccountEntry(RandomTestUtil.randomString(), keywords));

		_assertPaginationSort(expectedAccountEntries, keywords, false);
		_assertPaginationSort(expectedAccountEntries, keywords, true);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private long[] _addAccountEntries() throws Exception {
		return _addAccountEntries(WorkflowConstants.STATUS_APPROVED);
	}

	private long[] _addAccountEntries(int status) throws Exception {
		int size = 5;

		long[] accountEntryIds = new long[size];

		for (int i = 0; i < size; i++) {
			AccountEntry accountEntry = _addAccountEntry(status);

			accountEntryIds[i] = accountEntry.getAccountEntryId();
		}

		return accountEntryIds;
	}

	private AccountEntry _addAccountEntry() throws Exception {
		return _addAccountEntry(WorkflowConstants.STATUS_APPROVED);
	}

	private AccountEntry _addAccountEntry(int status) throws Exception {
		return AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService, status);
	}

	private AccountEntry _addAccountEntry(String name, String description)
		throws Exception {

		return AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService, name, description);
	}

	private AccountEntry _addAccountEntryWithEmailDomain(String emailDomain)
		throws Exception {

		return AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService, new String[] {emailDomain});
	}

	private AccountEntry _addAccountEntryWithOrganization(
			Organization organization)
		throws Exception {

		AccountEntry accountEntry = _addAccountEntry();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(), organization.getOrganizationId());

		return accountEntry;
	}

	private AccountEntry _addAccountEntryWithParentAccountEntryId(
			long parentAccountEntryId)
		throws Exception {

		return _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(), parentAccountEntryId,
			RandomTestUtil.randomString(50), RandomTestUtil.randomString(50),
			null, null, null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	private AccountEntry _addAccountEntryWithUser(User user) throws Exception {
		AccountEntry accountEntry = _addAccountEntry();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), user.getUserId());

		return accountEntry;
	}

	private AccountEntry _addPersonalAccountEntry() throws Exception {
		return AccountEntryTestUtil.addPersonalAccountEntry(
			_accountEntryLocalService);
	}

	private void _assertDeleted(long accountEntryId) throws Exception {
		Assert.assertNull(
			_accountEntryLocalService.fetchAccountEntry(accountEntryId));

		int resourcePermissionsCount =
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(accountEntryId));

		Assert.assertEquals(0, resourcePermissionsCount);
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
			_accountEntryLocalService.search(
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
			List<AccountEntry> expectedAccountEntries,
			LinkedHashMap<String, Object> params)
		throws Exception {

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.search(
				TestPropsValues.getCompanyId(), null, params, 0, 10, null,
				false);

		Assert.assertEquals(
			expectedAccountEntries.size(), baseModelSearchResult.getLength());
		Assert.assertTrue(
			expectedAccountEntries.containsAll(
				baseModelSearchResult.getBaseModels()));
	}

	private void _assertStatus(long accountEntryId, int expectedStatus) {
		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			accountEntryId);

		Assert.assertEquals(expectedStatus, accountEntry.getStatus());
	}

	private long[] _getAccountUserIds(AccountEntry accountEntry) {
		return ListUtil.toLongArray(
			_accountUserRetriever.getAccountUsers(
				accountEntry.getAccountEntryId()),
			User.USER_ID_ACCESSOR);
	}

	private String[] _getDomains(AccountEntry accountEntry) {
		return ArrayUtil.toStringArray(
			StringUtil.split(accountEntry.getDomains(), CharPool.COMMA));
	}

	private LinkedHashMap<String, Object> _getLinkedHashMap(
		String key, Object value) {

		return LinkedHashMapBuilder.<String, Object>put(
			key, value
		).build();
	}

	private BaseModelSearchResult<AccountEntry> _keywordSearch(String keywords)
		throws Exception {

		return _accountEntryLocalService.search(
			TestPropsValues.getCompanyId(), keywords, null, 0, 10, null, false);
	}

	private static final Comparator<AccountEntry> _accountEntryNameComparator =
		(accountEntry1, accountEntry2) -> {
			String name1 = accountEntry1.getName();
			String name2 = accountEntry2.getName();

			return name1.compareToIgnoreCase(name2);
		};

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountUserRetriever _accountUserRetriever;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}