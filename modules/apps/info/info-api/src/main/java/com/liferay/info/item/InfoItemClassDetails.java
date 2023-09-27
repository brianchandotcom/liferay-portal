/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.localized.InfoLocalizedValue;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class InfoItemClassDetails {

	public InfoItemClassDetails(String className) {
		this(className, InfoLocalizedValue.modelResource(className));
	}

	public InfoItemClassDetails(
		String className, InfoLocalizedValue<String> labelInfoLocalizedValue) {

		_className = className;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
	}

	public String getClassName() {
		return _className;
	}

	public String getLabel(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	public InfoLocalizedValue<String> getLabelInfoLocalizedValue() {
		return _labelInfoLocalizedValue;
	}

	private final String _className;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;

}