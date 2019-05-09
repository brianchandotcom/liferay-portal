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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.test.util.document.BaseDocumentTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public abstract class BaseDocumentMappingTestCase extends BaseDocumentTestCase {

	@Test
	public void testFirstNamesSearchResults() throws Exception {
		Stream<String> stream = getScreenNamesStream();

		stream.forEach(this::assertMappings);
	}

	@Test
	public void testLastNameSearchResults() throws Exception {
		assertMappings("Smith");
	}

	protected void assertMappings(Document document) {
		String screenName = document.get("screenName");

		Assert.assertEquals(
			Double.valueOf(document.get(FIELD_DOUBLE)), doubles.get(screenName),
			0);

		Assert.assertEquals(
			Long.valueOf(document.get(FIELD_LONG)), longs.get(screenName), 0);

		Assert.assertEquals(
			Float.valueOf(document.get(FIELD_FLOAT)), floats.get(screenName),
			0);

		Assert.assertEquals(
			Integer.valueOf(document.get(FIELD_INTEGER)),
			integers.get(screenName), 0);

		Assert.assertArrayEquals(
			getDoubleArray(document), doubleArrays.get(screenName));

		Assert.assertArrayEquals(
			getLongArray(document), longArrays.get(screenName));

		Assert.assertArrayEquals(
			getFloatArray(document), floatArrays.get(screenName));

		Assert.assertArrayEquals(
			getIntegerArray(document), integerArrays.get(screenName));
	}

	protected void assertMappings(String keywords) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setQuery(getQuery(keywords));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> {
						for (Document document : hits.getDocs()) {
							assertMappings(document);
						}
					});
			});
	}

	protected Double[] getDoubleArray(Document document) {
		List<Double> list = new ArrayList<>();

		for (String value : document.getValues(FIELD_DOUBLE_ARRAY)) {
			list.add(Double.valueOf(value));
		}

		return list.toArray(new Double[0]);
	}

	protected Float[] getFloatArray(Document document) {
		List<Float> list = new ArrayList<>();

		for (String value : document.getValues(FIELD_FLOAT_ARRAY)) {
			list.add(Float.valueOf(value));
		}

		return list.toArray(new Float[0]);
	}

	protected Integer[] getIntegerArray(Document document) {
		List<Integer> list = new ArrayList<>();

		for (String value : document.getValues(FIELD_INTEGER_ARRAY)) {
			list.add(Integer.valueOf(value));
		}

		return list.toArray(new Integer[0]);
	}

	protected Long[] getLongArray(Document document) {
		List<Long> list = new ArrayList<>();

		for (String value : document.getValues(FIELD_LONG_ARRAY)) {
			list.add(Long.valueOf(value));
		}

		return list.toArray(new Long[0]);
	}

	protected Query getQuery(String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new MatchQuery("firstName", keywords), BooleanClauseOccur.SHOULD);
		booleanQueryImpl.add(
			new MatchQuery("lastName", keywords), BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
	}

}