<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/blogs/init.jsp" %>

<%
BlogsViewDisplayContext blogsViewDisplayContext = new BlogsViewDisplayContext(request, renderRequest, renderResponse);

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = blogsViewDisplayContext.getBlogsPortletInstanceConfiguration();

SearchContainer<?> searchContainer = blogsViewDisplayContext.getSearchContainer();
%>

<portlet:actionURL name="/blogs/edit_entry" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

<c:if test="<%= blogsViewDisplayContext.getUnpublishedEntriesCount() > 0 %>">
	<clay:navigation-bar
		navigationItems="<%= blogsViewDisplayContext.getNavigationItems() %>"
	/>
</c:if>

<%@ include file="/blogs/view_entries.jspf" %>