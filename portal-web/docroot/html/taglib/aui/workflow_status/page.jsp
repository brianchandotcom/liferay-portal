<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/aui/workflow_status/init.jsp" %>

<div class="taglib-workflow-status">
	<c:if test="<%= Validator.isNotNull(id) %>">
		<span class="workflow-id"><liferay-ui:message key="id" />: <%= HtmlUtil.escape(id) %></span>
	</c:if>

	<c:if test="<%= Validator.isNotNull(version) %>">
		<span class="workflow-version"><liferay-ui:message key="version" />: <strong><%= version %></strong></span>
	</c:if>

	<%
	String additionalText = StringPool.BLANK;

	if (Validator.isNull(statusMessage)) {
		statusMessage = WorkflowConstants.toLabel(status);

		if (status == WorkflowConstants.STATUS_PENDING) {
			long companyId = BeanPropertiesUtil.getLong(bean, "companyId");
			long groupId = BeanPropertiesUtil.getLong(bean, "groupId");
			long classPK = BeanPropertiesUtil.getLong(bean, "primaryKey");

			StringBundler sb = new StringBundler(4);

			try {
				String workflowStatus = WorkflowInstanceLinkLocalServiceUtil.getState(companyId, groupId, model.getName(), classPK);

				sb.append(StringPool.SPACE);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(LanguageUtil.get(pageContext, workflowStatus));
				sb.append(StringPool.CLOSE_PARENTHESIS);

				additionalText = sb.toString();
			}
			catch (NoSuchWorkflowInstanceLinkException nswile) {
			}
		}
	}
	%>

	<span class='<%= showIcon ? "workflow-status workflow-status-icon" : "workflow-status" %>'>
		<c:if test="<%= showLabel %>">
			<liferay-ui:message key="status" />:
		</c:if>

		<strong class="<%= _getStatusCssClass(status) %>">
			<liferay-ui:message key="<%= statusMessage %>" /><%= additionalText %>
		</strong>
	</span>

	<c:if test="<%= Validator.isNotNull(helpMessage) %>">
		<liferay-ui:icon-help message="<%= helpMessage %>" />
	</c:if>
</div>

<%!
private String _getStatusCssClass(int status) {
	String statusLabel = WorkflowConstants.toLabel(status);

	String labelCssClass = "label workflow-status-" + statusLabel;

	if (status == WorkflowConstants.STATUS_APPROVED) {
		labelCssClass += " label-success";
	}
	else if (status == WorkflowConstants.STATUS_DRAFT) {
		labelCssClass += " label-info";
	}
	else if (status == WorkflowConstants.STATUS_EXPIRED) {
		labelCssClass += " label-important";
	}
	else if (status == WorkflowConstants.STATUS_PENDING) {
		labelCssClass += " label-warning";
	}

	return labelCssClass;
}
%>