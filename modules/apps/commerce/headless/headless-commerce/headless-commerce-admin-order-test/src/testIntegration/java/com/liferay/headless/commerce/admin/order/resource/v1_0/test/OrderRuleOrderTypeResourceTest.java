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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalServiceUtil;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleOrderType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class OrderRuleOrderTypeResourceTest
	extends BaseOrderRuleOrderTypeResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(), 1, 1,
			2022, 12, 0, 0, 0, 0, 0, 0, true, RandomTestUtil.randomString(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);
	}

	@Override
	@Test
	public void testDeleteOrderRuleOrderType() throws Exception {
		OrderRuleOrderType orderRuleOrderType = _addCOREntryRel(
			randomOrderRuleOrderType());

		assertHttpResponseStatusCode(
			204,
			orderRuleOrderTypeResource.deleteOrderRuleOrderTypeHttpResponse(
				orderRuleOrderType.getOrderRuleOrderTypeId()));

		assertHttpResponseStatusCode(
			404,
			orderRuleOrderTypeResource.deleteOrderRuleOrderTypeHttpResponse(
				orderRuleOrderType.getOrderRuleOrderTypeId()));

		assertHttpResponseStatusCode(
			404,
			orderRuleOrderTypeResource.deleteOrderRuleOrderTypeHttpResponse(
				orderRuleOrderType.getOrderRuleOrderTypeId()));
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderRuleOrderType() throws Exception {
		OrderRuleOrderType orderRuleOrderType = _addCOREntryRel(
			randomOrderRuleOrderType());

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrderRuleOrderType",
						HashMapBuilder.<String, Object>put(
							"orderRuleOrderTypeId",
							orderRuleOrderType.getOrderRuleOrderTypeId()
						).build())),
				"JSONObject/data", "Object/deleteOrderRuleOrderType"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"deleteOrderRuleOrderType",
					HashMapBuilder.<String, Object>put(
						"orderRuleOrderTypeId",
						orderRuleOrderType.getOrderRuleOrderTypeId()
					).build(),
					new GraphQLField("orderRuleOrderTypeId"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		try {
			return super.getEntityFields();
		}
		catch (NullPointerException nullPointerException) {
			Map<String, EntityField> entityFieldsMap = new HashMap<>();

			return entityFieldsMap.values();
		}
	}

	@Override
	protected OrderRuleOrderType randomOrderRuleOrderType() throws Exception {
		CommerceOrderType commerceOrderType =
			CommerceOrderTypeLocalServiceUtil.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean(), 1, 1, 2022, 12, 0, 0, 0, 0, 0,
				0, 0, true, _serviceContext);

		_commerceOrderTypes.add(commerceOrderType);

		return new OrderRuleOrderType() {
			{
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
				orderRuleOrderTypeId = RandomTestUtil.randomLong();
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
			}
		};
	}

	@Override
	protected OrderRuleOrderType
			testGetOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage_addOrderRuleOrderType(
				String externalReferenceCode,
				OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addCOREntryRel(orderRuleOrderType);
	}

	@Override
	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage_getExternalReferenceCode()
		throws Exception {

		return _corEntry.getExternalReferenceCode();
	}

	@Override
	protected OrderRuleOrderType
			testGetOrderRuleIdOrderRuleOrderTypesPage_addOrderRuleOrderType(
				Long id, OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addCOREntryRel(orderRuleOrderType);
	}

	@Override
	protected Long testGetOrderRuleIdOrderRuleOrderTypesPage_getId()
		throws Exception {

		return _corEntry.getCOREntryId();
	}

	@Override
	protected OrderRuleOrderType
			testPostOrderRuleByExternalReferenceCodeOrderRuleOrderType_addOrderRuleOrderType(
				OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addCOREntryRel(orderRuleOrderType);
	}

	@Override
	protected OrderRuleOrderType
			testPostOrderRuleIdOrderRuleOrderType_addOrderRuleOrderType(
				OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		return _addCOREntryRel(orderRuleOrderType);
	}

	private OrderRuleOrderType _addCOREntryRel(
			OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.getCommerceOrderType(
				orderRuleOrderType.getOrderTypeId());

		COREntryRel corEntryRel = _corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), CommerceOrderType.class.getName(),
			orderRuleOrderType.getOrderTypeId(),
			orderRuleOrderType.getOrderRuleId());

		return new OrderRuleOrderType() {
			{
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
				orderRuleOrderTypeId = corEntryRel.getCOREntryRelId();
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
			}
		};
	}

	@Inject
	private static CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@Inject
	private static COREntryLocalService _corEntryLocalService;

	@Inject
	private static COREntryRelLocalService _corEntryRelLocalService;

	@DeleteAfterTestRun
	private final List<CommerceOrderType> _commerceOrderTypes =
		new ArrayList<>();

	@DeleteAfterTestRun
	private COREntry _corEntry;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}