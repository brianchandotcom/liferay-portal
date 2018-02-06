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

<%@ include file="/com.liferay.blogs.analytics/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

String bodyId = (String)request.getAttribute("bodyId");
%>

<aui:script require="blogs-analytics/js/trackBlogEntry.es as trackBlogEntry">
	trackBlogEntry.default('<%= entry.getEntryId() %>', '#<%= bodyId %>');
</aui:script>