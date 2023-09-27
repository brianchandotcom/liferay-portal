/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.type;

import com.liferay.info.localized.InfoLocalizedValue;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class KeyLocalizedLabelPair implements Keyed, Labeled {

	public KeyLocalizedLabelPair(
		String key, InfoLocalizedValue<String> labelInfoLocalizedValue) {

		_key = key;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getLabel(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	private final String _key;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;

}