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

<%@ include file="/bookmarks/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("categoryId", StringPool.BLANK);
portletURL.setParameter("tag", StringPool.BLANK);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		portletURL.setParameter("navigation", "home");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="folders" selected='<%= navigation.equals("home") %>' />

		<%
		portletURL.setParameter("navigation", "recent");
		%>

		<aui:nav-item href="<%= portletURL.toString() %>" label="recent" selected='<%= navigation.equals("recent") %>' />

		<c:if test="<%= themeDisplay.isSignedIn() %>">

			<%
			portletURL.setParameter("navigation", "mine");
			%>

			<aui:nav-item href="<%= portletURL.toString() %>" label="mine" selected='<%= navigation.equals("mine") %>' />
		</c:if>
	</aui:nav>

	<c:if test="<%= bookmarksGroupServiceOverriddenConfiguration.showFoldersSearch() %>">
		<aui:nav-bar-search>
			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="mvcPath" value="/bookmarks/search.jsp" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
				<aui:input name="searchFolderId" type="hidden" value="<%= folderId %>" />

				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>