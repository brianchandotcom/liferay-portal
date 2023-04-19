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

<%@ include file="/blogs/init.jsp" %>

<%
BlogsViewDisplayContext blogsViewDisplayContext = new BlogsViewDisplayContext(request, renderRequest, renderResponse);

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = blogsViewDisplayContext.getBlogsPortletInstanceConfiguration();

SearchContainer<?> searchContainer = blogsViewDisplayContext.getSearchContainer();
%>

<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />
<liferay-ui:success key="blogsEntryPublished" message="the-blog-entry-was-published-successfully" />

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