/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.Collections;
import java.util.List;

import org.elasticsearch.search.aggregations.Aggregation;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
public class ElasticsearchFacetFieldCollector implements FacetCollector {

	public ElasticsearchFacetFieldCollector(Aggregation aggregation) {
		_fieldName = aggregation.getName();
	}

	@Override
	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public TermCollector getTermCollector(String term) {
		return new DefaultTermCollector(term, 0);
	}

	@Override
	public List<TermCollector> getTermCollectors() {
		return Collections.emptyList();
	}

	private final String _fieldName;

}