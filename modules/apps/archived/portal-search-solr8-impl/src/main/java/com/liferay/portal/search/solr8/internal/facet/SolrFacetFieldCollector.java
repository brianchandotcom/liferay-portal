/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.util.NamedList;

/**
 * @author Raymond Augé
 */
public class SolrFacetFieldCollector implements FacetCollector {

	public SolrFacetFieldCollector(Facet facet, NamedList<?> namedList) {
		String name = FacetUtil.getAggregationName(facet);

		List<NamedList<?>> list = _getBuckets(
			(NamedList<?>)namedList.get(name));

		_counts = _getCountsInSameOrder(list);

		_fieldName = name;
	}

	@Override
	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public TermCollector getTermCollector(String term) {
		return new DefaultTermCollector(
			term, GetterUtil.getInteger(_counts.get(term)));
	}

	@Override
	public List<TermCollector> getTermCollectors() {
		if (_termCollectors != null) {
			return _termCollectors;
		}

		List<TermCollector> termCollectors = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : _counts.entrySet()) {
			termCollectors.add(
				new DefaultTermCollector(entry.getKey(), entry.getValue()));
		}

		_termCollectors = termCollectors;

		return _termCollectors;
	}

	private List<NamedList<?>> _getBuckets(NamedList<?> namedList) {
		if (namedList == null) {
			return null;
		}

		return (List<NamedList<?>>)namedList.get("buckets");
	}

	private Map<String, Integer> _getCountsInSameOrder(
		List<NamedList<?>> list) {

		if (ListUtil.isEmpty(list)) {
			return Collections.emptyMap();
		}

		Map<String, Integer> map = new LinkedHashMap<>();

		for (NamedList<?> namedList : list) {
			map.put(
				GetterUtil.getString(String.valueOf(namedList.get("val"))),
				GetterUtil.getInteger(namedList.get("count")));
		}

		return map;
	}

	private final Map<String, Integer> _counts;
	private final String _fieldName;
	private List<TermCollector> _termCollectors;

}