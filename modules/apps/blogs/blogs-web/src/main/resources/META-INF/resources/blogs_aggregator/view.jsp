<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
SearchContainer<BlogsEntry> searchContainer = blogsAggregatorViewDisplayContext.getSearchContainer();

List<BlogsEntry> results = searchContainer.getResults();
%>

<%@ include file="/blogs_aggregator/view_entries.jspf" %>