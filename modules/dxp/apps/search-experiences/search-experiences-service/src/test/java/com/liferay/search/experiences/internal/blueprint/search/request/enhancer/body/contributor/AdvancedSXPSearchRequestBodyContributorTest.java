/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.enhancer.body.contributor;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.collapse.CollapseBuilderFactory;
import com.liferay.portal.search.collapse.InnerHitBuilderFactory;
import com.liferay.portal.search.internal.collapse.CollapseBuilderFactoryImpl;
import com.liferay.portal.search.internal.collapse.InnerHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.internal.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.sort.SortsImpl;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.internal.blueprint.query.QueryConverter;
import com.liferay.search.experiences.internal.blueprint.script.ScriptConverter;
import com.liferay.search.experiences.internal.blueprint.search.request.body.contributor.AdvancedSXPSearchRequestBodyContributor;
import com.liferay.search.experiences.internal.blueprint.sort.SortConverter;
import com.liferay.search.experiences.rest.dto.v1_0.AdvancedConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.Collapse;
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
			new AdvancedSXPSearchRequestBodyContributor(
				_collapseBuilderFactory, _innerHitBuilderFactory,
				_sortConverter);
	}

	@Test
	public void testCollapse() {
		Configuration configuration = new Configuration();

		AdvancedConfiguration advancedConfiguration =
			new AdvancedConfiguration();

		Collapse collapse = new Collapse();

		collapse.setField("entryClassPK");

		advancedConfiguration.setCollapse(collapse);

		configuration.setAdvancedConfiguration(advancedConfiguration);

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		_advancedSXPSearchRequestBodyContributor.contribute(
			configuration, searchRequestBuilder, null);

		SearchRequest searchRequest = searchRequestBuilder.build();

		com.liferay.portal.search.collapse.Collapse searchRequestCollapse =
			searchRequest.getCollapse();

		Assert.assertEquals("entryClassPK", searchRequestCollapse.getField());
	}

	@Test
	public void testFields() {
		Configuration configuration = new Configuration();

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		AdvancedConfiguration advancedConfiguration =
			new AdvancedConfiguration();

		List<String> storedFields = Arrays.asList(
			Field.TITLE, Field.DESCRIPTION);

		advancedConfiguration.setFields(storedFields.toArray(new String[0]));

		configuration.setAdvancedConfiguration(advancedConfiguration);

		_advancedSXPSearchRequestBodyContributor.contribute(
			configuration, searchRequestBuilder, null);

		SearchContext searchContext = searchRequestBuilder.withSearchContextGet(
			Function.identity());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		Assert.assertEquals(
			storedFields, Arrays.asList(queryConfig.getSelectedFieldNames()));
	}

	@Test
	public void testStoredFields() {
		Configuration configuration = new Configuration();

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		AdvancedConfiguration advancedConfiguration =
			new AdvancedConfiguration();

		List<String> storedFields = Arrays.asList(
			Field.TITLE, Field.DESCRIPTION);

		advancedConfiguration.setStored_fields(
			storedFields.toArray(new String[0]));

		configuration.setAdvancedConfiguration(advancedConfiguration);

		_advancedSXPSearchRequestBodyContributor.contribute(
			configuration, searchRequestBuilder, null);

		SearchRequest searchRequest = searchRequestBuilder.build();

		Assert.assertArrayEquals(
			storedFields.toArray(), searchRequest.getStoredFields());
	}

	private AdvancedSXPSearchRequestBodyContributor
		_advancedSXPSearchRequestBodyContributor;
	private final CollapseBuilderFactory _collapseBuilderFactory =
		new CollapseBuilderFactoryImpl();
	private final InnerHitBuilderFactory _innerHitBuilderFactory =
		new InnerHitBuilderFactoryImpl();
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory =
		new SearchRequestBuilderFactoryImpl();
	private final SortConverter _sortConverter = new SortConverter(
		new GeoBuildersImpl(), new QueryConverter(new QueriesImpl()),
		new ScriptConverter(new ScriptsImpl()), new SortsImpl());

}