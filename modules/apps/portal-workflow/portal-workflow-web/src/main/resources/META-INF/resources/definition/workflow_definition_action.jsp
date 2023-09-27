<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition/init.jsp" %>

<%
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowDefinition workflowDefinition = (WorkflowDefinition)row.getObject();

String duplicateTitle = workflowDefinitionDisplayContext.getDuplicateTitle(workflowDefinition);
%>

<liferay-portlet:actionURL name="/portal_workflow/duplicate_workflow_definition" var="duplicateWorkflowDefinition">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:actionURL>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="viewURL">
		<portlet:param name="mvcPath" value="/definition/view_workflow_definition.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view"
		url="<%= viewURL %>"
	/>

	<portlet:renderURL var="editURL">
		<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<c:if test="<%= workflowDefinitionDisplayContext.canPublishWorkflowDefinition() %>">
		<liferay-ui:icon
			id='<%= "duplicate" + HtmlUtil.getAUICompatibleId(workflowDefinition.getName()) %>'
			message="duplicate"
			url="javascript:void(0);"
		/>
	</c:if>

	<c:choose>
		<c:when test="<%= workflowDefinition.isActive() %>">
			<liferay-portlet:actionURL name="/portal_workflow/deactivate_workflow_definition" var="deactivateWorkflowDefinitionURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
				<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				message="unpublish"
				url="<%= deactivateWorkflowDefinitionURL %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-portlet:actionURL name="/portal_workflow/delete_workflow_definition" var="deleteWorkflowDefinitionURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
				<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				message="delete"
				onClick='<%= liferayPortletResponse.getNamespace() + "confirmDeleteDefinition('" + deleteWorkflowDefinitionURL + "'); return false;" %>'
				url="<%= deleteWorkflowDefinitionURL %>"
			/>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>

<div class="hide" id="<%= randomNamespace %>titleInputLocalized">
	<aui:form name='<%= randomNamespace + "form" %>'>
		<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="name" type="hidden" value="<%= PortalUUIDUtil.generate() %>" />
		<aui:input name="content" type="hidden" value="<%= workflowDefinition.getContent() %>" />
		<aui:input name="defaultDuplicationTitle" type="hidden" value="<%= duplicateTitle %>" />
		<aui:input name="duplicatedDefinitionName" type="hidden" value="<%= workflowDefinition.getName() %>" />
		<aui:input name="duplicatedDefinitionTitle" type="hidden" value="<%= workflowDefinition.getTitle(LanguageUtil.getLanguageId(request)) %>" />

		<aui:fieldset>
			<clay:col
				size="12"
			>
				<aui:field-wrapper label="title">
					<liferay-ui:input-localized
						name='<%= randomNamespace + "title" %>'
						xml="<%= duplicateTitle %>"
					/>
				</aui:field-wrapper>
			</clay:col>

			<clay:col
				size="12"
			>
				<liferay-ui:message key="copy-does-not-include-revisions" />
			</clay:col>
		</aui:fieldset>
	</aui:form>
</div>

<aui:script use="liferay-workflow-web">
	var title = '<liferay-ui:message key="duplicate-workflow" />';

	var confirmBeforeDuplicateDialog = A.rbind(
		'confirmBeforeDuplicateDialog',
		Liferay.WorkflowWeb,
		'<%= duplicateWorkflowDefinition %>',
		title,
		'<%= randomNamespace %>',
		'<portlet:namespace />'
	);

	Liferay.delegateClick(
		'<portlet:namespace />duplicate<%= HtmlUtil.getAUICompatibleId(workflowDefinition.getName()) %>',
		confirmBeforeDuplicateDialog
	);
</aui:script>