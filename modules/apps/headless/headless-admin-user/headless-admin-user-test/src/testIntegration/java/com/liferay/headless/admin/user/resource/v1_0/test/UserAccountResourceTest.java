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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class UserAccountResourceTest extends BaseUserAccountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_organization = OrganizationTestUtil.addOrganization();

		_testUser = UserLocalServiceUtil.getUserByEmailAddress(
			testGroup.getCompanyId(), "test@liferay.com");

		UserLocalServiceUtil.deleteGroupUser(
			testGroup.getGroupId(), _testUser.getUserId());

		// See LPS-94496 for why we have to delete all users except for the
		// test user

		List<User> users = UserLocalServiceUtil.getUsers(
			PortalUtil.getDefaultCompanyId(), false,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (User user : users) {
			if (user.getUserId() != _testUser.getUserId()) {
				UserLocalServiceUtil.deleteUser(user);
			}
		}

		Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			_testUser.getModelClassName());

		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			IndexWriterHelperUtil.deleteEntityDocuments(
				indexer.getSearchEngineId(), company.getCompanyId(),
				_testUser.getModelClassName(), true);

			indexer.reindex(
				new String[] {String.valueOf(company.getCompanyId())});
		}
	}

	@Override
	@Test
	public void testGetMyUserAccount() throws Exception {
		User user = UserTestUtil.getAdminUser(PortalUtil.getDefaultCompanyId());

		UserAccount userAccount = new UserAccount() {
			{
				additionalName = user.getMiddleName();
				alternateName = user.getScreenName();
				birthDate = user.getBirthday();
				emailAddress = user.getEmailAddress();
				familyName = user.getFirstName();
				givenName = user.getLastName();
				id = user.getUserId();
				jobTitle = user.getJobTitle();
			}
		};

		UserAccount getUserAccount = userAccountResource.getMyUserAccount();

		assertEquals(userAccount, getUserAccount);
		assertValid(getUserAccount);
	}

	@Override
	@Test
	public void testGetSiteUserAccountsPage() throws Exception {
		Page<UserAccount> page = userAccountResource.getSiteUserAccountsPage(
			testGetSiteUserAccountsPage_getSiteId(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteUserAccountsPage_getSiteId();

		UserAccount userAccount1 = testGetSiteUserAccountsPage_addUserAccount(
			siteId, randomUserAccount());
		UserAccount userAccount2 = testGetSiteUserAccountsPage_addUserAccount(
			siteId, randomUserAccount());

		page = userAccountResource.getSiteUserAccountsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Override
	@Test
	public void testGetUserAccountsPage() throws Exception {
		UserAccount userAccount1 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount2 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount3 = userAccountResource.getUserAccount(
			_testUser.getUserId());

		Page<UserAccount> page = userAccountResource.getUserAccountsPage(
			null, null, Pagination.of(1, 3), null);

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Ignore
	@Override
	@Test
	public void testGetUserAccountsPageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetUserAccountsPageWithSortDateTime() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetUserAccountsPageWithSortString() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetMyUserAccount() {
	}

	@Override
	@Test
	public void testGraphQLGetUserAccountsPage() throws Exception {
		UserAccount userAccount1 = testGraphQLUserAccount_addUserAccount();
		UserAccount userAccount2 = testGraphQLUserAccount_addUserAccount();

		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"userAccounts",
				HashMapBuilder.<String, Object>put(
					"page", 1
				).put(
					"pageSize", 3
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject userAccountsJSONObject = dataJSONObject.getJSONObject(
			"userAccounts");

		Assert.assertEquals(3, userAccountsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(userAccount1, userAccount2),
			userAccountsJSONObject.getJSONArray("items"));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"familyName", "givenName"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"emailAddress"};
	}

	@Override
	protected UserAccount randomUserAccount() throws Exception {
		UserAccount userAccount = super.randomUserAccount();

		userAccount.setEmailAddress(
			userAccount.getEmailAddress() + "@liferay.com");

		return userAccount;
	}

	@Override
	protected UserAccount testGetMyUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount(
			testGetSiteUserAccountsPage_getSiteId(), randomUserAccount());
	}

	@Override
	protected UserAccount testGetOrganizationUserAccountsPage_addUserAccount(
			String organizationId, UserAccount userAccount)
		throws Exception {

		userAccount = _addUserAccount(
			testGetSiteUserAccountsPage_getSiteId(), userAccount);

		UserLocalServiceUtil.addOrganizationUser(
			GetterUtil.getLong(organizationId), userAccount.getId());

		return userAccount;
	}

	@Override
	protected String testGetOrganizationUserAccountsPage_getOrganizationId() {
		return String.valueOf(_organization.getOrganizationId());
	}

	@Override
	protected UserAccount testGetSiteUserAccountsPage_addUserAccount(
			Long siteId, UserAccount userAccount)
		throws Exception {

		return _addUserAccount(siteId, userAccount);
	}

	@Override
	protected Long testGetSiteUserAccountsPage_getSiteId() {
		return testGroup.getGroupId();
	}

	@Override
	protected UserAccount testGetUserAccount_addUserAccount() throws Exception {
		return _addUserAccount(
			testGetSiteUserAccountsPage_getSiteId(), randomUserAccount());
	}

	@Override
	protected UserAccount testGetUserAccountsPage_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addUserAccount(testGroup.getGroupId(), userAccount);
	}

	@Override
	protected UserAccount testGraphQLUserAccount_addUserAccount()
		throws Exception {

		return testGetMyUserAccount_addUserAccount();
	}

	private UserAccount _addUserAccount(long siteId, UserAccount userAccount)
		throws Exception {

		User user = UserLocalServiceUtil.addUser(
			UserConstants.USER_ID_DEFAULT, PortalUtil.getDefaultCompanyId(),
			true, null, null, Validator.isNull(userAccount.getAlternateName()),
			userAccount.getAlternateName(), userAccount.getEmailAddress(), 0,
			StringPool.BLANK, LocaleUtil.getDefault(),
			userAccount.getGivenName(), StringPool.BLANK,
			userAccount.getFamilyName(), 0, 0, true, 1, 1, 1970,
			userAccount.getJobTitle(), null, null, null, null, false,
			new ServiceContext() {
				{
					setCreateDate(userAccount.getDateCreated());
					setModifiedDate(userAccount.getDateModified());
				}
			});

		userAccount.setDateModified(user.getModifiedDate());
		userAccount.setId(user.getUserId());

		_users.add(UserLocalServiceUtil.getUser(user.getUserId()));

		UserLocalServiceUtil.addGroupUser(siteId, userAccount.getId());

		return userAccount;
	}

	@DeleteAfterTestRun
	private Organization _organization;

	private User _testUser;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}