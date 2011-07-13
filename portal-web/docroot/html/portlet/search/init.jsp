<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %>
<%@ page import="com.liferay.portal.kernel.repository.model.FileEntry" %>
<%@ page import="com.liferay.portal.kernel.search.Document" %>
<%@ page import="com.liferay.portal.kernel.search.FacetedSearcher" %>
<%@ page import="com.liferay.portal.kernel.search.Hits" %>
<%@ page import="com.liferay.portal.kernel.search.Indexer" %>
<%@ page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %>
<%@ page import="com.liferay.portal.kernel.search.OpenSearch" %>
<%@ page import="com.liferay.portal.kernel.search.OpenSearchUtil" %>
<%@ page import="com.liferay.portal.kernel.search.SearchContextFactory" %>
<%@ page import="com.liferay.portal.kernel.search.SearchContext" %>
<%@ page import="com.liferay.portal.kernel.search.Summary" %>
<%@ page import="com.liferay.portal.kernel.search.facet.AssetEntriesFacet" %>
<%@ page import="com.liferay.portal.kernel.search.facet.Facet" %>
<%@ page import="com.liferay.portal.kernel.search.facet.ScopeFacet" %>
<%@ page import="com.liferay.portal.kernel.search.facet.collector.FacetCollector" %>
<%@ page import="com.liferay.portal.kernel.search.facet.collector.TermCollector" %>
<%@ page import="com.liferay.portal.kernel.search.facet.config.FacetConfiguration" %>
<%@ page import="com.liferay.portal.kernel.search.facet.config.FacetConfigurationUtil" %>
<%@ page import="com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.search.facet.util.RangeParserUtil" %>
<%@ page import="com.liferay.portal.kernel.xml.Element" %>
<%@ page import="com.liferay.portal.kernel.xml.SAXReaderUtil" %>
<%@ page import="com.liferay.portal.security.permission.comparator.ModelResourceComparator" %>
<%@ page import="com.liferay.portlet.asset.NoSuchCategoryException" %>
<%@ page import="com.liferay.portlet.asset.model.AssetCategory" %>
<%@ page import="com.liferay.portlet.asset.model.AssetVocabulary" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFileEntryConstants" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil" %>
<%@ page import="com.liferay.util.PropertyComparator" %>

<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.LinkedList" %>

<%
PortletPreferences portalPreferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	portalPreferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean dlLinkToViewURL = false;
boolean includeSystemPortlets = false;

boolean displayAssetTypeFacet = GetterUtil.getBoolean(portalPreferences.getValue("displayAssetTypeFacet", String.valueOf(true)));
boolean displayAssetTagsFacet = GetterUtil.getBoolean(portalPreferences.getValue("displayAssetTagsFacet", String.valueOf(true)));
boolean displayAssetCategoriesFacet = GetterUtil.getBoolean(portalPreferences.getValue("displayAssetCategoriesFacet", String.valueOf(true)));
boolean displayModifiedRangeFacet = GetterUtil.getBoolean(portalPreferences.getValue("displayModifiedRangeFacet", String.valueOf(true)));

String searchConfiguration = portalPreferences.getValue("searchConfiguration", StringPool.BLANK);

if (Validator.isNull(searchConfiguration)) {
	StringBundler sb = new StringBundler(6);

	sb.append("{facets: [");

	if (displayAssetTypeFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.AssetEntriesFacet', data: {frequencyThreshold: 1, values: ['com.liferay.portlet.bookmarks.model.BookmarksEntry','com.liferay.portlet.blogs.model.BlogsEntry','com.liferay.portlet.calendar.model.CalEvent','com.liferay.portlet.documentlibrary.model.DLFileEntry','com.liferay.portlet.journal.model.JournalArticle','com.liferay.portlet.messageboards.model.MBMessage','com.liferay.portlet.wiki.model.WikiPage','com.liferay.portal.model.User']}, displayStyle: 'asset_entries', fieldName: 'entryClassName', label: 'asset-type', order: 'OrderHitsDesc', static: false, weight: 1.5},");
	}
	if (displayAssetTagsFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.MultiValueFacet', data: {displayStyle: 'list', frequencyThreshold: 1, maxTerms: 10, showAssetCount: true}, displayStyle: 'asset_tags', fieldName: 'assetTagNames', label: 'tags', order: 'OrderValueAsc', static: false, weight: 1.4},");
	}
	if (displayAssetCategoriesFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.MultiValueFacet', data: {displayStyle: 'list', frequencyThreshold: 1, maxTerms: 10, showAssetCount: true}, displayStyle: 'asset_tags', fieldName: 'assetCategoryNames', label: 'categories', order: 'OrderValueAsc', static: false, weight: 1.3},");
	}
	if (displayModifiedRangeFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.RangeFacet',data: {frequencyThreshold: 1, ranges: [{label:'modified', range:'[19700101000000 TO *]'}]}, displayStyle: 'calendar', fieldName: 'modified', label: 'modified', order: 'OrderHitsDesc', static: false, weight: 1.1}");
	}

	sb.append("]}");

	searchConfiguration = sb.toString();
}

boolean displayResultsInDocumentForm = GetterUtil.getBoolean(portalPreferences.getValue("displayResultsInDocumentForm", String.valueOf(false)));
boolean viewInContext = GetterUtil.getBoolean(portalPreferences.getValue("viewInContext", String.valueOf(true)));
boolean displayMainQuery = GetterUtil.getBoolean(portalPreferences.getValue("displayMainQuery", String.valueOf(false)));
boolean displayOpenSearchResults = GetterUtil.getBoolean(portalPreferences.getValue("displayOpenSearchResults", String.valueOf(true)));
%>

<%@ include file="/html/portlet/search/init-ext.jsp" %>

<%!
private String _buildCategoryPath(AssetCategory category, Locale locale) throws PortalException, SystemException {
	List<AssetCategory> ancestorCategories = category.getAncestors();

	if (ancestorCategories.isEmpty()) {
		return category.getName();
	}

	Collections.reverse(ancestorCategories);

	StringBundler sb = new StringBundler(ancestorCategories.size() * 2 + 1);

	for (AssetCategory ancestorCategory : ancestorCategories) {
		sb.append(ancestorCategory.getTitle(locale));
		sb.append(" &raquo; ");
	}

	sb.append(category.getName());

	return sb.toString();
}

private String _checkViewURL(String viewURL, String currentURL, ThemeDisplay themeDisplay) {
	if (Validator.isNotNull(viewURL) && viewURL.startsWith(themeDisplay.getURLPortal())) {
		viewURL = HttpUtil.setParameter(viewURL, "redirect", currentURL);
	}

	return viewURL;
}

private PortletURL _getURL(HttpServletRequest request, ThemeDisplay themeDisplay, String portletId, Document result) throws Exception {
	long resultGroupId = GetterUtil.getLong(result.get(Field.GROUP_ID));

	if (resultGroupId == 0) {
		resultGroupId = themeDisplay.getScopeGroupId();
	}

	long resultScopeGroupId = GetterUtil.getLong(
		result.get(Field.SCOPE_GROUP_ID));

	if (resultScopeGroupId == 0) {
		resultScopeGroupId = themeDisplay.getScopeGroupId();
	}

	return _getPortletURL(request, portletId, resultGroupId);
}

private PortletURL _getPortletURL(HttpServletRequest request, String portletId, long groupId) throws Exception {
	long plid = LayoutServiceUtil.getDefaultPlid(groupId, false, portletId);

	if (plid == 0) {
		plid = LayoutServiceUtil.getDefaultPlid(groupId, true, portletId);
	}

	if (plid == 0) {
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		if (layout != null) {
			plid = layout.getPlid();
		}
	}

	PortletURL portletURL = PortletURLFactoryUtil.create(
		request, portletId, plid, PortletRequest.RENDER_PHASE);

	portletURL.setWindowState(WindowState.MAXIMIZED);
	portletURL.setPortletMode(PortletMode.VIEW);

	return portletURL;
}
%>