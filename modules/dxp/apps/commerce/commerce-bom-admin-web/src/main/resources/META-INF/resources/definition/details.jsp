<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceBOMAdminDisplayContext commerceBOMAdminDisplayContext = (CommerceBOMAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceBOMDefinition commerceBOMDefinition = commerceBOMAdminDisplayContext.getCommerceBOMDefinition();

CPAttachmentFileEntry cpAttachmentFileEntry = commerceBOMAdminDisplayContext.getCPAttachmentFileEntry();
%>

<portlet:actionURL name="editCommerceBOMDefinition" var="editCommerceBOMDefinitionActionURL" />

<div class="container-fluid container-fluid-max-xl entry-body">
	<aui:form action="<%= editCommerceBOMDefinitionActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceBOMDefinition == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="commerceBOMDefinitionId" type="hidden" value="<%= commerceBOMAdminDisplayContext.getCommerceBOMDefinitionId() %>" />
		<aui:input name="commerceBOMFolderId" type="hidden" value="<%= commerceBOMAdminDisplayContext.getCommerceBOMFolderId() %>" />

		<aui:model-context bean="<%= commerceBOMDefinition %>" model="<%= CommerceBOMDefinition.class %>" />

		<liferay-ui:error exception="<%= DuplicateCPAttachmentFileEntryException.class %>" message="that-attachment-is-already-in-use" />
		<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="please-select-an-existing-file" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<portlet:actionURL name="uploadTempAttachment" var="uploadCoverImageURL" />

				<div class="lfr-attachment-cover-image-selector">
					<liferay-item-selector:image-selector
						draggableImage="vertical"
						fileEntryId='<%= BeanParamUtil.getLong(cpAttachmentFileEntry, request, "fileEntryId") %>'
						itemSelectorEventName="addCPAttachmentFileEntry"
						itemSelectorURL="<%= commerceBOMAdminDisplayContext.getItemSelectorUrl() %>"
						maxFileSize="<%= commerceBOMAdminDisplayContext.getImageMaxSize() %>"
						paramName="fileEntry"
						uploadURL="<%= uploadCoverImageURL %>"
						validExtensions='<%= StringUtil.merge(commerceBOMAdminDisplayContext.getImageExtensions(), ", ") %>'
					/>
				</div>

				<aui:input name="name" />
			</aui:fieldset>

			<aui:fieldset>
				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" value="save" />

					<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</aui:fieldset-group>
	</aui:form>
</div>