/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.seo.studio.rest.client.dto.v1_0.CrawlHit;
import com.liferay.seo.studio.rest.client.http.HttpInvoker;
import com.liferay.seo.studio.rest.client.pagination.Page;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@FeatureFlag("LPD-44511")
@RunWith(Arquillian.class)
public class CrawlHitResourceTest extends BaseCrawlHitResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.seo.studio.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		for (String indexName : _indexNames) {
			_searchEngineAdapter.execute(new DeleteIndexRequest(indexName));
		}

		super.tearDown();
	}

	@Override
	@Test
	public void testGetSEOStudioDomainCrawlHitsPage() throws Exception {
		super.testGetSEOStudioDomainCrawlHitsPage();

		_testGetSEOStudioDomainCrawlHitsPageNotFound();
		_testGetSEOStudioDomainCrawlHitsPageWithLinks();
		_testGetSEOStudioDomainCrawlHitsPageWithoutUrl();
		_testGetSEOStudioDomainCrawlHitsPageWithReservedAndExcludedParameters();
		_testGetSEOStudioDomainCrawlHitsPageWithSearchAfter();
		_testGetSEOStudioDomainCrawlHitsPageWithSkippedHits();
		_testGetSEOStudioDomainCrawlHitsPageWithSpaceInUrl();
		_testGetSEOStudioDomainCrawlHitsPageWithTrailingSlashInUrl();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title", "url"};
	}

	@Override
	protected CrawlHit testGetSEOStudioDomainCrawlHitsPage_addCrawlHit(
			Long seoStudioDomainId, CrawlHit crawlHit)
		throws Exception {

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId), crawlHit.getTitle(),
			crawlHit.getUrl());

		return crawlHit;
	}

	@Override
	protected Long
			testGetSEOStudioDomainCrawlHitsPage_getIrrelevantSeoStudioDomainId()
		throws Exception {

		return _addDomain();
	}

	@Override
	protected Long testGetSEOStudioDomainCrawlHitsPage_getSeoStudioDomainId()
		throws Exception {

		return _addDomain();
	}

	private Long _addDomain() throws Exception {
		long companyId = testCompany.getCompanyId();

		ObjectDefinition domainObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_SEO_STUDIO_DOMAIN", companyId);
		ObjectDefinition instanceObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_SEO_STUDIO_INSTANCE", companyId);

		User user = UserTestUtil.getAdminUser(companyId);

		long userId = user.getUserId();

		if (_accountEntry == null) {
			_accountEntry = AccountEntryLocalServiceUtil.addAccountEntry(
				null, userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
				RandomTestUtil.randomString(), null, new String[0], null, null,
				null, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext());
		}

		ObjectEntry instanceObjectEntry =
			ObjectEntryLocalServiceUtil.addObjectEntry(
				0L, userId, instanceObjectDefinition.getObjectDefinitionId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				null,
				HashMapBuilder.<String, Serializable>put(
					"hostname", RandomTestUtil.randomString()
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"r_accountToSEOStudioInstances_accountEntryId",
					_accountEntry.getAccountEntryId()
				).put(
					"state", "active"
				).build(),
				ServiceContextTestUtil.getServiceContext());

		_createdObjectEntries.add(instanceObjectEntry);

		ObjectEntry domainObjectEntry =
			ObjectEntryLocalServiceUtil.addObjectEntry(
				0L, userId, domainObjectDefinition.getObjectDefinitionId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				null,
				HashMapBuilder.<String, Serializable>put(
					"defaultScanScope", "entireDomain"
				).put(
					"hostname",
					StringUtil.toLowerCase(RandomTestUtil.randomString())
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"r_accountToSEOStudioDomains_accountEntryId",
					_accountEntry.getAccountEntryId()
				).put(
					"r_seoStudioInstanceToSEOStudioDomains_seoStudioInstanceId",
					instanceObjectEntry.getObjectEntryId()
				).build(),
				ServiceContextTestUtil.getServiceContext());

		_createdObjectEntries.add(domainObjectEntry);

		String indexName = "seo_studio_" + domainObjectEntry.getObjectEntryId();

		_searchEngineAdapter.execute(new CreateIndexRequest(indexName));

		_indexNames.add(indexName);
		_indexNamesByDomainId.put(
			domainObjectEntry.getObjectEntryId(), indexName);

		return domainObjectEntry.getObjectEntryId();
	}

	private CrawlHit _getOnlyCrawlHit(Long seoStudioDomainId) throws Exception {
		Page<CrawlHit> page = crawlHitResource.getSEOStudioDomainCrawlHitsPage(
			seoStudioDomainId, null, null);

		List<CrawlHit> crawlHits = (List<CrawlHit>)page.getItems();

		Assert.assertEquals(crawlHits.toString(), 1, crawlHits.size());

		return crawlHits.get(0);
	}

	private void _indexCrawlDocument(
			String indexName, String title, String url, String... links)
		throws Exception {

		DocumentBuilder documentBuilder = DocumentBuilderFactory.builder();

		if (links.length > 0) {
			documentBuilder.setStrings("links", links);
		}

		documentBuilder.setString("title", title);

		if (url != null) {
			documentBuilder.setString("url", url);
		}

		Document document = documentBuilder.build();

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		indexDocumentRequest.setRefresh(true);
		indexDocumentRequest.setType("_doc");

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	private void _testGetSEOStudioDomainCrawlHitsPageNotFound()
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			crawlHitResource.getSEOStudioDomainCrawlHitsPageHttpResponse(
				RandomTestUtil.randomLong(), null, null);

		assertHttpResponseStatusCode(404, httpResponse);
	}

	private void _testGetSEOStudioDomainCrawlHitsPageWithLinks()
		throws Exception {

		Long seoStudioDomainId = _addDomain();

		String link1 = RandomTestUtil.randomString();
		String link2 = RandomTestUtil.randomString();

		String title = RandomTestUtil.randomString();

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId), title,
			RandomTestUtil.randomString(), link1, link2);

		CrawlHit crawlHit = _getOnlyCrawlHit(seoStudioDomainId);

		Assert.assertEquals(title, crawlHit.getTitle());
		Assert.assertArrayEquals(
			new String[] {link1, link2}, crawlHit.getLinks());
	}

	private void _testGetSEOStudioDomainCrawlHitsPageWithoutUrl()
		throws Exception {

		Long seoStudioDomainId = _addDomain();

		String indexName = _indexNamesByDomainId.get(seoStudioDomainId);

		String url = RandomTestUtil.randomString();

		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), null);
		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url);

		CrawlHit crawlHit = _getOnlyCrawlHit(seoStudioDomainId);

		Assert.assertEquals(url, crawlHit.getUrl());
	}

	private void _testGetSEOStudioDomainCrawlHitsPageWithReservedAndExcludedParameters()
		throws Exception {

		Long seoStudioDomainId = _addDomain();

		String url =
			"https://liferay.com/o/page?p_l_back_url=/search&delta=20" +
				"&highlight=x&filter_category_1=2&utm_cid=7014u&id=5";

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			RandomTestUtil.randomString(), url);

		CrawlHit crawlHit = _getOnlyCrawlHit(seoStudioDomainId);

		Assert.assertEquals(url, crawlHit.getUrl());
		Assert.assertEquals(
			"https://liferay.com/o/page?id=5", crawlHit.getCanonicalUrl());
	}

	private void _testGetSEOStudioDomainCrawlHitsPageWithSearchAfter()
		throws Exception {

		Long seoStudioDomainId = _addDomain();

		String indexName = _indexNamesByDomainId.get(seoStudioDomainId);

		String url1 = "https://liferay.com/1?utm_source=newsletter";
		String url2 = "https://liferay.com/2";
		String url3 = "https://liferay.com/3";

		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url1);
		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url2);
		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url3);

		Page<CrawlHit> page = crawlHitResource.getSEOStudioDomainCrawlHitsPage(
			seoStudioDomainId, 5, null);

		List<CrawlHit> crawlHits = (List<CrawlHit>)page.getItems();

		Assert.assertEquals(crawlHits.toString(), 3, crawlHits.size());

		CrawlHit crawlHit1 = crawlHits.get(0);

		Assert.assertEquals(url1, crawlHit1.getUrl());
		Assert.assertEquals(
			"https://liferay.com/1", crawlHit1.getCanonicalUrl());

		page = crawlHitResource.getSEOStudioDomainCrawlHitsPage(
			seoStudioDomainId, 5, crawlHit1.getUrl());

		crawlHits = (List<CrawlHit>)page.getItems();

		Assert.assertEquals(crawlHits.toString(), 2, crawlHits.size());

		CrawlHit crawlHit2 = crawlHits.get(0);
		CrawlHit crawlHit3 = crawlHits.get(1);

		Assert.assertEquals(url2, crawlHit2.getUrl());
		Assert.assertEquals(url3, crawlHit3.getUrl());
	}

	private void _testGetSEOStudioDomainCrawlHitsPageWithSkippedHits()
		throws Exception {

		Long seoStudioDomainId = _addDomain();

		String indexName = _indexNamesByDomainId.get(seoStudioDomainId);

		_indexCrawlDocument(
			indexName, RandomTestUtil.randomString(), StringPool.BLANK);
		_indexCrawlDocument(
			indexName, RandomTestUtil.randomString(), StringPool.BLANK);
		_indexCrawlDocument(
			indexName, RandomTestUtil.randomString(), StringPool.BLANK);

		String url = RandomTestUtil.randomString();

		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url);

		Page<CrawlHit> page = crawlHitResource.getSEOStudioDomainCrawlHitsPage(
			seoStudioDomainId, 2, null);

		List<CrawlHit> crawlHits = (List<CrawlHit>)page.getItems();

		Assert.assertEquals(crawlHits.toString(), 1, crawlHits.size());

		CrawlHit crawlHit = crawlHits.get(0);

		Assert.assertEquals(url, crawlHit.getUrl());
	}

	private void _testGetSEOStudioDomainCrawlHitsPageWithSpaceInUrl()
		throws Exception {

		Long seoStudioDomainId = _addDomain();

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			RandomTestUtil.randomString(), "https://liferay.com/de/page foo");

		CrawlHit crawlHit = _getOnlyCrawlHit(seoStudioDomainId);

		Assert.assertEquals(
			"https://liferay.com/de/page%20foo", crawlHit.getCanonicalUrl());
	}

	private void _testGetSEOStudioDomainCrawlHitsPageWithTrailingSlashInUrl()
		throws Exception {

		Long seoStudioDomainId = _addDomain();

		String url = "https://liferay.com/commerce/";

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			RandomTestUtil.randomString(), url);

		CrawlHit crawlHit = _getOnlyCrawlHit(seoStudioDomainId);

		Assert.assertEquals(url, crawlHit.getUrl());
		Assert.assertEquals(
			"https://liferay.com/commerce", crawlHit.getCanonicalUrl());
	}

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@DeleteAfterTestRun
	private final List<ObjectEntry> _createdObjectEntries = new ArrayList<>();

	private final List<String> _indexNames = new ArrayList<>();
	private final Map<Long, String> _indexNamesByDomainId = new HashMap<>();

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private SearchEngineAdapter _searchEngineAdapter;

}