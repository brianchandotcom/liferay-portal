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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetRendererFactoryLookup;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.legacy.document.DocumentBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.summary.SummaryBuilderFactory;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactoryImpl;
import com.liferay.portal.search.web.internal.display.context.SearchResultPreferences;
import com.liferay.portal.search.web.internal.document.DocumentFormPermissionCheckerImpl;
import com.liferay.portal.search.web.internal.portlet.shared.search.NullPortletURL;
import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;
import com.liferay.portal.search.web.internal.result.display.context.builder.SearchResultSummaryDisplayContextBuilder;
import com.liferay.portal.search.web.internal.search.results.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.result.SearchResultImageContributor;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-search-results",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Search Results",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/search/results/view.jsp",
		"javax.portlet.name=" + SearchResultsPortletKeys.SEARCH_RESULTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class SearchResultsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(renderRequest);

		SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
			_buildDisplayContext(
				portletSharedSearchResponse, renderRequest, renderResponse);

		if (searchResultsPortletDisplayContext.isRenderNothing()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			searchResultsPortletDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addSearchResultImageContributor(
		SearchResultImageContributor searchResultImageContributor) {

		_searchResultImageContributors.add(searchResultImageContributor);
	}

	protected String getCurrentURL(RenderRequest renderRequest) {
		return _portal.getCurrentURL(renderRequest);
	}

	protected HttpServletRequest getHttpServletRequest(
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	protected PortletURL getPortletURL(
		RenderRequest renderRequest, String paginationStartParameterName) {

		final String urlString = _getURLString(
			renderRequest, paginationStartParameterName);

		return new NullPortletURL() {

			@Override
			public String toString() {
				return urlString;
			}

		};
	}

	protected PortletURLFactory getPortletURLFactory(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return new PortletURLFactoryImpl(renderRequest, renderResponse);
	}

	protected boolean isRenderNothing(
		RenderRequest renderRequest, SearchRequest searchRequest) {

		long assetEntryId = ParamUtil.getLong(renderRequest, "assetEntryId");

		if (assetEntryId != 0) {
			return false;
		}

		if ((searchRequest.getQueryString() == null) &&
			!searchRequest.isEmptySearchEnabled()) {

			return true;
		}

		return false;
	}

	protected void removeSearchResultImageContributor(
		SearchResultImageContributor searchResultImageContributor) {

		_searchResultImageContributors.remove(searchResultImageContributor);
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	protected AssetRendererFactoryLookup assetRendererFactoryLookup;

	@Reference
	protected DocumentBuilderFactory documentBuilderFactory;

	@Reference
	protected FastDateFormatFactory fastDateFormatFactory;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected Http http;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected Language language;

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

	@Reference
	protected PortletSharedSearchRequest portletSharedSearchRequest;

	@Reference
	protected ResourceActions resourceActions;

	@Reference
	protected SummaryBuilderFactory summaryBuilderFactory;

	@Reference
	protected UserLocalService userLocalService;

	private SearchResultsPortletDisplayContext _buildDisplayContext(
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
			_createSearchResultsPortletDisplayContext(renderRequest);

		SearchResultsSummariesHolder searchResultsSummariesHolder =
			_buildSummaries(
				portletSharedSearchResponse, renderRequest, renderResponse);

		List<Document> documents = new ArrayList<>(
			searchResultsSummariesHolder.getDocuments());

		searchResultsPortletDisplayContext.setDocuments(documents);

		SearchResultsPortletPreferences searchResultsPortletPreferences =
			new SearchResultsPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		SearchResponse searchResponse = _getSearchResponse(
			portletSharedSearchResponse, searchResultsPortletPreferences);

		SearchRequest searchRequest = searchResponse.getRequest();

		Optional<String> keywordsOptional = Optional.ofNullable(
			searchRequest.getQueryString());

		searchResultsPortletDisplayContext.setKeywords(
			keywordsOptional.orElse(StringPool.BLANK));

		searchResultsPortletDisplayContext.setRenderNothing(
			isRenderNothing(renderRequest, searchRequest));

		int paginationDelta = Optional.ofNullable(
			searchRequest.getSize()
		).orElse(
			SearchContainer.DEFAULT_DELTA
		);
		int paginationStart = 0;

		int from = Optional.ofNullable(
			searchRequest.getFrom()
		).orElse(
			0
		);

		if (from > 0) {
			paginationStart = (from / paginationDelta) + 1;
		}

		searchResultsPortletDisplayContext.setSearchContainer(
			_buildSearchContainer(
				documents, searchResponse.getTotalHits(), paginationStart,
				searchResultsPortletPreferences.
					getPaginationStartParameterName(),
				paginationDelta,
				searchResultsPortletPreferences.
					getPaginationDeltaParameterName(),
				renderRequest));

		searchResultsPortletDisplayContext.setSearchResultsSummariesHolder(
			searchResultsSummariesHolder);
		searchResultsPortletDisplayContext.
			setSearchResultSummaryDisplayContexts(
				searchResultsPortletDisplayContext.
					translateSearchResultSummaryDisplayContexts(documents));
		searchResultsPortletDisplayContext.setTotalHits(
			searchResponse.getTotalHits());

		return searchResultsPortletDisplayContext;
	}

	private SearchContainer<Document> _buildSearchContainer(
			List<Document> documents, int totalHits, int paginationStart,
			String paginationStartParameterName, int paginationDelta,
			String paginationDeltaParameterName, RenderRequest renderRequest)
		throws PortletException {

		PortletRequest portletRequest = renderRequest;
		DisplayTerms displayTerms = null;
		DisplayTerms searchTerms = null;
		String curParam = paginationStartParameterName;
		int cur = paginationStart;
		int delta = paginationDelta;
		PortletURL portletURL = getPortletURL(
			renderRequest, paginationStartParameterName);
		List<String> headerNames = null;
		String emptyResultsMessage = null;
		String cssClass = null;

		SearchContainer<Document> searchContainer = new SearchContainer<>(
			portletRequest, displayTerms, searchTerms, curParam, cur, delta,
			portletURL, headerNames, emptyResultsMessage, cssClass);

		searchContainer.setDeltaParam(paginationDeltaParameterName);
		searchContainer.setResultsAndTotal(() -> documents, totalHits);

		return searchContainer;
	}

	private SearchResultsSummariesHolder _buildSummaries(
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			return _doBuildSummaries(
				portletSharedSearchResponse, renderRequest, renderResponse);
		}
		catch (PortletException portletException) {
			throw portletException;
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private SearchResultSummaryDisplayContext _buildSummary(
			Document document, RenderRequest renderRequest,
			RenderResponse renderResponse, ThemeDisplay themeDisplay,
			PortletURLFactory portletURLFactory,
			SearchResultsPortletPreferences searchResultsPortletPreferences,
			SearchResultPreferences searchResultPreferences)
		throws Exception {

		SearchResultSummaryDisplayContextBuilder
			searchResultSummaryDisplayContextBuilder =
				new SearchResultSummaryDisplayContextBuilder();

		searchResultSummaryDisplayContextBuilder.setAssetEntryLocalService(
			assetEntryLocalService
		).setAssetRendererFactoryLookup(
			assetRendererFactoryLookup
		).setCurrentURL(
			getCurrentURL(renderRequest)
		).setDocument(
			document
		).setDocumentBuilderFactory(
			documentBuilderFactory
		).setFastDateFormatFactory(
			fastDateFormatFactory
		).setGroupLocalService(
			groupLocalService
		).setHighlightEnabled(
			searchResultsPortletPreferences.isHighlightEnabled()
		).setImageRequested(
			true
		).setIndexerRegistry(
			indexerRegistry
		).setLanguage(
			language
		).setLocale(
			themeDisplay.getLocale()
		).setObjectDefinitionLocalService(
			objectDefinitionLocalService
		).setPortletURLFactory(
			portletURLFactory
		).setRenderRequest(
			renderRequest
		).setRenderResponse(
			renderResponse
		).setRequest(
			getHttpServletRequest(renderRequest)
		).setResourceActions(
			resourceActions
		).setSearchResultImageContributorsStream(
			_searchResultImageContributors.stream()
		).setSearchResultPreferences(
			searchResultPreferences
		).setSummaryBuilderFactory(
			summaryBuilderFactory
		).setThemeDisplay(
			themeDisplay
		).setUserLocalService(
			userLocalService
		);

		return searchResultSummaryDisplayContextBuilder.build();
	}

	private SearchResultsPortletDisplayContext
		_createSearchResultsPortletDisplayContext(RenderRequest renderRequest) {

		try {
			return new SearchResultsPortletDisplayContext(
				getHttpServletRequest(renderRequest));
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private SearchResultsSummariesHolder _doBuildSummaries(
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		SearchResultsPortletPreferences searchResultsPortletPreferences =
			new SearchResultsPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		SearchResponse searchResponse = _getSearchResponse(
			portletSharedSearchResponse, searchResultsPortletPreferences);

		List<Document> documents = searchResponse.getDocuments71();

		SearchResultsSummariesHolder searchResultsSummariesHolder =
			new SearchResultsSummariesHolder(documents.size());

		PortletURLFactory portletURLFactory = getPortletURLFactory(
			renderRequest, renderResponse);

		SearchResultPreferences searchResultPreferences =
			new SearchResultPreferencesImpl(
				searchResultsPortletPreferences,
				new DocumentFormPermissionCheckerImpl(themeDisplay));

		for (Document document : documents) {
			SearchResultSummaryDisplayContext
				searchResultSummaryDisplayContext = _buildSummary(
					document, renderRequest, renderResponse, themeDisplay,
					portletURLFactory, searchResultsPortletPreferences,
					searchResultPreferences);

			if (searchResultSummaryDisplayContext != null) {
				searchResultsSummariesHolder.put(
					document, searchResultSummaryDisplayContext);
			}
		}

		return searchResultsSummariesHolder;
	}

	private SearchResponse _getSearchResponse(
		PortletSharedSearchResponse portletSharedSearchResponse,
		SearchResultsPortletPreferences searchResultsPortletPreferences) {

		return portletSharedSearchResponse.getFederatedSearchResponse(
			searchResultsPortletPreferences.getFederatedSearchKeyOptional());
	}

	private String _getURLString(
		RenderRequest renderRequest, String paginationStartParameterName) {

		return http.removeParameter(
			portletSharedRequestHelper.getCompleteURL(renderRequest),
			paginationStartParameterName);
	}

	@Reference
	private Portal _portal;

	private final Set<SearchResultImageContributor>
		_searchResultImageContributors = new HashSet<>();

}