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

package com.liferay.adaptive.media;

import com.liferay.adaptive.media.util.AMAttributeConverterUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents a characteristic of an {@link AdaptiveMedia} instance (e.g., its
 * width, size, etc.). Instances are annotated by a processor type and by the
 * attribute value type. The processor type restriction prevents {@link
 * AdaptiveMedia} users from requesting unsupported attributes (i.e., the set of
 * available attributes is checked at compile time). The attribute value type
 * annotation reduces (or avoids completely) the need to cast retrieved
 * attribute values.
 *
 * @author Adolfo Pérez
 */
public final class AMAttribute<T, V> {

	/**
	 * Returns a string-attribute map containing the available name-attribute
	 * pairs.
	 *
	 * @return the list of available attributes
	 */
	public static Map<String, AMAttribute<?, ?>> getAllowedAMAttributes() {
		return _allowedAMAttributes;
	}

	/**
	 * Returns a generic attribute representing the configuration UUID used to
	 * generate the media. This attribute can be used with any kind of media.
	 *
	 * @return the configuration UUID
	 */
	public static final <S> AMAttribute<S, String>
		getConfigurationUuidAMAttribute() {

		return (AMAttribute<S, String>)_AM_ATTRIBUTE_CONFIGURATION_UUID;
	}

	/**
	 * Returns a generic attribute representing the length of the media's
	 * content. This attribute can be used with any kind of media.
	 *
	 * @return the content length attribute
	 */
	public static final <S> AMAttribute<S, Long> getContentLengthAMAttribute() {
		return (AMAttribute<S, Long>)_AM_ATTRIBUTE_CONTENT_LENGTH;
	}

	/**
	 * Returns a generic attribute representing the media's content type. This
	 * attribute can be used with any kind of media.
	 *
	 * @return the content type attribute
	 */
	public static final <S> AMAttribute<S, String> getContentTypeAMAttribute() {
		return (AMAttribute<S, String>)_AM_ATTRIBUTE_CONTENT_TYPE;
	}

	/**
	 * Returns a generic attribute representing the media's file name (if any).
	 * This attribute can be used with any kind of media.
	 *
	 * @return the file name attribute
	 */
	public static final <S> AMAttribute<S, String> getFileNameAMAttribute() {
		return (AMAttribute<S, String>)_AM_ATTRIBUTE_FILE_NAME;
	}

	/**
	 * Creates a new attribute. All attributes live in the same global
	 * namespace.
	 *
	 * @param name a value that uniquely identifies the attribute
	 * @param converter a function that converts a <code>String</code> to a
	 *        value of the correct type; this function should throw an {@link
	 *        com.liferay.adaptive.media.exception.AMRuntimeException.AMAttributeFormatException}
	 *        if it cannot convert the <code>String</code>
	 * @param amDistanceComparator the comparator to order the two arguments
	 *        based on the distance between their values; it should return a
	 *        value between {@link Long#MIN_VALUE} and {@link Long#MAX_VALUE}
	 */
	public AMAttribute(
		String name, Function<String, V> converter,
		AMDistanceComparator<V> amDistanceComparator) {

		_name = name;
		_converterFunction = converter;
		_amDistanceComparator = amDistanceComparator;
	}

	/**
	 * Compares the two arguments for order.
	 *
	 * @param  value1 the first value to compare
	 * @param  value2 the second value to compare
	 * @return a negative long, zero, or positive long, depending on whether the
	 *         first argument is less than, equal to, or greater than the second
	 *         argument
	 */
	public long compare(V value1, V value2) {
		return _amDistanceComparator.compare(value1, value2);
	}

	/**
	 * Returns the value converted to the correct type.
	 *
	 * @param  value the value to convert
	 * @return the value converted to the correct type
	 */
	public V convert(String value) {
		return _converterFunction.apply(value);
	}

	/**
	 * Returns the distance between the two values.
	 *
	 * @param  value1 the first value
	 * @param  value2 the second value
	 * @return a value between 0 and {@link Long#MAX_VALUE}, representing the
	 *         distance between the two values
	 */
	public long distance(V value1, V value2) {
		return Math.abs(_amDistanceComparator.compare(value1, value2));
	}

	/**
	 * Returns this {@link AdaptiveMedia} instance's globally unique name.
	 *
	 * @return the attribute's name
	 */
	public String getName() {
		return _name;
	}

	private static final AMAttribute<?, String>
		_AM_ATTRIBUTE_CONFIGURATION_UUID = new AMAttribute<>(
			"configuration-uuid", s -> s, String::compareTo);

	private static final AMAttribute<?, Long> _AM_ATTRIBUTE_CONTENT_LENGTH =
		new AMAttribute<>(
			"content-length", AMAttributeConverterUtil::parseLong,
			(Long value1, Long value2) -> value1 - value2);

	private static final AMAttribute<?, String> _AM_ATTRIBUTE_CONTENT_TYPE =
		new AMAttribute<>("content-type", value -> value, String::compareTo);

	private static final AMAttribute<?, String> _AM_ATTRIBUTE_FILE_NAME =
		new AMAttribute<>("file-name", value -> value, String::compareTo);

	private static final Map<String, AMAttribute<?, ?>> _allowedAMAttributes =
		new HashMap<String, AMAttribute<?, ?>>() {
			{
				put(
					_AM_ATTRIBUTE_CONFIGURATION_UUID.getName(),
					_AM_ATTRIBUTE_CONFIGURATION_UUID);
				put(
					_AM_ATTRIBUTE_CONTENT_LENGTH.getName(),
					_AM_ATTRIBUTE_CONTENT_LENGTH);
				put(
					_AM_ATTRIBUTE_CONTENT_TYPE.getName(),
					_AM_ATTRIBUTE_CONTENT_TYPE);
				put(_AM_ATTRIBUTE_FILE_NAME.getName(), _AM_ATTRIBUTE_FILE_NAME);
			}
		};

	private final AMDistanceComparator<V> _amDistanceComparator;
	private final Function<String, V> _converterFunction;
	private final String _name;

}