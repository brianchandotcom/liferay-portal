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

package com.liferay.info.localized;

import com.liferay.info.localized.bundle.ResourceBundleInfoLocalizedValue;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public interface InfoLocalizedValue<T> {

	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

	public static InfoLocalizedValue<String> localize(
		Class<?> clazz, String valueKey) {

		return new ResourceBundleInfoLocalizedValue(clazz, valueKey);
	}

	public static InfoLocalizedValue<String> localize(
		String symbolicName, String valueKey) {

		return new ResourceBundleInfoLocalizedValue(symbolicName, valueKey);
	}

	public static InfoLocalizedValue<String> singleValue(String value) {
		return new SingleValueInfoLocalizedValue(value);
	}

	public Set<Locale> getAvailableLocales();

	public Locale getDefaultLocale();

	public T getValue();

	public T getValue(Locale locale);

	public static class Builder<T> {

		public InfoLocalizedValue<T> build() {
			return new BuilderInfoLocalizedValue<>(
				_defaultLocale, Collections.unmodifiableMap(_values));
		}

		public Builder<T> defaultLocale(Locale locale) {
			_defaultLocale = locale;

			return this;
		}

		public Builder<T> put(Locale locale, T value) {
			_values.put(locale, value);

			return this;
		}

		public <E extends Exception> Builder<T> put(
				UnsafeConsumer<UnsafeBiConsumer<Locale, T, E>, E> consumer)
			throws E {

			consumer.accept(this::put);

			return this;
		}

		public Builder<T> putAll(Map<Locale, T> values) {
			_values.putAll(values);

			return this;
		}

		private Builder() {
		}

		private Locale _defaultLocale;
		private final Map<Locale, T> _values = new HashMap<>();

	}

	public static class BuilderInfoLocalizedValue<T>
		implements InfoLocalizedValue<T> {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof BuilderInfoLocalizedValue)) {
				return false;
			}

			BuilderInfoLocalizedValue builderInfoLocalizedValue =
				(BuilderInfoLocalizedValue)object;

			if (Objects.equals(
					_defaultLocale, builderInfoLocalizedValue._defaultLocale) &&
				Objects.equals(_values, builderInfoLocalizedValue._values)) {

				return true;
			}

			return false;
		}

		@Override
		public Set<Locale> getAvailableLocales() {
			return _values.keySet();
		}

		@Override
		public Locale getDefaultLocale() {
			if (_defaultLocale == null) {
				return LocaleUtil.getDefault();
			}

			return _defaultLocale;
		}

		@Override
		public T getValue() {
			return _values.get(getDefaultLocale());
		}

		@Override
		public T getValue(Locale locale) {
			T value = _values.get(locale);

			if (value == null) {
				value = _values.get(getDefaultLocale());
			}

			if (value instanceof String) {
				value = (T)LanguageUtil.get(locale, (String)value);
			}

			return value;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _defaultLocale);

			return HashUtil.hash(hash, _values);
		}

		private BuilderInfoLocalizedValue(
			Locale defaultLocale, Map<Locale, T> values) {

			_defaultLocale = defaultLocale;
			_values = values;
		}

		private final Locale _defaultLocale;
		private final Map<Locale, T> _values;

	}

}