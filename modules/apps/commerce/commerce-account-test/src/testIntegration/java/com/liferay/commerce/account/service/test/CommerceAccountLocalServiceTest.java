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

package com.liferay.commerce.account.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class CommerceAccountLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAddBusinessCommerceAccount() throws Exception {
		frutillaRule.scenario(
			"Adding a new business Commerce Account"
		).given(
			"A company"
		).and(
			"A user as Account Administrator"
		).when(
			"The Commerce Account is created"
		).then(
			"The list of accounts contains only one Commerce Account"
		).and(
			"That Commerce Account matches the one created before."
		);

		CommerceAccount businessCommerceAccount =
			CommerceAccountTestUtil.addBusinessCommerceAccount(
				_user.getUserId(), "Business Account", "example@email.com",
				_getServiceContext());

		List<CommerceAccount> commerceAccounts =
			_commerceAccountLocalService.getUserCommerceAccounts(
				_user.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int commerceAccountsCount =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				_user.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			_user.toString(), commerceAccounts.size(), commerceAccountsCount);

		Assert.assertEquals(_user.toString(), 1, commerceAccountsCount);

		CommerceAccount commerceAccount = commerceAccounts.get(0);

		Assert.assertEquals(
			businessCommerceAccount.getCommerceAccountId(),
			commerceAccount.getCommerceAccountId());
		Assert.assertEquals(
			businessCommerceAccount.getType(), commerceAccount.getType());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, commerceAccount.getStatus());
	}

	@Test
	public void testAddPersonalCommerceAccount() throws Exception {
		frutillaRule.scenario(
			"Adding a new personal Commerce Account"
		).given(
			"A company"
		).when(
			"The Commerce Account is created"
		).then(
			"The list of accounts contains only one Commerce Account"
		).and(
			"That Commerce Account matches the one created before."
		);

		CommerceAccount personalCommerceAccount =
			CommerceAccountTestUtil.addPersonalCommerceAccount(
				_user.getUserId(), _getServiceContext());

		Assert.assertEquals(
			personalCommerceAccount.getName(), _user.getFullName());

		List<CommerceAccount> commerceAccounts =
			_commerceAccountLocalService.getUserCommerceAccounts(
				_user.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2C, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int commerceAccountsCount =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				_user.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2C, StringPool.BLANK, null);

		Assert.assertEquals(
			_user.toString(), commerceAccounts.size(), commerceAccountsCount);

		Assert.assertEquals(_user.toString(), 1, commerceAccountsCount);

		Assert.assertEquals(
			commerceAccounts.toString(), 1, commerceAccounts.size());

		CommerceAccount commerceAccount = commerceAccounts.get(0);

		Assert.assertEquals(
			personalCommerceAccount.getCommerceAccountId(),
			commerceAccount.getCommerceAccountId());
		Assert.assertEquals(
			personalCommerceAccount.getName(), commerceAccount.getName());
		Assert.assertEquals(
			personalCommerceAccount.getType(), commerceAccount.getType());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, commerceAccount.getStatus());
	}

	@Test
	public void testBusinessOrganizationCommerceAccountsVisibility()
		throws Exception {

		frutillaRule.scenario(
			"Adding new business Commerce Accounts"
		).given(
			"A company"
		).when(
			"The Commerce Accounts are created"
		).and(
			"Organizations are added to them"
		).then(
			"Check the visibility of that accounts for all the organizations"
		);

		ServiceContext serviceContext = _getServiceContext();

		String organizationName = RandomTestUtil.randomString();

		for (int i = 1; i < 3; i++) {
			Organization organization =
				_organizationLocalService.addOrganization(
					_user.getUserId(),
					OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
					organizationName + i, false);

			User user = UserTestUtil.addUser(
				_user.getCompanyId(), _user.getUserId(), "organizationUser" + i,
				serviceContext.getLocale(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				new long[] {serviceContext.getScopeGroupId()}, serviceContext);

			_organizationLocalService.addUserOrganization(
				user.getUserId(), organization);

			CommerceAccountTestUtil.addBusinessCommerceAccount(
				_user.getUserId(), "businessOrganizationAccount" + i,
				"example@example.com", StringPool.BLANK, null,
				new long[] {organization.getOrganizationId()}, serviceContext);
		}

		User organizationUser1 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "organizationUser1");
		User organizationUser2 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "organizationUser2");

		List<CommerceAccount> organizationUserCommerceAccounts1 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				organizationUser1.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int organizationUserCommerceAccountsCount1 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				organizationUser1.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			organizationUser1.toString(),
			organizationUserCommerceAccounts1.size(),
			organizationUserCommerceAccountsCount1);

		Assert.assertEquals(
			organizationUser2.toString(), 1,
			organizationUserCommerceAccountsCount1);

		CommerceAccount organizationUserCommerceAccount1 =
			organizationUserCommerceAccounts1.get(0);

		Assert.assertEquals(
			"businessOrganizationAccount1",
			organizationUserCommerceAccount1.getName());

		List<CommerceAccount> organizationUserCommerceAccounts2 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				organizationUser2.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int organizationUserCommerceAccountsCount2 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				organizationUser2.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			organizationUser2.toString(),
			organizationUserCommerceAccounts2.size(),
			organizationUserCommerceAccountsCount2);

		Assert.assertEquals(
			organizationUser2.toString(), 1,
			organizationUserCommerceAccountsCount2);

		Assert.assertEquals(
			organizationUserCommerceAccounts2.toString(), 1,
			organizationUserCommerceAccounts2.size());

		CommerceAccount organizationUserCommerceAccount2 =
			organizationUserCommerceAccounts2.get(0);

		Assert.assertEquals(
			"businessOrganizationAccount2",
			organizationUserCommerceAccount2.getName());
	}

	@Test
	public void testBusinessUserCommerceAccountsVisibility() throws Exception {
		frutillaRule.scenario(
			"Adding new business Commerce Accounts"
		).given(
			"A company"
		).when(
			"The Commerce Accounts are created"
		).and(
			"Users are added to them"
		).then(
			"Check the visibility of that accounts for all the users"
		);

		ServiceContext serviceContext = _getServiceContext();

		for (int i = 1; i < 3; i++) {
			User user = UserTestUtil.addUser(
				_user.getCompanyId(), _user.getUserId(), "businessUser" + i,
				serviceContext.getLocale(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				new long[] {serviceContext.getScopeGroupId()}, serviceContext);

			CommerceAccountTestUtil.addBusinessCommerceAccount(
				_user.getUserId(), "businessUserAccount" + i,
				"example@example.com", StringPool.BLANK,
				new long[] {user.getUserId()}, null, serviceContext);
		}

		User businessUser1 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "businessUser1");
		User businessUser2 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "businessUser2");

		List<CommerceAccount> businessUserCommerceAccounts1 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				businessUser1.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int businessUserCommerceAccountsCount1 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				businessUser1.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			businessUser1.toString(), businessUserCommerceAccounts1.size(),
			businessUserCommerceAccountsCount1);

		Assert.assertEquals(
			businessUser1.toString(), 1, businessUserCommerceAccountsCount1);

		CommerceAccount businessUserCommerceAccount1 =
			businessUserCommerceAccounts1.get(0);

		Assert.assertEquals(
			"businessUserAccount1", businessUserCommerceAccount1.getName());

		List<CommerceAccount> businessUserCommerceAccounts2 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				businessUser2.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int businessUserCommerceAccountsCount2 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				businessUser2.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			businessUser2.toString(), businessUserCommerceAccounts2.size(),
			businessUserCommerceAccountsCount2);

		Assert.assertEquals(
			businessUser2.toString(), 1, businessUserCommerceAccountsCount2);

		CommerceAccount businessUserCommerceAccount2 =
			businessUserCommerceAccounts2.get(0);

		Assert.assertEquals(
			"businessUserAccount2", businessUserCommerceAccount2.getName());
	}

	@Test
	public void testUserCommerceAccountsVisibility() throws Exception {
		frutillaRule.scenario(
			"Adding new business Commerce Accounts"
		).given(
			"A company"
		).when(
			"The Commerce Accounts are created"
		).and(
			"Users are added to them with different criteria"
		).then(
			"Check the visibility of that accounts for all the users"
		);

		_addOrganizationSet("business");

		List<String> externalReferenceCodes = _getExternalReferenceCodes(5);

		ServiceContext serviceContext = _getServiceContext();

		for (int i = 1; i < 6; i++) {
			User user = UserTestUtil.addUser(
				_user.getCompanyId(), _user.getUserId(), "user" + i,
				serviceContext.getLocale(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null, serviceContext);

			if (i == 1) {
				CommerceAccountTestUtil.addBusinessCommerceAccount(
					user.getUserId(), "account" + i, "example@test.com",
					externalReferenceCodes.get(i - 1), serviceContext);

				continue;
			}

			CommerceAccountTestUtil.addBusinessCommerceAccount(
				_user.getUserId(), "account" + i, "example@test.com",
				externalReferenceCodes.get(i - 1), serviceContext);
		}

		Organization liferayOrganization =
			_organizationLocalService.getOrganization(
				_user.getCompanyId(), "businessLiferay");
		Organization italyOrganization =
			_organizationLocalService.getOrganization(
				_user.getCompanyId(), "businessItaly");
		Organization chicagoOrganization =
			_organizationLocalService.getOrganization(
				_user.getCompanyId(), "businessChicago");
		Organization losAngelesOrganization =
			_organizationLocalService.getOrganization(
				_user.getCompanyId(), "businessLosAngeles");

		User user1 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "user1");

		User user2 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "user2");

		_organizationLocalService.addUserOrganization(
			user2.getUserId(), italyOrganization);
		_organizationLocalService.addUserOrganization(
			user2.getUserId(), chicagoOrganization);

		User user3 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "user3");

		_organizationLocalService.addUserOrganization(
			user3.getUserId(), losAngelesOrganization);

		User user4 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "user4");

		_organizationLocalService.addUserOrganization(
			user4.getUserId(), chicagoOrganization);

		User user5 = _userLocalService.getUserByScreenName(
			_user.getCompanyId(), "user5");

		_organizationLocalService.addUserOrganization(
			user5.getUserId(), liferayOrganization);

		CommerceAccount commerceAccount1 =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				_user.getCompanyId(), externalReferenceCodes.get(0));

		Assert.assertNotNull(commerceAccount1);

		CommerceAccount commerceAccount2 =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				_user.getCompanyId(), externalReferenceCodes.get(1));

		Assert.assertNotNull(commerceAccount2);

		CommerceAccount commerceAccount3 =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				_user.getCompanyId(), externalReferenceCodes.get(2));

		Assert.assertNotNull(commerceAccount3);

		CommerceAccount commerceAccount4 =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				_user.getCompanyId(), externalReferenceCodes.get(3));

		Assert.assertNotNull(commerceAccount4);

		CommerceAccount commerceAccount5 =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				_user.getCompanyId(), externalReferenceCodes.get(4));

		Assert.assertNotNull(commerceAccount5);

		CommerceAccountTestUtil.addCommerceAccountUserRels(
			commerceAccount2.getCommerceAccountId(),
			new long[] {user2.getUserId()}, serviceContext);
		CommerceAccountTestUtil.addCommerceAccountUserRels(
			commerceAccount3.getCommerceAccountId(),
			new long[] {user3.getUserId()}, serviceContext);

		CommerceAccountTestUtil.addCommerceAccountOrganizationRels(
			commerceAccount2.getCommerceAccountId(),
			new long[] {italyOrganization.getOrganizationId()}, serviceContext);
		CommerceAccountTestUtil.addCommerceAccountOrganizationRels(
			commerceAccount3.getCommerceAccountId(),
			new long[] {chicagoOrganization.getOrganizationId()},
			serviceContext);
		CommerceAccountTestUtil.addCommerceAccountOrganizationRels(
			commerceAccount4.getCommerceAccountId(),
			new long[] {losAngelesOrganization.getOrganizationId()},
			serviceContext);
		CommerceAccountTestUtil.addCommerceAccountOrganizationRels(
			commerceAccount5.getCommerceAccountId(),
			new long[] {losAngelesOrganization.getOrganizationId()},
			serviceContext);

		List<CommerceAccount> userCommerceAccounts1 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				user1.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int userCommerceAccountsCount1 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				user1.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			userCommerceAccounts1.toString(), userCommerceAccounts1.size(),
			userCommerceAccountsCount1);

		Assert.assertEquals(
			userCommerceAccounts1.toString(), 1, userCommerceAccountsCount1);

		CommerceAccount userCommerceAccount1 = userCommerceAccounts1.get(0);

		Assert.assertEquals(
			commerceAccount1.getCommerceAccountId(),
			userCommerceAccount1.getCommerceAccountId());

		List<CommerceAccount> userCommerceAccounts2 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				user2.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int userCommerceAccountsCount2 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				user2.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			userCommerceAccounts2.toString(), userCommerceAccounts2.size(),
			userCommerceAccountsCount2);

		Assert.assertEquals(
			userCommerceAccounts2.toString(), 2, userCommerceAccountsCount2);

		userCommerceAccounts2 = ListUtil.sort(userCommerceAccounts2);

		CommerceAccount userCommerceAccount2a = userCommerceAccounts2.get(0);
		CommerceAccount userCommerceAccount2b = userCommerceAccounts2.get(1);

		Assert.assertEquals(
			commerceAccount2.getCommerceAccountId(),
			userCommerceAccount2a.getCommerceAccountId());
		Assert.assertEquals(
			commerceAccount3.getCommerceAccountId(),
			userCommerceAccount2b.getCommerceAccountId());

		List<CommerceAccount> userCommerceAccounts3 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				user3.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int userCommerceAccountsCount3 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				user3.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			userCommerceAccounts3.toString(), userCommerceAccounts3.size(),
			userCommerceAccountsCount3);

		Assert.assertEquals(
			userCommerceAccounts3.toString(), 3, userCommerceAccountsCount3);

		userCommerceAccounts3 = ListUtil.sort(userCommerceAccounts3);

		CommerceAccount userCommerceAccount3a = userCommerceAccounts3.get(0);
		CommerceAccount userCommerceAccount3b = userCommerceAccounts3.get(1);
		CommerceAccount userCommerceAccount3c = userCommerceAccounts3.get(2);

		Assert.assertEquals(
			commerceAccount3.getCommerceAccountId(),
			userCommerceAccount3a.getCommerceAccountId());
		Assert.assertEquals(
			commerceAccount4.getCommerceAccountId(),
			userCommerceAccount3b.getCommerceAccountId());
		Assert.assertEquals(
			commerceAccount5.getCommerceAccountId(),
			userCommerceAccount3c.getCommerceAccountId());

		List<CommerceAccount> userCommerceAccounts4 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				user4.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int userCommerceAccountsCount4 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				user4.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			userCommerceAccounts4.toString(), userCommerceAccounts4.size(),
			userCommerceAccountsCount4);

		Assert.assertEquals(
			userCommerceAccounts4.toString(), 1, userCommerceAccountsCount4);

		CommerceAccount userCommerceAccount4 = userCommerceAccounts4.get(0);

		Assert.assertEquals(
			commerceAccount3.getCommerceAccountId(),
			userCommerceAccount4.getCommerceAccountId());

		List<CommerceAccount> userCommerceAccounts5 =
			_commerceAccountLocalService.getUserCommerceAccounts(
				user5.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int userCommerceAccountsCount5 =
			_commerceAccountLocalService.getUserCommerceAccountsCount(
				user5.getUserId(),
				CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
				CommerceAccountConstants.SITE_TYPE_B2B, StringPool.BLANK, null);

		Assert.assertEquals(
			userCommerceAccounts5.toString(), userCommerceAccounts5.size(),
			userCommerceAccountsCount5);

		Assert.assertEquals(
			userCommerceAccounts5.toString(), 4, userCommerceAccountsCount5);

		userCommerceAccounts5 = ListUtil.sort(userCommerceAccounts5);

		CommerceAccount userCommerceAccount5a = userCommerceAccounts5.get(0);
		CommerceAccount userCommerceAccount5b = userCommerceAccounts5.get(1);
		CommerceAccount userCommerceAccount5c = userCommerceAccounts5.get(2);
		CommerceAccount userCommerceAccount5d = userCommerceAccounts5.get(3);

		Assert.assertEquals(
			commerceAccount2.getCommerceAccountId(),
			userCommerceAccount5a.getCommerceAccountId());
		Assert.assertEquals(
			commerceAccount3.getCommerceAccountId(),
			userCommerceAccount5b.getCommerceAccountId());
		Assert.assertEquals(
			commerceAccount4.getCommerceAccountId(),
			userCommerceAccount5c.getCommerceAccountId());
		Assert.assertEquals(
			commerceAccount5.getCommerceAccountId(),
			userCommerceAccount5d.getCommerceAccountId());
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private Organization _addOrganization(
			long parentOrganizationId, String name)
		throws PortalException {

		return _organizationLocalService.addOrganization(
			_user.getUserId(), parentOrganizationId, name, false);
	}

	private void _addOrganizationSet(String baseName) throws Exception {
		try {
			Organization liferayOrganization = _addOrganization(
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				baseName + "Liferay");

			_addOrganization(
				liferayOrganization.getOrganizationId(), baseName + "Italy");

			Organization usaOrganization = _addOrganization(
				liferayOrganization.getOrganizationId(), baseName + "USA");

			_addOrganization(
				usaOrganization.getOrganizationId(), baseName + "Chicago");
			_addOrganization(
				usaOrganization.getOrganizationId(), baseName + "LosAngeles");
		}
		catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	private List<String> _getExternalReferenceCodes(int count) {
		List<String> externalReferenceCodes = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			externalReferenceCodes.add(RandomTestUtil.randomString());
		}

		return externalReferenceCodes;
	}

	private ServiceContext _getServiceContext() {
		return ServiceContextTestUtil.getServiceContext(
			_user.getCompanyId(), _user.getGroupId(), _user.getUserId());
	}

	private static User _user;

	@Inject
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private UserLocalService _userLocalService;

}