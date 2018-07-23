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

package com.liferay.portal.search.elasticsearch6.internal.search.response;

import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.Locale;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;

/**
 * @author Dylan Rebelak
 */
public interface SearchResponseTranslator {

	public Hits translate(
		SearchResponse searchResponse, Map<String, Facet> facetMap,
		GroupBy groupBy, Map<String, Stats> statsMap,
		String alternateUidFieldName, String[] highlightFieldNames,
		Locale locale);

}