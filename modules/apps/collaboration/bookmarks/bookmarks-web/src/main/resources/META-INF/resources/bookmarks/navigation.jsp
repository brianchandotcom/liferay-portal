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
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="bookmarks" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= bookmarksGroupServiceOverriddenConfiguration.showFoldersSearch() %>">
		<aui:nav-bar-search>
			<liferay-portlet:renderURL varImpl="searchURL">
				<portlet:param name="mvcRenderCommandName" value="/bookmarks/view" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>