/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yasuyuki Takeo
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class JournalFolderIndexerLocalizedTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_indexer = _indexerRegistry.getIndexer(JournalFolder.class);

		UserTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		List<Locale> availableLocales = Collections.singletonList(
			LocaleUtil.JAPAN);

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), availableLocales, LocaleUtil.JAPAN);
	}

	@Test
	public void testJapaneseDescription() throws Exception {
		ServiceContext serviceContext = _getServiceContext();

		String description = "諸行無常";
		String title = "平家物語";

		_addFolder(serviceContext, title, description);

		String searchTerm = "諸行";

		Document document = _search(searchTerm, LocaleUtil.JAPAN);

		FieldValuesAssert.assertFieldValues(
			Collections.singletonMap("description_ja_JP", "諸行無常"),
			"description", document, searchTerm);
	}

	@Test
	public void testJapaneseSearchWithSimilarTexts() throws Exception {
		ServiceContext serviceContext = _getServiceContext();

		String description1 = "渋谷";
		String title1 = "東京都";

		_addFolder(serviceContext, title1, description1);

		String description2 = "三年坂";
		String title2 = "京都";

		_addFolder(serviceContext, title2, description2);

		String searchTerm = "東京";

		Document document = _search(searchTerm, LocaleUtil.JAPAN);

		FieldValuesAssert.assertFieldValues(
			Collections.singletonMap("title_ja_JP", "東京都"), "title", document,
			searchTerm);
	}

	@Test
	public void testJapaneseTitle() throws Exception {
		ServiceContext serviceContext = _getServiceContext();

		String description = "諸行無常";
		String title = "平家物語";

		_addFolder(serviceContext, title, description);

		String searchTerm = "平家";

		Document document = _search(searchTerm, LocaleUtil.JAPAN);

		FieldValuesAssert.assertFieldValues(
			Collections.singletonMap("title_ja_JP", "平家物語"), "title", document,
			searchTerm);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private void _addFolder(
			ServiceContext serviceContext, String title, String description)
		throws Exception {

		JournalTestUtil.addFolder(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, title, description,
			serviceContext);
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

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
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
			Hits hits = _indexer.search(_getSearchContext(searchTerm, locale));

			return _getSingleDocument(searchTerm, hits);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<JournalFolder> _indexer;

}