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
import com.liferay.petra.function.UnsafeConsumer;
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

	public static Builder builder() {
		return new Builder();
	}

	public InfoFieldValue<Object> getInfoFieldValue(String infoFieldName) {
		Collection<InfoFieldValue<Object>> infoFieldValues =
			_builder._infoFieldValuesByIdMap.getOrDefault(
				infoFieldName, Collections.emptyList());

		if (infoFieldValues.isEmpty()) {
			infoFieldValues = _builder._infoFieldValuesByNameMap.getOrDefault(
				infoFieldName, Collections.emptyList());
		}

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
		return _builder._infoFieldValues;
	}

	public Collection<InfoFieldValue<Object>> getInfoFieldValues(
		String infoFieldName) {

		Collection<InfoFieldValue<Object>> infoFieldValues =
			_builder._infoFieldValuesByIdMap.getOrDefault(
				infoFieldName, Collections.emptyList());

		if (!infoFieldValues.isEmpty()) {
			return infoFieldValues;
		}

		return _builder._infoFieldValuesByNameMap.getOrDefault(
			infoFieldName, Collections.emptyList());
	}

	public InfoItemReference getInfoItemReference() {
		return _builder._infoItemReference;
	}

	public Map<String, Object> getMap(Locale locale) {
		Map<String, Object> map = new HashMap<>(
			_builder._infoFieldValues.size());

		for (InfoFieldValue<Object> infoFieldValue :
				_builder._infoFieldValues) {

			InfoField infoField = infoFieldValue.getInfoField();

			map.put(infoField.getName(), infoFieldValue.getValue(locale));
			map.put(infoField.getUniqueId(), infoFieldValue.getValue(locale));
		}

		return map;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{infoFieldValues: ", _builder._infoFieldValues, "}");
	}

	public static class Builder {

		public InfoItemFieldValues build() {
			return new InfoItemFieldValues(this);
		}

		public Builder infoFieldValue(InfoFieldValue<Object> infoFieldValue) {
			if (infoFieldValue == null) {
				return this;
			}

			_infoFieldValues.add(infoFieldValue);

			InfoField infoField = infoFieldValue.getInfoField();

			Collection<InfoFieldValue<Object>> infoFieldValues =
				_infoFieldValuesByNameMap.computeIfAbsent(
					infoField.getName(), key -> new ArrayList<>());

			infoFieldValues.add(infoFieldValue);

			infoFieldValues = _infoFieldValuesByIdMap.computeIfAbsent(
				infoField.getUniqueId(), key -> new ArrayList<>());

			infoFieldValues.add(infoFieldValue);

			return this;
		}

		public <T extends Throwable> Builder infoFieldValue(
				UnsafeConsumer<UnsafeConsumer<InfoFieldValue<Object>, T>, T>
					unsafeConsumer)
			throws T {

			unsafeConsumer.accept(this::infoFieldValue);

			return this;
		}

		public Builder infoFieldValues(
			List<InfoFieldValue<Object>> infoFieldValues) {

			for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
				infoFieldValue(infoFieldValue);
			}

			return this;
		}

		public Builder infoItemReference(InfoItemReference infoItemReference) {
			_infoItemReference = infoItemReference;

			return this;
		}

		private final Collection<InfoFieldValue<Object>> _infoFieldValues =
			new LinkedHashSet<>();
		private final Map<String, Collection<InfoFieldValue<Object>>>
			_infoFieldValuesByIdMap = new HashMap<>();
		private final Map<String, Collection<InfoFieldValue<Object>>>
			_infoFieldValuesByNameMap = new HashMap<>();
		private InfoItemReference _infoItemReference;

	}

	private InfoItemFieldValues(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}