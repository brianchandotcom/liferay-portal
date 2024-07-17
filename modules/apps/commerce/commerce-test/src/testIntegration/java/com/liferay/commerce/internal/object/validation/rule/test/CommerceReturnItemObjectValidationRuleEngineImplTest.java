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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@FeatureFlags("LPD-10562")
@RunWith(Arquillian.class)
public class CommerceReturnItemObjectValidationRuleEngineImplTest {

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

		CPInstance cpInstance = CPTestUtil.addCPInstance(_group.getGroupId());

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			cpInstance.getCPDefinition());

		_commerceOrderItem = _commerceOrder.getCommerceOrderItems(
		).get(
			0
		);
	}

	@Test
	public void testCommerceReturnItemAccountEntryIdObjectValidationRuleEngineImpl()
		throws Exception {

		Map<String, Object> results =
			_commerceReturnItemAccountEntryIdObjectValidationRuleEngineImpl.
				execute(
					HashMapBuilder.<String, Object>put(
						"entryDTO",
						HashMapBuilder.put(
							"properties",
							HashMapBuilder.put(
								"r_accountToCommerceReturnItems_accountEntryId",
								RandomTestUtil.randomLong()
							).put(
								"r_commerceOrderItemToCommerceReturnItems_" +
									"commerceOrderItemId",
								RandomTestUtil.randomLong()
							).build()
						).build()
					).build(),
					null);

		Assert.assertFalse(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));

		results =
			_commerceReturnItemAccountEntryIdObjectValidationRuleEngineImpl.
				execute(
					HashMapBuilder.<String, Object>put(
						"entryDTO",
						HashMapBuilder.put(
							"properties",
							HashMapBuilder.put(
								"r_accountToCommerceReturnItems_accountEntryId",
								_accountEntry.getAccountEntryId()
							).put(
								"r_commerceOrderItemToCommerceReturnItems_" +
									"commerceOrderItemId",
								_commerceOrderItem.getCommerceOrderItemId()
							).build()
						).build()
					).build(),
					null);

		Assert.assertTrue(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));
	}

	@Test
	public void testCommerceReturnItemAuthorizedObjectValidationRuleEngineImpl() {
		Map<String, Object> results =
			_commerceReturnItemAuthorizedObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.put(
						"properties",
						HashMapBuilder.put(
							"authorized", 2
						).put(
							"quantity", 1
						).build()
					).build()
				).build(),
				null);

		Assert.assertFalse(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));

		results =
			_commerceReturnItemAuthorizedObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.put(
						"properties",
						HashMapBuilder.put(
							"authorized", 0
						).put(
							"quantity", 1
						).build()
					).build()
				).build(),
				null);

		Assert.assertTrue(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));
	}

	@Ignore
	@Test
	public void testCommerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl()
		throws Exception {

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		ObjectDefinition commerceReturnObjectDefinition = null;

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			if (StringUtil.equals(
					objectDefinition.getExternalReferenceCode(),
					"L_COMMERCE_RETURN")) {

				commerceReturnObjectDefinition = objectDefinition;

				break;
			}
		}

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			commerceReturnObjectDefinition.getUserId(), 0,
			commerceReturnObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"r_accountToCommerceReturns_accountEntryId",
				_accountEntry.getAccountEntryId()
			).put(
				"r_commerceOrderToCommerceReturns_commerceOrderId",
				_commerceOrder.getCommerceOrderId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Map<String, Object> results =
			_commerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl.
				execute(
					HashMapBuilder.<String, Object>put(
						"entryDTO",
						HashMapBuilder.put(
							"properties",
							HashMapBuilder.<String, Object>put(
								"r_commerceOrderItemToCommerceReturnItems_" +
									"commerceOrderItemId",
								RandomTestUtil.randomLong()
							).put(
								"r_commerceReturnToCommerceReturnItems_c_" +
									"commerceReturnERC",
								objectEntry.getExternalReferenceCode()
							).build()
						).build()
					).build(),
					null);

		Assert.assertFalse(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));

		results =
			_commerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl.
				execute(
					HashMapBuilder.<String, Object>put(
						"entryDTO",
						HashMapBuilder.put(
							"properties",
							HashMapBuilder.put(
								"r_commerceOrderItemToCommerceReturnItems_" +
									"commerceOrderItemId",
								String.valueOf(
									_commerceOrderItem.getCommerceOrderItemId())
							).put(
								"r_commerceReturnToCommerceReturnItems_c_" +
									"commerceReturnERC",
								objectEntry.getExternalReferenceCode()
							).build()
						).build()
					).build(),
					null);

		Assert.assertTrue(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));
	}

	@Test
	public void testCommerceReturnItemQuantityObjectValidationRuleEngineImpl()
		throws Exception {

		Map<String, Object> results =
			_commerceReturnItemQuantityObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.<String, Object>put(
						"properties",
						HashMapBuilder.<String, Object>put(
							"quantity", -1
						).put(
							"r_commerceOrderItemToCommerceReturnItems_" +
								"commerceOrderItemId",
							_commerceOrderItem.getCommerceOrderItemId()
						).build()
					).build()
				).build(),
				null);

		Assert.assertFalse(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));

		results =
			_commerceReturnItemQuantityObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.<String, Object>put(
						"properties",
						HashMapBuilder.<String, Object>put(
							"quantity", 100
						).put(
							"r_commerceOrderItemToCommerceReturnItems_" +
								"commerceOrderItemId",
							_commerceOrderItem.getCommerceOrderItemId()
						).build()
					).build()
				).build(),
				null);

		Assert.assertFalse(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));

		results =
			_commerceReturnItemQuantityObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.<String, Object>put(
						"properties",
						HashMapBuilder.<String, Object>put(
							"quantity", 1
						).put(
							"r_commerceOrderItemToCommerceReturnItems_" +
								"commerceOrderItemId",
							_commerceOrderItem.getCommerceOrderItemId()
						).build()
					).build()
				).build(),
				null);

		Assert.assertTrue(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));
	}

	@Test
	public void testCommerceReturnItemReceivedObjectValidationRuleEngineImpl() {
		Map<String, Object> results =
			_commerceReturnItemReceivedObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.put(
						"properties",
						HashMapBuilder.put(
							"authorized", 0
						).put(
							"received", 2
						).build()
					).build()
				).build(),
				null);

		Assert.assertFalse(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));

		results =
			_commerceReturnItemReceivedObjectValidationRuleEngineImpl.execute(
				HashMapBuilder.<String, Object>put(
					"entryDTO",
					HashMapBuilder.put(
						"properties",
						HashMapBuilder.put(
							"authorized", 1
						).put(
							"received", 1
						).build()
					).build()
				).build(),
				null);

		Assert.assertTrue(
			GetterUtil.getBoolean(results.get("validationCriteriaMet")));
	}

	private static User _user;

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

	@DeleteAfterTestRun
	private CommerceOrderItem _commerceOrderItem;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.object.validation.rule.CommerceReturnItemAccountEntryIdObjectValidationRuleEngineImpl"
	)
	private ObjectValidationRuleEngine
		_commerceReturnItemAccountEntryIdObjectValidationRuleEngineImpl;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.object.validation.rule.CommerceReturnItemAuthorizedObjectValidationRuleEngineImpl"
	)
	private ObjectValidationRuleEngine
		_commerceReturnItemAuthorizedObjectValidationRuleEngineImpl;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.object.validation.rule.CommerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl"
	)
	private ObjectValidationRuleEngine
		_commerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.object.validation.rule.CommerceReturnItemQuantityObjectValidationRuleEngineImpl"
	)
	private ObjectValidationRuleEngine
		_commerceReturnItemQuantityObjectValidationRuleEngineImpl;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.object.validation.rule.CommerceReturnItemReceivedObjectValidationRuleEngineImpl"
	)
	private ObjectValidationRuleEngine
		_commerceReturnItemReceivedObjectValidationRuleEngineImpl;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ServiceContext _serviceContext;

}