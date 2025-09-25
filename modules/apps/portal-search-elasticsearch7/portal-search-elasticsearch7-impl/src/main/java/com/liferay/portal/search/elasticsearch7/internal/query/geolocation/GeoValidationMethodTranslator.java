/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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