<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.FacetedSearcher" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.OpenSearch" %><%@
page import="com.liferay.portal.kernel.search.OpenSearchUtil" %><%@
page import="com.liferay.portal.kernel.search.SearchContext" %><%@
page import="com.liferay.portal.kernel.search.SearchContextFactory" %><%@
page import="com.liferay.portal.kernel.search.Summary" %><%@
page import="com.liferay.portal.kernel.search.facet.AssetEntriesFacet" %><%@
page import="com.liferay.portal.kernel.search.facet.Facet" %><%@
page import="com.liferay.portal.kernel.search.facet.ScopeFacet" %><%@
page import="com.liferay.portal.kernel.search.facet.collector.FacetCollector" %><%@
page import="com.liferay.portal.kernel.search.facet.collector.TermCollector" %><%@
page import="com.liferay.portal.kernel.search.facet.config.FacetConfiguration" %><%@
page import="com.liferay.portal.kernel.search.facet.config.FacetConfigurationUtil" %><%@
page import="com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil" %><%@
page import="com.liferay.portal.kernel.search.facet.util.RangeParserUtil" %><%@
page import="com.liferay.portal.kernel.util.DateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalClassLoaderUtil" %><%@
page import="com.liferay.portal.kernel.xml.Element" %><%@
page import="com.liferay.portal.kernel.xml.SAXReaderUtil" %><%@
page import="com.liferay.portal.security.permission.comparator.ModelResourceComparator" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.NoSuchCategoryException" %><%@
page import="com.liferay.portlet.asset.model.AssetCategory" %><%@
page import="com.liferay.portlet.asset.model.AssetEntry" %><%@
page import="com.liferay.portlet.asset.model.AssetRenderer" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portlet.asset.model.AssetVocabulary" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetCategoryServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyServiceUtil" %><%@
page import="com.liferay.portlet.documentlibrary.model.DLFileEntryConstants" %><%@
page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil" %><%@
page import="com.liferay.taglib.aui.ScriptTag" %><%@
page import="com.liferay.util.PropertyComparator" %>

<%@ page import="java.util.Comparator" %><%@
page import="java.util.LinkedList" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean advancedConfiguration = GetterUtil.getBoolean(portletPreferences.getValue("advancedConfiguration", null));
boolean displayAssetTypeFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetTypeFacet", null), true);
boolean displayAssetTagsFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetTagsFacet", null), true);
boolean displayAssetCategoriesFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetCategoriesFacet", null), true);
boolean displayModifiedRangeFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayModifiedRangeFacet", null), true);

boolean displayResultsInDocumentForm = GetterUtil.getBoolean(portletPreferences.getValue("displayResultsInDocumentForm", null));

if (!permissionChecker.isCompanyAdmin()) {
	displayResultsInDocumentForm = false;
}

boolean viewInContext = GetterUtil.getBoolean(portletPreferences.getValue("viewInContext", null), true);
boolean displayMainQuery = GetterUtil.getBoolean(portletPreferences.getValue("displayMainQuery", null));
boolean displayOpenSearchResults = GetterUtil.getBoolean(portletPreferences.getValue("displayOpenSearchResults", null));

String searchConfiguration = portletPreferences.getValue("searchConfiguration", StringPool.BLANK);

if (!advancedConfiguration && Validator.isNull(searchConfiguration)) {
	StringBundler sb = new StringBundler(6);

	sb.append("{facets: [");

	if (displayAssetTypeFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.AssetEntriesFacet', data: {frequencyThreshold: 1, values: ['com.liferay.portlet.bookmarks.model.BookmarksEntry','com.liferay.portlet.blogs.model.BlogsEntry','com.liferay.portlet.calendar.model.CalEvent','com.liferay.portlet.documentlibrary.model.DLFileEntry','com.liferay.portlet.journal.model.JournalArticle','com.liferay.portlet.messageboards.model.MBMessage','com.liferay.portlet.wiki.model.WikiPage','com.liferay.portal.model.User']}, displayStyle: 'asset_entries', fieldName: 'entryClassName', label: 'asset-type', order: 'OrderHitsDesc', static: false, weight: 1.5},");
	}

	if (displayAssetTagsFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.MultiValueFacet', data: {displayStyle: 'list', frequencyThreshold: 1, maxTerms: 10, showAssetCount: true}, displayStyle: 'asset_tags', fieldName: 'assetTagNames', label: 'tag', order: 'OrderHitsDesc', static: false, weight: 1.4},");
	}

	if (displayAssetCategoriesFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.MultiValueFacet', data: {displayStyle: 'list', frequencyThreshold: 1, maxTerms: 10, showAssetCount: true}, displayStyle: 'asset_tags', fieldName: 'assetCategoryNames', label: 'category', order: 'OrderHitsDesc', static: false, weight: 1.3},");
	}

	if (displayModifiedRangeFacet) {
		sb.append("{className: 'com.liferay.portal.kernel.search.facet.RangeFacet', data: {frequencyThreshold: 1, ranges: [{label:'modified', range:'[19700101000000 TO *]'}]}, displayStyle: 'modified', fieldName: 'modified', label: 'modified', order: 'OrderHitsDesc', static: false, weight: 1.1}");
	}

	sb.append("]}");

	searchConfiguration = sb.toString();
}

boolean dlLinkToViewURL = false;
boolean includeSystemPortlets = false;
%>

<%@ include file="/html/portlet/search/init-ext.jsp" %>

<%!
private String _buildAssetCategoryPath(AssetCategory assetCategory, Locale locale) throws Exception {
	List<AssetCategory> assetCategories = assetCategory.getAncestors();

	if (assetCategories.isEmpty()) {
		return assetCategory.getName();
	}

	Collections.reverse(assetCategories);

	StringBundler sb = new StringBundler(assetCategories.size() * 2 + 1);

	for (AssetCategory curAssetCategory : assetCategories) {
		sb.append(curAssetCategory.getTitle(locale));
		sb.append(" &raquo; ");
	}

	sb.append(assetCategory.getName());

	return sb.toString();
}

private String _checkViewURL(ThemeDisplay themeDisplay, String viewURL, String currentURL) {
	if (Validator.isNotNull(viewURL) && viewURL.startsWith(themeDisplay.getURLPortal())) {
		viewURL = HttpUtil.setParameter(viewURL, "redirect", currentURL);
	}

	return viewURL;
}

private PortletURL _getViewFullContentURL(HttpServletRequest request, ThemeDisplay themeDisplay, String portletId, Document document) throws Exception {
	long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

	if (groupId == 0) {
		Layout layout = themeDisplay.getLayout();

		groupId = layout.getGroupId();
	}

	long scopeGroupId = GetterUtil.getLong(document.get(Field.SCOPE_GROUP_ID));

	if (scopeGroupId == 0) {
		scopeGroupId = themeDisplay.getScopeGroupId();
	}

	long plid = LayoutServiceUtil.getDefaultPlid(groupId, scopeGroupId, false, portletId);

	if (plid == 0) {
		plid = LayoutServiceUtil.getDefaultPlid(groupId, scopeGroupId, true, portletId);
	}

	if (plid == 0) {
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		if (layout != null) {
			plid = layout.getPlid();
		}
	}

	PortletURL portletURL = PortletURLFactoryUtil.create(request, portletId, plid, PortletRequest.RENDER_PHASE);

	portletURL.setWindowState(WindowState.MAXIMIZED);
	portletURL.setPortletMode(PortletMode.VIEW);

	return portletURL;
}
%>