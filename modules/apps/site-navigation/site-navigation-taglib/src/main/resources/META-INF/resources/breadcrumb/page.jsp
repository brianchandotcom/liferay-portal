<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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