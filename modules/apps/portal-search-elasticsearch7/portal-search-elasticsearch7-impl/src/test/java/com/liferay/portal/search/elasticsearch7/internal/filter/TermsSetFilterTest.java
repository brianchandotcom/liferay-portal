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

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.elasticsearch7.internal.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.TermsSetFilter;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;
import com.liferay.portal.search.internal.filter.FilterBuildersImpl;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import java.util.Arrays;
import java.util.function.Function;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class TermsSetFilterTest extends BaseIndexingTestCase {

	@Test
	public void testKeywordField() throws Exception {
		Function<String[], DocumentCreationHelper> function = this::addKeyword;

		addDocument(function.apply(new String[] {"def", "ghi"}));
		addDocument(function.apply(new String[] {"ghi", "jkl"}));

		FilterBuilders filterBuilders = new FilterBuildersImpl();

		TermsSetFilterBuilder termsSetFilterBuilder =
			filterBuilders.termsSetFilterBuilder();

		termsSetFilterBuilder.setFieldName(_KEYWORD_FIELD);
		termsSetFilterBuilder.setMinimumShouldMatchField(_LONG_FIELD);
		termsSetFilterBuilder.setValues(Arrays.asList("abc", "def", "ghi"));

		TermsSetFilter termsSetFilter = termsSetFilterBuilder.build();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(termsSetFilter);

				indexingTestHelper.search();

				indexingTestHelper.assertValues(
					_CONCAT_KEYWORD_FIELD, Arrays.asList("[def, ghi]"));
			});
	}

	protected DocumentCreationHelper addKeyword(String... values) {
		return document -> {
			document.addKeyword(
				_CONCAT_KEYWORD_FIELD, String.valueOf(Arrays.asList(values)));

			document.addKeyword(_KEYWORD_FIELD, values);

			document.addNumber(_LONG_FIELD, 2);
		};
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private static final String _CONCAT_KEYWORD_FIELD = "screenName";

	private static final String _KEYWORD_FIELD = Field.STATUS;

	private static final String _LONG_FIELD = "endTime";

}