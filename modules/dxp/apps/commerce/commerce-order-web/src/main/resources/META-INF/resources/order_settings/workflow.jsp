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
CommerceOrderSettingsDisplayContext commerceOrderSettingsDisplayContext = (CommerceOrderSettingsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<WorkflowDefinition> workflowDefinitions = commerceOrderSettingsDisplayContext.getActiveWorkflowDefinitions();
%>

<aui:fieldset>

	<%
	long typePK = CommerceOrderConstants.TYPE_PK_APPROVAL;
	String typePrefix = "approval";
	%>

	<%@ include file="/order_settings/workflow_definition.jspf" %>

	<%
	typePK = CommerceOrderConstants.TYPE_PK_TRANSMISSION;
	typePrefix = "transmission";
	%>

	<%@ include file="/order_settings/workflow_definition.jspf" %>
</aui:fieldset>