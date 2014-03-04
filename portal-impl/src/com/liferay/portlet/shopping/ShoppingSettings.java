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
import com.liferay.portal.kernel.util.StringBundler;
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

import javax.portlet.ReadOnlyException;
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
		String value = _settings.getValue("alternativeShipping", null);

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
		String ccTypes = _settings.getValue(
			"ccTypes", StringUtil.merge(CC_TYPES));

		if (ccTypes.equals(CC_NONE)) {
			return new String[0];
		}
		else {
			return StringUtil.split(ccTypes);
		}
	}

	public String getCurrencyId() {
		return _settings.getValue("currencyId", "USD");
	}

	public String getEmailFromAddress() {
		return _settings.getValue(
			"emailFromAddress", PropsValues.SHOPPING_EMAIL_FROM_ADDRESS);
	}

	public String getEmailFromName() {
		return _settings.getValue(
			"emailFromName", PropsValues.SHOPPING_EMAIL_FROM_NAME);
	}

	public String getEmailOrderConfirmationBody() {
		String emailOrderConfirmationBody = _settings.getValue(
			"emailOrderConfirmationBody", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationBody)) {
			return emailOrderConfirmationBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_BODY));
		}
	}

	public boolean getEmailOrderConfirmationEnabled() {
		String emailOrderConfirmationEnabled = _settings.getValue(
			"emailOrderConfirmationEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationEnabled)) {
			return GetterUtil.getBoolean(emailOrderConfirmationEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_ENABLED));
		}
	}

	public String getEmailOrderConfirmationSubject() {
		String emailOrderConfirmationSubject = _settings.getValue(
			"emailOrderConfirmationSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationSubject)) {
			return emailOrderConfirmationSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(
					PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_SUBJECT));
		}
	}

	public String getEmailOrderShippingBody() {
		String emailOrderShippingBody = _settings.getValue(
			"emailOrderShippingBody", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingBody)) {
			return emailOrderShippingBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_BODY));
		}
	}

	public boolean getEmailOrderShippingEnabled() {
		String emailOrderShippingEnabled = _settings.getValue(
			"emailOrderShippingEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingEnabled)) {
			return GetterUtil.getBoolean(emailOrderShippingEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_ENABLED));
		}
	}

	public String getEmailOrderShippingSubject() {
		String emailOrderShippingSubject = _settings.getValue(
			"emailOrderShippingSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingSubject)) {
			return emailOrderShippingSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_SUBJECT));
		}
	}

	public String[] getInsurance() {
		String value = _settings.getValue("insurance", null);

		if (value == null) {
			return new String[5];
		}
		else {
			return StringUtil.split(value);
		}
	}

	public String getInsuranceFormula() {
		return _settings.getValue("insuranceFormula", "flat");
	}

	public double getMinOrder() {
		return GetterUtil.getDouble(
			_settings.getValue("minOrder", StringPool.BLANK));
	}

	public String getPayPalEmailAddress() {
		return _settings.getValue("paypalEmailAddress", StringPool.BLANK);
	}

	public String[] getShipping() {
		String value = _settings.getValue("shipping", null);

		if (value == null) {
			return new String[5];
		}
		else {
			return StringUtil.split(value);
		}
	}

	public String getShippingFormula() {
		return _settings.getValue("shippingFormula", "flat");
	}

	public double getTaxRate() {
		return GetterUtil.getDouble(
			_settings.getValue("taxRate", StringPool.BLANK));
	}

	public String getTaxState() {
		return _settings.getValue("taxState", "CA");
	}

	@Override
	public String getValue(String key, String defaultValue) {
		return _settings.getValue(key, defaultValue);
	}

	@Override
	public String[] getValues(String key, String[] defaultValue) {
		return _settings.getValues(key, defaultValue);
	}

	public void setAlternativeShipping(String[][] alternativeShipping)
		throws ReadOnlyException {

		if (alternativeShipping.length == 0) {
			_settings.setValue("alternativeShipping", StringPool.BLANK);
		}

		StringBundler sb = new StringBundler(
			alternativeShipping.length * 2 - 1);

		for (int i = 0; i < alternativeShipping.length; i++) {
			sb.append(StringUtil.merge(alternativeShipping[i]));

			if ((i + 1) < alternativeShipping.length) {
				sb.append("[$_ARRAY_$]");
			}
		}

		_settings.setValue("alternativeShipping", sb.toString());
	}

	public void setCcTypes(String[] ccTypes) throws ReadOnlyException {
		if (ccTypes.length == 0) {
			_settings.setValue("ccTypes", CC_NONE);
		}
		else {
			_settings.setValue("ccTypes", StringUtil.merge(ccTypes));
		}
	}

	public void setCurrencyId(String currencyId) throws ReadOnlyException {
		_settings.setValue("currencyId", currencyId);
	}

	public void setEmailFromAddress(String emailFromAddress)
		throws ReadOnlyException {

		_settings.setValue("emailFromAddress", emailFromAddress);
	}

	public void setEmailFromName(String emailFromName)
		throws ReadOnlyException {

		_settings.setValue("emailFromName", emailFromName);
	}

	public void setEmailOrderConfirmationBody(String emailOrderConfirmationBody)
		throws ReadOnlyException {

		_settings.setValue(
			"emailOrderConfirmationBody", emailOrderConfirmationBody);
	}

	public void setEmailOrderConfirmationEnabled(
			boolean emailOrderConfirmationEnabled)
		throws ReadOnlyException {

		_settings.setValue(
			"emailOrderConfirmationEnabled",
			String.valueOf(emailOrderConfirmationEnabled));
	}

	public void setEmailOrderConfirmationSubject(
			String emailOrderConfirmationSubject)
		throws ReadOnlyException {

		_settings.setValue(
			"emailOrderConfirmationSubject", emailOrderConfirmationSubject);
	}

	public void setEmailOrderShippingBody(String emailOrderShippingBody)
		throws ReadOnlyException {

		_settings.setValue("emailOrderShippingBody", emailOrderShippingBody);
	}

	public void setEmailOrderShippingEnabled(boolean emailOrderShippingEnabled)
		throws ReadOnlyException {

		_settings.setValue(
			"emailOrderShippingEnabled",
			String.valueOf(emailOrderShippingEnabled));
	}

	public void setEmailOrderShippingSubject(String emailOrderShippingSubject)
		throws ReadOnlyException {

		_settings.setValue(
			"emailOrderShippingSubject", emailOrderShippingSubject);
	}

	public void setInsurance(String[] insurance) throws ReadOnlyException {
		_settings.setValue("insurance", StringUtil.merge(insurance));
	}

	public void setInsuranceFormula(String insuranceFormula)
		throws ReadOnlyException {

		_settings.setValue("insuranceFormula", insuranceFormula);
	}

	public void setMinOrder(double minOrder) throws ReadOnlyException {
		_settings.setValue("minOrder", String.valueOf(minOrder));
	}

	public void setPayPalEmailAddress(String payPalEmailAddress)
		throws ReadOnlyException {

		_settings.setValue("paypalEmailAddress", payPalEmailAddress);
	}

	public void setShipping(String[] shipping) throws ReadOnlyException {
		_settings.setValue("shipping", StringUtil.merge(shipping));
	}

	public void setShippingFormula(String shippingFormula)
		throws ReadOnlyException {

		_settings.setValue("shippingFormula", shippingFormula);
	}

	public void setTaxRate(double taxRate) throws ReadOnlyException {
		_settings.setValue("taxRate", String.valueOf(taxRate));
	}

	public void setTaxState(String taxState) throws ReadOnlyException {
		_settings.setValue("taxState", taxState);
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

	private Settings _settings;

}