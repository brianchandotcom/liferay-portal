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

<%@ include file="/blogs_admin/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "entries");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/blogs/view");
portletURL.setParameter("navigation", navigation);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewEntriesURL" />

		<aui:nav-item
			href="<%= viewEntriesURL %>"
			label="entries"
			selected='<%= navigation.equals("entries") %>'
		/>

		<portlet:renderURL var="viewImagesURL">
			<portlet:param name="navigation" value="images" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewImagesURL %>"
			label="images"
			selected='<%= navigation.equals("images") %>'
		/>
	</aui:nav>

	<aui:form action="<%= portletURL.toString() %>" name="searchFm">
		<aui:nav-bar-search>
			<liferay-ui:input-search markupView="lexicon" />
		</aui:nav-bar-search>
	</aui:form>
</aui:nav-bar>

<c:choose>
	<c:when test='<%= navigation.equals("entries") %>'>
		<liferay-util:include page="/blogs_admin/view_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/blogs_admin/view_images.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<c:if test='<%= navigation.equals("entries") && BlogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) %>'>
	<portlet:renderURL var="viewEntriesURL">
		<portlet:param name="mvcRenderCommandName" value="/blogs/view" />
	</portlet:renderURL>

	<portlet:renderURL var="addEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/blogs/edit_entry" />
		<portlet:param name="redirect" value="<%= viewEntriesURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-blog-entry") %>' url="<%= addEntryURL %>" />
	</liferay-frontend:add-menu>
</c:if>