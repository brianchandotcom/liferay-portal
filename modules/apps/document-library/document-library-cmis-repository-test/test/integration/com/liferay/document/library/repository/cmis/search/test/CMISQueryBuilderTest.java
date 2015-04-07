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

package com.liferay.document.library.repository.cmis.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.repository.cmis.search.BaseCmisSearchQueryBuilder;
import com.liferay.document.library.repository.cmis.search.CMISSearchQueryBuilder;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilderUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;

import org.apache.chemistry.opencmis.commons.enums.CapabilityQuery;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mika Koivisto
 */
@RunWith(Arquillian.class)
public class CMISQueryBuilderTest {

	@Test
	public void testBooleanQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("+test* -test.doc");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

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

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.BOTHCOMBINED.value());

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

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

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"((cmis:name LIKE 'test%.jpg' OR cmis:createdBy LIKE " +
				"'test%.jpg') OR CONTAINS('(test AND .jpg)'))",
			cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals("CONTAINS('test')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryMultipleKeywords()
		throws Exception {

		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test multiple");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals("CONTAINS('(test OR multiple)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryWithConjunction()
		throws Exception {

		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("+test +multiple");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals("CONTAINS('(test multiple)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryWithNegation() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test -multiple");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals("CONTAINS('(-multiple OR test)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedQueryWithNegationPhrase()
		throws Exception {

		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test -\"multiple words\"");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"CONTAINS('(-\\'multiple words\\' OR test)')", cmisQuery);
	}

	@Test
	public void testContainsOnlySupportedWithApostrophe() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test's");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.FULLTEXTONLY.value());

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals("CONTAINS('test\\'s')", cmisQuery);
	}

	@Test
	public void testExactFilenameQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test.jpg");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"(cmis:name = 'test.jpg' OR cmis:createdBy = 'test.jpg')",
			cmisQuery);
	}

	@Test
	public void testFolderQuery() throws Exception {
		String folderQuery = buildFolderQuery(false);

		assertQueryEquals(
			"((IN_FOLDER('1000') AND (cmis:name = 'test' OR cmis:createdBy " +
				"= 'test')) OR CONTAINS('test'))",
			folderQuery);
	}

	@Test
	public void testFuzzyQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test~");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"(cmis:name LIKE 'test%' OR cmis:createdBy LIKE 'test%')",
			cmisQuery);
	}

	@Test
	public void testPhraseQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("\"My test document.jpg\"");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"(cmis:name = 'My test document.jpg' OR cmis:createdBy = 'My " +
				"test document.jpg')",
			cmisQuery);
	}

	@Test
	public void testPrefixQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("Test*");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"(cmis:name LIKE 'Test%' OR cmis:createdBy LIKE 'Test%')",
			cmisQuery);
	}

	@Test
	public void testProximityQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("\"test document\"~10");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"(cmis:name = 'test document' OR cmis:createdBy = 'test " +
				"document')",
			cmisQuery);
	}

	@Test
	public void testRangeQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords(
			"createDate:[20091011000000 TO 20091110235959]");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"cmis:creationDate >= 2009-10-11T00:00:00.000Z AND " +
				"cmis:creationDate <= 2009-11-10T23:59:59.000Z",
			cmisQuery);
	}

	@Test
	public void testSubfolderQuery() throws Exception {
		String folderQuery = buildFolderQuery(true);

		assertQueryEquals(
			"((IN_TREE('1000') AND (cmis:name = 'test' OR cmis:createdBy = " +
				"'test')) OR CONTAINS('test'))",
			folderQuery);
	}

	@Test
	public void testWildcardFieldQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("+title:test*.jpg +userName:bar*");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals(
			"(cmis:name LIKE 'test%.jpg' AND cmis:createdBy LIKE 'bar%')",
			cmisQuery);
	}

	@Test
	public void testWildcardQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test*.jpg");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = _cmisSearchQueryBuilder.buildQuery(
			searchContext, searchQuery);

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

		searchContext.setFolderIds(new long[] {1000});
		searchContext.setKeywords("test");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.BOTHCOMBINED.value());
		queryConfig.setSearchSubfolders(searchSubfolders);

		return _cmisSearchQueryBuilder.buildQuery(searchContext, searchQuery);
	}

	protected SearchContext getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(SearchEngineUtil.GENERIC_ENGINE_ID);

		return searchContext;
	}

	private static final String _QUERY_POSTFIX = " ORDER BY HITS DESC";

	private static final String _QUERY_PREFIX =
		"SELECT cmis:objectId, SCORE() AS HITS FROM cmis:document WHERE ";

	private final CMISSearchQueryBuilder _cmisSearchQueryBuilder =
		new BaseCmisSearchQueryBuilder();

}