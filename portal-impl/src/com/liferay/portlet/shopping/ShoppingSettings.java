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

package com.liferay.portlet.shopping;

import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
public class ShoppingSettings {

	public static final String CC_NONE = "none";

	public static final String[] CC_TYPES =
		{"visa", "mastercard", "discover", "amex"};

	public static final String[] CURRENCY_IDS;

	static {
		String[] ids = null;

		try {
			Set<String> set = new TreeSet<String>();

			Locale[] locales = Locale.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
				Locale locale = locales[i];

				if (locale.getCountry().length() == 2) {
					Currency currency = Currency.getInstance(locale);

					String currencyId = currency.getCurrencyCode();

					set.add(currencyId);
				}
			}

			ids = set.toArray(new String[set.size()]);
		}
		catch (Exception e) {
			ids = new String[] {"USD", "CAD", "EUR", "GBP", "JPY"};
		}
		finally {
			CURRENCY_IDS = ids;
		}
	}

	public static final double[] INSURANCE_RANGE = {
		0.01, 9.99, 10.00, 49.99, 50.00, 99.99, 100.00, 199.99, 200.00,
		Double.POSITIVE_INFINITY
	};

	public static final double[] SHIPPING_RANGE = {
		0.01, 9.99, 10.00, 49.99, 50.00, 99.99, 100.00, 199.99, 200.00,
		Double.POSITIVE_INFINITY
	};

	public static FallbackKeys FALLBACK_KEYS = new FallbackKeys();

	static {
		FALLBACK_KEYS.add("ccTypes", PropsKeys.SHOPPING_CREDIT_CARD_TYPES);
		FALLBACK_KEYS.add("currencyId", PropsKeys.SHOPPING_CURRENCY_ID);
		FALLBACK_KEYS.add(
			"emailFromAddress", PropsKeys.SHOPPING_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		FALLBACK_KEYS.add(
			"emailFromName", PropsKeys.SHOPPING_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		FALLBACK_KEYS.add(
			"emailOrderConfirmationBody",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_BODY);
		FALLBACK_KEYS.add(
			"emailOrderConfirmationEnabled",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_ENABLED);
		FALLBACK_KEYS.add(
			"emailOrderConfirmationSubject",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_SUBJECT);
		FALLBACK_KEYS.add(
			"emailOrderShippingBody",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_BODY);
		FALLBACK_KEYS.add(
			"emailOrderShippingEnabled",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_ENABLED);
		FALLBACK_KEYS.add(
			"emailOrderShippingSubject",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_SUBJECT);
		FALLBACK_KEYS.add("insurance", PropsKeys.SHOPPING_INSURANCE);
		FALLBACK_KEYS.add(
			"insuranceFormula", PropsKeys.SHOPPING_INSURANCE_FORMULA);
		FALLBACK_KEYS.add("shipping", PropsKeys.SHOPPING_SHIPPING);
		FALLBACK_KEYS.add(
			"shippingFormula", PropsKeys.SHOPPING_SHIPPING_FORMULA);
		FALLBACK_KEYS.add("taxState", PropsKeys.SHOPPING_TAX_STATE);
	}

	public ShoppingSettings(Settings settings) {
		_typedSettings = new TypedSettings(settings);
	}

	public String[][] getAlternativeShipping() {
		String value = _typedSettings.getValue("alternativeShipping", null);

		if (value == null) {
			return new String[0][0];
		}

		String[] array = StringUtil.split("alternativeShipping", "[$_ARRAY_$]");

		String[][] alternativeShipping = new String[array.length][0];

		for (int i = 0; i < array.length; i++) {
			alternativeShipping[i] = StringUtil.split(array[i]);
		}

		return alternativeShipping;
	}

	public String getAlternativeShippingName(int altShipping) {
		String altShippingName = StringPool.BLANK;

		try {
			altShippingName = getAlternativeShipping()[0][altShipping];
		}
		catch (Exception e) {
		}

		return altShippingName;
	}

	public String[] getCcTypes() {
		String[] ccTypes = _typedSettings.getValues("ccTypes");

		if ((ccTypes.length == 1) && ccTypes[0].equals(CC_NONE)) {
			return StringPool.EMPTY_ARRAY;
		}

		return ccTypes;
	}

	public String getCurrencyId() {
		return _typedSettings.getValue("currencyId", "USD");
	}

	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailOrderConfirmationBody() {
		LocalizedValuesMap emailOrderConfirmationBody =
			_typedSettings.getLocalizedValuesMap("emailOrderConfirmationBody");

		return emailOrderConfirmationBody;
	}

	public String getEmailOrderConfirmationBodyXml() {
		LocalizedValuesMap emailOrderConfirmationBodyMap =
			getEmailOrderConfirmationBody();

		return emailOrderConfirmationBodyMap.getLocalizationXml();
	}

	public boolean getEmailOrderConfirmationEnabled() {
		return _typedSettings.getBooleanValue("emailOrderConfirmationEnabled");
	}

	public LocalizedValuesMap getEmailOrderConfirmationSubject() {
		LocalizedValuesMap emailOrderConfirmationSubject =
			_typedSettings.getLocalizedValuesMap(
				"emailOrderConfirmationSubject");

		return emailOrderConfirmationSubject;
	}

	public String getEmailOrderConfirmationSubjectXml() {
		LocalizedValuesMap emailOrderConfirmationSubjectMap =
			getEmailOrderConfirmationSubject();

		return emailOrderConfirmationSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailOrderShippingBody() {
		return _typedSettings.getLocalizedValuesMap("emailOrderShippingBody");
	}

	public String getEmailOrderShippingBodyXml() {
		LocalizedValuesMap emailOrderShippingBodyMap =
			getEmailOrderShippingBody();

		return emailOrderShippingBodyMap.getLocalizationXml();
	}

	public boolean getEmailOrderShippingEnabled() {
		return _typedSettings.getBooleanValue("emailOrderShippingEnabled");
	}

	public LocalizedValuesMap getEmailOrderShippingSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailOrderShippingSubject");
	}

	public String getEmailOrderShippingSubjectXml() {
		LocalizedValuesMap emailOrderShippingSubjectMap =
			getEmailOrderShippingSubject();

		return emailOrderShippingSubjectMap.getLocalizationXml();
	}

	public String[] getInsurance() {
		return _typedSettings.getValues("insurance");
	}

	public String getInsuranceFormula() {
		return _typedSettings.getValue("insuranceFormula");
	}

	public double getMinOrder() {
		return _typedSettings.getDoubleValue("minOrder");
	}

	public String getPayPalEmailAddress() {
		return _typedSettings.getValue("paypalEmailAddress");
	}

	public String[] getShipping() {
		return _typedSettings.getValues("shipping");
	}

	public String getShippingFormula() {
		return _typedSettings.getValue("shippingFormula");
	}

	public double getTaxRate() {
		return _typedSettings.getDoubleValue("taxRate");
	}

	public String getTaxState() {
		return _typedSettings.getValue("taxState");
	}

	public boolean useAlternativeShipping() {
		String[][] alternativeShipping = getAlternativeShipping();

		try {
			for (int i = 0; i < 10; i++) {
				if (Validator.isNotNull(alternativeShipping[0][i]) &&
					Validator.isNotNull(alternativeShipping[1][i])) {

					return true;
				}
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean usePayPal() {
		return Validator.isNotNull(getPayPalEmailAddress());
	}

	private TypedSettings _typedSettings;

}