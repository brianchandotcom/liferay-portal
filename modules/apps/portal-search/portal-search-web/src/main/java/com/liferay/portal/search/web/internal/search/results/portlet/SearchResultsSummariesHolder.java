/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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