/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer.body.contributor;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.AdvancedSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.rest.dto.v1_0.AdvancedConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class AdvancedSXPSearchRequestBodyContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_advancedSXPSearchRequestBodyContributor =
			new AdvancedSXPSearchRequestBodyContributor();

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			new SearchRequestBuilderFactoryImpl();

		_searchRequestBuilder = searchRequestBuilderFactory.builder();
	}

	@Test
	public void testStoredFields() {
		Configuration configuration = new Configuration();

		AdvancedConfiguration advancedConfiguration =
			new AdvancedConfiguration();

		List<String> storedFields = Arrays.asList(
			Field.TITLE, Field.DESCRIPTION);

		advancedConfiguration.setStored_fields(
			storedFields.toArray(new String[0]));

		configuration.setAdvancedConfiguration(advancedConfiguration);

		_advancedSXPSearchRequestBodyContributor.contribute(
			configuration, _searchRequestBuilder, null);

		SearchContext searchContext =
			_searchRequestBuilder.withSearchContextGet(Function.identity());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		Assert.assertEquals(
			storedFields, Arrays.asList(queryConfig.getSelectedFieldNames()));
	}

	private AdvancedSXPSearchRequestBodyContributor
		_advancedSXPSearchRequestBodyContributor;
	private SearchRequestBuilder _searchRequestBuilder;

}