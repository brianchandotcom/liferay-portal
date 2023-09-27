/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.constants.COREntryConstants;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountResourceTest extends BaseAccountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				user.getUserId());

		_accountEntry = _accountEntryLocalService.addAccountEntry(
			user.getUserId(), 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null, null,
			"business", 1, serviceContext);

		COREntry corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), user.getUserId(), true,
			RandomTestUtil.randomString(), 1, 1, 2022, 12, 0, 0, 0, 0, 0, 0,
			true, RandomTestUtil.randomString(), RandomTestUtil.nextInt(),
			COREntryConstants.TYPE_MINIMUM_ORDER_AMOUNT, null, serviceContext);

		_corEntryRel = _corEntryRelLocalService.addCOREntryRel(
			user.getUserId(), AccountEntry.class.getName(),
			_accountEntry.getAccountEntryId(), corEntry.getCOREntryId());
	}

	@Ignore
	@Override
	@Test
	public void testGetOrderByExternalReferenceCodeAccount() throws Exception {
		super.testGetOrderByExternalReferenceCodeAccount();
	}

	@Ignore
	@Override
	@Test
	public void testGetOrderIdAccount() throws Exception {
		super.testGetOrderIdAccount();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetOrderByExternalReferenceCodeAccount()
		throws Exception {

		super.testGraphQLGetOrderByExternalReferenceCodeAccount();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetOrderIdAccount() throws Exception {
		super.testGraphQLGetOrderIdAccount();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"emailAddress", "name"};
	}

	@Override
	protected Account randomAccount() throws Exception {
		return new Account() {
			{
				emailAddress =
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						"@liferay.com";
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				logoId = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				root = true;
				type = 2;
			}
		};
	}

	@Override
	protected Account testGetOrderByExternalReferenceCodeAccount_addAccount()
		throws Exception {

		return _toAccount();
	}

	@Override
	protected Account testGetOrderIdAccount_addAccount() throws Exception {
		return _toAccount();
	}

	@Override
	protected Account testGetOrderRuleAccountAccount_addAccount()
		throws Exception {

		return _toAccount();
	}

	@Override
	protected Long testGetOrderRuleAccountAccount_getOrderRuleAccountId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	@Override
	protected Account testGraphQLAccount_addAccount() throws Exception {
		return _toAccount();
	}

	@Override
	protected Long testGraphQLGetOrderRuleAccountAccount_getOrderRuleAccountId()
		throws Exception {

		return _corEntryRel.getCOREntryRelId();
	}

	private Account _toAccount() throws Exception {
		return new Account() {
			{
				emailAddress = _accountEntry.getEmailAddress();
				externalReferenceCode =
					_accountEntry.getExternalReferenceCode();
				id = _accountEntry.getAccountEntryId();
				name = _accountEntry.getName();
				root = true;
				type = 2;
			}
		};
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	private COREntryRel _corEntryRel;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

}