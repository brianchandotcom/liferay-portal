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

package com.liferay.info.fields;

import com.liferay.info.fields.type.InfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public class InfoField implements InfoFieldSetEntry {

	public InfoField(
		InfoFieldType infoFieldType,
		InfoLocalizedValue<String> labelInfoLocalizedValue, String name) {

		_infoFieldType = infoFieldType;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
		_name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof InfoField)) {
			return false;
		}

		InfoField infoDisplayField = (InfoField)obj;

		if (Objects.equals(_infoFieldType, infoDisplayField._infoFieldType) &&
			Objects.equals(
				_labelInfoLocalizedValue,
				infoDisplayField._labelInfoLocalizedValue) &&
			Objects.equals(_name, infoDisplayField._name)) {

			return true;
		}

		return false;
	}

	public InfoFieldType getInfoFieldType() {
		return _infoFieldType;
	}

	@Override
	public String getLabel(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	@Override
	public InfoLocalizedValue getLabelInfoLocalizedValue() {
		return _labelInfoLocalizedValue;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _infoFieldType);

		hash = HashUtil.hash(hash, _labelInfoLocalizedValue);

		return HashUtil.hash(hash, _name);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{name: ");
		sb.append(_name);
		sb.append(", type: ");
		sb.append(_infoFieldType.getName());
		sb.append("}");

		return sb.toString();
	}

	private final InfoFieldType _infoFieldType;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;
	private final String _name;

}