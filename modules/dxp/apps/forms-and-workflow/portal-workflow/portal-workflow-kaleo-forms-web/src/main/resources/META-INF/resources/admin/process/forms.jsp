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

<%@ include file="/admin/init.jsp" %>

<%
KaleoProcess kaleoProcess = (KaleoProcess)request.getAttribute(KaleoFormsWebKeys.KALEO_PROCESS);

long kaleoProcessId = BeanParamUtil.getLong(kaleoProcess, request, "kaleoProcessId");

long ddmStructureId = KaleoFormsUtil.getKaleoProcessDDMStructureId(kaleoProcess, portletSession);

String workflowDefinition = KaleoFormsUtil.getWorkflowDefinition(kaleoProcess, portletSession);
%>

<h3 class="kaleo-process-header"><liferay-ui:message key="forms" /></h3>

<p class="kaleo-process-message"><liferay-ui:message key="please-select-or-create-one-form-for-each-workflow-task.-each-form-is-a-subset-of-the-field-set-defined-in-step-2" /></p>

<aui:field-wrapper>

	<%
	KaleoTaskFormPairs kaleoTaskFormPairs = KaleoFormsUtil.getKaleoTaskFormPairs(company.getCompanyId(), kaleoProcessId, ddmStructureId, workflowDefinition, portletSession);
	%>

	<aui:input name="kaleoTaskFormPairsData" type="hidden" value="<%= kaleoTaskFormPairs.toString() %>" />
</aui:field-wrapper>

<portlet:renderURL var="backURL">
	<portlet:param name="mvcPath" value="/admin/edit_kaleo_process.jsp" />
	<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
	<portlet:param name="historyKey" value="forms" />
</portlet:renderURL>

<div id="<portlet:namespace />resultsContainer">
	<liferay-util:include page="/admin/process/task_template_search_container.jsp" servletContext="<%= application %>">
		<liferay-util:param name="backURL" value="<%= backURL %>" />
		<liferay-util:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
		<liferay-util:param name="workflowDefinition" value="<%= workflowDefinition %>" />
	</liferay-util:include>
</div>