<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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