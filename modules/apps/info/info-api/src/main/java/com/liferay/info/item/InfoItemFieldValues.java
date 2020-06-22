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

package com.liferay.info.item;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class InfoItemFieldValues {

	public InfoItemFieldValues(
		InfoItemClassPKReference infoItemClassPKReference) {

		_infoItemClassPKReference = infoItemClassPKReference;
	}

	public InfoItemFieldValues add(InfoFieldValue<Object> infoFieldValue) {
		_infoFieldValues.add(infoFieldValue);

		InfoField infoField = infoFieldValue.getInfoField();

		Collection<InfoFieldValue<Object>> infoFieldValues =
			_infoFieldValuesByName.computeIfAbsent(
				infoField.getName(), key -> new ArrayList<>());

		infoFieldValues.add(infoFieldValue);

		return this;
	}

	public InfoItemFieldValues addAll(
		List<InfoFieldValue<Object>> infoFieldValues) {

		for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
			add(infoFieldValue);
		}

		return this;
	}

	public InfoFieldValue<Object> getInfoFieldValue(String fieldName) {
		Collection<InfoFieldValue<Object>> infoFieldValues =
			_infoFieldValuesByName.get(fieldName);

		if (infoFieldValues != null) {
			Iterator<InfoFieldValue<Object>> iterator =
				infoFieldValues.iterator();

			if (iterator.hasNext()) {
				return iterator.next();
			}
		}

		return null;
	}

	public Collection<InfoFieldValue<Object>> getInfoFieldValues() {
		return _infoFieldValues;
	}

	public Collection<InfoFieldValue<Object>> getInfoFieldValues(
		String fieldName) {

		return _infoFieldValuesByName.getOrDefault(
			fieldName, Collections.emptyList());
	}

	public InfoItemClassPKReference getInfoItemClassPKReference() {
		return _infoItemClassPKReference;
	}

	public Map<String, Object> getMap(Locale locale) {
		Map<String, Object> map = new HashMap<>(_infoFieldValues.size());

		for (InfoFieldValue<Object> infoFieldValue : _infoFieldValues) {
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

		sb.append("{infoFieldValues: ");
		sb.append(_infoFieldValues.size());
		sb.append("}");

		return sb.toString();
	}

	private final Collection<InfoFieldValue<Object>> _infoFieldValues =
		new LinkedHashSet<>();
	private final Map<String, Collection<InfoFieldValue<Object>>>
		_infoFieldValuesByName = new HashMap<>();
	private InfoItemClassPKReference _infoItemClassPKReference;

}