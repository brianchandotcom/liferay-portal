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

<%@ include file="/init.jsp" %>

<%
List<BreadcrumbEntry> breadcrumbEntries = (List<BreadcrumbEntry>)request.getAttribute("liferay-site-navigation:breadcrumb:breadcrumbEntries");
%>

<ol class="breadcrumb">

	<%
	for (int i = 0; i < breadcrumbEntries.size(); i++) {
		BreadcrumbEntry breadcrumbEntry = breadcrumbEntries.get(i);
	%>

		<c:choose>
			<c:when test="<%= (i < (breadcrumbEntries.size() - 1)) && Validator.isNotNull(breadcrumbEntry.getURL()) %>">
				<li class="breadcrumb-item">
					<a class="breadcrumb-link" href="<%= breadcrumbEntry.getURL() %>">
						<span class="breadcrumb-text-truncate"><%= HtmlUtil.escape(breadcrumbEntry.getTitle()) %></span>
					</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="active breadcrumb-item">
					<span class="breadcrumb-text-truncate"><%= HtmlUtil.escape(breadcrumbEntry.getTitle()) %></span>
				</li>
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

</ol>