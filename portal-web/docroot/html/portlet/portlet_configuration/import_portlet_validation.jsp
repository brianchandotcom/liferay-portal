<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
Layout exportableLayout = ExportImportHelperUtil.getExportableLayout(themeDisplay);
%>

<aui:form cssClass="lfr-export-dialog" method="post" name="fm1">
	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<%
	FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(scopeGroupId, themeDisplay.getUserId(), ExportImportHelper.TEMP_FOLDER_NAME + selPortlet.getPortletId());
	%>

	<aui:button-row>
		<aui:button cssClass='<%= fileEntry == null ? "hide" : StringPool.BLANK %>' name="continueButton" type="submit" value="continue" />
	</aui:button-row>

	<%
	Date expirationDate = new Date(System.currentTimeMillis() + PropsValues.SESSION_TIMEOUT * Time.MINUTE);

	Ticket ticket = TicketLocalServiceUtil.addTicket(user.getCompanyId(), User.class.getName(), user.getUserId(), TicketConstants.TYPE_IMPERSONATE, null, expirationDate, new ServiceContext());
	%>

	<aui:script use="liferay-upload">
		var liferayUpload = new Liferay.Upload(
			{
				boundingBox: '#<portlet:namespace />fileUpload',
				deleteFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="portletResource" value="<%= portletResource %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= Group.class.getName() %>" />',
				fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
				maxFileSize: '<%= PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE) %> B',
				metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
				metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
				multipleFiles: false,
				namespace: '<portlet:namespace />',
				'strings.dropFileText': '<liferay-ui:message key="drop-a-lar-file-here-to-import" />',
				'strings.fileCannotBeSavedText': '<liferay-ui:message key="the-file-x-cannot-be-imported" />',
				'strings.pendingFileText': '<liferay-ui:message key="this-file-was-previously-uploaded-but-not-actually-imported" />',
				'strings.uploadsCompleteText': '<liferay-ui:message key="the-file-is-ready-to-be-imported" />',
				tempFileURL: {
					method: Liferay.Service.bind('/layout/get-temp-file-entry-names'),
					params: {
						groupId: <%= scopeGroupId %>,
						tempFolderName: '<%= ExportImportHelper.TEMP_FOLDER_NAME + selPortlet.getPortletId() %>'
					}
				},
				uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="plid" value="<%= String.valueOf(exportableLayout.getPlid()) %>" /><portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>" /><portlet:param name="portletResource" value="<%= portletResource %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= Group.class.getName() %>" />'
			}
		);

		var continueButton = A.one('#<portlet:namespace />continueButton');

		liferayUpload._uploader.on(
			'alluploadscomplete',
			function(event) {
				toggleContinueButton();
			}
		);

		Liferay.on(
			'tempFileRemoved',
			function(event) {
				toggleContinueButton();
			}
		);

		function toggleContinueButton() {
			var uploadedFiles = liferayUpload._fileListContent.all('.upload-file.upload-complete');

			if (uploadedFiles.size() == 1) {
				continueButton.show();
			}
			else {
				continueButton.hide();
			}
		}
	</aui:script>
</aui:form>

<aui:script use="aui-base,aui-io-plugin-deprecated,aui-loading-mask-deprecated">
	var form = A.one('#<portlet:namespace />fm1');

	form.on(
		'submit',
		function(event) {
			event.halt();

			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="importPortletURL">
				<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
				<portlet:param name="portletResource" value="<%= portletResource %>" />
				<portlet:param name="validate" value="<%= String.valueOf(Boolean.FALSE) %>" />
			</liferay-portlet:resourceURL>

			var exportImportOptions = A.one('#<portlet:namespace />exportImportOptions');

			exportImportOptions.plug(
				A.Plugin.IO,
				{
					form: {
						id: '<portlet:namespace />fm1'
					},
					uri: '<%= importPortletURL %>'
				}
			);
		}
	);
</aui:script>