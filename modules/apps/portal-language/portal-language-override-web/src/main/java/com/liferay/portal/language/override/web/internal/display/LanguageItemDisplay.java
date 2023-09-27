/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.web.internal.display;

import com.liferay.petra.string.StringPool;

/**
 * @author Drew Brokke
 */
public class LanguageItemDisplay {

	public LanguageItemDisplay(String key, String value) {
		_key = key;
		_value = value;
	}

	public String getKey() {
		return _key;
	}

	public String getOverrideLanguageIdsString() {
		return _overrideLanguageIdsString;
	}

	public String getValue() {
		return _value;
	}

	public boolean isOverride() {
		return _override;
	}

	public boolean isOverrideSelectedLanguageId() {
		return _overrideSelectedLanguageId;
	}

	public void setOverride(boolean override) {
		_override = override;
	}

	public void setOverrideLanguageIdsString(String overrideLanguageIdsString) {
		_overrideLanguageIdsString = overrideLanguageIdsString;
	}

	public void setOverrideSelectedLanguageId(
		boolean overrideSelectedLanguageId) {

		_overrideSelectedLanguageId = overrideSelectedLanguageId;
	}

	private final String _key;
	private boolean _override;
	private String _overrideLanguageIdsString = StringPool.BLANK;
	private boolean _overrideSelectedLanguageId;
	private final String _value;

}