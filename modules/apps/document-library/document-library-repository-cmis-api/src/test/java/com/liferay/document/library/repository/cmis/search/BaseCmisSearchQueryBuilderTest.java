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

package com.liferay.document.library.repository.cmis.search;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.repository.search.internal.LuceneRepositorySearchQueryTermBuilder;
import com.liferay.document.library.repository.search.internal.RepositorySearchQueryBuilderImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilder;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryTermBuilder;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;

import java.text.SimpleDateFormat;

import java.util.Collections;

import org.apache.chemistry.opencmis.commons.enums.CapabilityQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Mika Koivisto
 * @author André de Oliveira
 */
public class BaseCmisSearchQueryBuilderTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpPropsUtil();

		setUpDateFormatFactoryUtil();

		_cmisSearchQueryBuilder = new BaseCmisSearchQueryBuilder(
			createRepositoryEntryLocalService(),
			Mockito.mock(UserLocalService.class));
	}

	@Test
	public void testBooleanQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("+test* -test.doc");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"((cmis:name LIKE 'test%' AND NOT(cmis:name = 'test.doc')) OR " +
				"(cmis:createdBy LIKE 'test%' AND NOT(cmis:createdBy = " +
					"'test.doc')))",
			cmisQuery);
	}

	@Test
	public void testContainsCombinedSupportedQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.BOTHCOMBINED.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"((cmis:name = 'test' OR cmis:createdBy = 'test') OR " +
				"CONTAINS('test'))",
			cmisQuery);
	}

	@Test
	public void testContainsCombinedSupportedWildcardQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test*.jpg");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.BOTHCOMBINED.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"((cmis:name LIKE 'test%.jpg' OR cmis:createdBy LIKE " +
				"'test%.jpg') OR CONTAINS('(test AND .jpg)'))",
			cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals("CONTAINS('test')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryMultipleKeywords()
		throws Exception {

		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test multiple");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals("CONTAINS('(test OR multiple)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryWithConjunction()
		throws Exception {

		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("+test +multiple");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals("CONTAINS('(test multiple)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryWithNegation() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test -multiple");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals("CONTAINS('(-multiple OR test)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryWithNegationPhrase()
		throws Exception {

		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test -\"multiple words\"");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"CONTAINS('(-\\'multiple words\\' OR test)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedWithApostrophe() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test's");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals("CONTAINS('test\\'s')", cmisQuery);
	}

	@Test
	public void testExactFilenameQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test.jpg");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"(cmis:name = 'test.jpg' OR cmis:createdBy = 'test.jpg')",
			cmisQuery);
	}

	@Test
	public void testFolderQuery() throws Exception {
		String folderQuery = buildFolderQuery(false);

		assertQueryEquals(
			StringBundler.concat(
				"((IN_FOLDER('", _MAPPED_ID, "') AND (cmis:name = 'test' OR ",
				"cmis:createdBy = 'test')) OR CONTAINS('test'))"),
			folderQuery);
	}

	@Test
	public void testFuzzyQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test~");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"(cmis:name LIKE 'test%' OR cmis:createdBy LIKE 'test%')",
			cmisQuery);
	}

	@Test
	public void testPhraseQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("\"My test document.jpg\"");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"(cmis:name = 'My test document.jpg' OR cmis:createdBy = 'My " +
				"test document.jpg')",
			cmisQuery);
	}

	@Test
	public void testPrefixQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("Test*");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"(cmis:name LIKE 'Test%' OR cmis:createdBy LIKE 'Test%')",
			cmisQuery);
	}

	@Test
	public void testProximityQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("\"test document\"~10");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"(cmis:name = 'test document' OR cmis:createdBy = 'test document')",
			cmisQuery);
	}

	@Test
	public void testRangeQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords(
			"createDate:[20091011000000 TO 20091110235959]");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"cmis:creationDate >= 2009-10-11T00:00:00.000Z AND " +
				"cmis:creationDate <= 2009-11-10T23:59:59.000Z",
			cmisQuery);
	}

	@Test
	public void testSubfolderQuery() throws Exception {
		String folderQuery = buildFolderQuery(true);

		assertQueryEquals(
			StringBundler.concat(
				"((IN_TREE('", _MAPPED_ID, "') AND (cmis:name = 'test' OR ",
				"cmis:createdBy = 'test')) OR CONTAINS('test'))"),
			folderQuery);
	}

	@Test
	public void testWildcardFieldQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("+title:test*.jpg +userName:bar*");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"(cmis:name LIKE 'test%.jpg' AND cmis:createdBy LIKE 'bar%')",
			cmisQuery);
	}

	@Test
	public void testWildcardQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test*.jpg");

		String cmisQuery = buildQuery(searchContext);

		assertQueryEquals(
			"(cmis:name LIKE 'test%.jpg' OR cmis:createdBy LIKE 'test%.jpg')",
			cmisQuery);
	}

	protected void assertQueryEquals(String where, String query) {
		Assert.assertEquals(_QUERY_PREFIX + where + _QUERY_POSTFIX, query);
	}

	protected String buildFolderQuery(boolean searchSubfolders)
		throws Exception {

		SearchContext searchContext = getSearchContext();

		searchContext.setFolderIds(new long[] {_DL_FOLDER_ID});
		searchContext.setKeywords("test");

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.BOTHCOMBINED.value());
		queryConfig.setSearchSubfolders(searchSubfolders);

		return buildQuery(searchContext);
	}

	protected String buildQuery(SearchContext searchContext) throws Exception {
		return _cmisSearchQueryBuilder.buildQuery(
			searchContext, getFullQuery(searchContext));
	}

	protected DateFormatFactory createDateFormatFactory(String pattern) {
		DateFormatFactory dateFormatFactory = Mockito.mock(
			DateFormatFactory.class);

		setUpPattern(dateFormatFactory, pattern);
		setUpPattern(dateFormatFactory, "yyyy-MM-dd'T'HH:mm:ss.000'Z'");

		return dateFormatFactory;
	}

	protected RepositoryEntry createRepositoryEntry() {
		RepositoryEntry repositoryEntry = Mockito.mock(RepositoryEntry.class);

		Mockito.doReturn(
			_MAPPED_ID
		).when(
			repositoryEntry
		).getMappedId();

		return repositoryEntry;
	}

	protected RepositoryEntryLocalService createRepositoryEntryLocalService() {
		RepositoryEntryLocalService repositoryEntryLocalService = Mockito.mock(
			RepositoryEntryLocalService.class);

		Mockito.doReturn(
			createRepositoryEntry()
		).when(
			repositoryEntryLocalService
		).fetchRepositoryEntry(
			Mockito.anyLong()
		);

		return repositoryEntryLocalService;
	}

	protected RepositorySearchQueryBuilder
		createRepositorySearchQueryBuilder() {

		return new RepositorySearchQueryBuilderImpl() {
			{
				setDLAppService(Mockito.mock(DLAppService.class));

				setRepositorySearchQueryTermBuilder(
					createRepositorySearchQueryTermBuilder());
			}
		};
	}

	protected RepositorySearchQueryTermBuilder
		createRepositorySearchQueryTermBuilder() {

		return new LuceneRepositorySearchQueryTermBuilder() {
			{
				activate(null);
			}
		};
	}

	protected BooleanQuery getFullQuery(SearchContext searchContext)
		throws Exception {

		RepositorySearchQueryBuilder repositorySearchQueryBuilder =
			createRepositorySearchQueryBuilder();

		return repositorySearchQueryBuilder.getFullQuery(searchContext);
	}

	protected SearchContext getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(SearchEngineHelper.GENERIC_ENGINE_ID);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setScoreEnabled(true);

		return searchContext;
	}

	protected void setUpDateFormatFactoryUtil() {
		String pattern = _INDEX_DATE_FORMAT_PATTERN;

		PropsTestUtil.setProps(PropsKeys.INDEX_DATE_FORMAT_PATTERN, pattern);

		DateFormatFactoryUtil dateFormatFactoryUtil =
			new DateFormatFactoryUtil();

		dateFormatFactoryUtil.setDateFormatFactory(
			createDateFormatFactory(pattern));
	}

	protected void setUpPattern(
		DateFormatFactory dateFormatFactory, String pattern) {

		Mockito.doReturn(
			new SimpleDateFormat(pattern)
		).when(
			dateFormatFactory
		).getSimpleDateFormat(
			pattern
		);
	}

	protected void setUpPropsUtil() {
		PropsTestUtil.setProps(Collections.emptyMap());
	}

	private static final long _DL_FOLDER_ID = RandomTestUtil.randomLong();

	private static final String _INDEX_DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";

	private static final String _MAPPED_ID = "1000";

	private static final String _QUERY_POSTFIX = " ORDER BY HITS DESC";

	private static final String _QUERY_PREFIX =
		"SELECT cmis:objectId, SCORE() AS HITS FROM cmis:document WHERE ";

	private CMISSearchQueryBuilder _cmisSearchQueryBuilder;

}