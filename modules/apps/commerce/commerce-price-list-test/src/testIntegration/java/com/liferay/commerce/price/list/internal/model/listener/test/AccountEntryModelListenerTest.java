/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.model.listener.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.test.util.price.list.CommercePriceListTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lianne Louie
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnBeforeRemove() throws Exception {
		User user = UserTestUtil.addUser();
		Group group = GroupTestUtil.addGroup();

		AccountEntry accountEntry =
			CommerceAccountTestUtil.addPersonAccountEntry(
				user.getUserId(),
				ServiceContextTestUtil.getServiceContext(
					group.getCompanyId(), group.getGroupId(),
					user.getUserId()));

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addAccountPriceList(
				accountEntry.getAccountEntryGroupId(),
				accountEntry.getAccountEntryId(),
				CommercePriceListConstants.TYPE_PRICE_LIST);

		List<CommercePriceListAccountRel> commercePriceListAccountRels =
			_commercePriceListAccountRelLocalService.
				getCommercePriceListAccountRels(
					commercePriceList.getCommercePriceListId());

		Assert.assertEquals(
			commercePriceListAccountRels.toString(), 1,
			commercePriceListAccountRels.size());

		_accountEntryLocalService.deleteAccountEntry(accountEntry);

		commercePriceListAccountRels =
			_commercePriceListAccountRelLocalService.
				getCommercePriceListAccountRels(
					commercePriceList.getCommercePriceListId());

		Assert.assertEquals(
			commercePriceListAccountRels.toString(), 0,
			commercePriceListAccountRels.size());
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private CommercePriceListAccountRelLocalService
		_commercePriceListAccountRelLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

}