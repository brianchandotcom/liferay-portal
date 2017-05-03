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

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class SimpleFacetCollector implements FacetCollector {

	public SimpleFacetCollector(
		String fieldName, List<TermCollector> termCollectors) {

		_fieldName = fieldName;

		for (TermCollector termCollector : termCollectors) {
			_termCollectors.put(termCollector.getTerm(), termCollector);
		}
	}

	@Override
	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public TermCollector getTermCollector(String term) {
		return _termCollectors.get(term);
	}

	@Override
	public List<TermCollector> getTermCollectors() {
		return ListUtil.fromMapValues(_termCollectors);
	}

	@Override
	public String toString() {
		return _fieldName + StringPool.EQUAL + _termCollectors;
	}

	private final String _fieldName;
	private final Map<String, TermCollector> _termCollectors = new HashMap<>();

}