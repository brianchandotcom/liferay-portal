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

package com.liferay.portal.search.internal.document;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Michael C. Han
 * @author Wade Cao
 */
public class DocumentImpl implements Document {

	public DocumentImpl() {
		_fields = new LinkedHashMap<>();
	}

	public DocumentImpl(DocumentImpl documentImpl) {
		_fields = new LinkedHashMap<>(documentImpl._fields);
	}

	@Override
	public Boolean getBoolean(String name) {
		return GetterUtil.getBoolean(getValue(name));
	}

	@Override
	public List<Boolean> getBooleans(String name) {
		return getValues(name, GetterUtil::getBoolean);
	}

	@Override
	public String getDate(String name) {
		return getValue(name, String::valueOf);
	}

	@Override
	public List<String> getDates(String name) {
		return getValues(name, String::valueOf);
	}

	@Override
	public Double getDouble(String name) {
		return getValue(name, GetterUtil::getDouble);
	}

	@Override
	public List<Double> getDoubles(String name) {
		return getValues(name, GetterUtil::getDouble);
	}

	@Override
	public Map<String, Field> getFields() {
		return Collections.unmodifiableMap(_fields);
	}

	@Override
	public Float getFloat(String name) {
		return getValue(name, GetterUtil::getFloat);
	}

	@Override
	public List<Float> getFloats(String name) {
		return getValues(name, GetterUtil::getFloat);
	}

	@Override
	public GeoLocationPoint getGeoLocationPoint(String name) {
		return getValue(name, value -> (GeoLocationPoint)value);
	}

	@Override
	public List<GeoLocationPoint> getGeoLocationPoints(String name) {
		return getValues(name, value -> (GeoLocationPoint)value);
	}

	@Override
	public Integer getInteger(String name) {
		return getValue(name, GetterUtil::getInteger);
	}

	@Override
	public List<Integer> getIntegers(String name) {
		return getValues(name, GetterUtil::getInteger);
	}

	@Override
	public Long getLong(String name) {
		return getValue(name, GetterUtil::getLong);
	}

	@Override
	public List<Long> getLongs(String name) {
		return getValues(name, GetterUtil::getLong);
	}

	@Override
	public String getString(String name) {
		return getValue(name, String::valueOf);
	}

	@Override
	public List<String> getStrings(String name) {
		return getValues(name, String::valueOf);
	}

	@Override
	public Object getValue(String name) {
		Field field = _fields.get(name);

		if (field == null) {
			return null;
		}

		return field.getValue();
	}

	public <T> T getValue(String name, Function<Object, T> function) {
		Object value = getValue(name);

		if (value != null) {
			return function.apply(value);
		}

		return null;
	}

	@Override
	public List<Object> getValues(String name) {
		Field field = _fields.get(name);

		if (field == null) {
			return Collections.emptyList();
		}

		return field.getValues();
	}

	public <T> List<T> getValues(String name, Function<Object, T> function) {
		List<Object> values = getValues(name);

		Stream<Object> stream = values.stream();

		return stream.map(
			function
		).collect(
			Collectors.toList()
		);
	}

	public void setFieldValues(String name, Collection<Object> values) {
		if ((values == null) || values.isEmpty()) {
			removeField(name);
		}
		else {
			putField(name, values);
		}
	}

	public void unsetField(String name) {
		removeField(name);
	}

	protected Field putField(String name, Collection<Object> values) {
		return _fields.put(name, new FieldImpl(name, values));
	}

	protected Field removeField(String name) {
		return _fields.remove(name);
	}

	protected void setFieldValue(String name, Object value) {
		if (_isEmpty(value)) {
			removeField(name);
		}
		else {
			putField(name, Collections.singleton(value));
		}
	}

	protected void setFieldValues(String name, Object[] values) {
		setFieldValues(name, _toCollection(values));
	}

	private static Collection<Object> _toCollection(Object[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return null;
		}

		if ((values.length == 1) && (values[0] == null)) {
			return null;
		}

		return Arrays.asList(values);
	}

	private boolean _isEmpty(Object value) {
		if (value == null) {
			return true;
		}

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			if (collection.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	private final Map<String, Field> _fields;

}