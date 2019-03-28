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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Comparator;
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
		return ListUtil.sort(
			ListUtil.fromMapValues(_termCollectors),
			Comparator.comparing(
				TermCollector::getFrequency, Comparator.reverseOrder()));
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{fieldName=");
		sb.append(_fieldName);
		sb.append(", termCollectors=");
		sb.append(_termCollectors);
		sb.append("}");

		return sb.toString();
	}

	private final String _fieldName;
	private final Map<String, TermCollector> _termCollectors = new HashMap<>();

}