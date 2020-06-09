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

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Jorge Ferrer
 */
public class InfoFieldSet implements InfoFieldSetEntry {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InfoFieldSet)) {
			return false;
		}

		InfoFieldSet infoFieldSet = (InfoFieldSet)object;

		if (Objects.equals(
				_labelInfoLocalizedValue,
				infoFieldSet._labelInfoLocalizedValue) &&
			Objects.equals(_name, infoFieldSet._name)) {

			return true;
		}

		return false;
	}

	public List<InfoField> getAllInfoFields() {
		List<InfoField> allFields = new ArrayList<>();

		for (InfoFieldSetEntry infoFieldSetEntry : _entries.values()) {
			if (infoFieldSetEntry instanceof InfoField) {
				allFields.add((InfoField)infoFieldSetEntry);
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				allFields.addAll(infoFieldSet.getAllInfoFields());
			}
		}

		return allFields;
	}

	public List<InfoFieldSetEntry> getInfoFieldSetEntries() {
		return new ArrayList<>(_entries.values());
	}

	public InfoFieldSetEntry getInfoFieldSetEntry(String name) {
		return _entries.get(name);
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
		int hash = HashUtil.hash(0, _labelInfoLocalizedValue);

		return HashUtil.hash(hash, _name);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{entries: ");
		sb.append(_entries.size());
		sb.append(", name: ");
		sb.append(_name);
		sb.append("}");

		return sb.toString();
	}

	public static class Builder {

		public Builder(
			InfoLocalizedValue<String> labelInfoLocalizedValue, String name) {

			_labelInfoLocalizedValue = labelInfoLocalizedValue;
			_name = name;
		}

		public Builder add(Consumer<Consumer<InfoFieldSetEntry>> consumer) {
			consumer.accept(this::add);

			return this;
		}

		public Builder add(InfoFieldSetEntry fieldSetEntry) {
			_entries.put(fieldSetEntry.getName(), fieldSetEntry);

			return this;
		}

		public Builder addAll(Collection<InfoFieldSetEntry> fieldSetEntries) {
			for (InfoFieldSetEntry fieldSetEntry : fieldSetEntries) {
				add(fieldSetEntry);
			}

			return this;
		}

		public InfoFieldSet build() {
			return new InfoFieldSet(
				Collections.unmodifiableMap(_entries), _labelInfoLocalizedValue,
				_name);
		}

		private final Map<String, InfoFieldSetEntry> _entries =
			new LinkedHashMap<>();
		private final InfoLocalizedValue<String> _labelInfoLocalizedValue;
		private final String _name;

	}

	private InfoFieldSet(
		Map<String, InfoFieldSetEntry> entries,
		InfoLocalizedValue<String> labelInfoLocalizedValue, String name) {

		_entries = entries;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
		_name = name;
	}

	private final Map<String, InfoFieldSetEntry> _entries;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;
	private final String _name;

}