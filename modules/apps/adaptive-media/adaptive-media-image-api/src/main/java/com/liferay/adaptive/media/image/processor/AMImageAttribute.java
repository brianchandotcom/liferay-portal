/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.processor;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.adaptive.media.util.AMAttributeConverterUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public final class AMImageAttribute {

	public static final AMAttribute<AMProcessor<FileVersion>, Integer>
		AM_IMAGE_ATTRIBUTE_HEIGHT = new AMAttribute<>(
			"height", AMAttributeConverterUtil::parseInt,
			AMImageAttribute::_intDistance);

	public static final AMAttribute<AMProcessor<FileVersion>, Integer>
		AM_IMAGE_ATTRIBUTE_WIDTH = new AMAttribute<>(
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
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(),
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);
		_allowedAMAttributes.put(
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(),
			AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);

		_allowedAMAttributes.putAll(AMAttribute.getAllowedAMAttributes());
	}

}