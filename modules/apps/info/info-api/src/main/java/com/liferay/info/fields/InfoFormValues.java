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

import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.petra.string.StringBundler;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class InfoFormValues {

	public InfoFormValues add(InfoFieldValue infoFieldValue) {
		_infoFieldValues.add(infoFieldValue);

		InfoField infoField = infoFieldValue.getInfoField();

		_infoFieldValuesByName.put(infoField.getName(), infoFieldValue);

		return this;
	}

	public InfoFormValues addAll(List<InfoFieldValue<Object>> infoFieldValues) {
		for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
			add(infoFieldValue);
		}

		return this;
	}

	public InfoFieldValue getInfoFieldValue(String fieldName) {
		return _infoFieldValuesByName.get(fieldName);
	}

	public Collection<InfoFieldValue> getInfoFieldValues() {
		return _infoFieldValues;
	}

	public InfoItemClassPKReference getInfoItemClassPKReference() {
		return _infoItemClassPKReference;
	}

	public Map<String, Object> getMap(Locale locale) {
		Map<String, Object> map = new HashMap<>(_infoFieldValues.size());

		for (InfoFieldValue infoFieldValue : _infoFieldValues) {
			InfoField infoField = infoFieldValue.getInfoField();

			map.put(infoField.getName(), infoFieldValue.getValue(locale));
		}

		return map;
	}

	public void setInfoItemClassPKReference(
		InfoItemClassPKReference infoItemClassPKReference) {

		_infoItemClassPKReference = infoItemClassPKReference;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{Form Values: ");
		sb.append(_infoFieldValues.size());
		sb.append("}");

		return sb.toString();
	}

	private final Collection<InfoFieldValue> _infoFieldValues =
		new LinkedHashSet<>();
	private final Map<String, InfoFieldValue> _infoFieldValuesByName =
		new HashMap<>();
	private InfoItemClassPKReference _infoItemClassPKReference;

}