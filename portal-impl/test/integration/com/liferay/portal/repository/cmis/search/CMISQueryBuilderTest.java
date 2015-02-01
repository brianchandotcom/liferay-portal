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

package com.liferay.portal.repository.cmis.search;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.repository.cmis.search.CMISSearchQueryBuilderUtil;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilderUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalService;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.lang.reflect.Field;

import org.apache.chemistry.opencmis.commons.enums.CapabilityQuery;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mika Koivisto
 */
public class CMISQueryBuilderTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() {
		resetServices();

		MockitoAnnotations.initMocks(this);

		_beanLocator = PortalBeanLocatorUtil.getBeanLocator();

		_mockBeanLocator = Mockito.spy(_beanLocator);

		PortalBeanLocatorUtil.setBeanLocator(_mockBeanLocator);
	}

	@After
	public void tearDown() {
		resetServices();

		PortalBeanLocatorUtil.setBeanLocator(_beanLocator);
	}

	@Test
	public void testBooleanQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("+test* -test.doc");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
			searchContext, searchQuery);

		assertQueryEquals("CONTAINS('test\\'s')", cmisQuery);
	}

	@Test
	public void testExactFilenameQuery() throws Exception {
		SearchContext searchContext = getSearchContext();

		searchContext.setKeywords("test.jpg");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		String cmisQuery = CMISSearchQueryBuilderUtil.buildQuery(
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

		when(
			_mockBeanLocator.locate(
				DLAppService.class.getName())
		).thenReturn(
			_dlAppService
		);

		when(
			_mockBeanLocator.locate(
				RepositoryEntryLocalService.class.getName())
		).thenReturn(
			_repositoryEntryLocalService
		);

		when(
			_repositoryEntry.getMappedId()
		).thenReturn(
			"1000"
		);

		when(
			_repositoryEntryLocalService.fetchRepositoryEntry(
				Matchers.eq(1000l))
		).thenReturn(
			_repositoryEntry
		);

		SearchContext searchContext = getSearchContext();

		searchContext.setFolderIds(new long[] {1000});
		searchContext.setKeywords("test");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.BOTHCOMBINED.value());
		queryConfig.setSearchSubfolders(searchSubfolders);

		return CMISSearchQueryBuilderUtil.buildQuery(
			searchContext, searchQuery);
	}

	protected SearchContext getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(SearchEngineUtil.GENERIC_ENGINE_ID);

		return searchContext;
	}

	protected void resetService(Class<?> serviceUtilClass) {
		try {
			Field field = serviceUtilClass.getDeclaredField("_service");

			field.setAccessible(true);

			field.set(serviceUtilClass, null);
		}
		catch (Exception e) {
		}
	}

	protected void resetServices() {
		resetService(DLAppServiceUtil.class);
		resetService(RepositoryEntryLocalServiceUtil.class);
	}

	private static final String _QUERY_POSTFIX = " ORDER BY HITS DESC";

	private static final String _QUERY_PREFIX =
		"SELECT cmis:objectId, SCORE() AS HITS FROM cmis:document WHERE ";

	private BeanLocator _beanLocator;

	@Mock
	private DLAppService _dlAppService;

	private BeanLocator _mockBeanLocator;

	@Mock
	private RepositoryEntry _repositoryEntry;

	@Mock
	private RepositoryEntryLocalService _repositoryEntryLocalService;

}