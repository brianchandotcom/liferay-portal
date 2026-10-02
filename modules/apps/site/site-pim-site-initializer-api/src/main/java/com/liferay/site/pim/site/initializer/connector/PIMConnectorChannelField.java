/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.connector;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Andrea Sbarra
 * @author Stefano Motta
 */
public class PIMConnectorChannelField {

	public PIMConnectorChannelField(
		String labelKey, boolean multiple, String name, boolean required,
		String type) {

		_labelKey = labelKey;
		_multiple = multiple;
		_name = name;
		_required = required;
		_type = type;
	}

	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, _labelKey);
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public boolean isMultiple() {
		return _multiple;
	}

	public boolean isRequired() {
		return _required;
	}

	private final String _labelKey;
	private final boolean _multiple;
	private final String _name;
	private final boolean _required;
	private final String _type;

}
