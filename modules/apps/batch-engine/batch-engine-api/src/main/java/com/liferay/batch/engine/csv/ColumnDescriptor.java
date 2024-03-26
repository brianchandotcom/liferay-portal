/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.csv;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Matija Petanjek
 */
public class ColumnDescriptor {

	public static ColumnDescriptor from(
		Field field, String fieldName, int index, Method method,
		ColumnDescriptor parentColumnDescriptor,
		UnsafeFunction<Object, Object, ReflectiveOperationException>
			unsafeFunction) {

		ColumnDescriptor columnDescriptor = new ColumnDescriptor(
			field, fieldName, index, method, unsafeFunction);

		if (parentColumnDescriptor == null) {
			return columnDescriptor;
		}

		columnDescriptor._add(parentColumnDescriptor);

		return columnDescriptor;
	}

	public static ColumnDescriptor from(
		String fieldName, int index,
		UnsafeFunction<Object, Object, ReflectiveOperationException>
			unsafeFunction) {

		return from(null, fieldName, index, null, null, unsafeFunction);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ColumnDescriptor)) {
			return false;
		}

		ColumnDescriptor columnDescriptor = (ColumnDescriptor)object;

		if (Objects.equals(_field, columnDescriptor._field) &&
			_parentColumnDescriptors.equals(
				columnDescriptor._parentColumnDescriptors)) {

			return true;
		}

		return false;
	}

	public String getHeader() {
		StringBundler sb = new StringBundler(
			(_parentColumnDescriptors.size() * 2) + 2);

		for (ColumnDescriptor columnDescriptor : _parentColumnDescriptors) {
			sb.append(columnDescriptor._getSanitizedFieldName());
			sb.append(StringPool.PERIOD);
		}

		sb.append(_getSanitizedFieldName());

		return sb.toString();
	}

	public int getIndex() {
		return _index;
	}

	public int getParentHashCode() {
		if (_parentColumnDescriptors.isEmpty()) {
			throw new UnsupportedOperationException();
		}

		ColumnDescriptor columnDescriptor = _parentColumnDescriptors.get(
			_parentColumnDescriptors.size() - 1);

		return columnDescriptor.hashCode();
	}

	public Object getValue(Object object) throws ReflectiveOperationException {
		if (!isChild()) {
			return _unsafeFunction.apply(object);
		}

		Object result = object;

		for (ColumnDescriptor columnDescriptor : _parentColumnDescriptors) {
			if (columnDescriptor._method == null) {
				result = columnDescriptor._field.get(result);
			}
			else {
				result = columnDescriptor._method.invoke(result);
			}

			if (result == null) {
				return StringPool.BLANK;
			}
		}

		return _unsafeFunction.apply(result);
	}

	@Override
	public int hashCode() {
		return _field.hashCode();
	}

	public boolean isChild() {
		if (_parentColumnDescriptors.isEmpty()) {
			return false;
		}

		return true;
	}

	private ColumnDescriptor(
		Field field, String fieldName, int index, Method method,
		UnsafeFunction<Object, Object, ReflectiveOperationException>
			unsafeFunction) {

		_field = field;
		_fieldName = fieldName;
		_index = index;
		_method = method;
		_unsafeFunction = unsafeFunction;
	}

	private void _add(ColumnDescriptor columnDescriptor) {
		if (!columnDescriptor._parentColumnDescriptors.isEmpty()) {
			_parentColumnDescriptors.addAll(
				columnDescriptor._parentColumnDescriptors);
		}

		_parentColumnDescriptors.add(columnDescriptor);
	}

	private String _getSanitizedFieldName() {
		if (_fieldName.startsWith(StringPool.UNDERLINE)) {
			return _fieldName.substring(1);
		}

		return _fieldName;
	}

	private final Field _field;
	private final String _fieldName;
	private final int _index;
	private final Method _method;
	private final List<ColumnDescriptor> _parentColumnDescriptors =
		new ArrayList<>();
	private final UnsafeFunction<Object, Object, ReflectiveOperationException>
		_unsafeFunction;

}