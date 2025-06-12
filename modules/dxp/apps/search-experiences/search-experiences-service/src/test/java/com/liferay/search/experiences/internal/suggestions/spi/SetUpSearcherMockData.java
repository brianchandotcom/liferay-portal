/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.suggestions.spi;

import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.search.hits.SearchHit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rodrigo Guedes de Souza
 */
public class SetUpSearcherMockData {

	public SetUpSearcherMockData add(Integer index, ClassName className) {
		_classNames.put(index, className);

		return this;
	}

	public SetUpSearcherMockData add(Integer index, SearchHit searchHit) {
		_searchHits.put(index, searchHit);

		return this;
	}

	public List<ClassName> getClassNames() {
		return new ArrayList<>(_classNames.values());
	}

	public List<SearchHit> getSearchHits() {
		return new ArrayList<>(_searchHits.values());
	}

	private final Map<Integer, ClassName> _classNames = new HashMap<>();
	private final Map<Integer, SearchHit> _searchHits = new HashMap<>();

}