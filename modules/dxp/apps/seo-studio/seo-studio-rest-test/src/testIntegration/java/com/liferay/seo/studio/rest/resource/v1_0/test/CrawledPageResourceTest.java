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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.seo.studio.rest.client.dto.v1_0.CrawledPage;
import com.liferay.seo.studio.rest.client.http.HttpInvoker;
import com.liferay.seo.studio.rest.client.pagination.Page;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class CrawledPageResourceTest extends BaseCrawledPageResourceTestCase {

	@After
	public void tearDownDomainFixture() throws Exception {
		for (String indexName : _indexNames) {
			_searchEngineAdapter.execute(new DeleteIndexRequest(indexName));
		}
	}

	@Override
	@Test
	public void testGetSeoStudioDomainCrawlHitsPage() throws Exception {
		super.testGetSeoStudioDomainCrawlHitsPage();

		_testGetSeoStudioDomainCrawlHitsPageAppliesSearchAfter();
		_testGetSeoStudioDomainCrawlHitsPageEncodesSpacesInCanonicalURL();
		_testGetSeoStudioDomainCrawlHitsPageFailsWithMissingObjectEntry();
		_testGetSeoStudioDomainCrawlHitsPageMapsTitleAndLinks();
		_testGetSeoStudioDomainCrawlHitsPageNormalizesLocaleRegionInCanonicalURL();
		_testGetSeoStudioDomainCrawlHitsPageRemovesReservedAndExcludedParameters();
		_testGetSeoStudioDomainCrawlHitsPageRemovesTrailingSlashFromCanonicalURL();
		_testGetSeoStudioDomainCrawlHitsPageSkipsHitsWithoutURL();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"title", "url"};
	}

	@Override
	protected CrawledPage testGetSeoStudioDomainCrawlHitsPage_addCrawledPage(
			Long seoStudioDomainId, CrawledPage crawledPage)
		throws Exception {

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			crawledPage.getTitle(), crawledPage.getUrl());

		return crawledPage;
	}

	@Override
	protected Long
			testGetSeoStudioDomainCrawlHitsPage_getIrrelevantSeoStudioDomainId()
		throws Exception {

		return _createDomain();
	}

	@Override
	protected Long testGetSeoStudioDomainCrawlHitsPage_getSeoStudioDomainId()
		throws Exception {

		return _createDomain();
	}

	private Long _createDomain() throws Exception {
		long companyId = testCompany.getCompanyId();

		ObjectDefinition instanceObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_SEO_STUDIO_INSTANCE", companyId);
		ObjectDefinition domainObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_SEO_STUDIO_DOMAIN", companyId);

		Assume.assumeTrue(
			"SEO Studio site initializer has not run in this test company; " +
				"skipping integration test",
			(instanceObjectDefinition != null) &&
			(domainObjectDefinition != null));

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

	private CrawledPage _getOnlyCrawledPage(Long seoStudioDomainId)
		throws Exception {

		Page<CrawledPage> page =
			crawledPageResource.getSeoStudioDomainCrawlHitsPage(
				seoStudioDomainId, null, null);

		List<CrawledPage> crawledPages = (List<CrawledPage>)page.getItems();

		Assert.assertEquals(crawledPages.toString(), 1, crawledPages.size());

		return crawledPages.get(0);
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

	private void _testGetSeoStudioDomainCrawlHitsPageAppliesSearchAfter()
		throws Exception {

		Long seoStudioDomainId = _createDomain();

		String indexName = _indexNamesByDomainId.get(seoStudioDomainId);

		String url1 = "https://liferay.com/1";
		String url2 = "https://liferay.com/2";
		String url3 = "https://liferay.com/3";

		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url1);
		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url2);
		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url3);

		Page<CrawledPage> page =
			crawledPageResource.getSeoStudioDomainCrawlHitsPage(
				seoStudioDomainId, 5, url1);

		List<CrawledPage> crawledPages = (List<CrawledPage>)page.getItems();

		Assert.assertEquals(crawledPages.toString(), 2, crawledPages.size());

		CrawledPage crawledPage1 = crawledPages.get(0);
		CrawledPage crawledPage2 = crawledPages.get(1);

		Assert.assertEquals(url2, crawledPage1.getUrl());
		Assert.assertEquals(url3, crawledPage2.getUrl());
	}

	private void _testGetSeoStudioDomainCrawlHitsPageEncodesSpacesInCanonicalURL()
		throws Exception {

		Long seoStudioDomainId = _createDomain();

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			RandomTestUtil.randomString(), "https://liferay.com/de/page foo");

		CrawledPage crawledPage = _getOnlyCrawledPage(seoStudioDomainId);

		Assert.assertEquals(
			"https://liferay.com/de/page%20foo", crawledPage.getCanonicalURL());
	}

	private void _testGetSeoStudioDomainCrawlHitsPageFailsWithMissingObjectEntry()
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			crawledPageResource.getSeoStudioDomainCrawlHitsPageHttpResponse(
				RandomTestUtil.randomLong(), null, null);

		assertHttpResponseStatusCode(404, httpResponse);
	}

	private void _testGetSeoStudioDomainCrawlHitsPageMapsTitleAndLinks()
		throws Exception {

		Long seoStudioDomainId = _createDomain();

		String linkAURL = "https://liferay.com/link-a";
		String linkBURL = "https://liferay.com/link-b";

		String title = RandomTestUtil.randomString();

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId), title,
			"https://liferay.com/" + RandomTestUtil.randomString(), linkAURL,
			linkBURL);

		CrawledPage crawledPage = _getOnlyCrawledPage(seoStudioDomainId);

		Assert.assertEquals(title, crawledPage.getTitle());
		Assert.assertArrayEquals(
			new String[] {linkAURL, linkBURL}, crawledPage.getLinks());
	}

	private void _testGetSeoStudioDomainCrawlHitsPageNormalizesLocaleRegionInCanonicalURL()
		throws Exception {

		Long seoStudioDomainId = _createDomain();

		String aboutURL = "https://liferay.com/en/about";
		String url = "https://liferay.com/en-US/page";

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			RandomTestUtil.randomString(), url, aboutURL,
			"https://liferay.com/en-AU/about");

		CrawledPage crawledPage = _getOnlyCrawledPage(seoStudioDomainId);

		Assert.assertEquals(url, crawledPage.getUrl());
		Assert.assertEquals(
			"https://liferay.com/en/page", crawledPage.getCanonicalURL());
		Assert.assertArrayEquals(
			new String[] {aboutURL, aboutURL}, crawledPage.getLinks());
	}

	private void _testGetSeoStudioDomainCrawlHitsPageRemovesReservedAndExcludedParameters()
		throws Exception {

		Long seoStudioDomainId = _createDomain();

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			RandomTestUtil.randomString(),
			"https://liferay.com/o/page?p_l_back_url=/search&delta=20" +
				"&highlight=x&filter_category_1=2&utm_cid=7014u&id=5");

		CrawledPage crawledPage = _getOnlyCrawledPage(seoStudioDomainId);

		Assert.assertEquals(
			"https://liferay.com/o/page?id=5", crawledPage.getUrl());
	}

	private void _testGetSeoStudioDomainCrawlHitsPageRemovesTrailingSlashFromCanonicalURL()
		throws Exception {

		Long seoStudioDomainId = _createDomain();

		String url = "https://liferay.com/commerce/";

		_indexCrawlDocument(
			_indexNamesByDomainId.get(seoStudioDomainId),
			RandomTestUtil.randomString(), url);

		CrawledPage crawledPage = _getOnlyCrawledPage(seoStudioDomainId);

		Assert.assertEquals(url, crawledPage.getUrl());
		Assert.assertEquals(
			"https://liferay.com/commerce", crawledPage.getCanonicalURL());
	}

	private void _testGetSeoStudioDomainCrawlHitsPageSkipsHitsWithoutURL()
		throws Exception {

		Long seoStudioDomainId = _createDomain();

		String indexName = _indexNamesByDomainId.get(seoStudioDomainId);

		String url = "https://liferay.com/o/with-url";

		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), null);
		_indexCrawlDocument(indexName, RandomTestUtil.randomString(), url);

		CrawledPage crawledPage = _getOnlyCrawledPage(seoStudioDomainId);

		Assert.assertEquals(url, crawledPage.getUrl());
	}

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