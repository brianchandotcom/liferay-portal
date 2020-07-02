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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class InfoForm {

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoForm(String name) {
		this(builder().name(name));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoForm add(InfoFieldSet fieldSet) {
		_builder.add(fieldSet);

		return this;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoForm add(InfoFieldSetEntry fieldSetEntry) {
		_builder.add(fieldSetEntry);

		return this;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoForm addAll(Collection<InfoFieldSetEntry> fieldSetEntries) {
		_builder.addAll(fieldSetEntries);

		return this;
	}

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
				_builder._descriptionInfoLocalizedValue,
				infoForm._builder._descriptionInfoLocalizedValue) &&
			Objects.equals(
				_builder._labelInfoLocalizedValue,
				infoForm._builder._labelInfoLocalizedValue) &&
			Objects.equals(_builder._name, infoForm._builder._name)) {

			return true;
		}

		return false;
	}

	public List<InfoField> getAllInfoFields() {
		List<InfoField> allFields = new ArrayList<>();

		for (InfoFieldSetEntry infoFieldSetEntry : _builder._entries.values()) {
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
		return _builder._descriptionInfoLocalizedValue;
	}

	public List<InfoFieldSetEntry> getInfoFieldSetEntries() {
		return new ArrayList<>(_builder._entries.values());
	}

	public InfoFieldSetEntry getInfoFieldSetEntry(String name) {
		return _builder._entries.get(name);
	}

	public String getLabel(Locale locale) {
		return _builder._labelInfoLocalizedValue.getValue(locale);
	}

	public InfoLocalizedValue<String> getLabelInfoLocalizedValue() {
		return _builder._labelInfoLocalizedValue;
	}

	public String getName() {
		return _builder._name;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _builder._descriptionInfoLocalizedValue);

		hash = HashUtil.hash(hash, _builder._labelInfoLocalizedValue);

		return HashUtil.hash(hash, _builder._name);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setDescriptionInfoLocalizedValue(
		InfoLocalizedValue<String> descriptionInfoLocalizedValue) {

		_builder.descriptionInfoLocalizedValue(descriptionInfoLocalizedValue);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setLabelInfoLocalizedValue(
		InfoLocalizedValue<String> labelInfoLocalizedValue) {

		_builder.labelInfoLocalizedValue(labelInfoLocalizedValue);
	}

	public static class Builder {

		public Builder add(InfoFieldSet fieldSet) {
			InfoFieldSetEntry infoFieldSetEntry = _entries.get(
				fieldSet.getName());

			if ((infoFieldSetEntry != null) &&
				(infoFieldSetEntry instanceof InfoFieldSet)) {

				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				_entries.put(
					fieldSet.getName(),
					InfoFieldSet.builder(
					).addAll(
						infoFieldSet.getInfoFieldSetEntries()
					).addAll(
						fieldSet.getInfoFieldSetEntries()
					).labelInfoLocalizedValue(
						infoFieldSet.getLabelInfoLocalizedValue()
					).name(
						infoFieldSet.getName()
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
			return new InfoForm(this);
		}

		public Builder descriptionInfoLocalizedValue(
			InfoLocalizedValue<String> description) {

			_descriptionInfoLocalizedValue = description;

			return this;
		}

		public Builder labelInfoLocalizedValue(
			InfoLocalizedValue<String> labelInfoLocalizedValue) {

			_labelInfoLocalizedValue = labelInfoLocalizedValue;

			return this;
		}

		public Builder name(String name) {
			_name = name;

			return this;
		}

		private InfoLocalizedValue<String> _descriptionInfoLocalizedValue;
		private final Map<String, InfoFieldSetEntry> _entries =
			new LinkedHashMap<>();
		private InfoLocalizedValue<String> _labelInfoLocalizedValue;
		private String _name;

	}

	private InfoForm(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}