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

<%@ page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.search.FacetedSearcher" %><%@
page import="com.liferay.portal.kernel.search.FolderSearcher" %><%@
page import="com.liferay.portal.kernel.search.HitsOpenSearchImpl" %><%@
page import="com.liferay.portal.kernel.search.KeywordsSuggestionHolder" %><%@
page import="com.liferay.portal.kernel.search.OpenSearch" %><%@
page import="com.liferay.portal.kernel.search.OpenSearchUtil" %><%@
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
page import="com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil" %><%@
page import="com.liferay.portal.security.permission.comparator.ModelResourceComparator" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.NoSuchCategoryException" %><%@
page import="com.liferay.portlet.asset.service.permission.AssetCategoryPermission" %><%@
page import="com.liferay.taglib.aui.ScriptTag" %><%@
page import="com.liferay.util.PropertyComparator" %>

<%@ page import="java.util.Comparator" %><%@
page import="java.util.LinkedList" %>

<%
PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(request);

boolean advancedConfiguration = GetterUtil.getBoolean(portletPreferences.getValue("advancedConfiguration", null));

int collatedSpellCheckResultDisplayThreshold = GetterUtil.getInteger(portletPreferences.getValue("collatedSpellCheckResultDisplayThreshold", null), PropsValues.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD);

if (collatedSpellCheckResultDisplayThreshold < 0) {
	collatedSpellCheckResultDisplayThreshold = PropsValues.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD;
}

boolean collatedSpellCheckResultEnabled = GetterUtil.getBoolean(portletPreferences.getValue("collatedSpellCheckResultEnabled", null), PropsValues.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_ENABLED);
boolean dlLinkToViewURL = false;
boolean displayAssetCategoriesFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetCategoriesFacet", null), true);
boolean displayAssetTagsFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetTagsFacet", null), true);
boolean displayAssetTypeFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayAssetTypeFacet", null), true);
boolean displayFolderFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayFolderFacet", null), true);
boolean displayMainQuery = GetterUtil.getBoolean(portletPreferences.getValue("displayMainQuery", null));
boolean displayModifiedRangeFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayModifiedRangeFacet", null), true);
boolean displayOpenSearchResults = GetterUtil.getBoolean(portletPreferences.getValue("displayOpenSearchResults", null));

boolean displayResultsInDocumentForm = GetterUtil.getBoolean(portletPreferences.getValue("displayResultsInDocumentForm", null));

if (!permissionChecker.isCompanyAdmin()) {
	displayResultsInDocumentForm = false;
}

boolean displayScopeFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayScopeFacet", null), true);
boolean displayUserFacet = GetterUtil.getBoolean(portletPreferences.getValue("displayUserFacet", null), true);
boolean includeSystemPortlets = false;
boolean queryIndexingEnabled = GetterUtil.getBoolean(portletPreferences.getValue("queryIndexingEnabled", null), PropsValues.INDEX_SEARCH_QUERY_INDEXING_ENABLED);

int queryIndexingThreshold = GetterUtil.getInteger(portletPreferences.getValue("queryIndexingThreshold", null), PropsValues.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD);

if (queryIndexingThreshold < 0) {
	queryIndexingThreshold = PropsValues.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD;
}

int querySuggestionsDisplayThreshold = GetterUtil.getInteger(portletPreferences.getValue("querySuggestionsDisplayThreshold", null), PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD);

if (querySuggestionsDisplayThreshold < 0) {
	querySuggestionsDisplayThreshold = PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD;
}

boolean querySuggestionsEnabled = GetterUtil.getBoolean(portletPreferences.getValue("querySuggestionsEnabled", null), PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_ENABLED);

int querySuggestionsMax = GetterUtil.getInteger(portletPreferences.getValue("querySuggestionsMax", null), PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_MAX);

if (querySuggestionsMax <= 0) {
	querySuggestionsMax = PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_MAX;
}

String searchConfiguration = portletPreferences.getValue("searchConfiguration", StringPool.BLANK);

if (!advancedConfiguration && Validator.isNull(searchConfiguration)) {
	searchConfiguration = ContentUtil.get(PropsValues.SEARCH_FACET_CONFIGURATION);
}

boolean viewInContext = GetterUtil.getBoolean(portletPreferences.getValue("viewInContext", null), true);
%>

<%@ include file="/html/portlet/search/init-ext.jsp" %>

<%!
private String _buildAssetCategoryPath(AssetCategory assetCategory, Locale locale) throws Exception {
	List<AssetCategory> assetCategories = assetCategory.getAncestors();

	if (assetCategories.isEmpty()) {
		return HtmlUtil.escape(assetCategory.getTitle(locale));
	}

	Collections.reverse(assetCategories);

	StringBundler sb = new StringBundler(assetCategories.size() * 2 + 1);

	for (AssetCategory curAssetCategory : assetCategories) {
		sb.append(HtmlUtil.escape(curAssetCategory.getTitle(locale)));
		sb.append(" &raquo; ");
	}

	sb.append(HtmlUtil.escape(assetCategory.getTitle(locale)));

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