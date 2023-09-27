/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.groupby;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.search.groupby.GroupByResponse;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class GroupByResponseImpl implements GroupByResponse, Serializable {

	public GroupByResponseImpl(String field) {
		_field = field;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Hits getHits(String term) {
		return _groupedHits.get(term);
	}

	@Override
	public Map<String, Hits> getHitsMap() {
		return _groupedHits;
	}

	@Override
	public void putHits(String term, Hits hits) {
		_groupedHits.put(term, hits);
	}

	@Override
	public void setField(String field) {
		_field = field;
	}

	private String _field;
	private final Map<String, Hits> _groupedHits = new LinkedHashMap<>();

}