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

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.util.AMAttributeConverterUtil;

import java.util.Comparator;
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
@ProviderType
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

		return (AMAttribute<S, String>)_CONFIGURATION_UUID;
	}

	/**
	 * Returns a generic attribute representing the length of the media's
	 * content. This attribute can be used with any kind of media.
	 *
	 * @return the content length attribute
	 */
	public static final <S>
		AMAttribute<S, Integer> getContentLengthAMAttribute() {

		return (AMAttribute<S, Integer>)_CONTENT_LENGTH;
	}

	/**
	 * Returns a generic attribute representing the media's content type. This
	 * attribute can be used with any kind of media.
	 *
	 * @return the content type attribute
	 */
	public static final <S> AMAttribute<S, String> getContentTypeAMAttribute() {
		return (AMAttribute<S, String>)_CONTENT_TYPE;
	}

	/**
	 * Returns a generic attribute representing the media's file name (if any).
	 * This attribute can be used with any kind of media.
	 *
	 * @return the file name attribute
	 */
	public static final <S> AMAttribute<S, String> getFileNameAMAttribute() {
		return (AMAttribute<S, String>)_FILE_NAME;
	}

	/**
	 * Creates a new attribute. All attributes live in the same global
	 * namespace.
	 *
	 * @param name a human-readable value that uniquely identifies this
	 *        attribute
	 * @param converter a function that can convert a <code>String</code> to a
	 *        value of the correct type; this function should throw an {@link
	 *        AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException}
	 *        if it cannot convert the String.
	 * @param comparator compares its two arguments for order considering the
	 *        distance between their values; it should return a value between
	 *        {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE} based on
	 *        the distance of the values.
	 */
	public AMAttribute(
		String name, Function<String, V> converter, Comparator<V> comparator) {

		_name = name;
		_converterFunction = converter;
		_comparator = comparator;
	}

	/**
	 * Compares its two arguments for order. Returns a negative integer, zero,
	 * or a positive integer depending on whether the first argument is less
	 * than, equal to, or greater than the second argument respectively.
	 *
	 * @param  value1 The first value to be compared
	 * @param  value2 The second value to be compared
	 * @return a negative integer, zero, or a positive integer depending on
	 *         whether the first argument is less than, equal to, or greater
	 *         than the second argument respectively.
	 */
	public int compare(V value1, V value2) {
		return _comparator.compare(value1, value2);
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
	 * @return a value between 0 and {@link Integer#MAX_VALUE} representing the
	 *         distance between the two values
	 */
	public int distance(V value1, V value2) {
		return Math.abs(_comparator.compare(value1, value2));
	}

	/**
	 * Returns this {@link AdaptiveMedia} instance's globally unique name.
	 *
	 * @return the attribute's name
	 */
	public String getName() {
		return _name;
	}

	private static final AMAttribute<?, String> _CONFIGURATION_UUID =
		new AMAttribute<>("configuration-uuid", (s) -> s, String::compareTo);

	private static final AMAttribute<?, Integer> _CONTENT_LENGTH =
		new AMAttribute<>(
			"content-length", AMAttributeConverterUtil::parseInt,
			(value1, value2) -> value1 - value2);

	private static final AMAttribute<?, String> _CONTENT_TYPE =
		new AMAttribute<>("content-type", (value) -> value, String::compareTo);

	private static final AMAttribute<?, String> _FILE_NAME = new AMAttribute<>(
		"file-name", (value) -> value, String::compareTo);

	private static final Map<String, AMAttribute<?, ?>> _allowedAMAttributes =
		new HashMap<>();

	static {
		_allowedAMAttributes.put(
			AMAttribute._CONFIGURATION_UUID.getName(),
			AMAttribute._CONFIGURATION_UUID);
		_allowedAMAttributes.put(
			AMAttribute._CONTENT_LENGTH.getName(), AMAttribute._CONTENT_LENGTH);
		_allowedAMAttributes.put(
			AMAttribute._CONTENT_TYPE.getName(), AMAttribute._CONTENT_TYPE);
		_allowedAMAttributes.put(
			AMAttribute._FILE_NAME.getName(), AMAttribute._FILE_NAME);
	}

	private final Comparator<V> _comparator;
	private final Function<String, V> _converterFunction;
	private final String _name;

}