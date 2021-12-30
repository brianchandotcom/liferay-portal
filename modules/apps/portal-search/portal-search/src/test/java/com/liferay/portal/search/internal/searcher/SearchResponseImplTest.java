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

package com.liferay.portal.search.internal.searcher;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SearchResponseImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDefaultsAreNullSafe() {
		SearchResponse searchResponse = new SearchResponseImpl(
			new SearchContext());

		_assertIs(searchResponse.getAggregationResult(null), _nullValue());
		_assertIs(searchResponse.getAggregationResultsMap(), _emptyMap());
		_assertIs(searchResponse.getCount(), _zeroLong());
		_assertIs(searchResponse.getDocuments71(), emptyList());
		_assertIs(searchResponse.getDocumentsStream(), _emptyStream());
		_assertIs(searchResponse.getFederatedSearchKey(), _blank());
		_assertIs(
			searchResponse.getFederatedSearchResponse(null),
			same(searchResponse));
		_assertIs(
			searchResponse.getFederatedSearchResponsesStream(), _emptyStream());
		_assertIs(searchResponse.getGroupByResponses(), emptyList());
		_assertIs(searchResponse.getRequest(), _nullValue());
		_assertIs(searchResponse.getRequestString(), _blank());
		_assertIs(searchResponse.getResponseString(), _blank());
		_assertIs(
			searchResponse.getSearchHits(), _instanceOf(SearchHits.class));
		_assertIs(searchResponse.getStatsResponseMap(), _emptyMap());
		_assertIs(searchResponse.getTotalHits(), _zeroInt());
	}

	protected static Consumer<List<?>> emptyList() {
		return list -> Assert.assertEquals("[]", String.valueOf(list));
	}

	protected static Consumer<Object> same(Object expected) {
		return actual -> Assert.assertSame(expected, actual);
	}

	private <T> void _assertIs(T actual, Consumer<T> consumer) {
		consumer.accept(actual);
	}

	private Consumer<String> _blank() {
		return string -> Assert.assertEquals(StringPool.BLANK, string);
	}

	private Consumer<Map<String, ?>> _emptyMap() {
		return map -> Assert.assertEquals("{}", String.valueOf(map));
	}

	private Consumer<Stream<?>> _emptyStream() {
		return stream -> Assert.assertEquals(
			"[]",
			String.valueOf(
				stream.map(
					String::valueOf
				).collect(
					Collectors.toList()
				)));
	}

	private Consumer<Object> _instanceOf(Class<?> clazz) {
		return object -> Assert.assertTrue(clazz.isInstance(object));
	}

	private Consumer<Object> _nullValue() {
		return object -> Assert.assertNull(object);
	}

	private Consumer<Integer> _zeroInt() {
		return value -> Assert.assertEquals(0, value.intValue());
	}

	private Consumer<Long> _zeroLong() {
		return value -> Assert.assertEquals(0, value.longValue());
	}

}