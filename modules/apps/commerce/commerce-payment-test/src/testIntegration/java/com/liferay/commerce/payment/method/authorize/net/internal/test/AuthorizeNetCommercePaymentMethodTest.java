/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.authorize.net.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.method.CommercePaymentMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Danny Situ
 */
@RunWith(Arquillian.class)
public class AuthorizeNetCommercePaymentMethodTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testProcessPayment() throws Exception {
		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(_group.getCompanyId());

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			_group.getGroupId(), commerceCurrency.getCode());

		CommerceOrder commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			_user.getUserId(), commerceChannel.getGroupId(), commerceCurrency);

		CommerceAddress billingCommerceAddress =
			CommerceTestUtil.addUserCommerceAddress(
				commerceChannel.getGroupId(), _user.getUserId());

		CommerceAddress shippingCommerceAddress =
			CommerceTestUtil.addUserCommerceAddress(
				commerceChannel.getGroupId(), _user.getUserId());

		commerceOrder.setBillingAddressId(
			billingCommerceAddress.getCommerceAddressId());
		commerceOrder.setShippingAddressId(
			shippingCommerceAddress.getCommerceAddressId());
		commerceOrder.setTotal(new BigDecimal(RandomTestUtil.nextDouble()));

		commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			commerceOrder);

		Object transactionRequestType = ReflectionTestUtil.invoke(
			_commercePaymentMethod, "_getTransactionRequestType",
			new Class<?>[] {CommerceOrder.class}, commerceOrder);

		Object customerAddressType = ReflectionTestUtil.getFieldValue(
			transactionRequestType, "billTo");

		_assertNameAndAddressType(customerAddressType, billingCommerceAddress);

		Assert.assertEquals(
			billingCommerceAddress.getPhoneNumber(),
			ReflectionTestUtil.getFieldValue(
				customerAddressType, "phoneNumber"));

		Object nameAndAddressType = ReflectionTestUtil.getFieldValue(
			transactionRequestType, "shipTo");

		_assertNameAndAddressType(nameAndAddressType, shippingCommerceAddress);
	}

	private void _assertNameAndAddressType(
			Object nameAndAddressType, CommerceAddress commerceAddress)
		throws Exception {

		Assert.assertEquals(
			commerceAddress.getName(),
			ReflectionTestUtil.getFieldValue(nameAndAddressType, "firstName"));
		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(nameAndAddressType, "lastName"));
		Assert.assertEquals(
			commerceAddress.getStreet1(),
			ReflectionTestUtil.getFieldValue(nameAndAddressType, "address"));
		Assert.assertEquals(
			commerceAddress.getCity(),
			ReflectionTestUtil.getFieldValue(nameAndAddressType, "city"));
		Assert.assertEquals(
			commerceAddress.getZip(),
			ReflectionTestUtil.getFieldValue(nameAndAddressType, "zip"));

		Country country = commerceAddress.fetchCountry();

		Assert.assertEquals(
			country.getA2(),
			ReflectionTestUtil.getFieldValue(nameAndAddressType, "country"));

		Region region = commerceAddress.getRegion();

		Assert.assertEquals(
			region.getRegionCode(),
			ReflectionTestUtil.getFieldValue(nameAndAddressType, "state"));
	}

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject(filter = "commerce.payment.engine.method.key=authorize-net")
	private CommercePaymentMethod _commercePaymentMethod;

	private Group _group;
	private User _user;

}