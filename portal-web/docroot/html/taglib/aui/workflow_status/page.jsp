<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/aui/workflow_status/init.jsp" %>

<span class="taglib-workflow-status">
	<c:if test="<%= Validator.isNotNull(id) %>">
		<span class="mr-2 workflow-id">
			<span class="workflow-label"><liferay-ui:message key="id" />:</span>

			<span class="workflow-value"><%= HtmlUtil.escape(id) %></span>
		</span>
	</c:if>

	<c:if test="<%= Validator.isNotNull(version) %>">
		<span class="mr-2 workflow-version">
			<span class="workflow-label"><liferay-ui:message key="version" />:</span>

			<span class="workflow-value"><%= version %></span>
		</span>
	</c:if>

	<span class="workflow-status">
		<c:if test="<%= showLabel %>">
			<span class="workflow-label"><liferay-ui:message key="status" />:</span>
		</c:if>

		<span class="label label-<%= WorkflowConstants.getStatusStyle(status) %> status workflow-status-<%= WorkflowConstants.getStatusLabel(status) %> <%= WorkflowConstants.getStatusCssClass(status) %> workflow-value">
			<liferay-ui:message key="<%= statusMessage %>" /><%= additionalText %>
		</span>
	</span>

	<c:if test="<%= showHelpMessage && Validator.isNotNull(helpMessage) %>">
		<liferay-ui:icon-help message="<%= helpMessage %>" />
	</c:if>
</span>