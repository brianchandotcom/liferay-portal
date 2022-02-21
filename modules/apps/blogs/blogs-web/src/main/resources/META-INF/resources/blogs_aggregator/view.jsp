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

<%@ include file="/blogs_aggregator/init.jsp" %>

<%
boolean blogsPortletFound = ParamUtil.getBoolean(request, "blogsPortletFound", true);
%>

<c:if test="<%= !blogsPortletFound %>">
	<clay:stripe
		displayType="danger"
		message="no-suitable-application-found-to-display-the-blogs-entry"
	/>
</c:if>

<%
BlogsAggregatorDisplayContext blogsAggregatorDisplayContext = new BlogsAggregatorDisplayContext(request, renderRequest, renderResponse);

SearchContainer<BlogsEntry> searchContainer = blogsAggregatorDisplayContext.getSearchContainer();

List<BlogsEntry> results = searchContainer.getResults();
%>

<%@ include file="/blogs_aggregator/view_entries.jspf" %>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(
			document.<portlet:namespace />fm1.<portlet:namespace />keywords
		);
	</aui:script>
</c:if>