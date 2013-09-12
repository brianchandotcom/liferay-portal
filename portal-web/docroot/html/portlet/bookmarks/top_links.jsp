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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("categoryId", StringPool.BLANK);
portletURL.setParameter("tag", StringPool.BLANK);
%>

<aui:nav-bar>
	<aui:nav>

		<%
		String label = "home";
		boolean selected = topLink.equals(label);

		portletURL.setParameter("topLink", label);
		%>

		<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

		<%
		label = "recent";
		selected = topLink.equals(label);

		portletURL.setParameter("topLink", label);
		%>

		<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />

		<c:if test="<%= themeDisplay.isSignedIn() %>">

			<%
			label = "mine";
			selected = topLink.equals(label);

			portletURL.setParameter("topLink", label);
			%>

			<aui:nav-item cssClass='<%= selected ? "active" : StringPool.BLANK %>' href="<%= portletURL.toString() %>" label="<%= label %>" selected="<%= selected %>" />
		</c:if>
	</aui:nav>

	<c:if test="<%= showFoldersSearch %>">
		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="struts_action" value="/bookmarks/search" />
		</liferay-portlet:renderURL>

		<aui:form action="<%= searchURL %>" method="get" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= folderId %>" />
			<aui:input name="searchFolderIds" type="hidden" value="<%= folderId %>" />

			<aui:nav-bar-search cssClass="pull-right">
				<div class="form-search">
					<liferay-ui:input-search autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" id="keywords1" name="keywords" placeholder='<%= LanguageUtil.get(locale, "keywords") %>' />
				</div>
			</aui:nav-bar-search>
		</aui:form>
	</c:if>
</aui:nav-bar>

<c:if test="<%= layout.isTypeControlPanel() %>">
	<div id="breadcrumb">
		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showCurrentPortlet="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</div>
</c:if>