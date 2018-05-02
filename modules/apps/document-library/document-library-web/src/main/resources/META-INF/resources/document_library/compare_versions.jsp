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

<%@ include file="/document_library/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

String sourceName = (String)renderRequest.getAttribute(WebKeys.SOURCE_NAME);
String targetName = (String)renderRequest.getAttribute(WebKeys.TARGET_NAME);
List[] diffResults = (List[])renderRequest.getAttribute(WebKeys.DIFF_RESULTS);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

String headerTitle = LanguageUtil.get(resourceBundle, "compare-versions");

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	renderResponse.setTitle(headerTitle);
}
%>

<c:if test="<%= !portletTitleBasedNavigation %>">
	<liferay-ui:header
		backURL="<%= backURL %>"
		title="<%= headerTitle %>"
	/>
</c:if>

<liferay-frontend:diff
	diffResults="<%= diffResults %>"
	sourceName="<%= sourceName %>"
	targetName="<%= targetName %>"
/>