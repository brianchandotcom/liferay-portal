/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.version=3.0"
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
			_buildDisplayContext(
				portletSharedSearchResponse, renderRequest, renderResponse));

		super.render(renderRequest, renderResponse);
	}

	@Reference
	protected SimilarResultsContributorsRegistry
		similarResultsContributorsRegistry;

	private SimilarResultsDisplayContext _buildDisplayContext(
		PortletSharedSearchResponse portletSharedSearchResponse,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		SimilarResultsDisplayContext similarResultsDisplayContext =
			_createSimilarResultsDisplayContext(renderRequest);

		SimilarResultsPortletPreferences similarResultsPortletPreferences =
			new SimilarResultsPortletPreferencesImpl(
				portletSharedSearchResponse.getPortletPreferences(
					renderRequest));

		SearchResponse searchResponse =
			portletSharedSearchResponse.getFederatedSearchResponse(
				similarResultsPortletPreferences.getFederatedSearchKey());

		if (searchResponse == null) {
			return similarResultsDisplayContext;
		}

		similarResultsDisplayContext.setTotalHits(
			searchResponse.getTotalHits());

		List<Document> legacyDocuments = searchResponse.getDocuments71();

		legacyDocuments = _excludingDocumentByUID(
			renderRequest, legacyDocuments);

		int maxItemDisplay =
			similarResultsPortletPreferences.getMaxItemDisplay();

		if (maxItemDisplay < legacyDocuments.size()) {
			legacyDocuments = legacyDocuments.subList(0, maxItemDisplay);
		}

		similarResultsDisplayContext.setDocuments(legacyDocuments);

		similarResultsDisplayContext.setSimilarResultsDocumentDisplayContexts(
			_buildSimilarResultsDocumentDisplayContexts(
				legacyDocuments,
				similarResultsContributorsRegistry.detectRoute(
					_portal.getCurrentURL(renderRequest)),
				renderRequest, renderResponse,
				portletSharedSearchResponse.getThemeDisplay(renderRequest)));

		return similarResultsDisplayContext;
	}

	private List<SimilarResultsDocumentDisplayContext>
		_buildSimilarResultsDocumentDisplayContexts(
			List<Document> documents, SimilarResultsRoute similarResultsRoute,
			RenderRequest renderRequest, RenderResponse renderResponse,
			ThemeDisplay themeDisplay) {

		List<SimilarResultsDocumentDisplayContext>
			similarResultsDocumentDisplayContexts = new ArrayList<>();

		for (Document document : documents) {
			SimilarResultsDocumentDisplayContext
				similarResultsDocumentDisplayContext = _buildSummary(
					document, similarResultsRoute, renderRequest,
					renderResponse, themeDisplay);

			if (!similarResultsDocumentDisplayContext.
					isTemporarilyUnavailable()) {

				similarResultsDocumentDisplayContexts.add(
					similarResultsDocumentDisplayContext);
			}
		}

		return similarResultsDocumentDisplayContexts;
	}

	private SimilarResultsDocumentDisplayContext _buildSummary(
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

	private SimilarResultsDisplayContext _createSimilarResultsDisplayContext(
		RenderRequest renderRequest) {

		try {
			return new SimilarResultsDisplayContext(
				_getHttpServletRequest(renderRequest));
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private List<Document> _excludingDocumentByUID(
		RenderRequest renderRequest, List<Document> documents71) {

		String uid = (String)renderRequest.getAttribute(Field.UID);

		if (Validator.isBlank(uid)) {
			return documents71;
		}

		List<Document> legacyDocuments = new ArrayList<>(documents71.size());

		for (Document legacyDocument : documents71) {
			if (uid.equals(legacyDocument.getUID()) ||
				_isReplyMBMessageDocument(legacyDocument) ||
				!_isSupportedDocument(uid, legacyDocument)) {

				continue;
			}

			legacyDocuments.add(legacyDocument);
		}

		return legacyDocuments;
	}

	private HttpServletRequest _getHttpServletRequest(
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	private boolean _isReplyMBMessageDocument(Document legacyDocument) {
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

	private boolean _isSupportedDocument(String uid, Document legacyDocument) {
		String className = legacyDocument.get(Field.ENTRY_CLASS_NAME);

		return uid.contains(className);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DocumentBuilderFactory _documentBuilderFactory;

	@Reference
	private FastDateFormatFactory _fastDateFormatFactory;

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