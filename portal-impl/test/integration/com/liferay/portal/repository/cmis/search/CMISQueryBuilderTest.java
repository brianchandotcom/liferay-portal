/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.repository.cmis.search;

import static org.mockito.Matchers.eq;

import java.lang.reflect.Field;

import org.apache.chemistry.opencmis.commons.enums.CapabilityQuery;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.cmis.search.CMISSearchQueryBuilderUtil;
import com.liferay.portal.kernel.repository.search.RepositorySearchQueryBuilderUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalService;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

/**
 * @author Mika Koivisto
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CMISQueryBuilderTest extends PowerMockito {

	@Before
	public void setUp() {
		resetServices();
		MockitoAnnotations.initMocks(this);
		PortalBeanLocatorUtil.setBeanLocator(_beanLocatorProxy);
	}

	@After
	public void tearDown() {
		resetServices();
		PortalBeanLocatorUtil.setBeanLocator(_DEFAULT_BEAN_LOCATOR);
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
			"(cmis:name LIKE 'test%' AND NOT(cmis:name = 'test.doc')) OR " +
				"(cmis:createdBy LIKE 'test%' AND NOT(cmis:createdBy = " +
					"'test.doc'))",
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
			"(cmis:name = 'test') OR (cmis:createdBy = 'test') OR " +
				"(CONTAINS('test'))",
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

		assertQueryEquals("(CONTAINS('test'))", cmisQuery);
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
			"(cmis:name = 'test.jpg') OR (cmis:createdBy = 'test.jpg')",
			cmisQuery);
	}

	@Test
	public void testFolderQuery() throws Exception {
		assertQueryEquals(
			"(IN_FOLDER('1000')) AND ((cmis:name = 'test') OR " +
				"(cmis:createdBy = 'test') OR (CONTAINS('test')))",
			buildTestFolderQuery(SearchSubfolders.NO));
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
			"(cmis:name LIKE 'test%') OR (cmis:createdBy LIKE 'test%')",
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
			"(cmis:name = 'My test document.jpg') OR " +
			"(cmis:createdBy = 'My test document.jpg')", cmisQuery);
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
			"(cmis:name LIKE 'Test%') OR (cmis:createdBy LIKE 'Test%')",
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
			"(cmis:name = 'test document') OR " +
			"(cmis:createdBy = 'test document')", cmisQuery);
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
			"(cmis:creationDate >= 2009-10-11T00:00:00.000Z AND " +
			"cmis:creationDate <= 2009-11-10T23:59:59.000Z)", cmisQuery);
	}

	@Test
	public void testSubfolderQuery() throws Exception {
		assertQueryEquals(
			"(IN_TREE('1000')) AND ((cmis:name = 'test') OR " +
				"(cmis:createdBy = 'test') OR (CONTAINS('test')))",
			buildTestFolderQuery(SearchSubfolders.YES));
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
			"(cmis:name LIKE 'test%.jpg') OR (cmis:createdBy LIKE 'test%.jpg')",
			cmisQuery);
	}

	protected void assertQueryEquals(String where, String query) {
		Assert.assertEquals(_QUERY_PREFIX + where + _QUERY_POSTFIX, query);
	}

	protected String buildTestFolderQuery(
		SearchSubfolders searchSubfolders)
		throws SystemException, SearchException {

		setExpectations();

		SearchContext searchContext = getSearchContext();

		searchContext.setFolderIds(new long[] {1000});
		searchContext.setKeywords("test");

		BooleanQuery searchQuery =
			RepositorySearchQueryBuilderUtil.getFullQuery(searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setAttribute(
			"capabilityQuery", CapabilityQuery.BOTHCOMBINED.value());
		queryConfig.setSearchSubfolders(
			searchSubfolders.equals(SearchSubfolders.YES));

		return CMISSearchQueryBuilderUtil.buildQuery(
			searchContext, searchQuery);
	}

	protected SearchContext getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(SearchEngineUtil.GENERIC_ENGINE_ID);

		return searchContext;
	}

	protected void resetServices() {
		resetServiceOf(DLAppServiceUtil.class);
		resetServiceOf(RepositoryEntryLocalServiceUtil.class);
	}

	private void resetServiceOf(Class<?> serviceUtilClass) {
		try {
			Field field = serviceUtilClass.getDeclaredField("_service");

			field.setAccessible(true);

			field.set(serviceUtilClass, null);
		}
		catch (Exception ignored) {
		}
	}

	protected void setExpectations() throws SystemException {
		when(
			_repositoryEntry.getMappedId()
		).thenReturn(
			"1000"
		);

		when(
			_repositoryEntryLocalService.fetchRepositoryEntry(eq(1000l))
		).thenReturn(
			_repositoryEntry
		);

		when(_beanLocatorProxy.locate(
			RepositoryEntryLocalService.class.getName())
		).thenReturn(
			_repositoryEntryLocalService
		);

		when(_beanLocatorProxy.locate(
			DLAppService.class.getName())
		).thenReturn(
			_dlAppService
		);
	}

	private static final String _QUERY_POSTFIX = ") ORDER BY HITS DESC";

	private static final String _QUERY_PREFIX =
		"SELECT cmis:objectId, SCORE() AS HITS FROM cmis:document WHERE (";

	private static final BeanLocator _DEFAULT_BEAN_LOCATOR =
		PortalBeanLocatorUtil.getBeanLocator();

	@Spy  private BeanLocator _beanLocatorProxy = _DEFAULT_BEAN_LOCATOR;
	@Mock private DLAppService _dlAppService;
	@Mock private RepositoryEntry _repositoryEntry;
	@Mock private RepositoryEntryLocalService _repositoryEntryLocalService;

	private static enum SearchSubfolders { YES, NO };
}