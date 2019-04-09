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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
	public void testGetUserAccountsPage() throws Exception {
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
	protected void assertValid(UserAccount userAccount) {
		boolean valid = false;

		if ((userAccount.getFamilyName() != null) &&
			(userAccount.getGivenName() != null) &&
			(userAccount.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		UserAccount userAccount, UserAccount userAccount2) {

		if (Objects.equals(
				userAccount.getFamilyName(), userAccount2.getFamilyName()) &&
			Objects.equals(
				userAccount.getGivenName(), userAccount2.getGivenName())) {

			return true;
		}

		return false;
	}

	@Override
	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = super.getEntityFields(type);

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> !Objects.equals(entityField.getName(), "email")
		).collect(
			Collectors.toList()
		);
	}

	@Override
	protected UserAccount randomIrrelevantUserAccount() {
		return randomUserAccount();
	}

	@Override
	protected UserAccount randomUserAccount() {
		return new UserAccount() {
			{
				additionalName = RandomTestUtil.randomString();
				alternateName = RandomTestUtil.randomString();
				birthDate = RandomTestUtil.nextDate();
				email = RandomTestUtil.randomString() + "@liferay.com";
				familyName = RandomTestUtil.randomString();
				givenName = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				jobTitle = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected UserAccount testDeleteUserAccount_addUserAccount()
		throws Exception {

		return invokePostUserAccount(randomUserAccount());
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
	protected Long testGetOrganizationUserAccountsPage_getOrganizationId()
		throws Exception {

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

		return _addUserAccount(randomUserAccount());
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
	protected Long testGetWebSiteUserAccountsPage_getWebSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	@Override
	protected UserAccount testPostFormDataUserAccount_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addUserAccount(userAccount);
	}

	@Override
	protected UserAccount testPostUserAccount_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		return _addUserAccount(userAccount);
	}

	@Override
	protected UserAccount testPutUserAccount_addUserAccount() throws Exception {
		return _addUserAccount(randomUserAccount());
	}

	private UserAccount _addUserAccount(UserAccount userAccount)
		throws Exception {

		userAccount = invokePostUserAccount(userAccount);

		_users.add(UserLocalServiceUtil.getUser(userAccount.getId()));

		return userAccount;
	}

	@DeleteAfterTestRun
	private Organization _organization;

	private User _testUser;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}