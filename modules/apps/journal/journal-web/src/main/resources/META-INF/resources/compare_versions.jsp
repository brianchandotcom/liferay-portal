<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId");
String articleId = ParamUtil.getString(request, "articleId");

Set<Locale> availableLocales = (Set<Locale>)request.getAttribute(WebKeys.AVAILABLE_LOCALES);
String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
String languageId = (String)request.getAttribute(WebKeys.LANGUAGE_ID);
double sourceVersion = (Double)request.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)request.getAttribute(WebKeys.TARGET_VERSION);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
portletDisplay.setURLBackTitle(portletDisplay.getPortletDisplayName());

renderResponse.setTitle(LanguageUtil.get(request, "compare-versions"));
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcRenderCommandName" value="/journal/compare_versions" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
</liferay-portlet:renderURL>

<liferay-portlet:resourceURL id="/journal/compare_versions" varImpl="resourceURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
</liferay-portlet:resourceURL>

<clay:container-fluid>
	<liferay-frontend:diff-version-comparator
		availableLocales="<%= availableLocales %>"
		diffHtmlResults="<%= diffHtmlResults %>"
		diffVersionsInfo="<%= JournalUtil.getDiffVersionsInfo(groupId, articleId, sourceVersion, targetVersion) %>"
		languageId="<%= languageId %>"
		portletURL="<%= portletURL %>"
		resourceURL="<%= resourceURL %>"
		sourceVersion="<%= sourceVersion %>"
		targetVersion="<%= targetVersion %>"
	/>
</clay:container-fluid>