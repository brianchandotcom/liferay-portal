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
BlogsEntry entry = (BlogsEntry)request.getAttribute("view_entry_content.jsp-entry");

String socialBookmarksDisplayStyle = blogsPortletInstanceConfiguration.socialBookmarksDisplayStyle();
%>

<portlet:renderURL var="bookmarkURL" windowState="<%= WindowState.NORMAL.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/blogs/view_entry" />

	<c:choose>
		<c:when test="<%= Validator.isNotNull(entry.getUrlTitle()) %>">
			<portlet:param name="urlTitle" value="<%= entry.getUrlTitle() %>" />
		</c:when>
		<c:otherwise>
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</c:otherwise>
	</c:choose>
</portlet:renderURL>

<div class="<%= socialBookmarksDisplayStyle.equals("vertical") ? "pull-right" : StringPool.BLANK %> social-bookmarks">
	<liferay-ui:social-bookmarks
		contentId="<%= String.valueOf(entry.getEntryId()) %>"
		displayStyle="<%= blogsPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
		target="_blank"
		title="<%= BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) %>"
		types="<%= blogsPortletInstanceConfiguration.socialBookmarksTypes() %>"
		url="<%= PortalUtil.getCanonicalURL(bookmarkURL.toString(), themeDisplay, layout) %>"
	/>
</div>