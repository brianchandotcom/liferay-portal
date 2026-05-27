/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.internal.resource.v1_0;

import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHitBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.seo.studio.rest.dto.v1_0.CrawledPage;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Brooke Dalton
 */
public class CrawledPageResourceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_crawledPageResourceImpl = new CrawledPageResourceImpl();

		Company company = Mockito.mock(Company.class);

		Mockito.doReturn(
			_TEST_COMPANY_ID
		).when(
			company
		).getCompanyId();

		ReflectionTestUtil.setFieldValue(
			_crawledPageResourceImpl, "contextCompany", company);

		ReflectionTestUtil.setFieldValue(
			_crawledPageResourceImpl, "_language", _language);
		ReflectionTestUtil.setFieldValue(
			_crawledPageResourceImpl, "_objectEntryService",
			_objectEntryService);
		ReflectionTestUtil.setFieldValue(
			_crawledPageResourceImpl, "_portal", _portal);
		ReflectionTestUtil.setFieldValue(
			_crawledPageResourceImpl, "_searchEngineAdapter",
			_searchEngineAdapter);
		ReflectionTestUtil.setFieldValue(
			_crawledPageResourceImpl, "_sorts", _sorts);

		Mockito.doReturn(
			SetUtil.fromArray(LocaleUtil.GERMANY, LocaleUtil.US)
		).when(
			_language
		).getCompanyAvailableLocales(
			_TEST_COMPANY_ID
		);

		Mockito.doReturn(
			Mockito.mock(FieldSort.class)
		).when(
			_sorts
		).field(
			Mockito.anyString(), Mockito.any(SortOrder.class)
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);

		Mockito.when(
			_portal.stripURLAnchor(Mockito.anyString(), Mockito.anyString())
		).thenAnswer(
			invocation -> new String[] {
				invocation.getArgument(0), StringPool.BLANK
			}
		);
	}

	@Test
	public void testGetCrawlHitsPageAppliesSearchAfter() throws Exception {
		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");
		_setUpSearchResponse(_hit("https://liferay.com/o/page", "Title", null));

		_crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, 5, "https://liferay.com/o/previous");

		SearchSearchRequest searchSearchRequest = _captureSearchRequest();

		Assert.assertEquals(Integer.valueOf(5), searchSearchRequest.getSize());

		Assert.assertArrayEquals(
			new Object[] {"https://liferay.com/o/previous"},
			searchSearchRequest.getSearchAfter());

		Mockito.verify(
			_sorts
		).field(
			"url.keyword", SortOrder.ASC
		);
	}

	@Test
	public void testGetCrawlHitsPageEncodesSpacesInCanonicalURL()
		throws Exception {

		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");
		_setUpSearchResponse(
			_hit("https://liferay.com/de/page foo", "Title", null));

		Page<CrawledPage> page = _crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, null, null);

		Collection<CrawledPage> items = page.getItems();

		Iterator<CrawledPage> iterator = items.iterator();

		CrawledPage crawledPage = iterator.next();

		Assert.assertEquals(
			"https://liferay.com/de/page%20foo", crawledPage.getCanonicalURL());
	}

	@Test(expected = NoSuchObjectEntryException.class)
	public void testGetCrawlHitsPageFailsWithMissingObjectEntry()
		throws Exception {

		Mockito.doThrow(
			new NoSuchObjectEntryException()
		).when(
			_objectEntryService
		).getObjectEntry(
			_DOMAIN_ID
		);

		_crawledPageResourceImpl.getCrawlHitsPage(_DOMAIN_ID, null, null);
	}

	@Test(expected = NoSuchObjectEntryException.class)
	public void testGetCrawlHitsPageFailsWithWrongAccount() throws Exception {
		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");

		Mockito.doThrow(
			new PrincipalException()
		).when(
			_objectEntryService
		).getObjectEntry(
			_INSTANCE_ID
		);

		_crawledPageResourceImpl.getCrawlHitsPage(_DOMAIN_ID, null, null);
	}

	@Test
	public void testGetCrawlHitsPageLinkFiltersInvalidEntries()
		throws Exception {

		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");
		_setUpSearchResponse(
			_hit(
				"https://liferay.com/o/page", "Mixed Links",
				Arrays.asList(
					"https://liferay.com/a", 42, "https://liferay.com/b", true,
					null)));

		Page<CrawledPage> page = _crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, null, null);

		Collection<CrawledPage> items = page.getItems();

		Iterator<CrawledPage> iterator = items.iterator();

		CrawledPage crawledPage = iterator.next();

		Assert.assertArrayEquals(
			new String[] {"https://liferay.com/a", "https://liferay.com/b"},
			crawledPage.getLinks());
	}

	@Test
	public void testGetCrawlHitsPageMapsTitleAndLinks() throws Exception {
		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");
		_setUpSearchResponse(
			_hit(
				"https://liferay.com/o/page", "Mapped Title",
				Arrays.asList(
					"https://liferay.com/link-a",
					"https://liferay.com/link-b")));

		Page<CrawledPage> page = _crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, null, null);

		Collection<CrawledPage> items = page.getItems();

		Assert.assertEquals(items.toString(), 1, items.size());

		Iterator<CrawledPage> iterator = items.iterator();

		CrawledPage crawledPage = iterator.next();

		Assert.assertEquals("Mapped Title", crawledPage.getTitle());

		Assert.assertArrayEquals(
			new String[] {
				"https://liferay.com/link-a", "https://liferay.com/link-b"
			},
			crawledPage.getLinks());
	}

	@Test
	public void testGetCrawlHitsPageNormalizesLocaleRegionInCanonicalURL()
		throws Exception {

		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");

		_setUpSearchResponse(
			_hit(
				"https://liferay.com/en-US/page", "Title",
				Arrays.asList(
					"https://liferay.com/en/about",
					"https://liferay.com/en-AU/about")));

		Page<CrawledPage> page = _crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, null, null);

		Collection<CrawledPage> items = page.getItems();

		Iterator<CrawledPage> iterator = items.iterator();

		CrawledPage crawledPage = iterator.next();

		Assert.assertEquals(
			"https://liferay.com/en-US/page", crawledPage.getUrl());
		Assert.assertEquals(
			"https://liferay.com/en/page", crawledPage.getCanonicalURL());
		Assert.assertArrayEquals(
			new String[] {
				"https://liferay.com/en/about", "https://liferay.com/en/about"
			},
			crawledPage.getLinks());
	}

	@Test
	public void testGetCrawlHitsPageRemovesReservedAndExcludedParameters()
		throws Exception {

		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");

		Mockito.when(
			_portal.isReservedParameter("p_l_back_url")
		).thenReturn(
			true
		);

		_setUpSearchResponse(
			_hit(
				"https://liferay.com/o/page?p_l_back_url=/search&delta=20" +
					"&highlight=x&filter_category_1=2&utm_cid=7014u&id=5",
				"Title", null));

		Page<CrawledPage> page = _crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, null, null);

		Collection<CrawledPage> items = page.getItems();

		Iterator<CrawledPage> iterator = items.iterator();

		CrawledPage crawledPage = iterator.next();

		Assert.assertEquals(
			"https://liferay.com/o/page?id=5", crawledPage.getUrl());
	}

	@Test
	public void testGetCrawlHitsPageRemovesTrailingSlashFromCanonicalURL()
		throws Exception {

		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");
		_setUpSearchResponse(
			_hit("https://liferay.com/commerce/", "Title", null));

		Page<CrawledPage> page = _crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, null, null);

		Collection<CrawledPage> items = page.getItems();

		Iterator<CrawledPage> iterator = items.iterator();

		CrawledPage crawledPage = iterator.next();

		Assert.assertEquals(
			"https://liferay.com/commerce/", crawledPage.getUrl());
		Assert.assertEquals(
			"https://liferay.com/commerce", crawledPage.getCanonicalURL());
	}

	@Test
	public void testGetCrawlHitsPageResolvesIndexNameFromDomainId()
		throws Exception {

		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");
		_setUpSearchResponse();

		_crawledPageResourceImpl.getCrawlHitsPage(_DOMAIN_ID, null, null);

		Assert.assertEquals(
			"seo_studio_" + _DOMAIN_ID, _captureSearchedIndexName());
	}

	@Test
	public void testGetCrawlHitsPageSkipsHitsWithoutURL() throws Exception {
		_mockObjectEntry(_TEST_COMPANY_ID, "liferay.com");
		_setUpSearchResponse(
			_hit(null, "No URL", null),
			_hit("https://liferay.com/o/with-url", "Has URL", null));

		Page<CrawledPage> page = _crawledPageResourceImpl.getCrawlHitsPage(
			_DOMAIN_ID, null, null);

		Collection<CrawledPage> items = page.getItems();

		Assert.assertEquals(items.toString(), 1, items.size());

		Iterator<CrawledPage> iterator = items.iterator();

		CrawledPage crawledPage = iterator.next();

		Assert.assertEquals(
			"https://liferay.com/o/with-url", crawledPage.getUrl());
	}

	private String _captureSearchedIndexName() throws Exception {
		SearchSearchRequest searchSearchRequest = _captureSearchRequest();

		String[] indexNames = searchSearchRequest.getIndexNames();

		Assert.assertEquals(Arrays.toString(indexNames), 1, indexNames.length);

		return indexNames[0];
	}

	private SearchSearchRequest _captureSearchRequest() throws Exception {
		ArgumentCaptor<SearchSearchRequest> argumentCaptor =
			ArgumentCaptor.forClass(SearchSearchRequest.class);

		Mockito.verify(
			_searchEngineAdapter
		).execute(
			argumentCaptor.capture()
		);

		return argumentCaptor.getValue();
	}

	private SearchHit _hit(String url, String title, List<?> links) {
		SearchHitBuilder searchHitBuilder = new SearchHitBuilder();

		searchHitBuilder.addSource("links", links);
		searchHitBuilder.addSource("title", title);
		searchHitBuilder.addSource("url", url);

		return searchHitBuilder.build();
	}

	private void _mockObjectEntry(long companyId, String hostname)
		throws Exception {

		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.doReturn(
			companyId
		).when(
			objectEntry
		).getCompanyId();

		Mockito.doReturn(
			HashMapBuilder.<String, Serializable>put(
				"hostname", hostname
			).put(
				"r_seoStudioInstanceToSEOStudioDomains_seoStudioInstanceId",
				_INSTANCE_ID
			).build()
		).when(
			objectEntry
		).getValues();

		Mockito.doReturn(
			objectEntry
		).when(
			_objectEntryService
		).getObjectEntry(
			_DOMAIN_ID
		);

		Mockito.doReturn(
			Mockito.mock(ObjectEntry.class)
		).when(
			_objectEntryService
		).getObjectEntry(
			_INSTANCE_ID
		);
	}

	private void _setUpSearchResponse(SearchHit... searchHits)
		throws Exception {

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		searchSearchResponse.setSearchHits(
			new SearchHitsBuilder(
			).addSearchHits(
				Arrays.asList(searchHits)
			).totalHits(
				searchHits.length
			).build());

		Mockito.doReturn(
			searchSearchResponse
		).when(
			_searchEngineAdapter
		).execute(
			Mockito.any(SearchSearchRequest.class)
		);
	}

	private static final long _DOMAIN_ID = 42;

	private static final long _INSTANCE_ID = 99;

	private static final long _TEST_COMPANY_ID = 123;

	private CrawledPageResourceImpl _crawledPageResourceImpl;
	private final Language _language = Mockito.mock(Language.class);
	private final ObjectEntryService _objectEntryService = Mockito.mock(
		ObjectEntryService.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final SearchEngineAdapter _searchEngineAdapter = Mockito.mock(
		SearchEngineAdapter.class);
	private final Sorts _sorts = Mockito.mock(Sorts.class);

}