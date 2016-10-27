<%--
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
	portletDisplay.setURLBack(backURL.toString());

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
	<portlet:param name="mvcPath" value='<%= templatePath + "compare_versions.jsp" %>' />
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="<%= Constants.CMD %>" value="compareVersions" />
	<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:resourceURL id="compareVersions" varImpl="resourceURL">
	<portlet:param name="backURL" value="<%= backURL %>" />
	<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
</liferay-portlet:resourceURL>

<div class="container-fluid-1280">
	<liferay-frontend:diff-version-comparator
		diffHtmlResults="<%= diffHtmlResults %>"
		diffVersionsInfo="<%= AdminUtil.getDiffVersionsInfo(scopeGroupId, kbArticle.getResourcePrimKey(), GetterUtil.getInteger(sourceVersion), GetterUtil.getInteger(targetVersion)) %>"
		portletURL="<%= portletURL %>"
		resourceURL="<%= resourceURL %>"
		sourceVersion="<%= sourceVersion %>"
		targetVersion="<%= targetVersion %>"
	/>
</div>