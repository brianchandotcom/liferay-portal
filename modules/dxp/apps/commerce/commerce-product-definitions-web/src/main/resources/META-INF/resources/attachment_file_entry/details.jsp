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

CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

long fileEntryId = BeanParamUtil.getLong(cpAttachmentFileEntry, request, "fileEntryId");

String fileEntryContainerCssClass = "file-entry-container hidden";

String buttonCssClass = StringPool.BLANK;

if (fileEntryId > 0) {
	fileEntryContainerCssClass = "file-entry-container";

	buttonCssClass += "hidden";
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpAttachmentFileEntry %>" model="<%= CPAttachmentFileEntry.class %>" />

<liferay-ui:error exception="<%= CPAttachmentFileEntryFileEntryIdException.class %>" message="please-select-an-existing-file" />

<liferay-util:buffer var="removeFileEntryIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL name="uploadTempAttachment" var="uploadCoverImageURL">
	<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
</portlet:actionURL>

<%
if (toolbarItem.equals("view-product-definition-images")) {
%>

	<div class="lfr-attachment-cover-image-selector">
		<liferay-item-selector:image-selector
			draggableImage="vertical"
			fileEntryId="<%= fileEntryId %>"
			itemSelectorEventName='<%= "addCPAttachmentFileEntry" %>'
			itemSelectorURL="<%= cpAttachmentFileEntriesDisplayContext.getItemSelectorUrl() %>"
			maxFileSize="<%= cpAttachmentFileEntriesDisplayContext.getImageMaxSize() %>"
			paramName="fileEntry"
			uploadURL="<%= uploadCoverImageURL %>"
			validExtensions='<%= StringUtil.merge(cpAttachmentFileEntriesDisplayContext.getImageExtensions(), ", ") %>'
		/>
	</div>

<%
}
else if (toolbarItem.equals("view-product-definition-attachments")) {
%>

	<aui:input name="fileEntryId" type="hidden" />

	<aui:button cssClass="<%= buttonCssClass %>" name="selectFile" value="select-file" />

	<div class="<%= fileEntryContainerCssClass %>">

		<%
		if (fileEntryId > 0) {
		%>

			<h5 class="file-entry-title"><%= cpAttachmentFileEntriesDisplayContext.getFileEntryName() %></h5>

		<%
		}
		else {
		%>

			<h5 class="file-entry-title"></h5>

		<%
		}
		%>

		<a class="modify-file-entry-link" href="javascript:<portlet:namespace/>modifyFileEntry();"><%= removeFileEntryIcon %></a>
	</div>

<%
}
%>

<aui:input name="title" />

<aui:input name="priority" />

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectFile').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'addCPAttachmentFileEntry',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {

								var value = JSON.parse(selectedItem.value);

								$('#<portlet:namespace />fileEntryId').val(value.fileEntryId);

								$('.file-entry-title').append(value.title);

								$('#<portlet:namespace />selectFile').addClass('hidden');

								$('.file-entry-container').removeClass('hidden');
							}
						}
					},
					title: '<liferay-ui:message key="select-file" />',
					url: '<%= cpAttachmentFileEntriesDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace/>modifyFileEntry() {

		$('#<portlet:namespace />fileEntryId').val(0);

		$('.file-entry-title').html('');

		$('#<portlet:namespace />selectFile').removeClass('hidden');

		$('.file-entry-container').addClass('hidden');

	}

</aui:script>