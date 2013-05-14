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

package com.liferay.portlet.currencyconverter;

import com.liferay.portlet.currencyconverter.model.Currency;
import com.liferay.portlet.currencyconverter.util.CurrencyUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class CurrencyPreferencesValidator implements PreferencesValidator {

	@Override
	public void validate(PortletPreferences preferences)
		throws ValidatorException {

		List<String> badSymbols = new ArrayList<String>();

		String[] symbols = preferences.getValues("symbols", new String[0]);

		for (int i = 0; i < symbols.length; i++) {
			Currency currency = CurrencyUtil.getCurrency(symbols[i]);

			if (currency == null) {
				badSymbols.add(symbols[i]);
			}
		}

		if (badSymbols.size() > 0) {
			throw new ValidatorException(
				"Failed to retrieve symbols", badSymbols);
		}
	}

}