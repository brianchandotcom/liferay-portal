/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.settings.Settings;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ContentUtil;

import java.io.IOException;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
public class ShoppingSettings implements Settings {

	public static final String CC_NONE = "none";

	public static final String[] CC_TYPES =
		new String[] {"visa", "mastercard", "discover", "amex"};

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
		_settings = settings;
	}

	public String[][] getAlternativeShipping() {
		String value = getValue("alternativeShipping", null);

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
		String ccTypes = getValue("ccTypes", StringUtil.merge(CC_TYPES));

		if (ccTypes.equals(CC_NONE)) {
			return new String[0];
		}
		else {
			return StringUtil.split(ccTypes);
		}
	}

	public String getCurrencyId() {
		return getValue("currencyId", "USD");
	}

	public String getEmailFromAddress() {
		return getValue(
			"emailFromAddress", PropsValues.SHOPPING_EMAIL_FROM_ADDRESS);
	}

	public String getEmailFromName() {
		return getValue("emailFromName", PropsValues.SHOPPING_EMAIL_FROM_NAME);
	}

	public String getEmailOrderConfirmationBody() {
		String emailOrderConfirmationBody = getValue(
			"emailOrderConfirmationBody", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationBody)) {
			return emailOrderConfirmationBody;
		}

		return ContentUtil.get(
			PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_BODY));
	}

	public boolean getEmailOrderConfirmationEnabled() {
		String emailOrderConfirmationEnabled = getValue(
			"emailOrderConfirmationEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationEnabled)) {
			return GetterUtil.getBoolean(emailOrderConfirmationEnabled);
		}

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_ENABLED));
	}

	public String getEmailOrderConfirmationSubject() {
		String emailOrderConfirmationSubject = getValue(
			"emailOrderConfirmationSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationSubject)) {
			return emailOrderConfirmationSubject;
		}

		return ContentUtil.get(
			PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_SUBJECT));
	}

	public String getEmailOrderShippingBody() {
		String emailOrderShippingBody = getValue(
			"emailOrderShippingBody", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingBody)) {
			return emailOrderShippingBody;
		}

		return ContentUtil.get(
			PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_BODY));
	}

	public boolean getEmailOrderShippingEnabled() {
		String emailOrderShippingEnabled = getValue(
			"emailOrderShippingEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingEnabled)) {
			return GetterUtil.getBoolean(emailOrderShippingEnabled);
		}

		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_ENABLED));
	}

	public String getEmailOrderShippingSubject() {
		String emailOrderShippingSubject = getValue(
			"emailOrderShippingSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingSubject)) {
			return emailOrderShippingSubject;
		}

		return ContentUtil.get(
			PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_SUBJECT));
	}

	public String[] getInsurance() {
		String value = getValue("insurance", null);

		if (value == null) {
			return new String[5];
		}

		return StringUtil.split(value);
	}

	public String getInsuranceFormula() {
		return getValue("insuranceFormula", "flat");
	}

	public double getMinOrder() {
		return GetterUtil.getDouble(getValue("minOrder", StringPool.BLANK));
	}

	public String getPayPalEmailAddress() {
		return getValue("paypalEmailAddress", StringPool.BLANK);
	}

	public String[] getShipping() {
		String value = getValue("shipping", null);

		if (value == null) {
			return new String[5];
		}

		return StringUtil.split(value);
	}

	public String getShippingFormula() {
		return getValue("shippingFormula", "flat");
	}

	public double getTaxRate() {
		return GetterUtil.getDouble(getValue("taxRate", StringPool.BLANK));
	}

	public String getTaxState() {
		return getValue("taxState", "CA");
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String value = _settings.getValue(key, defaultValue);

		if (Validator.isNotNull(value)) {
			return value;
		}

		String fallbackKey = getFallbackKey(key);

		if (fallbackKey != null) {
			return _settings.getValue(fallbackKey, value);
		}

		return null;
	}

	@Override
	public String[] getValues(String key, String[] defaultValue) {
		return _settings.getValues(key, defaultValue);
	}

	@Override
	public Settings setValue(String key, String value) {
		return _settings.setValue(key, value);
	}

	@Override
	public Settings setValues(String key, String[] values) {
		return _settings.setValues(key, values);
	}

	@Override
	public void store() throws IOException, ValidatorException {
		_settings.store();
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

	protected String getFallbackKey(String key) {
		String fallbackKey = null;

		if (key.equals("emailFromAddress")) {
			fallbackKey = PropsKeys.ADMIN_EMAIL_FROM_ADDRESS;
		}
		else if (key.equals("emailFromName")) {
			fallbackKey = PropsKeys.ADMIN_EMAIL_FROM_NAME;
		}

		return fallbackKey;
	}

	private Settings _settings;

}