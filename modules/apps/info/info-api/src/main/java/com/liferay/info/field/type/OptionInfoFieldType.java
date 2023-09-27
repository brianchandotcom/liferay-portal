/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

import com.liferay.info.localized.InfoLocalizedValue;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class OptionInfoFieldType implements InfoFieldType {

	public OptionInfoFieldType(
		boolean active, InfoLocalizedValue<String> labelInfoLocalizedValue,
		String value) {

		_active = active;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
		_value = value;
	}

	public OptionInfoFieldType(
		InfoLocalizedValue<String> labelInfoLocalizedValue, String value) {

		_labelInfoLocalizedValue = labelInfoLocalizedValue;
		_value = value;

		_active = false;
	}

	public String getLabel(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	@Override
	public String getName() {
		return "option";
	}

	public String getValue() {
		return _value;
	}

	public boolean isActive() {
		return _active;
	}

	private final boolean _active;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;
	private final String _value;

}