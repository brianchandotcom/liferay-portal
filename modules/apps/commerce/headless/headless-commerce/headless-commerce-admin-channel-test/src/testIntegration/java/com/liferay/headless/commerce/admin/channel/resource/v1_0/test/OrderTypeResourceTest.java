/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierLocalService;
import com.liferay.commerce.payment.test.util.TestCommercePaymentMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.OrderType;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class OrderTypeResourceTest extends BaseOrderTypeResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), commerceCurrency.getCode());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected OrderType randomOrderType() {
		return new OrderType() {
			{
				id = RandomTestUtil.randomLong();
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
			}
		};
	}

	@Override
	protected OrderType
			testGetPaymentMethodGroupRelOrderTypeOrderType_addOrderType()
		throws Exception {

		return _toOrderType(_addCommerceOrderType());
	}

	@Override
	protected Long
			testGetPaymentMethodGroupRelOrderTypeOrderType_getPaymentMethodGroupRelOrderTypeId()
		throws Exception {

		return _getCommercePaymentMethodGroupRelQualifierId();
	}

	@Override
	protected OrderType
			testGetShippingFixedOptionOrderTypeOrderType_addOrderType()
		throws Exception {

		return _toOrderType(_addCommerceOrderType());
	}

	@Override
	protected Long
			testGetShippingFixedOptionOrderTypeOrderType_getShippingFixedOptionOrderTypeId()
		throws Exception {

		return _getCommerceShippingFixedOptionQualifierId();
	}

	@Override
	protected Long
			testGraphQLGetPaymentMethodGroupRelOrderTypeOrderType_getPaymentMethodGroupRelOrderTypeId()
		throws Exception {

		return _getCommercePaymentMethodGroupRelQualifierId();
	}

	@Override
	protected Long
			testGraphQLGetShippingFixedOptionOrderTypeOrderType_getShippingFixedOptionOrderTypeId()
		throws Exception {

		return _getCommerceShippingFixedOptionQualifierId();
	}

	@Override
	protected OrderType testGraphQLOrderType_addOrderType() throws Exception {
		return _toOrderType(_addCommerceOrderType());
	}

	private CommerceOrderType _addCommerceOrderType() throws Exception {
		if (_commerceOrderType != null) {
			return _commerceOrderType;
		}

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		_commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean(), displayDateConfig.getMonth(),
				displayDateConfig.getDay(), displayDateConfig.getYear(),
				displayDateConfig.getHour(), displayDateConfig.getMinute(), 0,
				expirationDateConfig.getMonth(), expirationDateConfig.getDay(),
				expirationDateConfig.getYear(), expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(), true, _serviceContext);

		return _commerceOrderType;
	}

	private long _getCommercePaymentMethodGroupRelQualifierId()
		throws Exception {

		if (_commercePaymentMethodGroupRel == null) {
			_commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelLocalService.
					addCommercePaymentMethodGroupRel(
						_user.getUserId(), _commerceChannel.getGroupId(),
						RandomTestUtil.randomLocaleStringMap(),
						RandomTestUtil.randomLocaleStringMap(), true, null,
						TestCommercePaymentMethod.KEY, 99, null);
		}

		OrderType orderType = _toOrderType(_addCommerceOrderType());

		if (_commercePaymentMethodGroupRelQualifier == null) {
			_commercePaymentMethodGroupRelQualifier =
				_commercePaymentMethodGroupRelQualifierLocalService.
					addCommercePaymentMethodGroupRelQualifier(
						_user.getUserId(), CommerceOrderType.class.getName(),
						orderType.getId(),
						_commercePaymentMethodGroupRel.
							getCommercePaymentMethodGroupRelId());
		}

		return _commercePaymentMethodGroupRelQualifier.
			getCommercePaymentMethodGroupRelQualifierId();
	}

	private long _getCommerceShippingFixedOptionQualifierId() throws Exception {
		OrderType orderType = _toOrderType(_addCommerceOrderType());

		if (_commerceShippingFixedOption == null) {
			_commerceShippingFixedOption =
				_commerceShippingFixedOptionLocalService.
					addCommerceShippingFixedOption(
						_user.getUserId(), _commerceChannel.getGroupId(),
						RandomTestUtil.nextLong(),
						BigDecimal.valueOf(RandomTestUtil.nextDouble()),
						RandomTestUtil.randomLocaleStringMap(),
						RandomTestUtil.randomString(),
						RandomTestUtil.randomLocaleStringMap(),
						RandomTestUtil.nextDouble());
		}

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				_commerceShippingFixedOptionQualifierLocalService.
					addCommerceShippingFixedOptionQualifier(
						_user.getUserId(), CommerceOrderType.class.getName(),
						orderType.getId(),
						_commerceShippingFixedOption.
							getCommerceShippingFixedOptionId());

		return commerceShippingFixedOptionQualifier.
			getCommerceShippingFixedOptionQualifierId();
	}

	private OrderType _toOrderType(CommerceOrderType commerceOrderType) {
		return new OrderType() {
			{
				id = commerceOrderType.getCommerceOrderTypeId();
				name = LanguageUtils.getLanguageIdMap(
					commerceOrderType.getNameMap());
			}
		};
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceOrderType _commerceOrderType;

	@Inject
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	@DeleteAfterTestRun
	private CommercePaymentMethodGroupRel _commercePaymentMethodGroupRel;

	@Inject
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@DeleteAfterTestRun
	private CommercePaymentMethodGroupRelQualifier
		_commercePaymentMethodGroupRelQualifier;

	@Inject
	private CommercePaymentMethodGroupRelQualifierLocalService
		_commercePaymentMethodGroupRelQualifierLocalService;

	@DeleteAfterTestRun
	private CommerceShippingFixedOption _commerceShippingFixedOption;

	@Inject
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Inject
	private CommerceShippingFixedOptionQualifierLocalService
		_commerceShippingFixedOptionQualifierLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}