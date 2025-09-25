/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.suggest.SuggestionConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.elasticsearch7.internal.indexing.LiferayElasticsearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class ElasticsearchSpellCheckIndexWriterTest
	extends BaseIndexingTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ElasticsearchIndexWriter elasticsearchIndexWriter =
			(ElasticsearchIndexWriter)getIndexWriter();

		_elasticsearchSpellCheckIndexWriter =
			(ElasticsearchSpellCheckIndexWriter)
				elasticsearchIndexWriter.getSpellCheckIndexWriter();

		_searchContext = _getSearchContext();
	}

	@After
	@Override
	public void tearDown() throws SearchException {
		_elasticsearchSpellCheckIndexWriter.deleteDocuments(
			_searchContext, SuggestionConstants.TYPE_QUERY_SUGGESTION);
		_elasticsearchSpellCheckIndexWriter.deleteDocuments(
			_searchContext, SuggestionConstants.TYPE_SPELL_CHECKER);
	}

	@Test
	public void testAddDocument() {
		String value1 = RandomTestUtil.randomString();

		addDocument(_createQuerySuggestionDocument(LocaleUtil.US, value1));

		_assertOnlyOne(_getQuerySuggestionField(LocaleUtil.US), value1);

		String value2 = RandomTestUtil.randomString();

		addDocument(_createSpellCheckDocument(LocaleUtil.US, value2));

		_assertOnlyOne(_getSpellCheckWordField(LocaleUtil.US), value2);

		String value3 = RandomTestUtil.randomString();

		addDocument(_createSpellCheckDocument(LocaleUtil.JAPAN, value3));

		_assertOnlyOne(_getSpellCheckWordField(LocaleUtil.JAPAN), value3);
	}

	@Test
	public void testAddDocuments() throws SearchException {
		addDocument(
			_createSpellCheckDocument(
				LocaleUtil.US, RandomTestUtil.randomString()));
		addDocument(
			_createSpellCheckDocument(
				LocaleUtil.JAPAN, RandomTestUtil.randomString()));

		_assertDocumentCount(2, SuggestionConstants.TYPE_SPELL_CHECKER);
	}

	@Test
	public void testClearQuerySuggestionDictionaryIndexes()
		throws SearchException {

		addDocument(
			_createQuerySuggestionDocument(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_assertDocumentCount(1, SuggestionConstants.TYPE_QUERY_SUGGESTION);

		_elasticsearchSpellCheckIndexWriter.
			clearQuerySuggestionDictionaryIndexes(_searchContext);

		_assertDocumentCount(0, SuggestionConstants.TYPE_QUERY_SUGGESTION);
	}

	@Test
	public void testClearSpellCheckerDictionaryIndexes()
		throws SearchException {

		addDocument(
			_createSpellCheckDocument(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_assertDocumentCount(1, SuggestionConstants.TYPE_SPELL_CHECKER);

		_elasticsearchSpellCheckIndexWriter.clearSpellCheckerDictionaryIndexes(
			_searchContext);

		_assertDocumentCount(0, SuggestionConstants.TYPE_SPELL_CHECKER);
	}

	@Test
	public void testDeleteDocuments() throws Exception {
		addDocument(
			_createQuerySuggestionDocument(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_assertDocumentCount(1, SuggestionConstants.TYPE_QUERY_SUGGESTION);

		_elasticsearchSpellCheckIndexWriter.deleteDocuments(
			_searchContext, SuggestionConstants.TYPE_QUERY_SUGGESTION);

		_assertDocumentCount(0, SuggestionConstants.TYPE_QUERY_SUGGESTION);

		addDocument(
			_createSpellCheckDocument(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_assertDocumentCount(1, SuggestionConstants.TYPE_SPELL_CHECKER);

		_elasticsearchSpellCheckIndexWriter.deleteDocuments(
			_searchContext, SuggestionConstants.TYPE_SPELL_CHECKER);

		_assertDocumentCount(0, SuggestionConstants.TYPE_SPELL_CHECKER);
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayElasticsearchIndexingFixtureFactory.getInstance();
	}

	private void _assertDocumentCount(int count, String type) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setQuery(
					new TermQueryImpl(Field.TYPE, type));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> Assert.assertEquals(count, hits.getLength()));
			});
	}

	private void _assertOnlyOne(String field, String value) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.setQuery(new MatchQuery(field, value));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> Assert.assertEquals(1, hits.getLength()));
			});
	}

	private Document _createDocument(Locale locale) {
		DocumentImpl documentImpl = new DocumentImpl();

		documentImpl.addKeyword(Field.COMPANY_ID, getCompanyId());
		documentImpl.addKeyword(Field.GROUP_ID, getGroupId());
		documentImpl.addKeyword(
			Field.LANGUAGE_ID, LocaleUtil.toLanguageId(locale));

		return documentImpl;
	}

	private Document _createQuerySuggestionDocument(
		Locale locale, String value) {

		Document document = _createDocument(locale);

		document.addKeyword(
			Field.TYPE, SuggestionConstants.TYPE_QUERY_SUGGESTION);
		document.addText(_getQuerySuggestionField(locale), value);

		return document;
	}

	private Document _createSpellCheckDocument(Locale locale, String value) {
		Document document = _createDocument(locale);

		document.addKeyword(Field.TYPE, SuggestionConstants.TYPE_SPELL_CHECKER);
		document.addText(_getSpellCheckWordField(locale), value);

		return document;
	}

	private String _getQuerySuggestionField(Locale locale) {
		return StringBundler.concat(
			Field.KEYWORD_SEARCH, StringPool.UNDERLINE,
			LocaleUtil.toLanguageId(locale));
	}

	private SearchContext _getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCommitImmediately(true);
		searchContext.setCompanyId(getCompanyId());

		return searchContext;
	}

	private String _getSpellCheckWordField(Locale locale) {
		return StringBundler.concat(
			Field.SPELL_CHECK_WORD, StringPool.UNDERLINE,
			LocaleUtil.toLanguageId(locale));
	}

	private ElasticsearchSpellCheckIndexWriter
		_elasticsearchSpellCheckIndexWriter;
	private SearchContext _searchContext;

}