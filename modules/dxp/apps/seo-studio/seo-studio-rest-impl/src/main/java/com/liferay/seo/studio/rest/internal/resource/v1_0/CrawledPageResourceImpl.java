/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.rest.internal.resource.v1_0;

import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.seo.studio.rest.dto.v1_0.CrawledPage;
import com.liferay.seo.studio.rest.resource.v1_0.CrawledPageResource;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brooke Dalton
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/crawled-page.properties",
	scope = ServiceScope.PROTOTYPE, service = CrawledPageResource.class
)
public class CrawledPageResourceImpl extends BaseCrawledPageResourceImpl {

	@Override
	public Page<CrawledPage> getCrawlHitsPage(
			Long seoStudioDomainId, Integer pageSize, String searchAfter)
		throws Exception {

		String indexName = _resolveIndexName(seoStudioDomainId);

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(
			_sorts.field("url.keyword", SortOrder.ASC));
		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(indexName);
		searchSearchRequest.setSize(GetterUtil.getInteger(pageSize, 500));

		if (Validator.isNotNull(searchAfter)) {
			searchSearchRequest.setSearchAfter(new Object[] {searchAfter});
		}

		Set<String> languageCodes = new HashSet<>();

		for (Locale locale :
				_language.getCompanyAvailableLocales(
					contextCompany.getCompanyId())) {

			languageCodes.add(StringUtil.toLowerCase(locale.getLanguage()));
		}

		List<CrawledPage> crawledPages = new ArrayList<>();

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Map<String, Object> sourcesMap = searchHit.getSourcesMap();

			if (sourcesMap == null) {
				continue;
			}

			String url = _canonicalizeURL(
				GetterUtil.getString(sourcesMap.get("url")));

			if (Validator.isNull(url)) {
				continue;
			}

			String canonicalURL = _toCanonicalURL(languageCodes, url);

			CrawledPage crawledPage = new CrawledPage();

			crawledPage.setCanonicalURL(() -> canonicalURL);
			crawledPage.setTitle(
				() -> GetterUtil.getString(sourcesMap.get("title")));
			crawledPage.setUrl(() -> url);

			Object linksObject = sourcesMap.get("links");

			if (linksObject instanceof List<?>) {
				List<?> linksList = (List<?>)linksObject;

				List<String> stringLinks = new ArrayList<>(linksList.size());

				for (Object link : linksList) {
					if (link instanceof String) {
						stringLinks.add(
							_toCanonicalURL(
								languageCodes, _canonicalizeURL((String)link)));
					}
				}

				crawledPage.setLinks(() -> stringLinks.toArray(new String[0]));
			}

			crawledPages.add(crawledPage);
		}

		return Page.of(crawledPages);
	}

	private String _canonicalizeURL(String url) {
		String queryString = HttpComponentsUtil.getQueryString(url);

		if (Validator.isNull(queryString)) {
			return url;
		}

		for (String name : HttpComponentsUtil.getParameterNames(queryString)) {
			if (_excludedParameterNames.contains(name) ||
				name.startsWith("filter_category_") ||
				_portal.isReservedParameter(name)) {

				url = HttpComponentsUtil.removeParameter(url, name);
			}
		}

		return url;
	}

	private String _getLanguageCode(String segment) {
		String lowerCaseSegment = StringUtil.toLowerCase(segment);

		String normalizedSegment = StringUtil.replace(
			lowerCaseSegment, CharPool.UNDERLINE, CharPool.DASH);

		int index = normalizedSegment.indexOf(CharPool.DASH);

		if (index < 0) {
			return lowerCaseSegment;
		}

		return lowerCaseSegment.substring(0, index);
	}

	private String _normalizeLocale(Set<String> languageCodes, String url) {
		String[] pathSegments = StringUtil.split(
			HttpComponentsUtil.getPath(url), CharPool.SLASH);

		if (pathSegments.length < 2) {
			return url;
		}

		String localeSegment = pathSegments[1];

		String languageCode = _getLanguageCode(localeSegment);

		if (!languageCodes.contains(languageCode) ||
			languageCode.equals(localeSegment)) {

			return url;
		}

		String domain = HttpComponentsUtil.getDomain(url);

		return StringUtil.replaceFirst(
			url, StringBundler.concat(domain, StringPool.SLASH, localeSegment),
			StringBundler.concat(domain, StringPool.SLASH, languageCode));
	}

	private String _removeTrailingSlash(String url) {
		if ((url.length() > 1) && (url.indexOf(CharPool.QUESTION) < 0) &&
			url.endsWith(StringPool.SLASH)) {

			return url.substring(0, url.length() - 1);
		}

		return url;
	}

	private String _resolveIndexName(Long seoStudioDomainId) throws Exception {
		ObjectEntry objectEntry = _objectEntryService.getObjectEntry(
			seoStudioDomainId);

		Map<String, Serializable> valuesMap = objectEntry.getValues();

		try {
			_objectEntryService.getObjectEntry(
				GetterUtil.getLong(
					valuesMap.get(
						"r_seoStudioInstanceToSEOStudioDomains_" +
							"seoStudioInstanceId")));
		}
		catch (PortalException portalException) {
			throw new NoSuchObjectEntryException(
				"No object entry was found for " + seoStudioDomainId,
				portalException);
		}

		return "seo_studio_" + seoStudioDomainId;
	}

	private String _toCanonicalURL(Set<String> languageCodes, String url) {
		return _removeTrailingSlash(
			_normalizeLocale(
				languageCodes, StringUtil.replace(url, CharPool.SPACE, "%20")));
	}

	private static final Set<String> _excludedParameterNames =
		SetUtil.fromArray(
			"category", "cur", "delta", "facet", "filter", "highlight", "order",
			"orderby", "sort", "start", "tag", "utm_campaign", "utm_cid",
			"utm_content", "utm_medium", "utm_source", "utm_term");

	@Reference
	private Language _language;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private Sorts _sorts;

}