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

package com.liferay.currency.converter.web.portlet.validator;

import com.liferay.currency.converter.web.constants.CurrencyConverterPortletKeys;
import com.liferay.currency.converter.web.model.CurrencyConverter;
import com.liferay.currency.converter.web.util.CurrencyConverterUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CurrencyConverterPortletKeys.CURRENCY_CONVERTER
	}
)
public class CurrencyConverterPreferencesValidator
	implements PreferencesValidator {

	@Override
	public void validate(PortletPreferences portletPreferences)
		throws ValidatorException {

		List<String> badSymbols = new ArrayList<>();

		String[] symbols = portletPreferences.getValues(
			"symbols", new String[0]);

		for (String symbol : symbols) {
			CurrencyConverter currencyConverter =
				CurrencyConverterUtil.getCurrencyConverter(symbol);

			if (currencyConverter == null) {
				badSymbols.add(symbol);
			}
		}

		if (!badSymbols.isEmpty()) {
			throw new ValidatorException(
				"Unable to retrieve symbols", badSymbols);
		}
	}

}