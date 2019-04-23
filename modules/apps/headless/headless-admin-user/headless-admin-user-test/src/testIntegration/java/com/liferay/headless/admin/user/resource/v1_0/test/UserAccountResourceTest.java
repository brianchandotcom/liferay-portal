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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		Indexer<User> indexer = IndexerRegistryUtil.getIndexer(
			_testUser.getModelClassName());

		if (indexer != null) {
			indexer.reindex(
				_testUser.getModelClassName(), _testUser.getUserId());
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

		UserAccount getUserAccount = invokeGetMyUserAccount();

		assertEquals(userAccount, getUserAccount);
		assertValid(getUserAccount);
	}

	@Override
	@Test
	public void testGetUserAccountsPage() throws Exception {
		_removeAllUsersExceptAdmin();

		UserAccount userAccount1 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount2 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());
		UserAccount userAccount3 = invokeGetUserAccount(_testUser.getUserId());

		Page<UserAccount> page = invokeGetUserAccountsPage(
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

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"familyName", "givenName"};
	}

	@Override
	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = super.getEntityFields(type);

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> !Objects.equals(
				entityField.getName(), "emailAddress")
		).collect(
			Collectors.toList()
		);
	}

	@Override
	protected UserAccount randomUserAccount() {
		UserAccount userAccount = super.randomUserAccount();

		userAccount.setEmailAddress(
			userAccount.getEmailAddress() + "@liferay.com");

		return userAccount;
	}

	@Override
	protected UserAccount testGetMyUserAccount_addUserAccount()
		throws Exception {

		return _addUserAccount(randomUserAccount());
	}

	@Override
	protected UserAccount testGetOrganizationUserAccountsPage_addUserAccount(
			Long organizationId, UserAccount userAccount)
		throws Exception {

		userAccount = _addUserAccount(userAccount);

		UserLocalServiceUtil.addOrganizationUser(
			organizationId, userAccount.getId());

		return userAccount;
	}

	@Override
	protected Long testGetOrganizationUserAccountsPage_getOrganizationId() {
		return _organization.getOrganizationId();
	}

	@Override
	protected UserAccount testGetUserAccount_addUserAccount() throws Exception {
		return _addUserAccount(randomUserAccount());
	}

	@Override
	protected UserAccount testGetUserAccountsPage_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addUserAccount(userAccount);
	}

	@Override
	protected UserAccount testGetWebSiteUserAccountsPage_addUserAccount(
			Long webSiteId, UserAccount userAccount)
		throws Exception {

		userAccount = _addUserAccount(userAccount);

		UserLocalServiceUtil.addGroupUser(webSiteId, userAccount.getId());

		return userAccount;
	}

	@Override
	protected Long testGetWebSiteUserAccountsPage_getWebSiteId() {
		return testGroup.getGroupId();
	}

	private UserAccount _addUserAccount(UserAccount userAccount)
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

		return userAccount;
	}

	private void _removeAllUsersExceptAdmin() throws Exception {
		List<User> users = UserLocalServiceUtil.getUsers(
			PortalUtil.getDefaultCompanyId(), false,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (User user : users) {
			if (user.getUserId() != _testUser.getUserId()) {
				UserLocalServiceUtil.deleteUser(user);
			}
		}
	}

	@DeleteAfterTestRun
	private Organization _organization;

	private User _testUser;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}