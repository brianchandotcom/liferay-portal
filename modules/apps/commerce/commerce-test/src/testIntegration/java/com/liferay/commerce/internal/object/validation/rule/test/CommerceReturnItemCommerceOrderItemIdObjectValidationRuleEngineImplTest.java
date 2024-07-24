/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.validation.rule.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
public class
	CommerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImplTest
		extends BaseObjectValidationRuleEngineImplTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Ignore
	@Test
	public void test() throws Exception {
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
				accountEntry.getAccountEntryId()
			).put(
				"r_commerceOrderToCommerceReturns_commerceOrderId",
				commerceOrder.getCommerceOrderId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Map<String, Object> results = _objectValidationRuleEngineImpl.execute(
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

		results = _objectValidationRuleEngineImpl.execute(
			HashMapBuilder.<String, Object>put(
				"entryDTO",
				HashMapBuilder.put(
					"properties",
					HashMapBuilder.put(
						"r_commerceOrderItemToCommerceReturnItems_" +
							"commerceOrderItemId",
						String.valueOf(
							commerceOrderItem.getCommerceOrderItemId())
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

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.commerce.internal.object.validation.rule.CommerceReturnItemCommerceOrderItemIdObjectValidationRuleEngineImpl"
	)
	private ObjectValidationRuleEngine _objectValidationRuleEngineImpl;

}