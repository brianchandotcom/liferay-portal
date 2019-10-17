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

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

resourcePrimKey = BeanParamUtil.getLong(kbArticle, request, "resourcePrimKey");

List<FileEntry> attachmentsFileEntries = new ArrayList<FileEntry>();

if (kbArticle != null) {
	attachmentsFileEntries = kbArticle.getAttachmentsFileEntries();
}
%>

<div class="kb-attachments">
	<aui:input name="removeFileEntryIds" type="hidden" />

	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<span id="<portlet:namespace />selectedFileNameContainer"></span>

	<div class="hide" id="<portlet:namespace />metadataExplanationContainer"></div>

	<div class="hide selected" id="<portlet:namespace />selectedFileNameMetadataContainer"></div>

	<c:if test="<%= !attachmentsFileEntries.isEmpty() %>">
		<h4><liferay-ui:message key="saved-attachments" /></h4>

		<div id="<portlet:namespace />existingAttachmentsContainer">

			<%
			for (FileEntry fileEntry : attachmentsFileEntries) {
			%>

				<div id="<portlet:namespace />fileEntryIdWrapper<%= fileEntry.getFileEntryId() %>">

					<%
					String rowURL = PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, "status=" + WorkflowConstants.STATUS_APPROVED);
					%>

					<liferay-ui:icon
						icon="paperclip"
						label="<%= true %>"
						markupView="lexicon"
						message='<%= HtmlUtil.escape(fileEntry.getTitle()) + " (" + TextFormatter.formatStorageSize(fileEntry.getSize(), locale) + ")" %>'
						method="get"
						url="<%= rowURL %>"
					/>

					<%
					String taglibURL = "javascript:" + renderResponse.getNamespace() + "deleteFileEntry('" + fileEntry.getFileEntryId() + "');";
					%>

					<liferay-ui:icon-delete
						label="<%= true %>"
						url="<%= taglibURL %>"
					/>
				</div>

			<%
			}
			%>

		</div>
	</c:if>
</div>

<aui:script use="liferay-upload">
	new Liferay.Upload({
		boundingBox: '#<portlet:namespace />fileUpload',
		deleteFile:
			'<liferay-portlet:actionURL name="deleteTempAttachment"><portlet:param name="resourcePrimKey" value="<%= String.valueOf(resourcePrimKey) %>" /></liferay-portlet:actionURL>',

		<%
		DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
		%>

		fileDescription:
			'<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',
		maxFileSize: '<%= dlConfiguration.fileMaxSize() %> B',
		metadataContainer:
			'#<portlet:namespace />selectedFileNameMetadataContainer',
		metadataExplanationContainer:
			'#<portlet:namespace />metadataExplanationContainer',
		namespace: '<portlet:namespace />',
		tempFileURL: {
			method: Liferay.Service.bind('/kb.kbarticle/get-temp-attachment-names'),
			params: {
				groupId: <%= scopeGroupId %>,
				tempFolderName: '<%= KBWebKeys.TEMP_FOLDER_NAME %>'
			}
		},
		uploadFile:
			'<liferay-portlet:actionURL name="addTempAttachment"><portlet:param name="resourcePrimKey" value="<%= String.valueOf(resourcePrimKey) %>" /></liferay-portlet:actionURL>'
	});
</aui:script>

<aui:script>
	Liferay.provide(window, '<portlet:namespace />deleteFileEntry', function(
		fileEntryId
	) {
		var removeFileEntryIdsInput = document.getElementById(
			'<portlet:namespace />removeFileEntryIds'
		);

		var fileEntries = removeFileEntryIdsInput.value;

		if (fileEntries.length) {
			fileEntries += ',';
		}

		fileEntries += fileEntryId;

		removeFileEntryIdsInput.value = fileEntries;

		var fileEntryIdWrapper = document.getElementById(
			'<portlet:namespace />fileEntryIdWrapper' + fileEntryId
		);

		if (fileEntryIdWrapper) {
			fileEntryIdWrapper.hide();
		}
	});
</aui:script>