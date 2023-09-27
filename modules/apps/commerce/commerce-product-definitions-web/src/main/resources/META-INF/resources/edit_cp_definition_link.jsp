<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionLinkDisplayContext cpDefinitionLinkDisplayContext = (CPDefinitionLinkDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionLink cpDefinitionLink = cpDefinitionLinkDisplayContext.getCPDefinitionLink();
long cpDefinitionLinkId = cpDefinitionLinkDisplayContext.getCPDefinitionLinkId();

CPDefinition cpDefinition = cpDefinitionLink.getCPDefinition();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if (cpDefinitionLink.getExpirationDate() != null) {
	neverExpire = false;
}
%>

<liferay-frontend:side-panel-content
	title="<%= HtmlUtil.escape(cpDefinition.getName(languageId)) %>"
>
	<portlet:actionURL name="/cp_definitions/edit_cp_definition_link" var="editCPDefinitionLinkActionURL" />

	<aui:form action="<%= editCPDefinitionLinkActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
		<aui:input name="cpDefinitionLinkId" type="hidden" value="<%= cpDefinitionLinkId %>" />
		<aui:input name="type" type="hidden" value="<%= cpDefinitionLink.getType() %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<aui:model-context bean="<%= cpDefinitionLink %>" model="<%= CPDefinitionLink.class %>" />

		<aui:input name="priority" />

		<liferay-ui:error exception="<%= CPDefinitionLinkExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />

		<aui:input formName="fm" name="displayDate" />

		<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />

		<c:if test="<%= cpDefinitionLinkDisplayContext.hasCustomAttributesAvailable() %>">
			<liferay-expando:custom-attribute-list
				className="<%= CPDefinitionLink.class.getName() %>"
				classPK="<%= (cpDefinitionLink != null) ? cpDefinitionLink.getCPDefinitionLinkId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</c:if>

		<%
		boolean pending = false;

		if (cpDefinitionLink != null) {
			pending = cpDefinitionLink.isPending();
		}
		%>

		<c:if test="<%= pending %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
			</div>
		</c:if>

		<aui:button-row cssClass="product-definition-button-row">

			<%
			String saveButtonLabel = "save";

			if (cpDefinitionLink.isDraft() || cpDefinitionLink.isApproved() || cpDefinitionLink.isExpired() || cpDefinitionLink.isScheduled()) {
				saveButtonLabel = "save-as-draft";
			}

			String publishButtonLabel = "publish";

			if (cpDefinitionLinkDisplayContext.hasWorkflowDefinitionLink()) {
				publishButtonLabel = "submit-for-workflow";
			}
			%>

			<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

			<aui:button cssClass="btn-lg" type="cancel" />
		</aui:button-row>
	</aui:form>
</liferay-frontend:side-panel-content>

<aui:script use="aui-base,event-input">
	var publishButton = A.one('#<portlet:namespace />publishButton');

	publishButton.on('click', () => {
		var workflowActionInput = A.one('#<portlet:namespace />workflowAction');

		if (workflowActionInput) {
			workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
		}
	});
</aui:script>