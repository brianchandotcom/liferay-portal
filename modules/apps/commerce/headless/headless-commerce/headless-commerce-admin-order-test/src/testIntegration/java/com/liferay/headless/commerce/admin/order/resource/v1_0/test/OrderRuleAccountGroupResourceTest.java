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

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
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
public class OrderRuleAccountGroupResourceTest
	extends BaseOrderRuleAccountGroupResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(), 1, 1,
			2022, 12, 0, 0, 0, 0, 0, 0, true, RandomTestUtil.randomString(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				_user.getUserId()));
	}

	@Override
	@Test
	public void testDeleteOrderRuleAccountGroup() throws Exception {
		OrderRuleAccountGroup orderRuleAccountGroup = _addCOREntryRel(
			randomOrderRuleAccountGroup());

		assertHttpResponseStatusCode(
			204,
			orderRuleAccountGroupResource.
				deleteOrderRuleAccountGroupHttpResponse(
					orderRuleAccountGroup.getOrderRuleAccountGroupId()));

		assertHttpResponseStatusCode(
			404,
			orderRuleAccountGroupResource.
				deleteOrderRuleAccountGroupHttpResponse(
					orderRuleAccountGroup.getOrderRuleAccountGroupId()));

		assertHttpResponseStatusCode(
			404,
			orderRuleAccountGroupResource.
				deleteOrderRuleAccountGroupHttpResponse(
					orderRuleAccountGroup.getOrderRuleAccountGroupId()));
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderRuleAccountGroup() throws Exception {
		OrderRuleAccountGroup orderRuleAccountGroup = _addCOREntryRel(
			randomOrderRuleAccountGroup());

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrderRuleAccountGroup",
						HashMapBuilder.<String, Object>put(
							"orderRuleAccountGroupId",
							orderRuleAccountGroup.getOrderRuleAccountGroupId()
						).build())),
				"JSONObject/data", "Object/deleteOrderRuleAccountGroup"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"orderRuleAccountGroup",
					HashMapBuilder.<String, Object>put(
						"orderRuleAccountGroupId",
						orderRuleAccountGroup.getOrderRuleAccountGroupId()
					).build(),
					new GraphQLField("orderRuleAccountGroupId"))),
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
	protected OrderRuleAccountGroup randomOrderRuleAccountGroup()
		throws Exception {

		AccountGroup localAccountGroup =
			AccountGroupLocalServiceUtil.addAccountGroup(
				_user.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

		_accountGroups.add(localAccountGroup);

		return new OrderRuleAccountGroup() {
			{
				accountGroupExternalReferenceCode =
					localAccountGroup.getExternalReferenceCode();
				accountGroupId = localAccountGroup.getAccountGroupId();
				orderRuleAccountGroupId = RandomTestUtil.randomLong();
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
			}
		};
	}

	@Override
	protected OrderRuleAccountGroup
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				String externalReferenceCode,
				OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		return _addCOREntryRel(orderRuleAccountGroup);
	}

	@Override
	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_getExternalReferenceCode()
		throws Exception {

		return _corEntry.getExternalReferenceCode();
	}

	@Override
	protected OrderRuleAccountGroup
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				Long id, OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		return _addCOREntryRel(orderRuleAccountGroup);
	}

	@Override
	protected Long testGetOrderRuleIdOrderRuleAccountGroupsPage_getId()
		throws Exception {

		return _corEntry.getCOREntryId();
	}

	@Override
	protected OrderRuleAccountGroup
			testPostOrderRuleByExternalReferenceCodeOrderRuleAccountGroup_addOrderRuleAccountGroup(
				OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		return _addCOREntryRel(orderRuleAccountGroup);
	}

	@Override
	protected OrderRuleAccountGroup
			testPostOrderRuleIdOrderRuleAccountGroup_addOrderRuleAccountGroup(
				OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		return _addCOREntryRel(orderRuleAccountGroup);
	}

	private OrderRuleAccountGroup _addCOREntryRel(
			OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		COREntryRel corEntryRel = _corEntryRelLocalService.addCOREntryRel(
			_user.getUserId(), AccountGroup.class.getName(),
			orderRuleAccountGroup.getAccountGroupId(),
			orderRuleAccountGroup.getOrderRuleId());

		AccountGroup localAccountGroup =
			_accountGroupLocalService.getAccountGroup(corEntryRel.getClassPK());

		return new OrderRuleAccountGroup() {
			{
				accountGroupExternalReferenceCode =
					localAccountGroup.getExternalReferenceCode();
				accountGroupId = localAccountGroup.getAccountGroupId();
				orderRuleAccountGroupId = corEntryRel.getCOREntryRelId();
				orderRuleExternalReferenceCode =
					_corEntry.getExternalReferenceCode();
				orderRuleId = _corEntry.getCOREntryId();
			}
		};
	}

	@Inject
	private static AccountGroupLocalService _accountGroupLocalService;

	@Inject
	private static COREntryLocalService _corEntryLocalService;

	@Inject
	private static COREntryRelLocalService _corEntryRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountGroup> _accountGroups = new ArrayList<>();

	@DeleteAfterTestRun
	private COREntry _corEntry;

	@DeleteAfterTestRun
	private User _user;

}