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
	title='<%= portletName.equals(PortletKeys.MEDIA_GALLERY_DISPLAY) ? "add-multiple-media" : "add-multiple-documents" %>'
/>

<aui:row>
	<aui:col width="<%= 50 %>">
		<aui:form name="fm1">
			<div class="lfr-dynamic-uploader">
				<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
			</div>
		</aui:form>

		<%
		Date expirationDate = new Date(System.currentTimeMillis() + PropsValues.SESSION_TIMEOUT * Time.MINUTE);

		Ticket ticket = TicketLocalServiceUtil.addTicket(user.getCompanyId(), User.class.getName(), user.getUserId(), TicketConstants.TYPE_IMPERSONATE, null, expirationDate, new ServiceContext());
		%>

		<aui:script use="liferay-upload">
			new Liferay.Upload(
				{
					boundingBox: '#<portlet:namespace />fileUpload',
					deleteFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= DLFileEntryConstants.getClassName() %>" />',
					fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
					maxFileSize: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> B',
					metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
					metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
					namespace: '<portlet:namespace />',
					tempFileURL: {
						method: Liferay.Service.bind('/dlapp/get-temp-file-entry-names'),
						params: {
							groupId: <%= scopeGroupId %>,
							folderId: <%= folderId %>,
							tempFolderName: 'com.liferay.portlet.documentlibrary.action.EditFileEntryAction'
						}
					},
					uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= DLFileEntryConstants.getClassName() %>" />'
				}
			);
		</aui:script>
	</aui:col>
	<aui:col width="<%= 50 %>">
		<div class="common-file-metadata-container hide selected" id="<portlet:namespace />commonFileMetadataContainer">
			<liferay-util:include page="/html/portlet/document_library/upload_multiple_file_entries_resources.jsp" />
		</div>

		<%
		DLUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-multiple-file-entries"), currentURL);
		%>

		<aui:script>
			Liferay.provide(
				window,
				'<portlet:namespace />updateMultipleFiles',
				function() {
					var A = AUI();
					var Lang = A.Lang;

					var commonFileMetadataContainer = A.one('#<portlet:namespace />commonFileMetadataContainer');
					var selectedFileNameContainer = A.one('#<portlet:namespace />selectedFileNameContainer');

					var inputTpl = '<input id="<portlet:namespace />selectedFileName{0}" name="<portlet:namespace />selectedFileName" type="hidden" value="{1}" />';

					var values = A.all('input[name=<portlet:namespace />selectUploadedFileCheckbox]:checked').val();

					var buffer = [];
					var dataBuffer = [];
					var length = values.length;

					for (var i = 0; i < length; i++) {
						dataBuffer[0] = i;
						dataBuffer[1] = values[i];

						buffer[i] = Lang.sub(inputTpl, dataBuffer);
					}

					selectedFileNameContainer.html(buffer.join(''));

					commonFileMetadataContainer.plug(A.LoadingMask);

					commonFileMetadataContainer.loadingmask.show();

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
										var item = jsonArray[i];

										var checkBox = A.one('input[data-fileName="' + item.fileName + '"]');

										var li = checkBox.ancestor();

										checkBox.remove(true);

										li.removeClass('selectable').removeClass('selected');

										var cssClass = null;
										var childHTML = null;

										if (item.added) {
											cssClass = 'file-saved';

											childHTML = '<span class="success-message"><%= UnicodeLanguageUtil.get(pageContext, "successfully-saved") %></span>';
										}
										else {
											cssClass = 'upload-error';

											childHTML = '<span class="error-message">' + item.errorMessage + '</span>';
										}

										li.addClass(cssClass);
										li.append(childHTML);
									}

									<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="uploadMultipleFileEntries">
										<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
										<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
										<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
									</liferay-portlet:resourceURL>

									if (commonFileMetadataContainer.io) {
										commonFileMetadataContainer.io.start();
									}
									else {
										commonFileMetadataContainer.load('<%= uploadMultipleFileEntries %>');
									}

									Liferay.fire('filesSaved');
								},
								failure: function(event, id, obj) {
									var selectedItems = A.all('#<portlet:namespace />fileUpload li.selected');

									selectedItems.removeClass('selectable').removeClass('selected').addClass('upload-error');

									selectedItems.append('<span class="error-message"><%= UnicodeLanguageUtil.get(pageContext, "an-unexpected-error-occurred-while-deleting-the-file") %></span>');

									selectedItems.all('input').remove(true);

									commonFileMetadataContainer.loadingmask.hide();
								}
							}
						}
					);
				},
				['aui-base']
			);
		</aui:script>
	</aui:col>
</aui:row>