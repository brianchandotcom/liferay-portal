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

package com.liferay.adaptive.media.image.processor;

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.util.AdaptiveMediaAttributeConverterUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public final class AdaptiveMediaImageAttribute {

	public static final AdaptiveMediaAttribute
		<AdaptiveMediaImageProcessor, Integer> IMAGE_HEIGHT =
			new AdaptiveMediaAttribute<>(
				"height", AdaptiveMediaAttributeConverterUtil::parseInt,
				AdaptiveMediaImageAttribute::_intDistance);

	public static final AdaptiveMediaAttribute
		<AdaptiveMediaImageProcessor, Integer> IMAGE_WIDTH =
			new AdaptiveMediaAttribute<>(
				"width", AdaptiveMediaAttributeConverterUtil::parseInt,
				AdaptiveMediaImageAttribute::_intDistance);

	/**
	 * Returns a string-attribute map containing the available name-attribute
	 * pairs.
	 *
	 * @return the list of available attributes
	 */
	public static Map<String, AdaptiveMediaAttribute<?, ?>>
		getAllowedAdaptiveMediaAttributes() {

		return _allowedAdaptiveMediaAttributes;
	}

	private static int _intDistance(int i1, int i2) {
		return i1 - i2;
	}

	private AdaptiveMediaImageAttribute() {
	}

	private static final Map<String, AdaptiveMediaAttribute<?, ?>>
		_allowedAdaptiveMediaAttributes = new HashMap<>();

	static {
		_allowedAdaptiveMediaAttributes.put(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT.getName(),
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT);
		_allowedAdaptiveMediaAttributes.put(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH.getName(),
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		_allowedAdaptiveMediaAttributes.putAll(
			AdaptiveMediaAttribute.getAllowedAdaptiveMediaAttributes());
	}

}