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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class SearchResultsSummariesHolder implements Serializable {

	public SearchResultsSummariesHolder(int capacity) {
		_map = new LinkedHashMap<>(capacity);
	}

	public SearchResultSummaryDisplayContext get(Document document) {
		return _map.get(document);
	}

	public Collection<Document> getDocuments() {
		return Collections.unmodifiableSet(_map.keySet());
	}

	public void put(
		Document document,
		SearchResultSummaryDisplayContext searchResultSummaryDisplayContext) {

		_map.put(
			document,
			Objects.requireNonNull(searchResultSummaryDisplayContext));
	}

	private final Map<Document, SearchResultSummaryDisplayContext> _map;

}