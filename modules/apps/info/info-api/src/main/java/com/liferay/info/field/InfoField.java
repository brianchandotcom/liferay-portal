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

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoField(
		T infoFieldType, InfoLocalizedValue<String> labelInfoLocalizedValue,
		boolean localizable, String name) {

		this(
			builder(
			).infoFieldType(
				infoFieldType
			).name(
				name
			).labelInfoLocalizedValue(
				labelInfoLocalizedValue
			).localizable(
				localizable
			)._builder);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoField(
		T infoFieldType, InfoLocalizedValue<String> labelInfoLocalizedValue,
		String name) {

		this(
			builder(
			).infoFieldType(
				infoFieldType
			).name(
				name
			).labelInfoLocalizedValue(
				labelInfoLocalizedValue
			)._builder);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InfoField)) {
			return false;
		}

		InfoField infoField = (InfoField)object;

		if (Objects.equals(
				_builder._infoFieldType, infoField._builder._infoFieldType) &&
			Objects.equals(
				_builder._labelInfoLocalizedValue,
				infoField._builder._labelInfoLocalizedValue) &&
			Objects.equals(_builder._name, infoField._builder._name)) {

			return true;
		}

		return false;
	}

	public <V> Optional<V> getAttributeOptional(
		InfoFieldType.Attribute<T, V> attribute) {

		return Optional.ofNullable((V)_builder._attributes.get(attribute));
	}

	public InfoFieldType getInfoFieldType() {
		return _builder._infoFieldType;
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
		int hash = HashUtil.hash(0, _builder._infoFieldType);

		hash = HashUtil.hash(hash, _builder._labelInfoLocalizedValue);

		return HashUtil.hash(hash, _builder._name);
	}

	public boolean isLocalizable() {
		return _builder._localizable;
	}

	public boolean isMultivalued() {
		return _builder._multivalued;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{name: ", _builder._name, ", type: ",
			_builder._infoFieldType.getName(), "}");
	}

	public static class Builder {

		public <T extends InfoFieldType> NameStep<T> infoFieldType(
			T infoFieldType) {

			_infoFieldType = infoFieldType;

			return new NameStep<>(this);
		}

		private Builder() {
		}

		private final Map
			<InfoFieldType.Attribute<? extends InfoFieldType, ?>, Object>
				_attributes = new HashMap<>();
		private InfoFieldType _infoFieldType;
		private InfoLocalizedValue<String> _labelInfoLocalizedValue;
		private boolean _localizable;
		private boolean _multivalued;
		private String _name;

	}

	public static class FinalStep<T extends InfoFieldType> {

		public <V> FinalStep<T> attribute(
			InfoFieldType.Attribute<T, V> attribute, V value) {

			_builder._attributes.put(attribute, value);

			return this;
		}

		public InfoField<T> build() {
			if (_builder._labelInfoLocalizedValue == null) {
				_builder._labelInfoLocalizedValue = InfoLocalizedValue.localize(
					InfoField.class, _builder._name);
			}

			return new InfoField<>(_builder);
		}

		public FinalStep<T> labelInfoLocalizedValue(
			InfoLocalizedValue<String> labelInfoLocalizedValue) {

			_builder._labelInfoLocalizedValue = labelInfoLocalizedValue;

			return this;
		}

		public FinalStep<T> localizable(boolean localizable) {
			_builder._localizable = localizable;

			return this;
		}

		public FinalStep<T> multivalued(boolean multivalued) {
			_builder._multivalued = multivalued;

			return this;
		}

		private FinalStep(Builder builder) {
			_builder = builder;
		}

		private final Builder _builder;

	}

	public static class NameStep<T extends InfoFieldType> {

		public FinalStep<T> name(String name) {
			_builder._name = name;

			return new FinalStep<>(_builder);
		}

		private NameStep(Builder builder) {
			_builder = builder;
		}

		private final Builder _builder;

	}

	private InfoField(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}