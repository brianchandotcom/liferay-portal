/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.authorize.net.internal;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import net.authorize.api.contract.v1.CustomerAddressType;
import net.authorize.api.contract.v1.NameAndAddressType;
import net.authorize.api.contract.v1.TransactionRequestType;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Andrea Sbarra
 */
public class AuthorizeNetCommercePaymentMethodTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetTransactionRequestType() throws Exception {
		TransactionRequestType transactionRequestType =
			_getTransactionRequestType(
				_getCommerceOrder(
					_getCommerceAddress("US", "John Smith", "billing", "CA"),
					_getCommerceAddress(
						"IT", "Anna Maria De Rossi", "shipping", "MI"),
					new BigDecimal("10.555")));

		Assert.assertEquals(
			new BigDecimal("10.56"), transactionRequestType.getAmount());

		CustomerAddressType customerAddressType =
			transactionRequestType.getBillTo();

		_assertNameAndAddressType(
			"US", "John", "Smith", customerAddressType, "billing", "CA");

		Assert.assertEquals(
			"billingPhoneNumber", customerAddressType.getPhoneNumber());

		_assertNameAndAddressType(
			"IT", "Anna", "Rossi", transactionRequestType.getShipTo(),
			"shipping", "MI");
	}

	@Test
	public void testGetTransactionRequestTypeWithEmptyBillingAddress()
		throws Exception {

		TransactionRequestType transactionRequestType =
			_getTransactionRequestType(
				_getCommerceOrder(
					Mockito.mock(CommerceAddress.class), null, BigDecimal.ONE));

		CustomerAddressType customerAddressType =
			transactionRequestType.getBillTo();

		Assert.assertNull(customerAddressType.getFirstName());
		Assert.assertNull(customerAddressType.getLastName());
		Assert.assertNull(customerAddressType.getAddress());
		Assert.assertNull(customerAddressType.getCity());
		Assert.assertNull(customerAddressType.getZip());
		Assert.assertNull(customerAddressType.getCountry());
		Assert.assertNull(customerAddressType.getState());
		Assert.assertNull(customerAddressType.getPhoneNumber());
	}

	@Test
	public void testGetTransactionRequestTypeWithoutAddresses()
		throws Exception {

		TransactionRequestType transactionRequestType =
			_getTransactionRequestType(
				_getCommerceOrder(null, null, BigDecimal.ONE));

		Assert.assertNull(transactionRequestType.getBillTo());
		Assert.assertNull(transactionRequestType.getShipTo());
	}

	@Test
	public void testGetTransactionRequestTypeWithSingleWordName()
		throws Exception {

		TransactionRequestType transactionRequestType =
			_getTransactionRequestType(
				_getCommerceOrder(
					_getCommerceAddress("US", "Cher", "billing", "CA"), null,
					BigDecimal.ONE));

		CustomerAddressType customerAddressType =
			transactionRequestType.getBillTo();

		Assert.assertEquals("Cher", customerAddressType.getFirstName());
		Assert.assertNull(customerAddressType.getLastName());
	}

	private void _assertNameAndAddressType(
		String a2, String firstName, String lastName,
		NameAndAddressType nameAndAddressType, String prefix,
		String regionCode) {

		Assert.assertEquals(firstName, nameAndAddressType.getFirstName());
		Assert.assertEquals(lastName, nameAndAddressType.getLastName());
		Assert.assertEquals(
			prefix + "Street1", nameAndAddressType.getAddress());
		Assert.assertEquals(prefix + "City", nameAndAddressType.getCity());
		Assert.assertEquals(prefix + "Zip", nameAndAddressType.getZip());
		Assert.assertEquals(a2, nameAndAddressType.getCountry());
		Assert.assertEquals(regionCode, nameAndAddressType.getState());
	}

	private CommerceAddress _getCommerceAddress(
			String a2, String name, String prefix, String regionCode)
		throws Exception {

		CommerceAddress commerceAddress = Mockito.mock(CommerceAddress.class);

		Mockito.when(
			commerceAddress.getCity()
		).thenReturn(
			prefix + "City"
		);

		Mockito.when(
			commerceAddress.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			commerceAddress.getPhoneNumber()
		).thenReturn(
			prefix + "PhoneNumber"
		);

		Mockito.when(
			commerceAddress.getStreet1()
		).thenReturn(
			prefix + "Street1"
		);

		Mockito.when(
			commerceAddress.getZip()
		).thenReturn(
			prefix + "Zip"
		);

		Country country = _getCountry(a2);

		Mockito.when(
			commerceAddress.fetchCountry()
		).thenReturn(
			country
		);

		Region region = _getRegion(regionCode);

		Mockito.when(
			commerceAddress.getRegion()
		).thenReturn(
			region
		);

		return commerceAddress;
	}

	private CommerceOrder _getCommerceOrder(
			CommerceAddress billingCommerceAddress,
			CommerceAddress shippingCommerceAddress, BigDecimal total)
		throws Exception {

		CommerceOrder commerceOrder = Mockito.mock(CommerceOrder.class);

		CommerceCurrency commerceCurrency = Mockito.mock(
			CommerceCurrency.class);

		Mockito.when(
			commerceCurrency.getMaxFractionDigits()
		).thenReturn(
			2
		);

		Mockito.when(
			commerceCurrency.getRoundingMode()
		).thenReturn(
			"HALF_UP"
		);

		Mockito.when(
			commerceOrder.getBillingAddress()
		).thenReturn(
			billingCommerceAddress
		);

		Mockito.when(
			commerceOrder.getCommerceCurrency()
		).thenReturn(
			commerceCurrency
		);

		Mockito.when(
			commerceOrder.getShippingAddress()
		).thenReturn(
			shippingCommerceAddress
		);

		Mockito.when(
			commerceOrder.getTotal()
		).thenReturn(
			total
		);

		return commerceOrder;
	}

	private Country _getCountry(String a2) {
		Country country = Mockito.mock(Country.class);

		Mockito.when(
			country.getA2()
		).thenReturn(
			a2
		);

		return country;
	}

	private Region _getRegion(String regionCode) {
		Region region = Mockito.mock(Region.class);

		Mockito.when(
			region.getRegionCode()
		).thenReturn(
			regionCode
		);

		return region;
	}

	private TransactionRequestType _getTransactionRequestType(
		CommerceOrder commerceOrder) {

		return ReflectionTestUtil.invoke(
			_authorizeNetCommercePaymentMethod, "_getTransactionRequestType",
			new Class<?>[] {CommerceOrder.class}, commerceOrder);
	}

	private final AuthorizeNetCommercePaymentMethod
		_authorizeNetCommercePaymentMethod =
			new AuthorizeNetCommercePaymentMethod();

}