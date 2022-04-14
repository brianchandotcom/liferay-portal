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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.MapUtil;

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
public class InfoFieldSet implements InfoFieldSetEntry {

	public static Builder builder() {
		return new Builder();
	}

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
				_builder._labelInfoLocalizedValue,
				infoFieldSet._builder._labelInfoLocalizedValue) &&
			Objects.equals(_builder._name, infoFieldSet._builder._name)) {

			return true;
		}

		return false;
	}

	public List<InfoField> getAllInfoFields() {
		List<InfoField> allInfoFields = new ArrayList<>();

		for (InfoFieldSetEntry infoFieldSetEntry :
				_builder._infoFieldSetEntries.values()) {

			if (infoFieldSetEntry instanceof InfoField) {
				allInfoFields.add((InfoField)infoFieldSetEntry);
			}
			else if (infoFieldSetEntry instanceof InfoFieldSet) {
				InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

				allInfoFields.addAll(infoFieldSet.getAllInfoFields());
			}
		}

		return allInfoFields;
	}

	public List<InfoFieldSetEntry> getInfoFieldSetEntries() {
		return new ArrayList<>(_builder._infoFieldSetEntries.values());
	}

	public InfoFieldSetEntry getInfoFieldSetEntry(String name) {
		return _builder._infoFieldSetEntries.get(name);
	}

	@Override
	public String getLabel(Locale locale) {
		return _builder._labelInfoLocalizedValue.getValue(locale);
	}

	@Override
	public InfoLocalizedValue<String> getLabelInfoLocalizedValue() {
		return _builder._labelInfoLocalizedValue;
	}

	@Override
	public String getName() {
		return _builder._name;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _builder._labelInfoLocalizedValue);

		return HashUtil.hash(hash, _builder._name);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{_infoFieldSetEntries: ",
			MapUtil.toString(_builder._infoFieldSetEntries), ", name: ",
			_builder._name, "}");
	}

	public static class Builder {

		public InfoFieldSet build() {
			return new InfoFieldSet(this);
		}

		public Builder infoFieldSetEntries(
			Collection<InfoFieldSetEntry> infoFieldSetEntries) {

			for (InfoFieldSetEntry infoFieldSetEntry : infoFieldSetEntries) {
				infoFieldSetEntry(infoFieldSetEntry);
			}

			return this;
		}

		public Builder infoFieldSetEntry(InfoFieldSetEntry infoFieldSetEntry) {
			_infoFieldSetEntries.put(
				infoFieldSetEntry.getName(), infoFieldSetEntry);

			return this;
		}

		public <T extends Throwable> Builder infoFieldSetEntry(
				UnsafeConsumer<UnsafeConsumer<InfoFieldSetEntry, T>, T>
					unsafeConsumer)
			throws T {

			unsafeConsumer.accept(this::infoFieldSetEntry);

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

		private final Map<String, InfoFieldSetEntry> _infoFieldSetEntries =
			new LinkedHashMap<>();
		private InfoLocalizedValue<String> _labelInfoLocalizedValue;
		private String _name;

	}

	private InfoFieldSet(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}