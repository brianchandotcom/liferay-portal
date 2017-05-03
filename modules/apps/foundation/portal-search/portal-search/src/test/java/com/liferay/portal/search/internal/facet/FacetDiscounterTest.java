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
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.internal.test.util.AssertUtils;
import com.liferay.portal.search.internal.test.util.SearchMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class FacetDiscounterTest {

	@Test
	public void testDiscount() {
		Facet facet = _createFacet(
			_toTerm("foo", 10), _toTerm("bar", 5), _toTerm("qux", 2));

		FacetDiscounter facetDiscounter = new FacetDiscounter(facet);

		List<TermCollector> termCollectors = _discount(
			facetDiscounter, "foo", "bar", "qux");

		_assertTermCollectors(
			termCollectors,
			SearchMapUtil.join(
				_toMap("foo", 9), _toMap("bar", 4), _toMap("qux", 1)));
	}

	@Test
	public void testZeroedTermIsRemoved() {
		Facet facet = _createFacet(
			_toTerm("public", 1000), _toTerm("secret", 1));

		FacetDiscounter facetDiscounter = new FacetDiscounter(facet);

		List<TermCollector> termCollectors = _discount(
			facetDiscounter, "secret");

		_assertTermCollectors(termCollectors, _toMap("public", 1000));
	}

	private static Map<String, Integer> _toMap(
		List<TermCollector> termCollectors) {

		Map<String, Integer> map = new HashMap<>();

		for (TermCollector termCollector : termCollectors) {
			map.put(termCollector.getTerm(), termCollector.getFrequency());
		}

		return map;
	}

	private static Map _toMap(String key, int value) {
		return Collections.singletonMap(key, value);
	}

	private static TermCollector _toTerm(String term, int frequency) {
		return new DefaultTermCollector(term, frequency);
	}

	private void _assertTermCollectors(
		List<TermCollector> termCollectors, Map<String, Integer> frequencies) {

		AssertUtils.assertEquals(
			_fieldName, frequencies, _toMap(termCollectors));
	}

	private Document _createDocument(String term) {
		Document document = Mockito.mock(Document.class);

		Mockito.doReturn(
			new Field(_fieldName, term)
		).when(
			document
		).getField(
			_fieldName
		);

		return document;
	}

	private Facet _createFacet(TermCollector... termCollectors) {
		Facet facet = new SimpleFacet(null);

		facet.setFieldName(_fieldName);

		facet.setFacetCollector(_createFacetCollector(termCollectors));

		return facet;
	}

	private SimpleFacetCollector _createFacetCollector(
		TermCollector... termCollectors) {

		return new SimpleFacetCollector(
			_fieldName, Arrays.asList(termCollectors));
	}

	private List<TermCollector> _discount(
		FacetDiscounter facetDiscounter, String... terms) {

		Stream<String> termsStream = Stream.of(terms);

		Stream<Document> documentsStream = termsStream.map(
			this::_createDocument);

		return facetDiscounter.discount(
			documentsStream.collect(Collectors.toList()));
	}

	private static final String _fieldName = RandomTestUtil.randomString();

}