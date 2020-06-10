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

package com.liferay.info.form;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.lang.HashUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class InfoForm {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InfoForm)) {
			return false;
		}

		InfoForm infoForm = (InfoForm)object;

		if (Objects.equals(
				_descriptionInfoLocalizedValue,
				infoForm._descriptionInfoLocalizedValue) &&
			Objects.equals(
				_labelInfoLocalizedValue, infoForm._labelInfoLocalizedValue) &&
			Objects.equals(_name, infoForm._name)) {

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

	public InfoLocalizedValue<String> getDescriptionInfoLocalizedValue() {
		return _descriptionInfoLocalizedValue;
	}

	public List<InfoFieldSetEntry> getInfoFieldSetEntries() {
		return new ArrayList<>(_entries.values());
	}

	public InfoFieldSetEntry getInfoFieldSetEntry(String name) {
		return _entries.get(name);
	}

	public String getLabel(Locale locale) {
		return _labelInfoLocalizedValue.getValue(locale);
	}

	public InfoLocalizedValue<String> getLabelInfoLocalizedValue() {
		return _labelInfoLocalizedValue;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _descriptionInfoLocalizedValue);

		hash = HashUtil.hash(hash, _labelInfoLocalizedValue);

		return HashUtil.hash(hash, _name);
	}

	public static class Builder {

		public Builder(String name) {
			_name = name;
		}

		public Builder add(InfoFieldSet fieldSet) {
			InfoFieldSetEntry infoFieldSetEntry = _entries.get(
				fieldSet.getName());

			if (infoFieldSetEntry instanceof InfoFieldSet) {
				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				_entries.put(
					infoFieldSet.getName(),
					new InfoFieldSet.Builder(
						infoFieldSet.getLabelInfoLocalizedValue(),
						infoFieldSet.getName()
					).addAll(
						infoFieldSet.getInfoFieldSetEntries()
					).addAll(
						fieldSet.getInfoFieldSetEntries()
					).build());
			}
			else {
				_entries.put(fieldSet.getName(), fieldSet);
			}

			return this;
		}

		public Builder add(InfoFieldSetEntry fieldSetEntry) {
			_entries.put(fieldSetEntry.getName(), fieldSetEntry);

			return this;
		}

		public <T extends Throwable> Builder add(
				UnsafeConsumer<UnsafeConsumer<InfoFieldSetEntry, T>, T>
					consumer)
			throws T {

			consumer.accept(this::add);

			return this;
		}

		public Builder addAll(Collection<InfoFieldSetEntry> fieldSetEntries) {
			for (InfoFieldSetEntry fieldSetEntry : fieldSetEntries) {
				add(fieldSetEntry);
			}

			return this;
		}

		public InfoForm build() {
			return new InfoForm(
				_descriptionInfoLocalizedValue,
				Collections.unmodifiableMap(_entries), _labelInfoLocalizedValue,
				_name);
		}

		public Builder setDescriptionInfoLocalizedValue(
			InfoLocalizedValue<String> description) {

			_descriptionInfoLocalizedValue = description;

			return this;
		}

		public Builder setLabelInfoLocalizedValue(
			InfoLocalizedValue<String> labelInfoLocalizedValue) {

			_labelInfoLocalizedValue = labelInfoLocalizedValue;

			return this;
		}

		private InfoLocalizedValue<String> _descriptionInfoLocalizedValue;
		private final Map<String, InfoFieldSetEntry> _entries =
			new LinkedHashMap<>();
		private InfoLocalizedValue<String> _labelInfoLocalizedValue;
		private final String _name;

	}

	private InfoForm(
		InfoLocalizedValue<String> descriptionInfoLocalizedValue,
		Map<String, InfoFieldSetEntry> entries,
		InfoLocalizedValue<String> labelInfoLocalizedValue, String name) {

		_descriptionInfoLocalizedValue = descriptionInfoLocalizedValue;
		_entries = entries;
		_labelInfoLocalizedValue = labelInfoLocalizedValue;
		_name = name;
	}

	private final InfoLocalizedValue<String> _descriptionInfoLocalizedValue;
	private final Map<String, InfoFieldSetEntry> _entries;
	private final InfoLocalizedValue<String> _labelInfoLocalizedValue;
	private final String _name;

}