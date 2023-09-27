<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);

double sourceVersion = ParamUtil.getDouble(request, "sourceVersion");
double targetVersion = ParamUtil.getDouble(request, "targetVersion");

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
	portletDisplay.setURLBackTitle(kbArticle.getTitle());

	renderResponse.setTitle(LanguageUtil.get(resourceBundle, "compare-versions"));
}
%>

<c:if test="<%= !portletTitleBasedNavigation %>">
	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= false %>"
		title="<%= kbArticle.getTitle() %>"
	/>
</c:if>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcPath" value="/admin/common/compare_versions.jsp" />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="<%= Constants.CMD %>" value="compareVersions" />
	<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:resourceURL id="compareVersions" varImpl="resourceURL">
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
</liferay-portlet:resourceURL>

<clay:container-fluid>
	<liferay-frontend:diff-version-comparator
		diffHtmlResults="<%= diffHtmlResults %>"
		diffVersionsInfo="<%= AdminUtil.getDiffVersionsInfo(scopeGroupId, kbArticle.getResourcePrimKey(), GetterUtil.getInteger(sourceVersion), GetterUtil.getInteger(targetVersion)) %>"
		portletURL="<%= portletURL %>"
		resourceURL="<%= resourceURL %>"
		sourceVersion="<%= sourceVersion %>"
		targetVersion="<%= targetVersion %>"
	/>
</clay:container-fluid>