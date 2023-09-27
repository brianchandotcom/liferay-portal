/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return StringBundler.concat(
			"{fieldName=", _fieldName, ", termCollectors=", _termCollectors,
			"}");
	}

	private final String _fieldName;
	private final Map<String, TermCollector> _termCollectors = new HashMap<>();

}