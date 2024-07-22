/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceReturnConstants;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@FeatureFlags("LPD-10562")
@RunWith(Arquillian.class)
public class CommerceReturnReturnStatusObjectValidationRuleEngineImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		PrincipalThreadLocal.setName(_user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_commerceChannel = CommerceChannelLocalServiceUtil.addCommerceChannel(
			null, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			_group.getGroupId(), "Test Channel",
			CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
			_commerceCurrency.getCode(), _serviceContext);

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()}, null,
			_serviceContext);

		_commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_group.getGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		_commerceOrder = CommerceTestUtil.addCheckoutDetailsToCommerceOrder(
			_commerceOrder, _user.getUserId(), false);

		_commerceOrder = _commerceOrderEngine.checkoutCommerceOrder(
			_commerceOrder, _user.getUserId());

		_commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
			_commerceOrder, CommerceOrderConstants.ORDER_STATUS_PROCESSING,
			_user.getUserId(), true);
	}

	@Test
	public void testCommerceReturnReturnStatusObjectValidationRuleEngineImpl() {
		Map<String, Object> results =
			_commerceReturnReturnStatusObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.put(
						"properties",
						HashMapBuilder.put(
							"r_commerceOrderToCommerceReturns_commerceOrderId",
							RandomTestUtil.randomLong()
						).build()
					).build()
				).build(),
				null);

		Assert.assertFalse(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));

		results =
			_commerceReturnReturnStatusObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.put(
						"properties",
						HashMapBuilder.put(
							"returnStatus",
							HashMapBuilder.put(
								"key",
								CommerceReturnConstants.RETURN_STATUS_DRAFT
							).build()
						).build()
					).build()
				).build(),
				null);

		Assert.assertTrue(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));
	}

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderEngine _commerceOrderEngine;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.object.validation.rule.CommerceReturnReturnStatusObjectValidationRuleEngineImpl"
	)
	private ObjectValidationRuleEngine
		_commerceReturnReturnStatusObjectValidationRuleEngineImpl;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}