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

package com.liferay.portal.search.elasticsearch7.internal.query.geolocation;

import com.liferay.portal.search.query.geolocation.GeoValidationMethod;

/**
 * @author Michael C. Han
 */
public class GeoValidationMethodTranslator {

	public org.elasticsearch.index.query.GeoValidationMethod translate(
		GeoValidationMethod geoValidationMethod) {

		if (geoValidationMethod == GeoValidationMethod.COERCE) {
			return org.elasticsearch.index.query.GeoValidationMethod.COERCE;
		}
		else if (geoValidationMethod == GeoValidationMethod.IGNORE_MALFORMED) {
			return org.elasticsearch.index.query.GeoValidationMethod.
				IGNORE_MALFORMED;
		}
		else if (geoValidationMethod == GeoValidationMethod.STRICT) {
			return org.elasticsearch.index.query.GeoValidationMethod.STRICT;
		}

		throw new IllegalArgumentException(
			"Invalid GeoValidationMethod: " + geoValidationMethod);
	}

}