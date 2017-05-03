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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class FacetDiscounter {

	public FacetDiscounter(Facet facet) {
		_facet = facet;
	}

	public List<TermCollector> discount(Collection<Document> documents) {
		for (Document document : documents) {
			_exclude(document);
		}

		return _decrement(_getTermCollectors());
	}

	private List<TermCollector> _decrement(List<TermCollector> termCollectors) {
		List<TermCollector> newTermCollectors = new ArrayList<>(
			termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			String term = termCollector.getTerm();

			int exclusions = _getExclusions(term);

			int frequency = termCollector.getFrequency() - exclusions;

			if (frequency > 0) {
				newTermCollectors.add(
					new DefaultTermCollector(term, frequency));
			}
		}

		return newTermCollectors;
	}

	private void _exclude(Document document) {
		Field field = document.getField(_facet.getFieldName());

		if (field == null) {
			return;
		}

		Optional<String> termOptional = _findTermOfField(field);

		termOptional.ifPresent(this::_exclude);
	}

	private void _exclude(String term) {
		int exclusions = _getExclusions(term);

		_excludedTermsMap.put(term, exclusions + 1);
	}

	private Optional<String> _findTermOfField(Field field) {
		List<TermCollector> termCollectors = _getTermCollectors();

		Stream<TermCollector> termCollectorsStream = termCollectors.stream();

		Stream<String> termsStream = termCollectorsStream.map(
			TermCollector::getTerm);

		Stream<String> termsOfFieldStream = termsStream.filter(
			term -> FacetBucketUtil.isFieldInBucket(field, term, _facet));

		return termsOfFieldStream.findAny();
	}

	private int _getExclusions(String term) {
		Integer exclusions = _excludedTermsMap.get(term);

		if (exclusions != null) {
			return exclusions;
		}

		return 0;
	}

	private List<TermCollector> _getTermCollectors() {
		FacetCollector facetCollector = _facet.getFacetCollector();

		return facetCollector.getTermCollectors();
	}

	private final Map<String, Integer> _excludedTermsMap = new HashMap<>();
	private final Facet _facet;

}