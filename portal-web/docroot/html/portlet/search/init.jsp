<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
page import="com.liferay.portal.kernel.search.FolderSearcher" %><%@
page import="com.liferay.portal.kernel.search.Hits" %><%@
page import="com.liferay.portal.kernel.search.HitsOpenSearchImpl" %><%@
page import="com.liferay.portal.kernel.search.Indexer" %><%@
page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.search.OpenSearch" %><%@
page import="com.liferay.portal.kernel.search.OpenSearchUtil" %><%@
page import="com.liferay.portal.kernel.search.QueryConfig" %><%@
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
boolean displayScopeFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayScopeFacet", null), true);
boolean displayAssetTypeFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetTypeFacet", null), true);
boolean displayAssetTagsFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetTagsFacet", null), true);
boolean displayAssetCategoriesFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetCategoriesFacet", null), true);
boolean displayFolderFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayFolderFacet", null), true);
boolean displayUserFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayUserFacet", null), true);
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
	searchConfiguration = ContentUtil.get(PropsValues.SEARCH_FACET_CONFIGURATION);
}

boolean dlLinkToViewURL = false;
boolean includeSystemPortlets = false;
%>

<%@ include file="/html/portlet/search/init-ext.jsp" %>

<%!
private String _buildAssetCategoryPath(AssetCategory assetCategory, Locale locale) throws Exception {
	List<AssetCategory> assetCategories = assetCategory.getAncestors();

	if (assetCategories.isEmpty()) {
		return HtmlUtil.escape(assetCategory.getName());
	}

	Collections.reverse(assetCategories);

	StringBundler sb = new StringBundler(assetCategories.size() * 2 + 1);

	for (AssetCategory curAssetCategory : assetCategories) {
		sb.append(HtmlUtil.escape(curAssetCategory.getTitle(locale)));
		sb.append(" &raquo; ");
	}

	sb.append(HtmlUtil.escape(assetCategory.getName()));

	return sb.toString();
}

private String _checkViewURL(ThemeDisplay themeDisplay, String viewURL, String currentURL, boolean inheritRedirect) {
	if (Validator.isNotNull(viewURL) && viewURL.startsWith(themeDisplay.getURLPortal())) {
		viewURL = HttpUtil.setParameter(viewURL, "inheritRedirect", inheritRedirect);

		if (!inheritRedirect) {
			viewURL = HttpUtil.setParameter(viewURL, "redirect", currentURL);
		}
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

	long plid = LayoutConstants.DEFAULT_PLID;

	Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

	if (layout != null) {
		plid = layout.getPlid();
	}

	if (plid == 0) {
		plid = LayoutServiceUtil.getDefaultPlid(groupId, scopeGroupId, portletId);
	}

	PortletURL portletURL = PortletURLFactoryUtil.create(request, portletId, plid, PortletRequest.RENDER_PHASE);

	portletURL.setPortletMode(PortletMode.VIEW);
	portletURL.setWindowState(WindowState.MAXIMIZED);

	return portletURL;
}
%>