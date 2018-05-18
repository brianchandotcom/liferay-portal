<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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