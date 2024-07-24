/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;

/**
 * @author Danny Situ
 */
public abstract class BaseObjectValidationRuleEngineImplTestCase {

	@Before
	public void setUp() throws Exception {
		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		group = GroupTestUtil.addGroup();

		user = UserTestUtil.addUser();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			group.getCompanyId());

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());

		commerceChannel = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, group.getGroupId(),
			"Test Channel", CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			commerceCurrency.getCode(), serviceContext);

		accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {user.getUserId()}, null,
			serviceContext);

		commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			group.getGroupId(), user.getUserId(),
			accountEntry.getAccountEntryId(),
			commerceCurrency.getCommerceCurrencyId());

		commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			commerceOrder, user.getUserId(), false);

		commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			commerceOrder, user.getUserId());

		commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			user.getUserId(), true);

		CPInstance cpInstance = CPTestUtil.addCPInstance(group.getGroupId());

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			cpInstance.getCPDefinition());

		commerceOrderItem = commerceOrder.getCommerceOrderItems(
		).get(
			0
		);
	}

	@DeleteAfterTestRun
	protected AccountEntry accountEntry;

	@DeleteAfterTestRun
	protected CommerceChannel commerceChannel;

	@DeleteAfterTestRun
	protected CommerceCurrency commerceCurrency;

	@DeleteAfterTestRun
	protected CommerceOrder commerceOrder;

	@DeleteAfterTestRun
	protected CommerceOrderItem commerceOrderItem;

	@DeleteAfterTestRun
	protected Group group;

	protected ServiceContext serviceContext;

	@DeleteAfterTestRun
	protected User user;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

}