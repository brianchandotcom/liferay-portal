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

<%@ include file="/message_boards/init.jsp" %>

<%
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

long categoryId = MBUtil.getCategoryId(request, message);

int deletedAttachmentsFileEntriesCount = 0;

if (message != null) {
	deletedAttachmentsFileEntriesCount = message.getDeletedAttachmentsFileEntriesCount();
}
%>

<div class="lfr-dynamic-uploader" id="<portlet:namespace />uploaderContainer">
	<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
</div>

<span id="<portlet:namespace />selectedFileNameContainer"></span>

<div class="hide" id="<portlet:namespace />metadataExplanationContainer"></div>

<div class="hide selected" id="<portlet:namespace />selectedFileNameMetadataContainer"></div>

<c:if test="<%= (message != null) && trashHelper.isTrashEnabled(scopeGroupId) && MBMessagePermission.contains(permissionChecker, message, ActionKeys.UPDATE) %>">
	<div align="right">
		<a href="javascript:;" id="view-removed-attachments-link" style='display: <%= (deletedAttachmentsFileEntriesCount > 0) ? "initial" : "none" %>;'>
			<liferay-ui:message arguments="<%= deletedAttachmentsFileEntriesCount %>" key='<%= (deletedAttachmentsFileEntriesCount == 1) ? "x-recently-removed-attachment" : "x-recently-removed-attachments" %>' /> &raquo;
		</a>
	</div>
</c:if>

<aui:script use="liferay-portlet-url,liferay-upload">
	var uploader = new Liferay.Upload({
		boundingBox: '#<portlet:namespace />fileUpload',

		<%
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
		%>

		decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
		deleteFile:
			'<liferay-portlet:actionURL name="/message_boards/edit_message_attachments"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></liferay-portlet:actionURL>',
		fallback: '#<portlet:namespace />fallback',

		<%
		DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
		%>

		fileDescription:
			'<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',
		maxFileSize: '<%= DLValidatorUtil.getMaxAllowableSize(null) %> ',
		namespace: '<portlet:namespace />',
		rootElement: '#<portlet:namespace />uploaderContainer',
		tempFileURL: {
			method: Liferay.Service.bind('/mb.mbmessage/get-temp-attachment-names'),
			params: {
				groupId: <%= scopeGroupId %>,
				folderName: '<%= MBMessageConstants.TEMP_FOLDER_NAME %>',
			},
		},
		tempRandomSuffix: '<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>',
		uploadFile:
			'<liferay-portlet:actionURL name="/message_boards/edit_message_attachments"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></liferay-portlet:actionURL>',
	});
</aui:script>