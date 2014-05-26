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

import com.liferay.portal.kernel.settings.BaseServiceSettings;
import com.liferay.portal.kernel.settings.FallbackKeys;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.Settings;
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
public class ShoppingSettings extends BaseServiceSettings {

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

	public ShoppingSettings(Settings settings) {
		super(settings, _fallbackKeys);
	}

	public String[][] getAlternativeShipping() {
		String value = typedSettings.getValue("alternativeShipping", null);

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
		String[] ccTypes = typedSettings.getValues("ccTypes");

		if ((ccTypes.length == 1) && ccTypes[0].equals(CC_NONE)) {
			return StringPool.EMPTY_ARRAY;
		}

		return ccTypes;
	}

	public String getCurrencyId() {
		return typedSettings.getValue("currencyId", "USD");
	}

	public String getEmailFromAddress() {
		return typedSettings.getValue("emailFromAddress");
	}

	public String getEmailFromName() {
		return typedSettings.getValue("emailFromName");
	}

	public LocalizedValuesMap getEmailOrderConfirmationBody() {
		LocalizedValuesMap emailOrderConfirmationBody =
			typedSettings.getLocalizedValuesMap("emailOrderConfirmationBody");

		return emailOrderConfirmationBody;
	}

	public String getEmailOrderConfirmationBodyXml() {
		LocalizedValuesMap emailOrderConfirmationBodyMap =
			getEmailOrderConfirmationBody();

		return emailOrderConfirmationBodyMap.getLocalizationXml();
	}

	public boolean getEmailOrderConfirmationEnabled() {
		return typedSettings.getBooleanValue("emailOrderConfirmationEnabled");
	}

	public LocalizedValuesMap getEmailOrderConfirmationSubject() {
		LocalizedValuesMap emailOrderConfirmationSubject =
			typedSettings.getLocalizedValuesMap(
				"emailOrderConfirmationSubject");

		return emailOrderConfirmationSubject;
	}

	public String getEmailOrderConfirmationSubjectXml() {
		LocalizedValuesMap emailOrderConfirmationSubjectMap =
			getEmailOrderConfirmationSubject();

		return emailOrderConfirmationSubjectMap.getLocalizationXml();
	}

	public LocalizedValuesMap getEmailOrderShippingBody() {
		return typedSettings.getLocalizedValuesMap("emailOrderShippingBody");
	}

	public String getEmailOrderShippingBodyXml() {
		LocalizedValuesMap emailOrderShippingBodyMap =
			getEmailOrderShippingBody();

		return emailOrderShippingBodyMap.getLocalizationXml();
	}

	public boolean getEmailOrderShippingEnabled() {
		return typedSettings.getBooleanValue("emailOrderShippingEnabled");
	}

	public LocalizedValuesMap getEmailOrderShippingSubject() {
		return typedSettings.getLocalizedValuesMap("emailOrderShippingSubject");
	}

	public String getEmailOrderShippingSubjectXml() {
		LocalizedValuesMap emailOrderShippingSubjectMap =
			getEmailOrderShippingSubject();

		return emailOrderShippingSubjectMap.getLocalizationXml();
	}

	public String[] getInsurance() {
		return typedSettings.getValues("insurance");
	}

	public String getInsuranceFormula() {
		return typedSettings.getValue("insuranceFormula");
	}

	public double getMinOrder() {
		return typedSettings.getDoubleValue("minOrder");
	}

	public String getPayPalEmailAddress() {
		return typedSettings.getValue("paypalEmailAddress");
	}

	public String[] getShipping() {
		return typedSettings.getValues("shipping");
	}

	public String getShippingFormula() {
		return typedSettings.getValue("shippingFormula");
	}

	public double getTaxRate() {
		return typedSettings.getDoubleValue("taxRate");
	}

	public String getTaxState() {
		return typedSettings.getValue("taxState");
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

	private static FallbackKeys _fallbackKeys = new FallbackKeys();

	static {
		_fallbackKeys.add("ccTypes", PropsKeys.SHOPPING_CREDIT_CARD_TYPES);
		_fallbackKeys.add("currencyId", PropsKeys.SHOPPING_CURRENCY_ID);
		_fallbackKeys.add(
			"emailFromAddress", PropsKeys.SHOPPING_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		_fallbackKeys.add(
			"emailFromName", PropsKeys.SHOPPING_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		_fallbackKeys.add(
			"emailOrderConfirmationBody",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_BODY);
		_fallbackKeys.add(
			"emailOrderConfirmationEnabled",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_ENABLED);
		_fallbackKeys.add(
			"emailOrderConfirmationSubject",
			PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_SUBJECT);
		_fallbackKeys.add(
			"emailOrderShippingBody",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_BODY);
		_fallbackKeys.add(
			"emailOrderShippingEnabled",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_ENABLED);
		_fallbackKeys.add(
			"emailOrderShippingSubject",
			PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_SUBJECT);
		_fallbackKeys.add("insurance", PropsKeys.SHOPPING_INSURANCE);
		_fallbackKeys.add(
			"insuranceFormula", PropsKeys.SHOPPING_INSURANCE_FORMULA);
		_fallbackKeys.add("shipping", PropsKeys.SHOPPING_SHIPPING);
		_fallbackKeys.add(
			"shippingFormula", PropsKeys.SHOPPING_SHIPPING_FORMULA);
		_fallbackKeys.add("taxState", PropsKeys.SHOPPING_TAX_STATE);
	}

}