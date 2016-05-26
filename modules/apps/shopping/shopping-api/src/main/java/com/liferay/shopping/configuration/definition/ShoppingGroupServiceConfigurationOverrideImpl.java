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

package com.liferay.shopping.configuration.definition;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.shopping.configuration.ShoppingGroupServiceConfigurationOverride;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 * @author Peter Fellwock
 */
public class ShoppingGroupServiceConfigurationOverrideImpl
	implements ShoppingGroupServiceConfigurationOverride {

	public static final String[] CURRENCY_IDS;

	static {
		String[] ids = null;

		try {
			Set<String> set = new TreeSet<>();

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

	public ShoppingGroupServiceConfigurationOverrideImpl(
		TypedSettings typedSettings) {

		_typedSettings = typedSettings;
	}

	@Override
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

	@Override
	public String getAlternativeShippingName(int altShipping) {
		String altShippingName = StringPool.BLANK;

		try {
			altShippingName = getAlternativeShipping()[0][altShipping];
		}
		catch (Exception e) {
		}

		return altShippingName;
	}

	@Override
	public String[] getCcTypes() {
		String[] ccTypes = _typedSettings.getValues("ccTypes");

		if ((ccTypes.length == 1) && ccTypes[0].equals(CC_NONE)) {
			return StringPool.EMPTY_ARRAY;
		}

		return ccTypes;
	}

	@Override
	public String getCurrencyId() {
		return _typedSettings.getValue("currencyId", "USD");
	}

	@Override
	public String[] getCurrencyIds() {
		return CURRENCY_IDS;
	}

	@Override
	public String getEmailFromAddress() {
		return _typedSettings.getValue("emailFromAddress");
	}

	@Override
	public String getEmailFromName() {
		return _typedSettings.getValue("emailFromName");
	}

	@Override
	public LocalizedValuesMap getEmailOrderConfirmationBody() {
		LocalizedValuesMap emailOrderConfirmationBody =
			_typedSettings.getLocalizedValuesMap("emailOrderConfirmationBody");

		return emailOrderConfirmationBody;
	}

	@Override
	public String getEmailOrderConfirmationBodyXml() {
		return LocalizationUtil.getXml(
			getEmailOrderConfirmationBody(), "emailOrderConfirmationBody");
	}

	@Override
	public LocalizedValuesMap getEmailOrderConfirmationSubject() {
		LocalizedValuesMap emailOrderConfirmationSubject =
			_typedSettings.getLocalizedValuesMap(
				"emailOrderConfirmationSubject");

		return emailOrderConfirmationSubject;
	}

	@Override
	public String getEmailOrderConfirmationSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailOrderConfirmationSubject(),
			"emailOrderConfirmationSubject");
	}

	@Override
	public LocalizedValuesMap getEmailOrderShippingBody() {
		return _typedSettings.getLocalizedValuesMap("emailOrderShippingBody");
	}

	@Override
	public String getEmailOrderShippingBodyXml() {
		return LocalizationUtil.getXml(
			getEmailOrderShippingBody(), "emailOrderShippingBody");
	}

	@Override
	public LocalizedValuesMap getEmailOrderShippingSubject() {
		return _typedSettings.getLocalizedValuesMap(
			"emailOrderShippingSubject");
	}

	@Override
	public String getEmailOrderShippingSubjectXml() {
		return LocalizationUtil.getXml(
			getEmailOrderShippingSubject(), "emailOrderShippingSubject");
	}

	@Override
	public String[] getInsurance() {
		return _typedSettings.getValues("insurance");
	}

	@Override
	public String getInsuranceFormula() {
		return _typedSettings.getValue("insuranceFormula");
	}

	@Override
	public double getMinOrder() {
		return _typedSettings.getDoubleValue("minOrder");
	}

	@Override
	public String getPayPalEmailAddress() {
		return _typedSettings.getValue("paypalEmailAddress");
	}

	@Override
	public String[] getShipping() {
		return _typedSettings.getValues("shipping");
	}

	@Override
	public String getShippingFormula() {
		return _typedSettings.getValue("shippingFormula");
	}

	@Override
	public double getTaxRate() {
		return _typedSettings.getDoubleValue("taxRate");
	}

	@Override
	public String getTaxState() {
		return _typedSettings.getValue("taxState");
	}

	@Override
	public boolean isEmailOrderConfirmationEnabled() {
		return _typedSettings.getBooleanValue("emailOrderConfirmationEnabled");
	}

	@Override
	public boolean isEmailOrderShippingEnabled() {
		return _typedSettings.getBooleanValue("emailOrderShippingEnabled");
	}

	@Override
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

	@Override
	public boolean usePayPal() {
		return Validator.isNotNull(getPayPalEmailAddress());
	}

	private final TypedSettings _typedSettings;

}