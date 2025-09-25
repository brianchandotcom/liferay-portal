/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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