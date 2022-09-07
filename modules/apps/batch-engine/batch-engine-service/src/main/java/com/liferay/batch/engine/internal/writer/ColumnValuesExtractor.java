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

package com.liferay.batch.engine.internal.writer;

import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Shuyang Zhou
 * @author Igor Beslic
 */
public class ColumnValuesExtractor {

	public ColumnValuesExtractor(
		Map<String, Field> fieldMap, List<String> fieldNames) {

		_columnDescriptors = _getColumnDescriptors(
			fieldMap, fieldNames, 0, null);
	}

	public List<Object[]> extractValues(Object item)
		throws ReflectiveOperationException {

		List<Object[]> rows = new ArrayList<>();

		Object[] values = _getBlanksArray(_columnDescriptors.length);

		List<ColumnDescriptor> childFieldColumnDescriptors = new ArrayList<>();

		for (ColumnDescriptor columnDescriptor : _columnDescriptors) {
			if (columnDescriptor._isChild()) {
				childFieldColumnDescriptors.add(columnDescriptor);

				continue;
			}

			values[columnDescriptor._index] = columnDescriptor._getValue(item);
		}

		rows.add(values);

		int hash = -1;

		for (ColumnDescriptor childFieldColumnDescriptor :
				childFieldColumnDescriptors) {

			if (hash != childFieldColumnDescriptor._getParentHashCode()) {
				hash = childFieldColumnDescriptor._getParentHashCode();

				values = _getBlanksArray(_columnDescriptors.length);

				rows.add(values);
			}

			values[childFieldColumnDescriptor._index] =
				childFieldColumnDescriptor._getValue(item);
		}

		return rows;
	}

	public String[] getHeaders() {
		String[] headers = new String[_columnDescriptors.length];

		for (ColumnDescriptor columnDescriptor : _columnDescriptors) {
			headers[columnDescriptor._index] = columnDescriptor._getHeader();
		}

		return headers;
	}

	private <T> T[] _combine(T[] array1, T[] array2, int combinePos) {
		Class<?> array1Class = array1.getClass();

		T[] newArray = (T[])Array.newInstance(
			array1Class.getComponentType(), array1.length + array2.length - 1);

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, combinePos, array2.length);

		return newArray;
	}

	private Object[] _getBlanksArray(int size) {
		Object[] objects = new Object[size];

		Arrays.fill(objects, StringPool.BLANK);

		return objects;
	}

	private ColumnDescriptor[] _getColumnDescriptors(
		Map<String, Field> fieldMap, Collection<String> fieldNames,
		int masterIndex, ColumnDescriptor parentColumnDescriptor) {

		int localIndex = 0;
		ColumnDescriptor[] columnDescriptors =
			new ColumnDescriptor[fieldNames.size()];

		for (String fieldName : fieldNames) {
			Field field = fieldMap.get(fieldName);

			if (field == null) {
				throw new IllegalArgumentException("Field " + fieldName);
			}

			columnDescriptors[localIndex] = ColumnDescriptor._from(
				masterIndex++, _getUnsafeFunction(fieldMap, fieldName), field,
				parentColumnDescriptor);

			Class<?> fieldClass = field.getType();

			if (ItemClassIndexUtil.isSingleColumnAdoptableValue(fieldClass) ||
				ItemClassIndexUtil.isSingleColumnAdoptableArray(fieldClass) ||
				ItemClassIndexUtil.isMap(fieldClass)) {

				localIndex++;

				continue;
			}

			Map<String, Field> childFieldMap = ItemClassIndexUtil.index(
				fieldClass);

			ColumnDescriptor[] childFieldColumnDescriptors =
				_getColumnDescriptors(
					childFieldMap, _sort(childFieldMap.keySet()), localIndex,
					columnDescriptors[localIndex]);

			columnDescriptors = _combine(
				columnDescriptors, childFieldColumnDescriptors, localIndex);

			masterIndex = _getLastMasterIndex(childFieldColumnDescriptors) + 1;

			localIndex = localIndex + childFieldColumnDescriptors.length;
		}

		return columnDescriptors;
	}

	private int _getLastMasterIndex(ColumnDescriptor[] columnDescriptors) {
		ColumnDescriptor columnDescriptor =
			columnDescriptors[columnDescriptors.length - 1];

		return columnDescriptor._index;
	}

	private String _getListEntryKey(Object object) {
		ListEntry listEntry = (ListEntry)object;

		return listEntry.getKey();
	}

	private UnsafeFunction<Object, Object, ReflectiveOperationException>
		_getUnsafeFunction(Map<String, Field> fieldMap, String fieldName) {

		Field field = fieldMap.get(fieldName);

		if (field != null) {
			Class<?> fieldClass = field.getType();

			if (ItemClassIndexUtil.isSingleColumnAdoptableValue(fieldClass)) {
				return new UnsafeFunction
					<Object, Object, ReflectiveOperationException>() {

					@Override
					public Object apply(Object object)
						throws ReflectiveOperationException {

						if (field.get(object) == null) {
							return StringPool.BLANK;
						}

						return field.get(object);
					}

				};
			}

			if (ItemClassIndexUtil.isSingleColumnAdoptableArray(fieldClass)) {
				return new UnsafeFunction
					<Object, Object, ReflectiveOperationException>() {

					@Override
					public Object apply(Object object)
						throws ReflectiveOperationException {

						if (field.get(object) == null) {
							return StringPool.BLANK;
						}

						return StringUtil.merge(
							(Object[])field.get(object), CSVUtil::encode,
							StringPool.COMMA);
					}

				};
			}

			if (ItemClassIndexUtil.isMap(fieldClass)) {
				return new UnsafeFunction
					<Object, Object, ReflectiveOperationException>() {

					@Override
					public Object apply(Object object)
						throws ReflectiveOperationException {

						Map<?, ?> map = (Map<?, ?>)field.get(object);

						if (map == null) {
							return StringPool.BLANK;
						}

						StringBundler sb = new StringBundler(map.size() * 3);

						Set<? extends Map.Entry<?, ?>> entries = map.entrySet();

						Iterator<? extends Map.Entry<?, ?>> iterator =
							entries.iterator();

						while (iterator.hasNext()) {
							Map.Entry<?, ?> entry = iterator.next();

							sb.append(CSVUtil.encode(entry.getKey()));

							sb.append(StringPool.COLON);

							if (entry.getValue() != null) {
								sb.append(CSVUtil.encode(entry.getValue()));
							}
							else {
								sb.append(StringPool.BLANK);
							}

							if (iterator.hasNext()) {
								sb.append(StringPool.COMMA_AND_SPACE);
							}
						}

						return sb.toString();
					}

				};
			}

			return new UnsafeFunction
				<Object, Object, ReflectiveOperationException>() {

				@Override
				public Object apply(Object object)
					throws ReflectiveOperationException {

					if (field.get(object) == null) {
						return StringPool.BLANK;
					}

					return CSVUtil.encode(object);
				}

			};
		}

		Field propertiesField = fieldMap.get("properties");

		if (!ItemClassIndexUtil.isObjectEntryProperties(propertiesField)) {
			throw new IllegalArgumentException(
				"Invalid field name : " + fieldName);
		}

		return new UnsafeFunction
			<Object, Object, ReflectiveOperationException>() {

			@Override
			public Object apply(Object object)
				throws ReflectiveOperationException {

				Map<?, ?> map = (Map<?, ?>)propertiesField.get(object);

				Object value = map.get(fieldName);

				if (value == null) {
					return StringPool.BLANK;
				}

				if (ItemClassIndexUtil.isListEntry(value)) {
					return _getListEntryKey(value);
				}

				if (value instanceof String) {
					return CSVUtil.encode(value);
				}

				return value;
			}

		};
	}

	private Collection<String> _sort(Collection<String> collection) {
		return ListUtil.sort(
			new ArrayList<>(collection),
			(value1, value2) -> value1.compareToIgnoreCase(value2));
	}

	private final ColumnDescriptor[] _columnDescriptors;

	private static class ColumnDescriptor {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof ColumnDescriptor)) {
				return false;
			}

			ColumnDescriptor other = (ColumnDescriptor)object;

			if (Objects.equals(_field, other._field) &&
				_parentColumnDescriptors.equals(
					other._parentColumnDescriptors)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return _field.hashCode();
		}

		private static ColumnDescriptor _from(
			int index,
			UnsafeFunction<Object, Object, ReflectiveOperationException>
				unsafeFunction,
			Field field, ColumnDescriptor parentColumnDescriptor) {

			ColumnDescriptor columnDescriptor = new ColumnDescriptor(
				index, field, unsafeFunction);

			if (parentColumnDescriptor == null) {
				return columnDescriptor;
			}

			columnDescriptor._add(parentColumnDescriptor);

			return columnDescriptor;
		}

		private ColumnDescriptor(
			int index, Field field,
			UnsafeFunction<Object, Object, ReflectiveOperationException>
				unsafeFunction) {

			_index = index;
			_field = field;
			_unsafeFunction = unsafeFunction;
		}

		private void _add(ColumnDescriptor columnDescriptor) {
			if (!columnDescriptor._parentColumnDescriptors.isEmpty()) {
				_parentColumnDescriptors.addAll(
					columnDescriptor._parentColumnDescriptors);
			}

			_parentColumnDescriptors.add(columnDescriptor);
		}

		private String _getHeader() {
			StringBundler sb = new StringBundler(
				(_parentColumnDescriptors.size() * 2) + 2);

			for (ColumnDescriptor columnDescriptor : _parentColumnDescriptors) {
				sb.append(_getSanitizedFieldName(columnDescriptor._field));
				sb.append(StringPool.PERIOD);
			}

			sb.append(_getSanitizedFieldName(_field));

			return sb.toString();
		}

		private int _getParentHashCode() {
			if (_parentColumnDescriptors.isEmpty()) {
				throw new UnsupportedOperationException();
			}

			ColumnDescriptor columnDescriptor = _parentColumnDescriptors.get(
				_parentColumnDescriptors.size() - 1);

			return columnDescriptor.hashCode();
		}

		private String _getSanitizedFieldName(Field field) {
			String name = field.getName();

			if (name.startsWith(StringPool.UNDERLINE)) {
				return name.substring(1);
			}

			return name;
		}

		private Object _getValue(Object object)
			throws ReflectiveOperationException {

			if (!_isChild()) {
				return _unsafeFunction.apply(object);
			}

			Object result = object;

			for (ColumnDescriptor columnDescriptor : _parentColumnDescriptors) {
				result = columnDescriptor._field.get(result);

				if (result == null) {
					return StringPool.BLANK;
				}
			}

			return _unsafeFunction.apply(result);
		}

		private boolean _isChild() {
			if (_parentColumnDescriptors.isEmpty()) {
				return false;
			}

			return true;
		}

		private final Field _field;
		private final int _index;
		private final List<ColumnDescriptor> _parentColumnDescriptors =
			new ArrayList<>();
		private final UnsafeFunction
			<Object, Object, ReflectiveOperationException> _unsafeFunction;

	}

}