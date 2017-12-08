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
CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

long fileEntryId = BeanParamUtil.getLong(cpAttachmentFileEntry, request, "fileEntryId");

int type = cpAttachmentFileEntriesDisplayContext.getType();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpAttachmentFileEntry %>" model="<%= CPAttachmentFileEntry.class %>" />

<liferay-ui:error exception="<%= CPAttachmentFileEntryFileEntryIdException.class %>" message="please-select-an-existing-file" />

<portlet:actionURL name="uploadTempAttachment" var="uploadCoverImageURL">
	<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
</portlet:actionURL>

<c:choose>
	<c:when test="<%= type == CPAttachmentFileEntryConstants.TYPE_IMAGE %>">
		<div class="lfr-attachment-cover-image-selector">
			<liferay-item-selector:image-selector
				draggableImage="vertical"
				fileEntryId="<%= fileEntryId %>"
				itemSelectorEventName='<%= "addCPAttachmentFileEntry" %>'
				itemSelectorURL="<%= cpAttachmentFileEntriesDisplayContext.getImageItemSelectorUrl() %>"
				maxFileSize="<%= cpAttachmentFileEntriesDisplayContext.getImageMaxSize() %>"
				paramName="fileEntry"
				uploadURL="<%= uploadCoverImageURL %>"
				validExtensions='<%= StringUtil.merge(cpAttachmentFileEntriesDisplayContext.getImageExtensions(), ", ") %>'
			/>
		</div>
	</c:when>
	<c:when test="<%= type == CPAttachmentFileEntryConstants.TYPE_OTHER %>">
		<aui:input name="fileEntryId" type="hidden" />

		<div id="<portlet:namespace />fileEntryContainer">
			<h5 id="<portlet:namespace />fileEntryTitle">
				<c:if test="<%= (fileEntryId > 0) %>">
					<%= cpAttachmentFileEntriesDisplayContext.getFileEntryName() %>
				</c:if>
			</h5>
		</div>

		<aui:button name="selectFile" value="select-file" />

		<aui:button cssClass='<%= (fileEntryId > 0) ? StringPool.BLANK : "hidden" %>' name="deleteFile" value="delete" />
	</c:when>
</c:choose>

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

								$('#<portlet:namespace />fileEntryTitle').html(value.title);

								$('#<portlet:namespace />fileEntryContainer').removeClass('hidden');

								$('#<portlet:namespace />deleteFile').removeClass('hidden');
							}
						}
					},
					title: '<liferay-ui:message key="select-file" />',
					url: '<%= cpAttachmentFileEntriesDisplayContext.getAttachmentItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	$('#<portlet:namespace />deleteFile').on(
		'click',
		function(event) {
			event.preventDefault();

			$('#<portlet:namespace />fileEntryId').val(0);

			$('#<portlet:namespace />fileEntryTitle').html('');

			$('#<portlet:namespace />fileEntryContainer').addClass('hidden');

			$('#<portlet:namespace />deleteFile').addClass('hidden');
		}
	);
</aui:script>