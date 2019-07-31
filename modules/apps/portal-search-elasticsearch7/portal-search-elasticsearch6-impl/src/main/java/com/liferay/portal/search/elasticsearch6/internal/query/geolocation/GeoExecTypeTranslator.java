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

package com.liferay.portal.search.elasticsearch6.internal.query.geolocation;

import com.liferay.portal.search.query.geolocation.GeoExecType;

/**
 * @author Michael C. Han
 */
public class GeoExecTypeTranslator {

	public org.elasticsearch.index.query.GeoExecType translate(
		GeoExecType geoExecType) {

		if (geoExecType == GeoExecType.INDEXED) {
			return org.elasticsearch.index.query.GeoExecType.INDEXED;
		}
		else if (geoExecType == GeoExecType.MEMORY) {
			return org.elasticsearch.index.query.GeoExecType.MEMORY;
		}

		throw new IllegalArgumentException(
			"Invalid GeoExecType: " + geoExecType);
	}

}