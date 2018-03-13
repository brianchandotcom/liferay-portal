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
import com.liferay.journal.test.util.FieldValuesAssert;
import com.liferay.journal.test.util.JournalArticleBuilder;
import com.liferay.journal.test.util.JournalArticleContent;
import com.liferay.journal.test.util.JournalArticleTitle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class JournalArticleIndexerLocalizedContentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_indexer = _indexerRegistry.getIndexer(JournalArticle.class);

		_journalArticleBuilder = new JournalArticleBuilder();

		_journalArticleBuilder.setGroupId(_group.getGroupId());

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());
	}

	@Test
	public void testIndexedFields() throws Exception {
		String originalTitle = "entity title";
		String translatedTitle = "entitas neve";

		setTitle(
			new JournalArticleTitle() {
				{
					put(LocaleUtil.US, originalTitle);
					put(LocaleUtil.HUNGARY, translatedTitle);
				}
			});

		String originalContent = RandomTestUtil.randomString();
		String translatedContent = RandomTestUtil.randomString();

		setContent(
			new JournalArticleContent() {
				{
					name = "content";
					defaultLocale = LocaleUtil.US;

					put(LocaleUtil.US, originalContent);
					put(LocaleUtil.HUNGARY, translatedContent);
				}
			});

		addArticle();

		Map<String, String> titleStrings = new HashMap<String, String>() {
			{
				put("title_en_US", originalTitle);
				put("title_hu_HU", translatedTitle);
			}
		};

		Map<String, String> contentStrings = new HashMap<String, String>() {
			{
				put("content", originalContent);
				put("content_en_US", originalContent);
				put("content_hu_HU", translatedContent);
			}
		};

		Map<String, String> localizedTitleStrings = _withSortableValues(
			new HashMap<String, String>() {
				{
					put("localized_title_en_US", originalTitle);
					put("localized_title_hu_HU", translatedTitle);

					put("localized_title_ca_ES", originalTitle);
					put("localized_title_de_DE", originalTitle);
					put("localized_title_es_ES", originalTitle);
					put("localized_title_fi_FI", originalTitle);
					put("localized_title_fr_FR", originalTitle);
					put("localized_title_iw_IL", originalTitle);
					put("localized_title_ja_JP", originalTitle);
					put("localized_title_nl_NL", originalTitle);
					put("localized_title_pt_BR", originalTitle);
					put("localized_title_zh_CN", originalTitle);
				}
			});

		localizedTitleStrings.put("localized_title", originalTitle);

		String searchTerm = "nev";

		Document document = _search(searchTerm, LocaleUtil.HUNGARY);

		FieldValuesAssert.assertFieldValues(
			titleStrings, "title", document, searchTerm);

		FieldValuesAssert.assertFieldValues(
			contentStrings, "content", document, searchTerm);

		FieldValuesAssert.assertFieldValues(
			localizedTitleStrings, "localized_title", document, searchTerm);
	}

	@Test
	public void testIndexedFieldsMissingWhenContentIsEmpty() throws Exception {
		String originalTitle = "entity title";
		String translatedTitle = "título da entidade";

		setTitle(
			new JournalArticleTitle() {
				{
					put(LocaleUtil.US, originalTitle);
					put(LocaleUtil.BRAZIL, translatedTitle);
				}
			});

		setContent(new JournalArticleContent());

		JournalArticle journalArticle = addArticle();

		String articleId = journalArticle.getArticleId();

		Map<String, String> titleStrings = Collections.emptyMap();

		Map<String, String> contentStrings = Collections.emptyMap();

		Map<String, String> localizedTitleStrings = _withSortableValues(
			new HashMap<String, String>() {
				{
					put("localized_title_en_US", originalTitle);
					put("localized_title_pt_BR", translatedTitle);

					put("localized_title_ca_ES", originalTitle);
					put("localized_title_de_DE", originalTitle);
					put("localized_title_es_ES", originalTitle);
					put("localized_title_fi_FI", originalTitle);
					put("localized_title_fr_FR", originalTitle);
					put("localized_title_hu_HU", originalTitle);
					put("localized_title_iw_IL", originalTitle);
					put("localized_title_ja_JP", originalTitle);
					put("localized_title_nl_NL", originalTitle);
					put("localized_title_zh_CN", originalTitle);
				}
			});

		localizedTitleStrings.put("localized_title", originalTitle);

		Map<String, String> ddmContentStrings = Collections.emptyMap();

		String searchTerm = articleId;

		Document document = _search(searchTerm, LocaleUtil.BRAZIL);

		FieldValuesAssert.assertFieldValues(
			titleStrings, "title", document, searchTerm);

		FieldValuesAssert.assertFieldValues(
			contentStrings, "content", document, searchTerm);

		FieldValuesAssert.assertFieldValues(
			localizedTitleStrings, "localized_title", document, searchTerm);

		FieldValuesAssert.assertFieldValues(
			ddmContentStrings, "ddm__text", document, searchTerm);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		String title = "新規作成";

		setTitle(
			new JournalArticleTitle() {
				{
					put(LocaleUtil.JAPAN, title);
				}
			});

		String content = RandomTestUtil.randomString();

		setContent(
			new JournalArticleContent() {
				{
					name = "content";
					defaultLocale = LocaleUtil.JAPAN;

					put(LocaleUtil.JAPAN, content);
				}
			});

		addArticle();

		Map<String, String> titleStrings = new HashMap<String, String>() {
			{
				put("title_ja_JP", title);
			}
		};

		Map<String, String> contentStrings = new HashMap<String, String>() {
			{
				put("content", content);
				put("content_ja_JP", content);
			}
		};

		Map<String, String> localizedTitleStrings = _withSortableValues(
			new HashMap<String, String>() {
				{
					put("localized_title_ja_JP", title);

					put("localized_title_ca_ES", title);
					put("localized_title_de_DE", title);
					put("localized_title_en_US", title);
					put("localized_title_es_ES", title);
					put("localized_title_fi_FI", title);
					put("localized_title_fr_FR", title);
					put("localized_title_hu_HU", title);
					put("localized_title_iw_IL", title);
					put("localized_title_nl_NL", title);
					put("localized_title_pt_BR", title);
					put("localized_title_zh_CN", title);
				}
			});

		localizedTitleStrings.put("localized_title", title);

		String word1 = "新規";
		String word2 = "作成";
		String prefix1 = "新";
		String prefix2 = "作";

		Stream<String> searchTerms = Stream.of(word1, word2, prefix1, prefix2);

		searchTerms.forEach(
			searchTerm -> {
				Document document = _search(searchTerm, LocaleUtil.JAPAN);

				FieldValuesAssert.assertFieldValues(
					titleStrings, "title", document, searchTerm);

				FieldValuesAssert.assertFieldValues(
					contentStrings, "content", document, searchTerm);

				FieldValuesAssert.assertFieldValues(
					localizedTitleStrings, "localized_title", document,
					searchTerm);
			});
	}

	@Test
	public void testJapaneseTitleFullWordOnly() throws Exception {
		String full = "新規作成";
		String partial1 = "新大阪";
		String partial2 = "作戦大成功";

		Stream<String> titles = Stream.of(full, partial1, partial2);

		titles.forEach(
			title -> {
				setTitle(
					new JournalArticleTitle() {
						{
							put(LocaleUtil.JAPAN, title);
						}
					});

				setContent(
					new JournalArticleContent() {
						{
							name = "content";
							defaultLocale = LocaleUtil.JAPAN;

							put(
								LocaleUtil.JAPAN,
								RandomTestUtil.randomString());
						}
					});

				addArticle();
			});

		Map<String, String> titleStrings = new HashMap<String, String>() {
			{
				put("title_ja_JP", full);
			}
		};

		String word1 = "新規";
		String word2 = "作成";

		Stream<String> searchTerms = Stream.of(word1, word2);

		searchTerms.forEach(
			searchTerm -> {
				Document document = _search(searchTerm, LocaleUtil.JAPAN);

				FieldValuesAssert.assertFieldValues(
					titleStrings, "title", document, searchTerm);
			});
	}

	protected JournalArticle addArticle() {
		try {
			return _journalArticleBuilder.addArticle();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void setContent(JournalArticleContent journalArticleContent) {
		_journalArticleBuilder.setContent(journalArticleContent);
	}

	protected void setTitle(JournalArticleTitle journalArticleTitle) {
		_journalArticleBuilder.setTitle(journalArticleTitle);
	}

	private static Map<String, String> _withSortableValues(
		Map<String, String> map) {

		Set<Entry<String, String>> entrySet = map.entrySet();

		Stream<Entry<String, String>> entries = entrySet.stream();

		Map<String, String> map2 = entries.collect(
			Collectors.toMap(
				entry -> entry.getKey() + "_sortable",
				entry -> StringUtil.toLowerCase(entry.getValue())));

		map2.putAll(map);

		return map2;
	}

	private SearchContext _getSearchContext(String searchTerm, Locale locale)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords(searchTerm);

		searchContext.setLocale(locale);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(StringPool.STAR);

		return searchContext;
	}

	private Document _getSingleDocument(String searchTerm, Hits hits) {
		List<Document> documents = hits.toList();

		if (documents.size() == 1) {
			return documents.get(0);
		}

		throw new AssertionError(searchTerm + "->" + documents);
	}

	private Document _search(String searchTerm, Locale locale) {
		try {
			SearchContext searchContext = _getSearchContext(searchTerm, locale);

			Hits hits = _indexer.search(searchContext);

			return _getSingleDocument(searchTerm, hits);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<JournalArticle> _indexer;
	private JournalArticleBuilder _journalArticleBuilder;

}