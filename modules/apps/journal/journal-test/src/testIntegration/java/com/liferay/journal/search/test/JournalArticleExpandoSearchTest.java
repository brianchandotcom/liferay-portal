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

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleBlueprintBuilder;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.ExpandoTableSearchFixture;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;

import java.io.Serializable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class JournalArticleExpandoSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_journalArticleSearchFixture = new JournalArticleSearchFixture(
			journalArticleLocalService);

		_journalArticles = _journalArticleSearchFixture.getJournalArticles();

		ExpandoTableSearchFixture expandoTableSearchFixture =
			new ExpandoTableSearchFixture(
				classNameLocalService, expandoColumnLocalService,
				expandoTableLocalService);

		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		Group group = groupSearchFixture.addGroup(new GroupBlueprint());

		_expandoColumns = expandoTableSearchFixture.getExpandoColumns();
		_expandoTables = expandoTableSearchFixture.getExpandoTables();
		_expandoTableSearchFixture = expandoTableSearchFixture;

		_group = group;
		_groups = groupSearchFixture.getGroups();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testGeolocationComboQueryString() throws Exception {
		addJournalArticleCombo(
			"Software Engineer", _EXPANDO_COLUMN, ExpandoColumnConstants.STRING,
			_GEOLOCATION_VALUE, _EXPANDO_COLUMN_GEOLOCATION,
			ExpandoColumnConstants.GEOLOCATION);

		assertSearch(
			searchRequestBuilder -> searchRequestBuilder.queryString(
				"Engineer"),
			"[Software Engineer]");

		assertGeolocationExpandoFieldIndexed();

		assertNoGeolocationExpandoClauseInSearchQuery(
			searchRequestBuilder -> searchRequestBuilder.queryString("alpha"));
	}

	@Test
	public void testGeolocationQueryString() throws Exception {
		addJournalArticle(
			_GEOLOCATION_VALUE, _EXPANDO_COLUMN_GEOLOCATION,
			ExpandoColumnConstants.GEOLOCATION);

		assertNoGeolocationExpandoClauseInSearchQuery(
			searchRequestBuilder -> searchRequestBuilder.queryString("alpha"));
	}

	@Test
	public void testQueryString() throws Exception {
		addJournalArticle("Software Engineer");

		assertSearch(
			searchRequestBuilder -> searchRequestBuilder.queryString(
				"Engineer"),
			"[Software Engineer]");
	}

	@Test
	public void testSearchContextAttributeParamsExpandoAttributes()
		throws Exception {

		addJournalArticle("Software Engineer");

		String expected = "[Software Engineer]";

		if (!_LPS_123611_FIXED) {
			expected = "[]";
		}

		assertSearch(
			searchRequestBuilder -> searchRequestBuilder.queryString(
				"Nonexistent"
			).withSearchContext(
				searchContext -> putParamsExpandoAttributes(
					"Engineer", searchContext)
			),
			expected);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void addJournalArticle(String expandoValue) throws Exception {
		addJournalArticle(
			expandoValue, _EXPANDO_COLUMN, ExpandoColumnConstants.STRING);
	}

	protected void addJournalArticle(
			String expandoValue, String expandoColumn, int expandoColumnType)
		throws Exception {

		addJournalExpandoColumn(expandoColumn, expandoColumnType);

		addJournalArticleWithExpando(
			Collections.singletonMap(expandoColumn, expandoValue));
	}

	protected void addJournalArticleCombo(
			String expandoValue1, String expandoColumn1, int expandoColumnType1,
			String expandoValue2, String expandoColumn2, int expandoColumnType2)
		throws Exception {

		addJournalExpandoColumn(expandoColumn1, expandoColumnType1);
		addJournalExpandoColumn(expandoColumn2, expandoColumnType2);

		Map<String, Serializable> expandoMap =
			HashMapBuilder.<String, Serializable>put(
				expandoColumn1, expandoValue1
			).put(
				expandoColumn2, expandoValue2
			).build();

		addJournalArticleWithExpando(expandoMap);
	}

	protected void addJournalArticleWithExpando(
		Map<String, Serializable> expandoMap) {

		_journalArticleSearchFixture.addArticle(
			JournalArticleBlueprintBuilder.builder(
			).expandoBridgeAttributes(
				expandoMap
			).groupId(
				_group.getGroupId()
			).journalArticleContent(
				new JournalArticleContent() {
					{
						put(LocaleUtil.US, "alpha");

						setDefaultLocale(LocaleUtil.US);
						setName("content");
					}
				}
			).journalArticleTitle(
				new JournalArticleTitle() {
					{
						put(LocaleUtil.US, "gamma");
					}
				}
			).build());
	}

	protected void addJournalExpandoColumn(
			String expandoColumn, int expandoColumnType)
		throws Exception {

		_expandoTableSearchFixture.addExpandoColumn(
			JournalArticle.class, ExpandoColumnConstants.INDEX_TYPE_KEYWORD,
			expandoColumnType, expandoColumn);
	}

	protected void assertGeolocationExpandoFieldIndexed() {
		assertSearch(
			searchRequestBuilder -> searchRequestBuilder.queryString("alpha"),
			_EXPANDO_COLUMN_GEOLOCATION_FULL_NAME, _GEOLOCATION_EXPECTED);
	}

	protected void assertNoGeolocationExpandoClauseInSearchQuery(
		Consumer<SearchRequestBuilder> consumer) {

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).fields(
				StringPool.STAR
			).groupIds(
				_group.getGroupId()
			).modelIndexerClasses(
				JournalArticle.class
			).withSearchRequestBuilder(
				consumer
			).build());

		String requestString = searchResponse.getRequestString();

		Assert.assertFalse(
			requestString.contains(_EXPANDO_COLUMN_GEOLOCATION_FULL_NAME));

		Assert.assertFalse(
			requestString.contains(
				"expando__keyword__custom_fields__" +
					_EXPANDO_COLUMN_GEOLOCATION));

		Assert.assertFalse(
			requestString.contains(
				"expando__custom_fields__" + _EXPANDO_COLUMN_GEOLOCATION));
	}

	protected void assertSearch(
		Consumer<SearchRequestBuilder> consumer, String expected) {

		assertSearch(
			consumer, "expando__keyword__custom_fields__" + _EXPANDO_COLUMN,
			expected);
	}

	protected void assertSearch(
		Consumer<SearchRequestBuilder> consumer, String fieldName,
		String expected) {

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).fields(
				StringPool.STAR
			).groupIds(
				_group.getGroupId()
			).modelIndexerClasses(
				JournalArticle.class
			).withSearchRequestBuilder(
				consumer
			).build());

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(),
			searchResponse.getDocumentsStream(), fieldName, expected);
	}

	protected void putParamsExpandoAttributes(
		String string, SearchContext searchContext) {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			params = new LinkedHashMap<>();

			searchContext.setAttribute("params", params);
		}

		params.put("expandoAttributes", string);
	}

	@Inject
	protected ClassNameLocalService classNameLocalService;

	@Inject
	protected ExpandoColumnLocalService expandoColumnLocalService;

	@Inject
	protected ExpandoTableLocalService expandoTableLocalService;

	@Inject
	protected JournalArticleLocalService journalArticleLocalService;

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private static final String _EXPANDO_COLUMN = "expandoColumn";

	private static final String _EXPANDO_COLUMN_GEOLOCATION = "location";

	private static final String _EXPANDO_COLUMN_GEOLOCATION_FULL_NAME =
		"expando__keyword__custom_fields__location_geolocation";

	private static final String _GEOLOCATION_EXPECTED =
		"[(34.013727866113186,-117.42460448294878)]";

	private static final String _GEOLOCATION_VALUE =
		"{\"latitude\":34.013727866113186, \"longitude\":-117.42460448294878}";

	private static final boolean _LPS_123611_FIXED = false;

	@DeleteAfterTestRun
	private List<ExpandoColumn> _expandoColumns;

	@DeleteAfterTestRun
	private List<ExpandoTable> _expandoTables;

	private ExpandoTableSearchFixture _expandoTableSearchFixture;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

	private JournalArticleSearchFixture _journalArticleSearchFixture;

}