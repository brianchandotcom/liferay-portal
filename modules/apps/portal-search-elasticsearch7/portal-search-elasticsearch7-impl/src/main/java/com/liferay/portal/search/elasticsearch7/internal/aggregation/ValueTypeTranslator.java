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

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.ValueType;

/**
 * @author Michael C. Han
 */
public class ValueTypeTranslator {

	public org.elasticsearch.search.aggregations.support.ValueType translate(
		ValueType valueType) {

		if (valueType == ValueType.BOOLEAN) {
			return org.elasticsearch.search.aggregations.support.ValueType.
				BOOLEAN;
		}
		else if (valueType == ValueType.DATE) {
			return org.elasticsearch.search.aggregations.support.ValueType.DATE;
		}
		else if (valueType == ValueType.DOUBLE) {
			return org.elasticsearch.search.aggregations.support.ValueType.
				DOUBLE;
		}
		else if (valueType == ValueType.GEOPOINT) {
			return org.elasticsearch.search.aggregations.support.ValueType.
				GEOPOINT;
		}
		else if (valueType == ValueType.IP) {
			return org.elasticsearch.search.aggregations.support.ValueType.IP;
		}
		else if (valueType == ValueType.LONG) {
			return org.elasticsearch.search.aggregations.support.ValueType.LONG;
		}
		else if (valueType == ValueType.NUMBER) {
			return org.elasticsearch.search.aggregations.support.ValueType.
				NUMBER;
		}
		else if (valueType == ValueType.NUMERIC) {
			return org.elasticsearch.search.aggregations.support.ValueType.
				NUMERIC;
		}
		else if (valueType == ValueType.STRING) {
			return org.elasticsearch.search.aggregations.support.ValueType.
				STRING;
		}

		throw new IllegalArgumentException(
			"No available mapping for value type " + valueType);
	}

}