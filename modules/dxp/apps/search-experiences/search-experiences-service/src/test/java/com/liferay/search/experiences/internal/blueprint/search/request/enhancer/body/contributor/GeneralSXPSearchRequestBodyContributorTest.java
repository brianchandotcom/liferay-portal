/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer.body.contributor;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.internal.asset.AssetSubtypeIdentifierBuilderImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryPartBuilderFactoryImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.GeneralSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Joshua Cords
 */
public class GeneralSXPSearchRequestBodyContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContribute() {
		_testContribute(
			Collections.emptyList(), Collections.emptyList(), new String[0]);

		List<String> entryClassNames = Arrays.asList(
			"com.liferay.journal.model.JournalArticle");

		_testContribute(
			entryClassNames, entryClassNames,
			entryClassNames.toArray(new String[0]));
		_testContribute(
			entryClassNames, new ArrayList<>(), new String[0],
			searchContext -> searchContext.setEntryClassNames(
				entryClassNames.toArray(new String[1])));
	}

	private SearchRequestBuilder _setUpSearchRequestBuilder(
		Consumer<SearchContext> searchContextConsumer) {

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			new SearchRequestBuilderFactoryImpl();

		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder();

		searchRequestBuilder.withSearchContext(searchContextConsumer);

		return searchRequestBuilder;
	}

	private void _testContribute(
		List<String> expectedEntryClassNames,
		List<String> expectedModelIndexerClassNames,
		String[] searchableAssetTypes) {

		_testContribute(
			expectedEntryClassNames, expectedModelIndexerClassNames,
			searchableAssetTypes,
			searchContext -> {
			});
	}

	private void _testContribute(
		List<String> expectedEntryClassNames,
		List<String> expectedModelIndexerClassNames,
		String[] searchableAssetTypes1,
		Consumer<SearchContext> searchContextConsumer) {

		GeneralSXPSearchRequestBodyContributor
			generalSXPSearchRequestBodyContributor =
				new GeneralSXPSearchRequestBodyContributor(
					new AssetSubtypeIdentifierBuilderImpl(),
					new ComplexQueryPartBuilderFactoryImpl());

		SearchRequestBuilder searchRequestBuilder = _setUpSearchRequestBuilder(
			searchContextConsumer);

		generalSXPSearchRequestBodyContributor.contribute(
			new Configuration() {
				{
					setGeneralConfiguration(
						new GeneralConfiguration() {
							{
								setSearchableAssetTypes(searchableAssetTypes1);
							}
						});
				}
			},
			searchRequestBuilder, null);

		SearchRequest searchRequest = searchRequestBuilder.build();

		Assert.assertEquals(
			expectedEntryClassNames, searchRequest.getEntryClassNames());
		Assert.assertEquals(
			expectedModelIndexerClassNames,
			searchRequest.getModelIndexerClassNames());
	}

}