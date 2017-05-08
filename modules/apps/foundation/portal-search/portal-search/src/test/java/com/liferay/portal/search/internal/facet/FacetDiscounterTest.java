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
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
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
	public void testMultiValueFacet() {
		Facet facet = new MultiValueFacet(null);

		_populate(facet, _toTerm("a", 10), _toTerm("b", 5), _toTerm("c", 2));

		FacetDiscounter facetDiscounter = new FacetDiscounter(facet);

		_discount(facetDiscounter, _createDocument(new String[] {"a", "c"}));

		_assertTermCollectors(
			facet,
			SearchMapUtil.join(_toMap("a", 9), _toMap("b", 5), _toMap("c", 1)));
	}

	@Test
	public void testRangeFacet() {
		Facet facet = new RangeFacet(null);

		_populate(facet, _toTerm("[0 TO 5]", 3), _toTerm("[0 TO 9]", 3));

		FacetDiscounter facetDiscounter = new FacetDiscounter(facet);

		_discount(facetDiscounter, "2", "7");

		_assertTermCollectors(
			facet,
			SearchMapUtil.join(_toMap("[0 TO 5]", 2), _toMap("[0 TO 9]", 1)));
	}

	@Test
	public void testSimpleFacet() {
		Facet facet = new SimpleFacet(null);

		_populate(facet, _toTerm("a", 10), _toTerm("b", 5), _toTerm("c", 2));

		FacetDiscounter facetDiscounter = new FacetDiscounter(facet);

		_discount(facetDiscounter, "a", "b", "c");

		_assertTermCollectors(
			facet,
			SearchMapUtil.join(_toMap("a", 9), _toMap("b", 4), _toMap("c", 1)));
	}

	@Test
	public void testZeroedTermIsRemoved() {
		SimpleFacet facet = new SimpleFacet(null);

		_populate(facet, _toTerm("public", 1000), _toTerm("secret", 1));

		FacetDiscounter facetDiscounter = new FacetDiscounter(facet);

		_discount(facetDiscounter, "secret");

		_assertTermCollectors(facet, _toMap("public", 1000));
	}

	private static Map<String, Integer> _toMap(
		List<TermCollector> termCollectors) {

		Map<String, Integer> map = new HashMap<>();

		for (TermCollector termCollector : termCollectors) {
			map.put(termCollector.getTerm(), termCollector.getFrequency());
		}

		return map;
	}

	private static Map<String, Integer> _toMap(String key, int value) {
		return Collections.singletonMap(key, value);
	}

	private static TermCollector _toTerm(String term, int frequency) {
		return new DefaultTermCollector(term, frequency);
	}

	private void _assertTermCollectors(
		Facet facet, Map<String, Integer> frequencies) {

		FacetCollector facetCollector = facet.getFacetCollector();

		AssertUtils.assertEquals(
			_fieldName, frequencies,
			_toMap(facetCollector.getTermCollectors()));
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

	private Document _createDocument(String[] terms) {
		Document document = Mockito.mock(Document.class);

		Mockito.doReturn(
			new Field(_fieldName, terms)
		).when(
			document
		).getField(
			_fieldName
		);

		return document;
	}

	private SimpleFacetCollector _createFacetCollector(
		TermCollector... termCollectors) {

		return new SimpleFacetCollector(
			_fieldName, Arrays.asList(termCollectors));
	}

	private void _discount(
		FacetDiscounter facetDiscounter, Document... documents) {

		_discount(facetDiscounter, Stream.of(documents));
	}

	private void _discount(
		FacetDiscounter facetDiscounter, Stream<Document> documentsStream) {

		facetDiscounter.discount(documentsStream.collect(Collectors.toList()));
	}

	private void _discount(FacetDiscounter facetDiscounter, String... terms) {
		Stream<String> termsStream = Stream.of(terms);

		Stream<Document> documentsStream = termsStream.map(
			this::_createDocument);

		_discount(facetDiscounter, documentsStream);
	}

	private void _populate(Facet facet, TermCollector... termCollectors) {
		facet.setFieldName(_fieldName);

		facet.setFacetCollector(_createFacetCollector(termCollectors));
	}

	private static final String _fieldName = RandomTestUtil.randomString();

}