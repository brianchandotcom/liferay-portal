/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.term.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.term.evaluator.NotificationTermEvaluatorTracker;
import com.liferay.notification.type.util.NotificationTypeUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
 * @author Balázs Breier
 */
@RunWith(Arquillian.class)
public class CommerceOrderAddressNotificationTermTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				TestPropsValues.getCompanyId());

		CommerceChannel commerceChannel = CommerceTestUtil.addCommerceChannel(
			TestPropsValues.getGroupId(), commerceCurrency.getCode());

		_commerceOrder = CommerceTestUtil.addB2CCommerceOrder(
			TestPropsValues.getUserId(), commerceChannel.getGroupId(),
			commerceCurrency);

		CommerceAddress billingCommerceAddress =
			CommerceTestUtil.addUserCommerceAddress(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		_commerceOrder.setBillingAddressId(
			billingCommerceAddress.getCommerceAddressId());

		CommerceAddress shippingCommerceAddress =
			CommerceTestUtil.addUserCommerceAddress(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		_commerceOrder.setShippingAddressId(
			shippingCommerceAddress.getCommerceAddressId());

		_commerceOrder = _commerceOrderLocalService.updateCommerceOrder(
			_commerceOrder);
	}

	@Test
	public void testCommerceOrderAddressNotificationEvaluateTerms()
		throws Exception {

		String content = StringBundler.concat(
			"[%COMMERCEORDER_BILLING_ADDRESS_CITY%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_COUNTRY%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_NAME%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_PHONE_NUMBER%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_REGION%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_STREET1%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_STREET2%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_STREET3%] | ",
			"[%COMMERCEORDER_BILLING_ADDRESS_ZIP%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_CITY%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_COUNTRY%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_NAME%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_PHONE_NUMBER%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_REGION%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_STREET1%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_STREET2%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_STREET3%] | ",
			"[%COMMERCEORDER_SHIPPING_ADDRESS_ZIP%]");

		Map<String, Object> termValues = HashMapBuilder.<String, Object>put(
			"externalReferenceCode", _commerceOrder.getExternalReferenceCode()
		).put(
			"groupId", _commerceOrder.getGroupId()
		).put(
			"id", _commerceOrder.getCommerceOrderId()
		).build();

		content = NotificationTypeUtil.evaluateTerms(
			content,
			new NotificationContextBuilder(
			).className(
				"com.liferay.commerce.model.CommerceOrder"
			).classPK(
				GetterUtil.getLong(termValues.get("id"))
			).externalReferenceCode(
				GetterUtil.getString(termValues.get("externalReferenceCode"))
			).groupId(
				GetterUtil.getLong(termValues.get("groupId"))
			).termValues(
				termValues
			).build(),
			_notificationTermEvaluatorTracker);

		Assert.assertEquals(_getExpectedContent(), content);
	}

	private String _getExpectedContent() throws Exception {
		CommerceAddress shippingAddress = _commerceOrder.getShippingAddress();
		CommerceAddress billingAddress = _commerceOrder.getBillingAddress();
		Country shippingCountry = shippingAddress.getCountry();
		Country billingCountry = billingAddress.getCountry();
		Region shippingRegion = shippingAddress.getRegion();
		Region billingRegion = billingAddress.getRegion();

		return StringBundler.concat(
			billingAddress.getCity(), " | ", billingCountry.getTitle(), " | ",
			billingAddress.getName(), " | ", billingAddress.getPhoneNumber(),
			" | ", billingRegion.getTitle(), " | ", billingAddress.getStreet1(),
			" | ", billingAddress.getStreet2(), " | ",
			billingAddress.getStreet3(), " | ", billingAddress.getZip(), " | ",
			shippingAddress.getCity(), " | ", shippingCountry.getTitle(), " | ",
			shippingAddress.getName(), " | ", shippingAddress.getPhoneNumber(),
			" | ", shippingRegion.getTitle(), " | ",
			shippingAddress.getStreet1(), " | ", shippingAddress.getStreet2(),
			" | ", shippingAddress.getStreet3(), " | ",
			shippingAddress.getZip());
	}

	private CommerceOrder _commerceOrder;

	@Inject
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Inject
	private NotificationTermEvaluatorTracker _notificationTermEvaluatorTracker;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}