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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoItemFieldValues(
		InfoItemClassPKReference infoItemClassPKReference) {

		this(builder().infoItemClassPKReference(infoItemClassPKReference));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoItemFieldValues add(InfoFieldValue<Object> infoFieldValue) {
		_builder.infoFieldValue(infoFieldValue);

		return this;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public InfoItemFieldValues addAll(
		List<InfoFieldValue<Object>> infoFieldValues) {

		_builder.infoFieldValues(infoFieldValues);

		return this;
	}

	public InfoFieldValue<Object> getInfoFieldValue(String infoFieldName) {
		Collection<InfoFieldValue<Object>> infoFieldValues =
			_builder._infoFieldValuesMap.get(infoFieldName);

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

		return _builder._infoFieldValuesMap.getOrDefault(
			infoFieldName, Collections.emptyList());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 * #getInfoItemReference()}
	 */
	@Deprecated
	public InfoItemClassPKReference getInfoItemClassPKReference() {
		return _builder._infoItemClassPKReference;
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
		}

		return map;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setInfoItemClassPKReference(
		InfoItemClassPKReference infoItemClassPKReference) {

		_builder.infoItemClassPKReference(infoItemClassPKReference);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{infoFieldValues: ");
		sb.append(_builder._infoFieldValues);
		sb.append("}");

		return sb.toString();
	}

	public static class Builder {

		public InfoItemFieldValues build() {
			return new InfoItemFieldValues(this);
		}

		public Builder infoFieldValue(InfoFieldValue<Object> infoFieldValue) {
			_infoFieldValues.add(infoFieldValue);

			InfoField infoField = infoFieldValue.getInfoField();

			Collection<InfoFieldValue<Object>> infoFieldValues =
				_infoFieldValuesMap.computeIfAbsent(
					infoField.getName(), key -> new ArrayList<>());

			infoFieldValues.add(infoFieldValue);

			return this;
		}

		public <T extends Throwable> Builder infoFieldValue(
				UnsafeConsumer<UnsafeConsumer<InfoFieldValue<Object>, T>, T>
					consumer)
			throws T {

			consumer.accept(this::infoFieldValue);

			return this;
		}

		public Builder infoFieldValues(
			List<InfoFieldValue<Object>> infoFieldValues) {

			for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
				infoFieldValue(infoFieldValue);
			}

			return this;
		}

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by {@link
		 * #infoItemReference(InfoItemReference)}
		 */
		@Deprecated
		public Builder infoItemClassPKReference(
			InfoItemClassPKReference infoItemClassPKReference) {

			_infoItemClassPKReference = infoItemClassPKReference;

			return this;
		}

		public Builder infoItemReference(InfoItemReference infoItemReference) {
			_infoItemReference = infoItemReference;

			return this;
		}

		private final Collection<InfoFieldValue<Object>> _infoFieldValues =
			new LinkedHashSet<>();
		private final Map<String, Collection<InfoFieldValue<Object>>>
			_infoFieldValuesMap = new HashMap<>();
		private InfoItemClassPKReference _infoItemClassPKReference;
		private InfoItemReference _infoItemReference;

	}

	private InfoItemFieldValues(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}