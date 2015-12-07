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
DDLRecordSet recordSet = ddlFormAdminDisplayContext.getRecordSet();

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");

long groupId = BeanParamUtil.getLong(recordSet, request, "groupId", scopeGroupId);
String redirectURL = GetterUtil.getString(recordSet.getSettingsProperty("redirectURL", StringPool.BLANK));
boolean requireCaptcha = GetterUtil.getBoolean(recordSet.getSettingsProperty("requireCaptcha", Boolean.FALSE.toString()));
%>

<portlet:actionURL name="updateRecordSetSettings" var="updateRecordSetSettingsURL">
	<portlet:param name="mvcPath" value="/admin/record_set_settings.jsp" />
</portlet:actionURL>

<liferay-ui:error exception="<%= RecordSetSettingsException.MustEnterValidEmailAddress.class %>" message="please-enter-a-valid-email-address" />
<liferay-ui:error exception="<%= RecordSetSettingsException.MustEnterValidURL.class %>" message="please-enter-a-valid-redirect-url" />
<liferay-ui:error exception="<%= RecordSetSettingsException.RequiredValue.class %>" message="please-enter-a-valid-from-address">

	<%
	RecordSetSettingsException.RequiredValue rv = (RecordSetSettingsException.RequiredValue)errorException;
	%>

	<liferay-ui:message arguments="<%= rv.getPropertyName() %>" key="no-value-defined-for-field-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<div class="container-fluid-1280">
	<aui:form action="<%= updateRecordSetSettingsURL %>" method="post" name="fm">
		<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />

		<aui:fieldset>
			<aui:input helpMessage="enable-email-notification-for-each-form-submission" label="send-email-notification" name="sendEmailNotification" type="checkbox" value="<%= DDLFormEmailNotificationUtil.isEmailNotificationEnabled(recordSet) %>" />

			<aui:input label="from-name" name="emailFromName" value="<%= DDLFormEmailNotificationUtil.getEmailFromName(recordSet) %>" />

			<aui:input label="from-address" name="emailFromAddress" value="<%= DDLFormEmailNotificationUtil.getEmailFromAddress(recordSet) %>" />

			<aui:input label="to-address" name="emailToAddress" value="<%= DDLFormEmailNotificationUtil.getEmailToAddress(recordSet) %>" />

			<aui:input label="subject" name="emailSubject" value="<%= DDLFormEmailNotificationUtil.getEmailSubject(recordSet) %>" />
		</aui:fieldset>

		<aui:fieldset>
			<aui:input label="redirect-url-on-success" name="redirectURL" value="<%= HtmlUtil.toInputSafe(redirectURL) %>" wrapperCssClass="lfr-input-text-container" />

			<aui:input name="requireCaptcha" type="checkbox" value="<%= requireCaptcha %>" />

			<c:if test="<%= ddlFormAdminDisplayContext.isDDLRecordWorkflowHandlerDeployed() %>">
				<aui:select label="workflow" name="workflowDefinition">

					<%
					WorkflowDefinitionLink workflowDefinitionLink = null;

					try {
						workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLink(company.getCompanyId(), themeDisplay.getScopeGroupId(), DDLRecordSet.class.getName(), recordSetId, 0, true);
					}
					catch (NoSuchWorkflowDefinitionLinkException nswdle) {
					}
					%>

					<aui:option><%= LanguageUtil.get(request, "no-workflow") %></aui:option>

					<%
					List<WorkflowDefinition> workflowDefinitions = WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

					for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
						boolean selected = false;

						if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
							selected = true;
						}
					%>

						<aui:option label='<%= HtmlUtil.escape(workflowDefinition.getName()) + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<%= selected %>" useModelValue="<%= false %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

					<%
					}
					%>

				</aui:select>
			</c:if>
		</aui:fieldset>

		<aui:button-row cssClass="ddl-form-builder-buttons">
			<aui:button cssClass="btn-lg" id="submit" label="save" primary="<%= true %>" type="submit" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script use="aui-base">
	var sendEmailNotificationCheckbox = A.one('#<portlet:namespace />sendEmailNotification');

	<portlet:namespace />toogleDisabledEmailNotificationFields();

	sendEmailNotificationCheckbox.on(
		'change',
		function(event) {

			<portlet:namespace />toogleDisabledEmailNotificationFields();
		}
	);

	function <portlet:namespace />toogleDisabledEmailNotificationFields() {
		var toggleDisabled = Liferay.Util.toggleDisabled;

		var checked = sendEmailNotificationCheckbox.get('checked');

		var disable = !checked;

		toggleDisabled(A.one('#<portlet:namespace />emailFromName'), disable);
		toggleDisabled(A.one('#<portlet:namespace />emailFromAddress'), disable);
		toggleDisabled(A.one('#<portlet:namespace />emailToAddress'), disable);
		toggleDisabled(A.one('#<portlet:namespace />emailSubject'), disable);
	}
</aui:script>