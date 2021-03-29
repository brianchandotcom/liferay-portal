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

package com.liferay.portal.search.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.highlight.HighlightBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class SearchRequestBuilderHighlightTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testSearchWithHighlight() throws Exception {
		_addJournalArticle(
			"alpha", "alpha beta", "alpha beta alpha",
			"alpha beta gamma alpha eta theta alpha zeta eta alpha iota",
			"alpha beta gamma delta epsilon zeta eta theta iota alpha");

		Highlight highlight = _highlightBuilderFactory.builder(
		).addFieldConfig(
			_fieldConfigBuilderFactory.builder(
				"title_en_US"
			).build()
		).fragmentSize(
			20
		).postTags(
			"[/H]"
		).preTags(
			"[H]"
		).build();

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).queryString(
				"alpha"
			).highlight(
				highlight
			);

		_assertSearch(
			Arrays.asList(
				"[H]alpha[/H]", "[H]alpha[/H] beta",
				"[H]alpha[/H] beta [H]alpha[/H]",
				"[H]alpha[/H] beta gamma [H]alpha[/H]...eta theta [H]alpha" +
					"[/H] zeta...eta [H]alpha[/H] iota",
				"[H]alpha[/H] beta gamma delta...zeta eta theta iota [H]alpha" +
					"[/H]"),
			"title_en_US", searchRequestBuilder);
	}

	@Test
	public void testSearchWithHighlightEnabled() throws Exception {
		_addJournalArticle("alpha beta", "alpha beta alpha");

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).groupIds(
				_group.getGroupId()
			).queryString(
				"alpha"
			).highlightEnabled(
				true
			);

		_assertSearch(
			Arrays.asList(
				"<liferay-hl>alpha</liferay-hl> beta",
				"<liferay-hl>alpha</liferay-hl> beta " +
					"<liferay-hl>alpha</liferay-hl>"),
			"title_en_US", searchRequestBuilder);
	}

	private void _addJournalArticle(String... titles) throws Exception {
		for (String title : titles) {
			JournalTestUtil.addArticle(
				_group.getGroupId(), 0,
				PortalUtil.getClassNameId(JournalArticle.class),
				HashMapBuilder.put(
					LocaleUtil.US, title
				).build(),
				null,
				HashMapBuilder.put(
					LocaleUtil.US, ""
				).build(),
				LocaleUtil.getSiteDefault(), false, true, _serviceContext);
		}
	}

	private void _assertSearch(
		List<String> expected, String fieldName,
		SearchRequestBuilder searchRequestBuilder) {

		if (!_isElasticsearch()) {
			return;
		}

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		Hits hits = searchResponse.withHitsGet(Function.identity());

		DocumentsAssert.assertValuesIgnoreRelevance(
			searchResponse.getRequestString(), hits.getDocs(),
			"snippet_" + fieldName, expected);
	}

	private boolean _isElasticsearch() {
		return Objects.equals(_searchEngine.getVendor(), "Elasticsearch");
	}

	@Inject
	private FieldConfigBuilderFactory _fieldConfigBuilderFactory;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private HighlightBuilderFactory _highlightBuilderFactory;

	@Inject(filter = "search.engine.id=SYSTEM_ENGINE")
	private SearchEngine _searchEngine;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private ServiceContext _serviceContext;

}