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

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class InfoForm {

	public InfoForm(String name) {
		_name = name;
	}

	public InfoForm add(InfoFieldSetEntry fieldSetEntry) {
		_entries.put(fieldSetEntry.getName(), fieldSetEntry);

		return this;
	}

	public InfoForm addAll(Collection<InfoFieldSetEntry> fieldSetEntries) {
		for (InfoFieldSetEntry fieldSetEntry : fieldSetEntries) {
			add(fieldSetEntry);
		}

		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof InfoField)) {
			return false;
		}

		InfoForm infoItemFieldSet = (InfoForm)obj;

		if (Objects.equals(
				_descriptionInfoLocalizedValue,
				infoItemFieldSet._descriptionInfoLocalizedValue) &&
			Objects.equals(
				_labelInfoLocalizedValue,
				infoItemFieldSet._labelInfoLocalizedValue) &&
			Objects.equals(_name, infoItemFieldSet._name)) {

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

	public InfoLocalizedValue getDescriptionInfoLocalizedValue() {
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

	public InfoLocalizedValue getLabelInfoLocalizedValue() {
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

	public void setDescriptionInfoLocalizedValue(
		InfoLocalizedValue<String> description) {

		_descriptionInfoLocalizedValue = description;
	}

	public void setLabelInfoLocalizedValue(
		InfoLocalizedValue<String> labelInfoLocalizedValue) {

		_labelInfoLocalizedValue = labelInfoLocalizedValue;
	}

	private InfoLocalizedValue<String> _descriptionInfoLocalizedValue;
	private final Map<String, InfoFieldSetEntry> _entries =
		new LinkedHashMap<>();
	private InfoLocalizedValue<String> _labelInfoLocalizedValue;
	private final String _name;

}