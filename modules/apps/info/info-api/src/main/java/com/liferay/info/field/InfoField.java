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

package com.liferay.info.field;

import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
public class InfoField<T extends InfoFieldType> implements InfoFieldSetEntry {

	public InfoField(
		T infoFieldType, InfoLocalizedValue<String> labelInfoLocalizedValue,
		boolean localizable, String name) {

		this(infoFieldType, labelInfoLocalizedValue, name);

		_localizable = localizable;
	}

	public InfoField(
		T infoFieldType, InfoLocalizedValue<String> labelInfoLocalizedValue,
		String name) {

		_infoFieldType = infoFieldType;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
		_name = name;
	}

	public <V> void addAttribute(
		InfoFieldType.Attribute<T, V> attribute, V value) {

		_attributes.put(attribute, value);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InfoField)) {
			return false;
		}

		InfoField infoDisplayField = (InfoField)object;

		if (Objects.equals(_infoFieldType, infoDisplayField._infoFieldType) &&
			Objects.equals(
				_labelInfoLocalizedValue,
				infoDisplayField._labelInfoLocalizedValue) &&
			Objects.equals(_name, infoDisplayField._name)) {

			return true;
		}

		return false;
	}

	public <V> Optional<V> getAttributeOptional(
		InfoFieldType.Attribute<T, V> attribute) {

		return Optional.ofNullable((V)_attributes.get(attribute));
	}

	public InfoFieldType getInfoFieldType() {
		return _infoFieldType;
	}

	@Override
	public String getLabel(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	@Override
	public InfoLocalizedValue<String> getLabelInfoLocalizedValue() {
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

	public boolean isLocalizable() {
		return _localizable;
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

	private final Map<InfoFieldType.Attribute<T, ?>, Object> _attributes =
		new HashMap<>();
	private final T _infoFieldType;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;
	private boolean _localizable;
	private final String _name;

}