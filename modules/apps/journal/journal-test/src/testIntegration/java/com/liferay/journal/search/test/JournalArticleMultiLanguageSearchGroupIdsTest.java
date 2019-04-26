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
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class JournalArticleMultiLanguageSearchGroupIdsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_indexer = _indexerRegistry.getIndexer(JournalArticle.class);

		_journalArticleSearchFixture = new JournalArticleSearchFixture(
			_journalArticleLocalService);

		_journalArticleSearchFixture.setUp();

		_journalArticles = _journalArticleSearchFixture.getJournalArticles();

		_userSearchFixture = new UserSearchFixture();

		_userSearchFixture.setUp();

		_groups = _userSearchFixture.getGroups();

		_jpGroup = addGroup(LocaleUtil.JAPAN);
		_usGroup = addGroup(LocaleUtil.US);
	}

	@After
	public void tearDown() throws Exception {
		_journalArticleSearchFixture.tearDown();

		_userSearchFixture.tearDown();
	}

	@Test
	public void testSearchEverything() throws Exception {
		String jpContent = RandomTestUtil.randomString();
		String jpTitle = "新規作成";

		addJapanArticle(jpTitle, jpContent);

		String usContent = RandomTestUtil.randomString();
		String usTitle = "entity title";

		addUSArticle(usTitle, usContent);

		SearchContext searchContext = _getSearchContext("entity 作成");

		List<Document> documents = _search(searchContext);

		assertTitleAndContent(
			LocaleUtil.JAPAN, jpTitle, jpContent, documents, searchContext);

		assertTitleAndContent(
			LocaleUtil.US, usTitle, usContent, documents, searchContext);

		Assert.assertEquals(documents.toString(), 2, documents.size());
	}

	@Test
	public void testSearchOneSiteOnlyJapan() throws Exception {
		String jpContent = RandomTestUtil.randomString();
		String jpTitle = "新規作成";

		addJapanArticle(jpTitle, jpContent);

		addUSArticle("entity title", RandomTestUtil.randomString());

		SearchContext searchContext = _getSearchContext("entity 作成", _jpGroup);

		List<Document> documents = _search(searchContext);

		assertTitleAndContent(
			LocaleUtil.JAPAN, jpTitle, jpContent, documents, searchContext);

		Assert.assertEquals(documents.toString(), 1, documents.size());
	}

	@Test
	public void testSearchOneSiteOnlyUS() throws Exception {
		addJapanArticle("新規作成", RandomTestUtil.randomString());

		String usContent = RandomTestUtil.randomString();
		String usTitle = "entity title";

		addUSArticle(usTitle, usContent);

		SearchContext searchContext = _getSearchContext("entity 作成", _usGroup);

		List<Document> documents = _search(searchContext);

		assertTitleAndContent(
			LocaleUtil.US, usTitle, usContent, documents, searchContext);

		Assert.assertEquals(documents.toString(), 1, documents.size());
	}

	@Test
	public void testSearchTwoSitesJapanAndUS() throws Exception {
		String jpContent = RandomTestUtil.randomString();
		String jpTitle = "新規作成";

		addJapanArticle(jpTitle, jpContent);

		String usContent = RandomTestUtil.randomString();
		String usTitle = "entity title";

		addUSArticle(usTitle, usContent);

		SearchContext searchContext = _getSearchContext(
			"entity 作成", _usGroup, _jpGroup);

		List<Document> documents = _search(searchContext);

		assertTitleAndContent(
			LocaleUtil.JAPAN, jpTitle, jpContent, documents, searchContext);

		assertTitleAndContent(
			LocaleUtil.US, usTitle, usContent, documents, searchContext);

		Assert.assertEquals(documents.toString(), 2, documents.size());
	}

	protected Group addGroup(Locale locale) throws Exception, PortalException {
		return _userSearchFixture.addGroup(
			new GroupBlueprint() {
				{
					setDefaultLocale(locale);
				}
			});
	}

	protected void addJapanArticle(String title, String content) {
		_journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_jpGroup.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								put(LocaleUtil.JAPAN, content);

								setDefaultLocale(LocaleUtil.JAPAN);
								setName("content");
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(LocaleUtil.JAPAN, title);
							}
						});
				}
			});
	}

	protected void addUSArticle(String title, String content) {
		_journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_usGroup.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								put(LocaleUtil.US, content);

								setDefaultLocale(LocaleUtil.US);
								setName("content");
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(LocaleUtil.US, title);
							}
						});
				}
			});
	}

	protected void assertOnlyPrefixedFieldIsTranslation(
		String fieldPrefix, Locale locale, String fieldValue, Document document,
		SearchContext searchContext) {

		FieldValuesAssert.assertFieldValues(
			Collections.singletonMap(fieldPrefix + "_" + locale, fieldValue),
			fieldPrefix, document,
			(String)searchContext.getAttribute("queryString"));
	}

	protected void assertTitleAndContent(
		Locale locale, String title, String content, List<Document> documents,
		SearchContext searchContext) {

		Document document = findDocument(locale, documents);

		assertOnlyPrefixedFieldIsTranslation(
			"content", locale, content, document, searchContext);

		assertOnlyPrefixedFieldIsTranslation(
			"title", locale, title, document, searchContext);
	}

	protected Document findDocument(Locale locale, List<Document> documents) {
		for (Document document : documents) {
			if (document.getField("title_" + locale) != null) {
				return document;
			}
		}

		throw new AssertionError(locale + "->" + documents);
	}

	private SearchContext _getSearchContext(
		String searchTerm, Group... groups) {

		try {
			SearchContext searchContext = new SearchContext();

			searchContext.setCompanyId(TestPropsValues.getCompanyId());
			searchContext.setKeywords(searchTerm);
			searchContext.setUserId(TestPropsValues.getUserId());

			if (ArrayUtil.isNotEmpty(groups)) {
				searchContext.setGroupIds(
					Stream.of(
						groups
					).mapToLong(
						Group::getGroupId
					).toArray());
			}

			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setSelectedFieldNames(StringPool.STAR);

			return searchContext;
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private List<Document> _search(SearchContext searchContext) {
		try {
			Hits hits = _indexer.search(searchContext);

			return hits.toList();
		}
		catch (SearchException se) {
			throw new RuntimeException(se);
		}
	}

	@Inject
	private static GroupService _groupService;

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private Indexer<JournalArticle> _indexer;

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

	private JournalArticleSearchFixture _journalArticleSearchFixture;
	private Group _jpGroup;
	private UserSearchFixture _userSearchFixture;
	private Group _usGroup;

}