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

package com.liferay.adaptive.media.image.internal.util.comparator;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AMDistanceComparator;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;

import java.util.Map;
import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class AMPropertyDistanceComparator
	implements AMDistanceComparator<AdaptiveMedia<AMImageProcessor>> {

	public AMPropertyDistanceComparator(
		Map<AMAttribute<AMImageProcessor, ?>, ?> amAttributes) {

		_amAttributes = amAttributes;
	}

	@Override
	public long compare(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia1,
		AdaptiveMedia<AMImageProcessor> adaptiveMedia2) {

		for (Map.Entry<AMAttribute<AMImageProcessor, ?>, ?> entry :
				_amAttributes.entrySet()) {

			AMAttribute<AMImageProcessor, Object> amAttribute =
				(AMAttribute<AMImageProcessor, Object>)entry.getKey();

			Object requestedValue = entry.getValue();

			Optional<?> value1Optional = adaptiveMedia1.getValueOptional(
				amAttribute);

			Optional<Long> value1DistanceOptional = value1Optional.map(
				value1 -> amAttribute.distance(value1, requestedValue));

			Optional<?> value2Optional = adaptiveMedia2.getValueOptional(
				amAttribute);

			Optional<Long> value2DistanceOptional = value2Optional.map(
				value2 -> amAttribute.distance(value2, requestedValue));

			Optional<Long> resultOptional = value1DistanceOptional.flatMap(
				value1 -> value2DistanceOptional.map(
					value2 -> value1 - value2));

			long result = resultOptional.orElse(0L);

			if (result != 0) {
				return result;
			}
		}

		return 0L;
	}

	private final Map<AMAttribute<AMImageProcessor, ?>, ?> _amAttributes;

}