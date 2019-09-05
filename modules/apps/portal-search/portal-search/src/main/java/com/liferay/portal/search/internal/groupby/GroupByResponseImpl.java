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
	private Map<String, Hits> _groupedHits = new LinkedHashMap<>();

}