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
BookmarksEntry entry = (BookmarksEntry)request.getAttribute(BookmarksWebKeys.BOOKMARKS_ENTRY);

int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_ANY);
%>

<aui:a href='<%= themeDisplay.getPathMain() + "/bookmarks/open_entry?entryId=" + entry.getEntryId() + ((status != WorkflowConstants.STATUS_ANY) ? "&status=" + status : StringPool.BLANK) %>' target="_blank"><%= HtmlUtil.escape(entry.getName()) %> (<%= HtmlUtil.escape(entry.getUrl()) %>)</aui:a>

<p class="asset-description"><%= HtmlUtil.escape(entry.getDescription()) %></p>

<liferay-expando:custom-attributes-available
	className="<%= BookmarksEntry.class.getName() %>"
>
	<liferay-expando:custom-attribute-list
		className="<%= BookmarksEntry.class.getName() %>"
		classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
		editable="<%= false %>"
		label="<%= true %>"
	/>
</liferay-expando:custom-attributes-available>