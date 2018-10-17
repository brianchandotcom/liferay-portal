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

package com.liferay.portal.search.solr7.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;

/**
 * @author André de Oliveira
 */
public class FacetUtil {

	public static String getAggregationName(Facet facet) {
		if (facet instanceof com.liferay.portal.search.facet.Facet) {
			return ((com.liferay.portal.search.facet.Facet)facet).
				getAggregationName();
		}

		return facet.getFieldName();
	}

}