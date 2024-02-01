/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.filter;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;
import com.liferay.portal.search.internal.filter.FilterBuildersImpl;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.function.Function;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class TermsSetFilterTest extends BaseIndexingTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testKeywordField() throws Exception {
		Function<String[], DocumentCreationHelper> function = this::_addKeyword;

		addDocument(function.apply(new String[] {"def", "ghi"}));
		addDocument(function.apply(new String[] {"ghi", "jkl"}));

		FilterBuilders filterBuilders = new FilterBuildersImpl();

		TermsSetFilterBuilder termsSetFilterBuilder =
			filterBuilders.termsSetFilterBuilder();

		termsSetFilterBuilder.setFieldName(_KEYWORD_FIELD);
		termsSetFilterBuilder.setMinimumShouldMatchField(_LONG_FIELD);
		termsSetFilterBuilder.setValues(Arrays.asList("abc", "def", "ghi"));

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setFilter(termsSetFilterBuilder.build());

				indexingTestHelper.search();

				indexingTestHelper.assertValues(
					_CONCAT_KEYWORD_FIELD, Arrays.asList("[def, ghi]"));
			});
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

	private DocumentCreationHelper _addKeyword(String... values) {
		return document -> {
			document.addKeyword(
				_CONCAT_KEYWORD_FIELD, String.valueOf(Arrays.asList(values)));
			document.addKeyword(_KEYWORD_FIELD, values);
			document.addNumber(_LONG_FIELD, 2);
		};
	}

	private static final String _CONCAT_KEYWORD_FIELD = "screenName";

	private static final String _KEYWORD_FIELD = Field.STATUS;

	private static final String _LONG_FIELD = "endTime";

}