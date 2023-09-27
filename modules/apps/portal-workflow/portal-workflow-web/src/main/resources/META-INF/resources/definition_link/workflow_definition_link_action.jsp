<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition_link/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

String randomNamespace = (String)row.getParameter("randomNamespace");
%>

<div class="btn-group btn-group-nowrap d-none" id="<%= randomNamespace %>saveCancelGroup">
	<div class="btn-group-item">
		<button class="btn btn-primary btn-sm" id="<%= randomNamespace %>saveButton" type="button")><liferay-ui:message key="save" /></button>
	</div>

	<div class="btn-group-item">
		<button class="btn btn-secondary btn-sm" id="<%= randomNamespace %>cancelButton" type="button"><liferay-ui:message key="cancel" /></button>
	</div>
</div>

<button class="btn btn-secondary btn-sm" id="<%= randomNamespace %>editButton" type="button"><liferay-ui:message key="edit" /></button>

<aui:script use="liferay-workflow-web">
	var saveWorkflowDefinitionLink = A.rbind(
		'saveWorkflowDefinitionLink',
		Liferay.WorkflowWeb,
		'<%= randomNamespace %>'
	);

	Liferay.delegateClick(
		'<%= randomNamespace %>saveButton',
		saveWorkflowDefinitionLink
	);

	var toggleDefinitionLinkEditionMode = A.rbind(
		'toggleDefinitionLinkEditionMode',
		Liferay.WorkflowWeb,
		'<%= randomNamespace %>'
	);

	Liferay.delegateClick(
		'<%= randomNamespace %>editButton',
		toggleDefinitionLinkEditionMode
	);

	Liferay.delegateClick(
		'<%= randomNamespace %>cancelButton',
		toggleDefinitionLinkEditionMode
	);
</aui:script>