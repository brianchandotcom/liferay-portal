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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Gives convenient access to a set of media attributes. It offers a type-safe
 * interface to access attribute values, accepting only attributes of the
 * correct type (those for adaptive images), and returning values of the correct
 * type.
 *
 * @author Adolfo Pérez
 */
public class AdaptiveMediaImageAttributeMapping {

	/**
	 * Returns an {@link AdaptiveMediaImageAttributeMapping} that uses the map
	 * as the underlying attribute storage.
	 *
	 * @param  properties the map to get the properties from
	 * @return a non-<code>null</code> mapping that provides type-safe access to
	 *         an underlying map
	 */
	public static AdaptiveMediaImageAttributeMapping fromProperties(
		Map<String, String> properties) {

		if (properties == null) {
			throw new IllegalArgumentException("properties map cannot be null");
		}

		Map<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?>, Optional<?>>
			attributes = new HashMap<>();

		attributes.put(
			AdaptiveMediaAttribute.getConfigurationUuid(),
			_getValueOptional(
				properties, AdaptiveMediaAttribute.getConfigurationUuid()));
		attributes.put(
			AdaptiveMediaAttribute.getContentLength(),
			_getValueOptional(
				properties, AdaptiveMediaAttribute.getContentLength()));
		attributes.put(
			AdaptiveMediaAttribute.getContentType(),
			_getValueOptional(
				properties, AdaptiveMediaAttribute.getContentType()));
		attributes.put(
			AdaptiveMediaAttribute.getFileName(),
			_getValueOptional(
				properties, AdaptiveMediaAttribute.getFileName()));
		attributes.put(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT,
			_getValueOptional(
				properties, AdaptiveMediaImageAttribute.IMAGE_HEIGHT));
		attributes.put(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH,
			_getValueOptional(
				properties, AdaptiveMediaImageAttribute.IMAGE_WIDTH));

		return new AdaptiveMediaImageAttributeMapping(attributes);
	}

	/**
	 * Returns an {@link Optional} instance that contains the value of the
	 * attribute (if any) in this mapping.
	 *
	 * @param  adaptiveMediaAttribute a non <code>null</code> attribute
	 * @return a non-<code>null</code> optional that contains the
	 *         (non-<code>null</code>) value (if any)
	 */
	public <V> Optional<V> getValueOptional(
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, V>
			adaptiveMediaAttribute) {

		if (adaptiveMediaAttribute == null) {
			throw new IllegalArgumentException("attribute cannot be null");
		}

		return (Optional<V>)_attributes.get(adaptiveMediaAttribute);
	}

	protected AdaptiveMediaImageAttributeMapping(
		Map<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?>, Optional<?>>
			attributes) {

		_attributes = attributes;
	}

	private static <V> Optional<V> _getValueOptional(
		Map<String, String> properties,
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, V>
			adaptiveMediaAttribute) {

		String value = properties.get(adaptiveMediaAttribute.getName());

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(adaptiveMediaAttribute.convert(value));
	}

	private final Map
		<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?>, Optional<?>>
			_attributes;

}