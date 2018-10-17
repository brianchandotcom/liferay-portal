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

package com.liferay.portal.search.solr7.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.Facet;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class AggregationFilteringFacetProcessorContext
	implements FacetProcessorContext {

	public static FacetProcessorContext newInstance(
		Map<String, Facet> facets, boolean basicFacetSelection) {

		if (basicFacetSelection) {
			return new AggregationFilteringFacetProcessorContext(
				Optional.of(getAllNamesString(facets)));
		}

		return new AggregationFilteringFacetProcessorContext(Optional.empty());
	}

	@Override
	public Optional<String> getExcludeTagsStringOptional() {
		return _excludeTagsStringOptional;
	}

	protected static String getAllNamesString(Map<String, Facet> facets) {
		Collection<String> names = facets.keySet();

		Stream<String> stream = names.stream();

		return stream.collect(Collectors.joining(StringPool.COMMA));
	}

	private AggregationFilteringFacetProcessorContext(
		Optional<String> excludeTagsStringOptional) {

		_excludeTagsStringOptional = excludeTagsStringOptional;
	}

	private final Optional<String> _excludeTagsStringOptional;

}