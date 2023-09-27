/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
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
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

		_indexerFixture = new IndexerFixture<>(
			JournalArticle.class, _searchRequestBuilderFactory);

		_journalArticleSearchFixture = new JournalArticleSearchFixture(
			_ddmStructureLocalService, _journalArticleLocalService, _portal);

		_journalArticleSearchFixture.setUp();

		_journalArticles = _journalArticleSearchFixture.getJournalArticles();

		UserTestUtil.setUser(TestPropsValues.getUser());

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

		Map<String, String> titleStrings = HashMapBuilder.put(
			"title_en_US", originalTitle
		).put(
			"title_hu_HU", translatedTitle
		).build();

		Map<String, String> contentStrings = HashMapBuilder.put(
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

		SearchResponse searchResponse =
			_indexerFixture.searchOnlyOneSearchResponse(
				searchTerm, LocaleUtil.HUNGARY);

		FieldValuesAssert.assertFieldValues(
			titleStrings, name -> name.startsWith("title_"), searchResponse);
		FieldValuesAssert.assertFieldValues(
			contentStrings, name -> name.startsWith("content_"),
			searchResponse);
		FieldValuesAssert.assertFieldValues(
			localizedTitleStrings, name -> name.startsWith("localized_title"),
			searchResponse);
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
			"alpha", LocaleUtil.HUNGARY, "content_", "content_en_US");

		assertSearchOneDocumentOneField(
			"gamma", LocaleUtil.HUNGARY, "title_", "title_en_US");
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

		Map<String, String> titleStrings = HashMapBuilder.put(
			"title_en_US", originalTitle
		).put(
			"title_pt_BR", translatedTitle
		).build();

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

		SearchResponse searchResponse =
			_indexerFixture.searchOnlyOneSearchResponse(
				searchTerm, LocaleUtil.BRAZIL);

		FieldValuesAssert.assertFieldValues(
			titleStrings, name -> name.startsWith("title"), searchResponse);
		FieldValuesAssert.assertFieldValues(
			contentStrings, name -> name.startsWith("content"), searchResponse);
		FieldValuesAssert.assertFieldValues(
			localizedTitleStrings, name -> name.startsWith("localized_title"),
			searchResponse);
		FieldValuesAssert.assertFieldValues(
			ddmContentStrings, name -> name.startsWith("ddm__text"),
			searchResponse);
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

		for (String searchTerm : Arrays.asList("新規", "作成", "新", "作")) {
			SearchResponse searchResponse =
				_indexerFixture.searchOnlyOneSearchResponse(
					searchTerm, LocaleUtil.JAPAN);

			FieldValuesAssert.assertFieldValues(
				titleStrings, name -> name.startsWith("title_"),
				searchResponse);
			FieldValuesAssert.assertFieldValues(
				contentStrings, name -> name.startsWith("content_"),
				searchResponse);
			FieldValuesAssert.assertFieldValues(
				localizedTitleStrings,
				name -> name.startsWith("localized_title"), searchResponse);
		}
	}

	@Test
	public void testJapaneseTitleFullWordOnly() throws Exception {
		Map<String, String> titleStrings = HashMapBuilder.put(
			"title_ja_JP",
			() -> {
				String full = "新規作成";

				for (String title : Arrays.asList(full, "新大阪", "作戦大成功")) {
					_journalArticleSearchFixture.addArticle(
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
						});
				}

				return full;
			}
		).build();

		for (String searchTerm : Arrays.asList("新規", "作成")) {
			FieldValuesAssert.assertFieldValues(
				titleStrings, "title_",
				_indexerFixture.searchOnlyOne(searchTerm, LocaleUtil.JAPAN),
				searchTerm);
		}
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertSearchOneDocumentOneField(
		String fieldValue, Locale locale, String fieldPrefix,
		String fieldName) {

		Document document = _indexerFixture.searchOnlyOne(fieldValue, locale);

		FieldValuesAssert.assertFieldValues(
			Collections.singletonMap(fieldName, fieldValue), fieldPrefix,
			document, document.toString());
	}

	private Map<String, String> _withSortableValues(Map<String, String> map) {
		Map<String, String> values = new HashMap<>();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			values.put(
				entry.getKey() + "_sortable",
				StringUtil.toLowerCase(entry.getValue()));
		}

		values.putAll(map);

		return values;
	}

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static Portal _portal;

	@DeleteAfterTestRun
	private Group _group;

	private IndexerFixture<JournalArticle> _indexerFixture;

	@DeleteAfterTestRun
	private List<JournalArticle> _journalArticles;

	private JournalArticleSearchFixture _journalArticleSearchFixture;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}