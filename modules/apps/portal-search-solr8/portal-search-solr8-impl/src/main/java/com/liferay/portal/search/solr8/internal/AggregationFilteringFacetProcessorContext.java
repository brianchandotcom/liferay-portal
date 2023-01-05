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

package com.liferay.portal.search.solr8.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.Collection;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class AggregationFilteringFacetProcessorContext
	implements FacetProcessorContext {

	public static FacetProcessorContext newInstance(
		Map<String, Facet> facets, boolean basicFacetSelection) {

		if (basicFacetSelection) {
			return new AggregationFilteringFacetProcessorContext(
				getAllNamesString(facets));
		}

		return new AggregationFilteringFacetProcessorContext(null);
	}

	@Override
	public String getExcludeTagsString() {
		return _excludeTagsString;
	}

	protected static String getAllNamesString(Map<String, Facet> facets) {
		Collection<String> names = facets.keySet();

		return StringUtil.merge(names, StringPool.COMMA);
	}

	private AggregationFilteringFacetProcessorContext(
		String excludeTagsString) {

		_excludeTagsString = excludeTagsString;
	}

	private final String _excludeTagsString;

}