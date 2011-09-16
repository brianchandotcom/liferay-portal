/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.ContentUtil;

import java.io.IOException;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingPreferences {

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

	public static final double[] SHIPPING_RANGE = {
		0.01, 9.99, 10.00, 49.99, 50.00, 99.99, 100.00, 199.99, 200.00,
		Double.POSITIVE_INFINITY
	};

	public static final double[] INSURANCE_RANGE = {
		0.01, 9.99, 10.00, 49.99, 50.00, 99.99, 100.00, 199.99, 200.00,
		Double.POSITIVE_INFINITY
	};

	public static ShoppingPreferences getInstance(long companyId, long groupId)
		throws SystemException {

		return new ShoppingPreferences(companyId, groupId);
	}

	public String getPayPalEmailAddress() {
		return _preferences.getValue("paypal-email-address", StringPool.BLANK);
	}

	public void setPayPalEmailAddress(String payPalEmailAddress)
		throws ReadOnlyException {

		_preferences.setValue("paypal-email-address", payPalEmailAddress);
	}

	public boolean usePayPal() {
		return Validator.isNotNull(getPayPalEmailAddress());
	}

	public String getCurrencyId() {
		return _preferences.getValue("currency-id", "USD");
	}

	public void setCurrencyId(String currencyId) throws ReadOnlyException {
		_preferences.setValue("currency-id", currencyId);
	}

	public String[] getCcTypes() {
		String ccTypes = _preferences.getValue(
			"cc-types", StringUtil.merge(CC_TYPES));

		if (ccTypes.equals(CC_NONE)) {
			return new String[0];
		}
		else {
			return StringUtil.split(ccTypes);
		}
	}

	public void setCcTypes(String[] ccTypes) throws ReadOnlyException {
		if (ccTypes.length == 0) {
			_preferences.setValue("cc-types", CC_NONE);
		}
		else {
			_preferences.setValue("cc-types", StringUtil.merge(ccTypes));
		}
	}

	public String getTaxState() {
		return _preferences.getValue("tax-state", "CA");
	}

	public void setTaxState(String taxState) throws ReadOnlyException {
		_preferences.setValue("tax-state", taxState);
	}

	public double getTaxRate() {
		return GetterUtil.getDouble(_preferences.getValue(
			"tax-rate", StringPool.BLANK));
	}

	public void setTaxRate(double taxRate) throws ReadOnlyException {
		_preferences.setValue("tax-rate", String.valueOf(taxRate));
	}

	public String getShippingFormula() {
		return _preferences.getValue("shipping-formula", "flat");
	}

	public void setShippingFormula(String shippingFormula)
		throws ReadOnlyException {

		_preferences.setValue("shipping-formula", shippingFormula);
	}

	public String[] getShipping() {
		String value = _preferences.getValue("shipping", null);

		if (value == null) {
			return new String[5];
		}
		else {
			return StringUtil.split(value);
		}
	}

	public void setShipping(String[] shipping) throws ReadOnlyException {
		_preferences.setValue("shipping", StringUtil.merge(shipping));
	}

	public String[][] getAlternativeShipping() {
		String value = _preferences.getValue("alternative-shipping", null);

		if (value == null) {
			return new String[0][0];
		}
		else {
			String[] array =
				StringUtil.split("alternative-shipping", "[$_ARRAY_$]");

			String[][] alternativeShipping = new String[array.length][0];

			for (int i = 0; i < array.length; i++) {
				alternativeShipping[i] = StringUtil.split(array[i]);
			}

			return alternativeShipping;
		}
	}

	public void setAlternativeShipping(String[][] alternativeShipping)
		throws ReadOnlyException {

		if (alternativeShipping.length == 0) {
			_preferences.setValue("alternative-shipping", StringPool.BLANK);
		}

		StringBundler sb = new StringBundler(
			alternativeShipping.length * 2 - 1);

		for (int i = 0; i < alternativeShipping.length; i++) {
			sb.append(StringUtil.merge(alternativeShipping[i]));

			if ((i + 1) < alternativeShipping.length) {
				sb.append("[$_ARRAY_$]");
			}
		}

		_preferences.setValue("alternative-shipping", sb.toString());
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

	public String getAlternativeShippingName(int altShipping) {
		String altShippingName = StringPool.BLANK;

		try {
			altShippingName = getAlternativeShipping()[0][altShipping];
		}
		catch (Exception e) {
		}

		return altShippingName;
	}

	public String getInsuranceFormula() {
		return _preferences.getValue("insurance-formula", "flat");
	}

	public void setInsuranceFormula(String insuranceFormula)
		throws ReadOnlyException {

		_preferences.setValue("insurance-formula", insuranceFormula);
	}

	public String[] getInsurance() {
		String value = _preferences.getValue("insurance", null);

		if (value == null) {
			return new String[5];
		}
		else {
			return StringUtil.split(value);
		}
	}

	public void setInsurance(String[] insurance) throws ReadOnlyException {
		_preferences.setValue("insurance", StringUtil.merge(insurance));
	}

	public double getMinOrder() {
		return GetterUtil.getDouble(_preferences.getValue(
			"min-order", StringPool.BLANK));
	}

	public void setMinOrder(double minOrder) throws ReadOnlyException {
		_preferences.setValue("min-order", String.valueOf(minOrder));
	}

	public String getEmailFromAddress(long companyId) throws SystemException {
		String emailFromAddress = PropsUtil.get(
			PropsKeys.SHOPPING_EMAIL_FROM_ADDRESS);

		emailFromAddress = _preferences.getValue(
			"email-from-address", emailFromAddress);

		if (Validator.isNull(emailFromAddress)) {
			emailFromAddress = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		}

		return _preferences.getValue("email-from-address", emailFromAddress);
	}

	public void setEmailFromAddress(String emailFromAddress)
		throws ReadOnlyException {

		_preferences.setValue("email-from-address", emailFromAddress);
	}

	public String getEmailFromName(long companyId) throws SystemException {
		String emailFromName = PropsUtil.get(
			PropsKeys.SHOPPING_EMAIL_FROM_NAME);

		emailFromName = _preferences.getValue("email-from-name", emailFromName);

		if (Validator.isNull(emailFromName)) {
			emailFromName = PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		}

		return _preferences.getValue("email-from-name", emailFromName);
	}

	public void setEmailFromName(String emailFromName)
		throws ReadOnlyException {

		_preferences.setValue("email-from-name", emailFromName);
	}

	public boolean getEmailOrderConfirmationEnabled() {
		String emailOrderConfirmationEnabled = _preferences.getValue(
			"email-order-confirmation-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationEnabled)) {
			return GetterUtil.getBoolean(emailOrderConfirmationEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_ENABLED));
		}
	}

	public void setEmailOrderConfirmationEnabled(
			boolean emailOrderConfirmationEnabled)
		throws ReadOnlyException {

		_preferences.setValue(
			"email-order-confirmation-enabled",
			String.valueOf(emailOrderConfirmationEnabled));
	}

	public String getEmailOrderConfirmationBody() {
		String emailOrderConfirmationBody = _preferences.getValue(
			"email-order-confirmation-body", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationBody)) {
			return emailOrderConfirmationBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_BODY));
		}
	}

	public void setEmailOrderConfirmationBody(String emailOrderConfirmationBody)
		throws ReadOnlyException {

		_preferences.setValue(
			"email-order-confirmation-body", emailOrderConfirmationBody);
	}

	public String getEmailOrderConfirmationSubject() {
		String emailOrderConfirmationSubject = _preferences.getValue(
			"email-order-confirmation-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderConfirmationSubject)) {
			return emailOrderConfirmationSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.SHOPPING_EMAIL_ORDER_CONFIRMATION_SUBJECT));
		}
	}

	public void setEmailOrderConfirmationSubject(
			String emailOrderConfirmationSubject)
		throws ReadOnlyException {

		_preferences.setValue(
			"email-order-confirmation-subject", emailOrderConfirmationSubject);
	}

	public boolean getEmailOrderShippingEnabled() {
		String emailOrderShippingEnabled = _preferences.getValue(
			"email-order-shipping-enabled", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingEnabled)) {
			return GetterUtil.getBoolean(emailOrderShippingEnabled);
		}
		else {
			return GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_ENABLED));
		}
	}

	public void setEmailOrderShippingEnabled(boolean emailOrderShippingEnabled)
		throws ReadOnlyException {

		_preferences.setValue(
			"email-order-shipping-enabled",
			String.valueOf(emailOrderShippingEnabled));
	}

	public String getEmailOrderShippingBody() {
		String emailOrderShippingBody = _preferences.getValue(
			"email-order-shipping-body", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingBody)) {
			return emailOrderShippingBody;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_BODY));
		}
	}

	public void setEmailOrderShippingBody(String emailOrderShippingBody)
		throws ReadOnlyException {

		_preferences.setValue(
			"email-order-shipping-body", emailOrderShippingBody);
	}

	public String getEmailOrderShippingSubject() {
		String emailOrderShippingSubject = _preferences.getValue(
			"email-order-shipping-subject", StringPool.BLANK);

		if (Validator.isNotNull(emailOrderShippingSubject)) {
			return emailOrderShippingSubject;
		}
		else {
			return ContentUtil.get(PropsUtil.get(
				PropsKeys.SHOPPING_EMAIL_ORDER_SHIPPING_SUBJECT));
		}
	}

	public void setEmailOrderShippingSubject(String emailOrderShippingSubject)
		throws ReadOnlyException {

		_preferences.setValue(
			"email-order-shipping-subject", emailOrderShippingSubject);
	}

	public void store() throws IOException, ValidatorException {
		_preferences.store();
	}

	protected ShoppingPreferences(long companyId, long groupId)
		throws SystemException {

		long ownerId = groupId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
		long plid = PortletKeys.PREFS_PLID_SHARED;
		String portletId = PortletKeys.SHOPPING;

		_preferences = PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, ownerId, ownerType, plid, portletId);
	}

	private PortletPreferences _preferences;

}