/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.groupby;

import com.liferay.portal.kernel.search.Hits;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Bryan Engler
 * @author Michael C. Han
 */
public class GroupByResponse {

	public GroupByResponse(String field) {
		_field = field;
	}

	public String getField() {
		return _field;
	}

	public Hits getHits(String term) {
		return _groupedHits.get(term);
	}

	public Map<String, Hits> getHitsMap() {
		return _groupedHits;
	}

	public void putHits(String term, Hits hits) {
		_groupedHits.put(term, hits);
	}

	public void setField(String field) {
		_field = field;
	}

	private String _field;
	private final Map<String, Hits> _groupedHits = new LinkedHashMap<>();

}