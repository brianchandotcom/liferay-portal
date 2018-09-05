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
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sergio González
 */
public class AMAttributeDistanceComparator
	implements AMDistanceComparator<AdaptiveMedia<AMImageProcessor>> {

	public AMAttributeDistanceComparator(
		AMAttribute<AMImageProcessor, ?> amAttribute) {

		this(
			Collections.singletonMap(
				amAttribute, AMImageQueryBuilder.SortOrder.ASC));
	}

	public AMAttributeDistanceComparator(
		AMAttribute<AMImageProcessor, ?> amAttribute,
		AMImageQueryBuilder.SortOrder sortOrder) {

		this(Collections.singletonMap(amAttribute, sortOrder));
	}

	public AMAttributeDistanceComparator(
		Map<AMAttribute<AMImageProcessor, ?>, AMImageQueryBuilder.SortOrder>
			sortCriteria) {

		_sortCriteria = (Map)sortCriteria;
	}

	@Override
	public long compare(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia1,
		AdaptiveMedia<AMImageProcessor> adaptiveMedia2) {

		for (Map.Entry
				<AMAttribute<AMImageProcessor, Object>,
				 AMImageQueryBuilder.SortOrder> sortCriterion :
					_sortCriteria.entrySet()) {

			AMAttribute<AMImageProcessor, Object> amAttribute =
				sortCriterion.getKey();

			Optional<?> value1Optional = adaptiveMedia1.getValueOptional(
				amAttribute);
			Optional<?> value2Optional = adaptiveMedia2.getValueOptional(
				amAttribute);

			Optional<Long> valueOptional = value1Optional.flatMap(
				value1 -> value2Optional.map(
					value2 -> amAttribute.compare(value1, value2)));

			AMImageQueryBuilder.SortOrder sortOrder = sortCriterion.getValue();

			long result = valueOptional.map(
				sortOrder::getSortValue
			).orElse(
				0L
			);

			if (result != 0) {
				return result;
			}
		}

		return 0L;
	}

	private final Map
		<AMAttribute<AMImageProcessor, Object>, AMImageQueryBuilder.SortOrder>
			_sortCriteria;

}