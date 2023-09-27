/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class InfoItemFormVariation {

	public InfoItemFormVariation(
		long groupId, String key,
		InfoLocalizedValue<String> labelInfoLocalizedValue) {

		_groupId = groupId;
		_key = key;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InfoItemFormVariation)) {
			return false;
		}

		InfoItemFormVariation infoItemFormVariation =
			(InfoItemFormVariation)object;

		if (Objects.equals(_key, infoItemFormVariation._key)) {
			return true;
		}

		return false;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getKey() {
		return _key;
	}

	public String getLabel(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	public InfoLocalizedValue<String> getLabelInfoLocalizedValue() {
		return _labelInfoLocalizedValue;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _key);
	}

	private final long _groupId;
	private final String _key;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;

}