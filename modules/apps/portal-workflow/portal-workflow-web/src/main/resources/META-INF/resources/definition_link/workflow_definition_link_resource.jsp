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

<%@ include file="/definition_link/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry = (WorkflowDefinitionLinkSearchEntry)row.getObject();

Map<String, String> resourceTooltips = (Map<String, String>)row.getParameter("resourceTooltips");

String className = workflowDefinitionLinkSearchEntry.getClassName();

String resource = workflowDefinitionLinkSearchEntry.getResource();
%>

<c:choose>
	<c:when test="<%= resourceTooltips.containsKey(className) %>">
		<span class="lfr-portal-tooltip text-truncate-inline" title="<%= resourceTooltips.get(className) %>">
			<span class="text-truncate">
				<%= resource %>
			</span>
		</span>
	</c:when>
	<c:otherwise>
		<span class="text-truncate">
			<%= resource %>
		</span>
	</c:otherwise>
</c:choose>