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

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.util.AMAttributeConverterUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public final class AMImageAttribute {

	public static final AMAttribute<AMImageProcessor, Integer> IMAGE_HEIGHT =
		new AMAttribute<>(
			"height", AMAttributeConverterUtil::parseInt,
			AMImageAttribute::_intDistance);

	public static final AMAttribute<AMImageProcessor, Integer> IMAGE_WIDTH =
		new AMAttribute<>(
			"width", AMAttributeConverterUtil::parseInt,
			AMImageAttribute::_intDistance);

	/**
	 * Returns a string-attribute map containing the available name-attribute
	 * pairs.
	 *
	 * @return the list of available attributes
	 */
	public static Map<String, AMAttribute<?, ?>> getAllowedAMAttributes() {
		return _allowedAMAttributes;
	}

	private static int _intDistance(int i1, int i2) {
		return i1 - i2;
	}

	private AMImageAttribute() {
	}

	private static final Map<String, AMAttribute<?, ?>> _allowedAMAttributes =
		new HashMap<>();

	static {
		_allowedAMAttributes.put(
			AMImageAttribute.IMAGE_HEIGHT.getName(),
			AMImageAttribute.IMAGE_HEIGHT);
		_allowedAMAttributes.put(
			AMImageAttribute.IMAGE_WIDTH.getName(),
			AMImageAttribute.IMAGE_WIDTH);

		_allowedAMAttributes.putAll(AMAttribute.getAllowedAMAttributes());
	}

}