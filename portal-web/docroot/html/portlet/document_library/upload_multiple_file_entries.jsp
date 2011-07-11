<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long repositoryId = BeanParamUtil.getLong(fileEntry, request, "repositoryId");

if (repositoryId <= 0) {

	// add_asset.jspf only passes in groupId

	repositoryId = BeanParamUtil.getLong(fileEntry, request, "groupId");
}

long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");
%>

<c:if test="<%= Validator.isNull(referringPortletResource) %>">
	<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />
</c:if>

<liferay-ui:header
	backURL="<%= backURL %>"
	title="add-multiple-documents"
/>

<aui:form name="fm1">
	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>
</aui:form>

<aui:script use="liferay-upload">
	var params = {
		groupId: <%= scopeGroupId %>,
		folderId: <%= folderId %>,
		tempFolderName: 'document_temp_upload'
	};

	var service = {
		method : Liferay.Service.DL.DLApp.getTempFileEntryNames,
		params : params
	};

	new Liferay.Upload(
		{
			allowedFileTypes: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
			metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
			container: '#<portlet:namespace />fileUpload',
			fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			maxFileSize: <%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> / 1024,
			namespace: '<portlet:namespace />',
			uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL><liferay-ui:input-permissions-params modelName="<%= DLFileEntryConstants.getClassName() %>" />',
			deleteFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL><liferay-ui:input-permissions-params modelName="<%= DLFileEntryConstants.getClassName() %>" />',
			service: service
		}
	);
</aui:script>

<portlet:actionURL var="editMultipleFileEntriesURL">
	<portlet:param name="struts_action" value="document_library/edit_file_entry" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_MULTIPLE %>" />
</portlet:actionURL>

<aui:form action="<%= editMultipleFileEntriesURL %>" method="post" name="fm2" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateMultipleFiles();" %>'>
	<div class="no-files-selected-info portlet-msg-info aui-helper-hidden" id="<portlet:namespace />metadataExplanationContainer">
		<liferay-ui:message key="select-documents-from-the-left-to-add-them-to-the-document-library" />
	</div>

	<div class="common-file-metadata-container aui-helper-hidden selected" id="<portlet:namespace />commonFileMetadataContainer">
		<liferay-util:include page="/html/portlet/document_library/upload_multiple_file_entries_resources.jsp" />
	</div>
</aui:form>

<%
DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-multiple-file-entries"), currentURL);
%>

<aui:script use="aui-base">
	var commonFileMetadataContainer = A.one('#<portlet:namespace />commonFileMetadataContainer');

	Liferay.provide(
		window,
		'<portlet:namespace />updateMultipleFiles',
		function() {
			var selectedFileNameContainer = A.one('#<portlet:namespace />selectedFileNameContainer');

			selectedFileNameContainer.empty();

			A.all('input[name=<portlet:namespace />selectUploadedFileCheckbox]').each(
				function(item, index, collection) {
					var val = item.val();

					if (item.get('checked')) {
						var selectedFileName = A.Node.create('<input id="<portlet:namespace />selectedFileName' + index + '" name="<portlet:namespace />selectedFileName" type="hidden" value="' + val + '" />');

						selectedFileNameContainer.append(selectedFileName);
					}
				}
			);

			commonFileMetadataContainer.plug(A.LoadingMask);

			commonFileMetadataContainer.loadingmask.toggle();

			A.io.request(
				document.<portlet:namespace />fm2.action,
				{
					dataType: 'json',
					form: {
						id: document.<portlet:namespace />fm2
					},
					after: {
						success: function(event, id, obj) {
							var jsonArray = this.get('responseData');

							for (var i = 0; i < jsonArray.length; i++) {
								var fileName = jsonArray[i].fileName;
								var added = jsonArray[i].added;

								var checkBox = A.one('input[data-fileName=' + fileName + ']');

								var li = checkBox.get('parentNode');

								checkBox.remove(true);

								if (added) {
									li.removeClass('selectable').removeClass('selected').addClass('file-saved');

									var successMessage = A.Node.create('<span class="success-message"><%= LanguageUtil.get(pageContext, "successfully-saved") %></span>')

									li.appendChild(successMessage);
								}
								else {
									var errorMessage = jsonArray[i].errorMessage;

									li.removeClass('selectable').removeClass('selected').addClass('upload-error');

									var errorMessageContainer = A.Node.create('<span class="error-message">' + errorMessage + '</span>')

									li.appendChild(errorMessageContainer);
								}
							}

							<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="uploadMultipleFileEntries">
								<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
								<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
								<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							</liferay-portlet:resourceURL>

							A.io.request(
								'<%= uploadMultipleFileEntries %>',
								{
									on: {
										success: function(event, id, obj) {
											commonFileMetadataContainer.loadingmask.toggle();

											var responseData = this.get('responseData');

											var content = A.Node.create(responseData);

											commonFileMetadataContainer.plug(A.Plugin.ParseContent);
											commonFileMetadataContainer.setContent(content);
										},
									}
								}
							);

							Liferay.fire('filesSaved');
						},
						failure: function(event, id, obj) {
							A.all('#<portlet:namespace />fileUpload li.selected').each(
								function(item, index, collection) {

									item.removeClass('selectable').removeClass('selected').addClass('upload-error');

									var errorMessageContainer = A.Node.create('<span class="error-message"><%= LanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-deleting-the-file") %></span>')

									item.appendChild(errorMessageContainer);

									var checkBox = item.one('input');

									checkBox.remove(true);

									commonFileMetadataContainer.loadingmask.toggle();
								}
							);
						}
					}
				}
			);
		},
		['aui-base']
	);
</aui:script>