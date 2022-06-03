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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CSVUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author Shuyang Zhou
 * @author Igor Beslic
 */
public class ColumnValuesExtractor {

	public ColumnValuesExtractor(
		Map<String, Field> fieldMap, List<String> fieldNames) {

		List<UnsafeFunction<Object, Object, ReflectiveOperationException>>
			unsafeFunctions = new ArrayList<>(fieldNames.size());

		for (String fieldName : fieldNames) {
			Field field = fieldMap.get(fieldName);

			if (field != null) {
				Class<?> fieldClass = field.getType();

				if (ItemClassIndexUtil.isSingleColumnAdoptableValue(
						fieldClass)) {

					unsafeFunctions.add(
						item -> {
							if (field.get(item) == null) {
								return StringPool.BLANK;
							}

							return field.get(item);
						});

					continue;
				}

				if (ItemClassIndexUtil.isSingleColumnAdoptableArray(
						fieldClass)) {

					if (!Objects.equals(
							fieldClass.getComponentType(), String.class)) {

						unsafeFunctions.add(
							item -> {
								if (field.get(item) == null) {
									return StringPool.BLANK;
								}

								return CSVUtil.encode(field.get(item));
							});

						continue;
					}

					unsafeFunctions.add(
						item -> {
							if (field.get(item) == null) {
								return StringPool.BLANK;
							}

							ByteArrayOutputStream byteArrayOutputStream =
								new ByteArrayOutputStream();

							try (CSVPrinter csvPrinter = new CSVPrinter(
									new OutputStreamWriter(
										byteArrayOutputStream),
									CSVFormat.DEFAULT)) {

								csvPrinter.print(
									StringUtil.merge(
										(String[])field.get(item),
										value -> CSVUtil.encode(value),
										StringPool.COMMA));
							}
							catch (IOException ioException) {
								_log.error(
									"Unable to export array to column",
									ioException);

								return StringPool.BLANK;
							}

							return new String(
								byteArrayOutputStream.toByteArray());
						});

					continue;
				}

				unsafeFunctions.add(item -> StringPool.BLANK);

				continue;
			}

			int index = fieldName.indexOf(CharPool.UNDERLINE);

			if (index == -1) {
				throw new IllegalArgumentException(
					"Invalid field name : " + fieldName);
			}

			String prefixFieldName = fieldName.substring(0, index);

			Field mapField = fieldMap.get(prefixFieldName);

			if (mapField == null) {
				throw new IllegalArgumentException(
					"Invalid field name : " + fieldName);
			}

			if (mapField.getType() != Map.class) {
				throw new IllegalArgumentException(
					"Invalid field name : " + fieldName +
						", it is not Map type.");
			}

			String key = fieldName.substring(index + 1);

			unsafeFunctions.add(
				item -> {
					Map<?, ?> map = (Map<?, ?>)mapField.get(item);

					Object value = map.get(key);

					if (value == null) {
						return StringPool.BLANK;
					}

					return value;
				});
		}

		_unsafeFunctions = unsafeFunctions;
	}

	public List<Object> extractValues(Object item)
		throws ReflectiveOperationException {

		List<Object> values = new ArrayList<>(_unsafeFunctions.size());

		for (UnsafeFunction<Object, Object, ReflectiveOperationException>
				unsafeFunction : _unsafeFunctions) {

			values.add(unsafeFunction.apply(item));
		}

		return values;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ColumnValuesExtractor.class);

	private final List
		<UnsafeFunction<Object, Object, ReflectiveOperationException>>
			_unsafeFunctions;

}