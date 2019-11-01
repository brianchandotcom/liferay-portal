/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.similar.results.web.internal.portlet;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.legacy.document.DocumentBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsContributorsRegistry;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsDocumentDisplayContextBuilder;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.internal.constants.SimilarResultsPortletKeys;
import com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDisplayContext;
import com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDocumentDisplayContext;
import com.liferay.portal.search.summary.SummaryBuilderFactory;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-similar-results",
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/similar/results/view.jsp",
		"javax.portlet.name=" + SimilarResultsPortletKeys.SIMILAR_RESULTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SimilarResultsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			buildDisplayContext(
				portletSharedSearchResponse, renderRequest, renderResponse));

		super.render(renderRequest, renderResponse);
	}

	protected SimilarResultsDisplayContext buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		SimilarResultsDisplayContext similarResultsDisplayContext =
			createSimilarResultsDisplayContext(renderRequest);

		SimilarResultsPortletPreferences similarResultsPortletPreferences =
			new SimilarResultsPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		SearchResponse searchResponse =
			portletSharedSearchResponse.getFederatedSearchResponse(
				Optional.of(
					similarResultsPortletPreferences.getFederatedSearchKey()));

		if (searchResponse == null) {
			return similarResultsDisplayContext;
		}

		similarResultsDisplayContext.setTotalHits(
			searchResponse.getTotalHits());

		List<Document> legacyDocuments = searchResponse.getDocuments71();

		legacyDocuments = excludingDocumentByUID(
			renderRequest, legacyDocuments);

		int maxItemDisplay =
			similarResultsPortletPreferences.getMaxItemDisplay();

		if (maxItemDisplay < legacyDocuments.size()) {
			legacyDocuments = legacyDocuments.subList(0, maxItemDisplay);
		}

		similarResultsDisplayContext.setDocuments(legacyDocuments);

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			renderRequest);

		Optional<SimilarResultsRoute> optional =
			similarResultsContributorsRegistry.detectRoute(
				_portal.getCurrentURL(renderRequest));

		SimilarResultsRoute similarResultsRoute = optional.orElse(null);

		similarResultsDisplayContext.setSimilarResultsDocumentDisplayContexts(
			buildSimilarResultsDocumentDisplayContexts(
				legacyDocuments, similarResultsRoute, renderRequest,
				renderResponse, themeDisplay));

		return similarResultsDisplayContext;
	}

	protected List<SimilarResultsDocumentDisplayContext>
		buildSimilarResultsDocumentDisplayContexts(
			List<Document> documents, SimilarResultsRoute similarResultsRoute,
			RenderRequest renderRequest, RenderResponse renderResponse,
			ThemeDisplay themeDisplay) {

		List<SimilarResultsDocumentDisplayContext>
			similarResultsDocumentDisplayContexts = new ArrayList<>();

		for (Document document : documents) {
			similarResultsDocumentDisplayContexts.add(
				doBuildSummary(
					document, similarResultsRoute, renderRequest,
					renderResponse, themeDisplay));
		}

		return similarResultsDocumentDisplayContexts;
	}

	protected SimilarResultsDisplayContext createSimilarResultsDisplayContext(
		RenderRequest renderRequest) {

		try {
			return new SimilarResultsDisplayContext(
				getHttpServletRequest(renderRequest));
		}
		catch (ConfigurationException ce) {
			throw new RuntimeException(ce);
		}
	}

	protected SimilarResultsDocumentDisplayContext doBuildSummary(
		Document document, SimilarResultsRoute similarResultsRoute,
		RenderRequest renderRequest, RenderResponse renderResponse,
		ThemeDisplay themeDisplay) {

		SimilarResultsDocumentDisplayContextBuilder
			similarResultsDocumentDisplayContextBuilder =
				new SimilarResultsDocumentDisplayContextBuilder(
					similarResultsRoute);

		similarResultsDocumentDisplayContextBuilder.setAssetEntryLocalService(
			_assetEntryLocalService
		).setDocument(
			document
		).setDocumentBuilderFactory(
			_documentBuilderFactory
		).setFastDateFormatFactory(
			_fastDateFormatFactory
		).setHighlightEnabled(
			false
		).setHttp(
			_http
		).setIndexerRegistry(
			_indexerRegistry
		).setLocale(
			themeDisplay.getLocale()
		).setPortal(
			_portal
		).setRenderRequest(
			renderRequest
		).setRenderResponse(
			renderResponse
		).setResourceActions(
			_resourceActions
		).setSummaryBuilderFactory(
			_summaryBuilderFactory
		).setThemeDisplay(
			themeDisplay
		);

		return similarResultsDocumentDisplayContextBuilder.build();
	}

	protected List<Document> excludingDocumentByUID(
		RenderRequest renderRequest, List<Document> documents71) {

		String uid = (String)renderRequest.getAttribute(Field.UID);

		if (Validator.isBlank(uid)) {
			return documents71;
		}

		List<Document> legacyDocuments = new ArrayList<>(documents71.size());

		for (Document legacyDocument : documents71) {
			if (uid.equals(legacyDocument.getUID())) {
				continue;
			}

			if (isReplyMBMessageDocument(legacyDocument)) {
				continue;
			}

			legacyDocuments.add(legacyDocument);
		}

		return legacyDocuments;
	}

	protected HttpServletRequest getHttpServletRequest(
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	protected boolean isReplyMBMessageDocument(Document legacyDocument) {
		String className = legacyDocument.get(Field.ENTRY_CLASS_NAME);

		boolean mbMessage = false;
		String clazzName = MBMessage.class.getName();

		if (!clazzName.equals(className)) {
			return mbMessage;
		}

		long classPK = GetterUtil.getLong(
			legacyDocument.get(Field.ENTRY_CLASS_PK));

		long resourcePrimKey = GetterUtil.getLong(
			legacyDocument.get(Field.ROOT_ENTRY_CLASS_PK));

		if (resourcePrimKey != classPK) {
			mbMessage = true;
		}

		return mbMessage;
	}

	@Reference
	protected SimilarResultsContributorsRegistry
		similarResultsContributorsRegistry;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DocumentBuilderFactory _documentBuilderFactory;

	@Reference
	private FastDateFormatFactory _fastDateFormatFactory;

	@Reference
	private Http _http;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private SummaryBuilderFactory _summaryBuilderFactory;

}