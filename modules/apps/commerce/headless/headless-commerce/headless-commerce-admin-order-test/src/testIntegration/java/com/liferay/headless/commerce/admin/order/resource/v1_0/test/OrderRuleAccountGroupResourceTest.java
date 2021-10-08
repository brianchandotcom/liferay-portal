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
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.order.rule.service.COREntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collection;

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

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		_corEntry = _corEntryLocalService.addCOREntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomString(),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			true, RandomTestUtil.randomString(), 0,
			RandomTestUtil.randomString(), StringPool.BLANK, _serviceContext);
	}

	@Override
	@Test
	public void testDeleteOrderRuleAccountGroup() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLDeleteOrderRuleAccountGroup() throws Exception {
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected OrderRuleAccountGroup randomOrderRuleAccountGroup()
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupLocalService.addCommerceAccountGroup(
				_user.getCompanyId(), RandomTestUtil.randomString(), 0, false,
				null, _serviceContext);

		return new OrderRuleAccountGroup() {
			{
				accountGroupExternalReferenceCode =
					commerceAccountGroup.getExternalReferenceCode();
				accountGroupId =
					commerceAccountGroup.getCommerceAccountGroupId();
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

		return _addOrderRuleAccountGroup(orderRuleAccountGroup);
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

		return _addOrderRuleAccountGroup(orderRuleAccountGroup);
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

		return _addOrderRuleAccountGroup(orderRuleAccountGroup);
	}

	@Override
	protected OrderRuleAccountGroup
			testPostOrderRuleIdOrderRuleAccountGroup_addOrderRuleAccountGroup(
				OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		return _addOrderRuleAccountGroup(orderRuleAccountGroup);
	}

	private OrderRuleAccountGroup _addOrderRuleAccountGroup(
			OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		return _toOrderRuleAccountGroup(
			_corEntryRelLocalService.addCOREntryRel(
				_user.getUserId(), AccountGroup.class.getName(),
				orderRuleAccountGroup.getAccountGroupId(),
				orderRuleAccountGroup.getOrderRuleId()));
	}

	private OrderRuleAccountGroup _toOrderRuleAccountGroup(
			COREntryRel corEntryRel)
		throws Exception {

		CommerceAccountGroup commerceAccountGroup =
			_commerceAccountGroupLocalService.getCommerceAccountGroup(
				corEntryRel.getClassPK());
		COREntry corEntry = _corEntryLocalService.fetchCOREntry(
			corEntryRel.getCOREntryId());

		return new OrderRuleAccountGroup() {
			{
				accountGroupExternalReferenceCode =
					commerceAccountGroup.getExternalReferenceCode();
				accountGroupId =
					commerceAccountGroup.getCommerceAccountGroupId();
				orderRuleAccountGroupId = corEntryRel.getCOREntryRelId();
				orderRuleExternalReferenceCode =
					corEntry.getExternalReferenceCode();
				orderRuleId = corEntry.getCOREntryId();
			}
		};
	}

	@Inject
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	private COREntry _corEntry;

	@Inject
	private COREntryLocalService _corEntryLocalService;

	@Inject
	private COREntryRelLocalService _corEntryRelLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}