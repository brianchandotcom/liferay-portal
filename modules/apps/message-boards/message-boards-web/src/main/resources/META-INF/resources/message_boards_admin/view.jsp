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

<%@ include file="/message_boards/init.jsp" %>

<%
MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view");
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

request.setAttribute("view.jsp-categoryId", categoryId);
request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());
request.setAttribute("view.jsp-portletURL", portletURL);
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<liferay-util:include page="/message_boards_admin/nav.jsp" servletContext="<%= application %>">
		<liferay-util:param name="navItemSelected" value="message-boards-home" />
	</liferay-util:include>

	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="mvcRenderCommandName" value="/message_boards/search" />
	</liferay-portlet:renderURL>

	<aui:nav-bar-search>
		<aui:form action="<%= searchURL %>" name="searchFm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
			<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-util:include page="/message_boards_admin/view_entries.jsp" servletContext="<%= application %>" />