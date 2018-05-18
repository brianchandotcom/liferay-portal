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
CategoryCPAttachmentFileEntriesDisplayContext categoryCPAttachmentFileEntriesDisplayContext = (CategoryCPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAttachmentFileEntry cpAttachmentFileEntry = categoryCPAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

long categoryId = ParamUtil.getLong(request, "categoryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((cpAttachmentFileEntry == null) ? LanguageUtil.get(request, "add-image") : cpAttachmentFileEntry.getTitle(languageId));
%>

<portlet:actionURL name="editAssetCategoryCPAttachmentFileEntry" var="editAssetCategoryCPAttachmentFileEntryActionURL" />

<aui:form action="<%= editAssetCategoryCPAttachmentFileEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpAttachmentFileEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="categoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="cpAttachmentFileEntryId" type="hidden" value="<%= (cpAttachmentFileEntry == null) ? 0 : cpAttachmentFileEntry.getCPAttachmentFileEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= CPAttachmentFileEntryConstants.TYPE_IMAGE %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			formModelBean="<%= cpAttachmentFileEntry %>"
			id="<%= CategoryCPAttachmentFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_CP_ATTACHMENT_FILE_ENTRY %>"
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

<aui:script use="aui-base,event-input">
	A.one('#<portlet:namespace />publishButton').on(
		'click',
		function() {
			var workflowActionInput = A.one('#<portlet:namespace />workflowAction');

			if (workflowActionInput) {
				workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
			}
		}
	);
</aui:script>