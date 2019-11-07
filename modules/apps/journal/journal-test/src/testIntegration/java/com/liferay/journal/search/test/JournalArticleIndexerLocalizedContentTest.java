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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
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

		_indexerFixture = new IndexerFixture<>(JournalArticle.class);

		_journalArticleSearchFixture = new JournalArticleSearchFixture(
			_journalArticleLocalService);

		_journalArticleSearchFixture.setUp();

		_journalArticles = _journalArticleSearchFixture.getJournalArticles();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());
	}

	@After
	public void tearDown() throws Exception {
		_journalArticleSearchFixture.tearDown();
	}

	@Test
	public void testIndexedFields() throws Exception {
		String originalTitle = "entity title";
		String translatedTitle = "entitas neve";

		String originalContent = RandomTestUtil.randomString();
		String translatedContent = RandomTestUtil.randomString();

		_journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_group.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								put(LocaleUtil.US, originalContent);
								put(LocaleUtil.HUNGARY, translatedContent);

								setDefaultLocale(LocaleUtil.US);
								setName("content");
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(LocaleUtil.US, originalTitle);
								put(LocaleUtil.HUNGARY, translatedTitle);
							}
						});
				}
			});

		Map<String, String> titleStrings = HashMapBuilder.<String, String>put(
			"title_en_US", originalTitle
		).put(
			"title_hu_HU", translatedTitle
		).build();

		Map<String, String> contentStrings = HashMapBuilder.<String, String>put(
			"content_en_US", originalContent
		).put(
			"content_hu_HU", translatedContent
		).build();

		Map<String, String> localizedTitleStrings = _withSortableValues(
			new HashMap<String, String>() {
				{
					Set<Locale> locales = LanguageUtil.getAvailableLocales();

					locales.forEach(
						locale -> {
							String mapKey = StringBundler.concat(
								"localized_title_", locale.getLanguage(), "_",
								locale.getCountry());

							put(mapKey, originalTitle);
						});

					put("localized_title_hu_HU", translatedTitle);
				}
			});

		localizedTitleStrings.put("localized_title", originalTitle);

		String searchTerm = "nev";

		Document document = _indexerFixture.searchOnlyOne(
			searchTerm, LocaleUtil.HUNGARY);

		FieldValuesAssert.assertFieldValues(
			titleStrings, "title", document, searchTerm);

		FieldValuesAssert.assertFieldValues(
			contentStrings, "content", document, searchTerm);

		FieldValuesAssert.assertFieldValues(
			localizedTitleStrings, "localized_title", document, searchTerm);
	}

	@Test
	public void testIndexedFieldsInOnlyOneLanguage() throws Exception {
		_journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_group.getGroupId());
					setJournalArticleContent(
						new JournalArticleContent() {
							{
								put(LocaleUtil.US, "alpha");

								setDefaultLocale(LocaleUtil.US);
								setName("content");
							}
						});
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(LocaleUtil.US, "gamma");
							}
						});
				}
			});

		assertSearchOneDocumentOneField(
			"alpha", LocaleUtil.HUNGARY, "content", "content_en_US");

		assertSearchOneDocumentOneField(
			"gamma", LocaleUtil.HUNGARY, "title", "title_en_US");
	}

	@Test
	public void testIndexedFieldsMissingWhenContentIsEmpty() throws Exception {
		String originalTitle = "entity title";
		String translatedTitle = "título da entidade";

		JournalArticle journalArticle = _journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_group.getGroupId());
					setJournalArticleContent(new JournalArticleContent());
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(LocaleUtil.US, originalTitle);
								put(LocaleUtil.BRAZIL, translatedTitle);
							}
						});
				}
			});

		String articleId = journalArticle.getArticleId();

		Map<String, String> titleStrings = Collections.emptyMap();

		Map<String, String> contentStrings = Collections.emptyMap();

		Map<String, String> localizedTitleStrings = _withSortableValues(
			new HashMap<String, String>() {
				{
					Set<Locale> locales = LanguageUtil.getAvailableLocales();

					locales.forEach(
						locale -> {
							String mapKey = StringBundler.concat(
								"localized_title_", locale.getLanguage(), "_",
								locale.getCountry());

							put(mapKey, originalTitle);
						});

					put("localized_title_pt_BR", translatedTitle);
				}
			});

		localizedTitleStrings.put("localized_title", originalTitle);

		Map<String, String> ddmContentStrings = Collections.emptyMap();

		String searchTerm = articleId;

		Document document = _indexerFixture.searchOnlyOne(
			searchTerm, LocaleUtil.BRAZIL);

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

		String content = RandomTestUtil.randomString();

		_journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_group.getGroupId());
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

		Map<String, String> titleStrings = Collections.singletonMap(
			"title_ja_JP", title);

		Map<String, String> contentStrings = Collections.singletonMap(
			"content_ja_JP", content);

		Map<String, String> localizedTitleStrings = _withSortableValues(
			new HashMap<String, String>() {
				{
					Set<Locale> locales = LanguageUtil.getAvailableLocales();

					locales.forEach(
						locale -> {
							String mapKey = StringBundler.concat(
								"localized_title_", locale.getLanguage(), "_",
								locale.getCountry());

							put(mapKey, title);
						});
				}
			});

		localizedTitleStrings.put("localized_title", title);

		String word1 = "新規";
		String word2 = "作成";
		String prefix1 = "新";
		String prefix2 = "作";

		Stream.of(
			word1, word2, prefix1, prefix2
		).forEach(
			searchTerm -> {
				Document document = _indexerFixture.searchOnlyOne(
					searchTerm, LocaleUtil.JAPAN);

				FieldValuesAssert.assertFieldValues(
					titleStrings, "title", document, searchTerm);

				FieldValuesAssert.assertFieldValues(
					contentStrings, "content", document, searchTerm);

				FieldValuesAssert.assertFieldValues(
					localizedTitleStrings, "localized_title", document,
					searchTerm);
			}
		);
	}

	@Test
	public void testJapaneseTitleFullWordOnly() throws Exception {
		String full = "新規作成";
		String partial1 = "新大阪";
		String partial2 = "作戦大成功";

		Stream.of(
			full, partial1, partial2
		).forEach(
			title -> _journalArticleSearchFixture.addArticle(
				new JournalArticleBlueprint() {
					{
						setGroupId(_group.getGroupId());
						setJournalArticleContent(
							new JournalArticleContent() {
								{
									put(
										LocaleUtil.JAPAN,
										RandomTestUtil.randomString());

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
				})
		);

		Map<String, String> titleStrings = HashMapBuilder.<String, String>put(
			"title_ja_JP", full
		).build();

		String word1 = "新規";
		String word2 = "作成";

		Stream.of(
			word1, word2
		).forEach(
			searchTerm -> {
				Document document = _indexerFixture.searchOnlyOne(
					searchTerm, LocaleUtil.JAPAN);

				FieldValuesAssert.assertFieldValues(
					titleStrings, "title", document, searchTerm);
			}
		);
	}

	protected void assertSearchOneDocumentOneField(
		String fieldValue, Locale locale, String fieldPrefix,
		String fieldName) {

		Document document = _indexerFixture.searchOnlyOne(fieldValue, locale);

		FieldValuesAssert.assertFieldValues(
			Collections.singletonMap(fieldName, fieldValue), fieldPrefix,
			document, document.toString());
	}

	private static Map<String, String> _withSortableValues(
		Map<String, String> map) {

		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Stream<Map.Entry<String, String>> entries = entrySet.stream();

		Map<String, String> map2 = entries.collect(
			Collectors.toMap(
				entry -> entry.getKey() + "_sortable",
				entry -> StringUtil.toLowerCase(entry.getValue())));

		map2.putAll(map);

		return map2;
	}

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private IndexerFixture<JournalArticle> _indexerFixture;

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

	private JournalArticleSearchFixture _journalArticleSearchFixture;

}