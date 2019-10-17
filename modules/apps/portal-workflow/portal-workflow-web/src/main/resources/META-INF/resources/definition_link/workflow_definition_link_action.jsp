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

String randomNamespace = (String)row.getParameter("randomNamespace");
%>

<div class="btn-group btn-group-nowrap" hidden="true" id="<%= randomNamespace %>saveCancelGroup">
	<div class="btn-group-item">
		<button class="btn btn-primary btn-sm" id="<%= randomNamespace %>saveButton" type="button")><%= LanguageUtil.get(request, "save") %></button>
	</div>

	<div class="btn-group-item">
		<button class="btn btn-secondary btn-sm" id="<%= randomNamespace %>cancelButton" type="button"><%= LanguageUtil.get(request, "cancel") %></button>
	</div>
</div>

<button class="btn btn-secondary btn-sm" id="<%= randomNamespace %>editButton" type="button"><%= LanguageUtil.get(request, "edit") %></button>

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