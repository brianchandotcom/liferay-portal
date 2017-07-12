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
String toolbarItem = ParamUtil.getString(request, "toolbarItem");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("mvcRenderCommandName", "editCPAttachmentFileEntry");

CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpAttachmentFileEntriesDisplayContext.getCPDefinition();

long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

long cpAttachmentFileEntryId = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntryId();

int type = CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE;
String addMenuTitle = LanguageUtil.get(request, "add-image");

if (toolbarItem.equals("view-product-definition-attachments")) {
	type = CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_OTHER;
	addMenuTitle = LanguageUtil.get(request, "add-attachment");
}

PortletURL productAttachmentsURL = renderResponse.createRenderURL();

productAttachmentsURL.setParameter("mvcRenderCommandName", "viewAttachmentFileEntries");
productAttachmentsURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
productAttachmentsURL.setParameter("type", String.valueOf(type));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(productAttachmentsURL.toString());

renderResponse.setTitle((cpDefinition == null) ? LanguageUtil.get(request, addMenuTitle) : cpDefinition.getTitle(languageId));
%>

<portlet:actionURL name="editCPAttachmentFileEntry" var="editProductDefinitionOptionRelActionURL" />

<div class="container-fluid-1280 entry-body">
	<aui:form action="<%= editProductDefinitionOptionRelActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveAttachmentFileEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpAttachmentFileEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= productAttachmentsURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />
		<aui:input name="cpAttachmentFileEntryId" type="hidden" value="<%= cpAttachmentFileEntryId %>" />
		<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
		<aui:input name="type" type="hidden" value="<%= type %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<c:if test="<%= (cpAttachmentFileEntry != null) && !cpAttachmentFileEntry.isNew() %>">
			<liferay-frontend:info-bar>
				<aui:workflow-status
					id="<%= String.valueOf(cpAttachmentFileEntryId) %>"
					markupView="lexicon"
					showHelpMessage="<%= false %>"
					showIcon="<%= false %>"
					showLabel="<%= false %>"
					status="<%= cpAttachmentFileEntry.getStatus() %>"
				/>
			</liferay-frontend:info-bar>
		</c:if>

		<div class="lfr-form-content">
			<liferay-ui:form-navigator
				backURL="<%= productAttachmentsURL.toString() %>"
				formModelBean="<%= cpAttachmentFileEntry %>"
				id="<%= CPAttachmentFileEntryFormNavigatorConstants.FORM_NAVIGATOR_ID_CP_ATTACHMENT_FILE_ENTRY %>"
				markupView="lexicon"
				showButtons="<%= false %>"
			/>
		</div>

		<aui:button-row cssClass="product-definition-button-row">

			<%
			boolean pending = false;

			if (cpAttachmentFileEntry != null) {
				pending = cpAttachmentFileEntry.isPending();
			}

			String saveButtonLabel = "save";

			if ((cpAttachmentFileEntry == null) || cpAttachmentFileEntry.isDraft() || cpAttachmentFileEntry.isApproved() || cpAttachmentFileEntry.isExpired() || cpAttachmentFileEntry.isScheduled()) {
				saveButtonLabel = "save-as-draft";
			}

			String publishButtonLabel = "publish";

			if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, CPAttachmentFileEntry.class.getName())) {
				publishButtonLabel = "submit-for-publication";
			}
			%>

			<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

			<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />saveAttachmentFileEntry(forceDisable) {
		var form = AUI.$(document.<portlet:namespace />fm);

		var cpDefinitionOptionsRenderDDMForm = Liferay.component("cpDefinitionOptionsRenderDDMForm");

		if (cpDefinitionOptionsRenderDDMForm) {
			form.fm('ddmFormValues').val(JSON.stringify(cpDefinitionOptionsRenderDDMForm.toJSON()));
		}

		submitForm(form);
	}
</aui:script>

<aui:script use="aui-base,event-input">
	var publishButton = A.one('#<portlet:namespace />publishButton');

	publishButton.on(
		'click',
		function() {
			var workflowActionInput = A.one('#<portlet:namespace />workflowAction');

			if (workflowActionInput) {
				workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
			}
		}
	);
</aui:script>