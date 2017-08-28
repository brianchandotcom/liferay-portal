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

<c:choose>
	<c:when test="<%= workflowDefinitionTabVisible && tab.equals(WorkflowWebKeys.WORKFLOW_TAB_DEFINITION) %>">
		<liferay-util:include page="/definition/view.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test="<%= workflowDefinitionLinkTabVisible && tab.equals(WorkflowWebKeys.WORKFLOW_TAB_DEFINITION_LINK) %>">
		<liferay-util:include page="/definition_link/view.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test="<%= workflowInstanceTabVisible && tab.equals(WorkflowWebKeys.WORKFLOW_TAB_INSTANCE) %>">
		<liferay-util:include page="/instance/view.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>