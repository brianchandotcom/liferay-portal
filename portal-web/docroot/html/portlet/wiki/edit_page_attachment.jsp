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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_tabs.jsp">
	<liferay-util:param name="tabs1" value="attachments" />
</liferay-util:include>

<portlet:actionURL var="editPageAttachmentURL">
	<portlet:param name="struts_action" value="/wiki/edit_page_attachment" />
</portlet:actionURL>

<aui:form action="<%= editPageAttachmentURL %>" enctype="multipart/form-data" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= String.valueOf(node.getNodeId()) %>" />
	<aui:input name="title" type="hidden" value="<%= wikiPage.getTitle() %>" />
	<aui:input name="numOfFiles" type="hidden" value="3" />

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="a-file-with-that-name-already-exists" />

	<liferay-ui:error exception="<%= FileExtensionException.class %>">
		<liferay-ui:message key="document-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>.
	</liferay-ui:error>

	<liferay-ui:error exception="<%= FileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />

	<liferay-ui:error exception="<%= FileSizeException.class %>">

		<%
		long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if (fileMaxSize == 0) {
			fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		}

		fileMaxSize /= 1024;
		%>

		<liferay-ui:message arguments="<%= fileMaxSize %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" />
	</liferay-ui:error>

	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<div class="lfr-fallback hide" id="<portlet:namespace />fallback">
		<aui:fieldset label="upload-files">
			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 1" %>' name="file1" type="file" />

			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 2" %>' name="file2" type="file" />

			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 3" %>' name="file3" type="file" />
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" />

			<%
			String taglibOnClick = "parent.location = '" + HtmlUtil.escape(redirect) + "';";
			%>

			<aui:button onClick="<%= taglibOnClick %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>

<aui:script use="aui-base">
	var validateFile = function(fileField) {
		var value = fileField.val();

		if (value) {
			var extension = value.substring(value.lastIndexOf('.')).toLowerCase();
			var validExtensions = ['<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), "', '") %>'];

			if ((A.Array.indexOf(validExtensions, '*') == -1) &&
				(A.Array.indexOf(validExtensions, extension) == -1)) {

				alert('<%= UnicodeLanguageUtil.get(pageContext, "document-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>');

				fileField.val('');
			}
		}
	};

	var onFileChange = function(event) {
		validateFile(event.currentTarget);
	};

	for (var i = 1; i < 4; i++) {
		var fileField = A.one('#<portlet:namespace />file' + i);

		if (fileField) {
			fileField.on('change', onFileChange);

			validateFile(fileField);
		}
	}
</aui:script>

<%
Date expirationDate = new Date(System.currentTimeMillis() + PropsValues.SESSION_TIMEOUT * Time.MINUTE);

Ticket ticket = TicketLocalServiceUtil.addTicket(user.getCompanyId(), User.class.getName(), user.getUserId(), TicketConstants.TYPE_IMPERSONATE, null, expirationDate, new ServiceContext());
%>

<aui:script use="liferay-upload">
	new Liferay.Upload(
		{
			boundingBox: '#<portlet:namespace />fileUpload',
			deleteFile: '<liferay-portlet:actionURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/wiki/edit_page_attachment" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= WikiPage.class.getName() %>" />',
			fallback: '#<portlet:namespace />fallback',
			fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			maxFileSize: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> B',
			metadataContainer: '#<portlet:namespace />selectedFileNameMetadataContainer',
			metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
			namespace: '<portlet:namespace />',
			tempFileURL: {
				method: Liferay.Service.bind('/wikipage/get-temp-page-attachment-names'),
				params: {
					nodeId: <%= node.getNodeId() %>,
					tempFolderName: 'com.liferay.portlet.wiki.action.EditPageAttachmentsAction'
				}
			},
			uploadFile: '<liferay-portlet:actionURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/wiki/edit_page_attachment" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= WikiPage.class.getName() %>" />'
		}
	);
</aui:script>

<portlet:actionURL var="editMultiplePageAttachmentsURL">
	<portlet:param name="struts_action" value="/wiki/edit_page_attachment" />
</portlet:actionURL>

<aui:form action="<%= editMultiplePageAttachmentsURL %>" method="post" name="fm2" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updateMultiplePageAttachments();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="nodeId" type="hidden" value="<%= String.valueOf(node.getNodeId()) %>" />
	<aui:input name="title" type="hidden" value="<%= wikiPage.getTitle() %>" />

	<span id="<portlet:namespace />selectedFileNameContainer"></span>

	<div class="hide" id="<portlet:namespace />metadataExplanationContainer"></div>

	<div class="hide selected" id="<portlet:namespace />selectedFileNameMetadataContainer">
		<aui:button type="submit" />
	</div>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />updateMultiplePageAttachments',
		function() {
			var A = AUI();
			var Lang = A.Lang;

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

								checkBox.attr('checked', false);
								checkBox.hide();

								var li = checkBox.ancestor();

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
						}
					}
				}
			);
		},
		['aui-base']
	);
</aui:script>