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

<%@ include file="/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
%>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/wiki/page_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="attachments" />
</liferay-util:include>

<portlet:actionURL name="/wiki/edit_page_attachment" var="editPageAttachmentURL" />

<aui:form action="<%= editPageAttachmentURL %>" enctype="multipart/form-data" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= String.valueOf(node.getNodeId()) %>" />
	<aui:input name="title" type="hidden" value="<%= wikiPage.getTitle() %>" />
	<aui:input name="numOfFiles" type="hidden" value="3" />

	<liferay-ui:error exception="<%= AntivirusScannerException.class %>">

		<%
		AntivirusScannerException ase = (AntivirusScannerException)errorException;
		%>

		<liferay-ui:message key="<%= ase.getMessageKey() %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="a-file-with-that-name-already-exists" />

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
		%>

		<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<div class="hide lfr-fallback" id="<portlet:namespace />fallback">
		<aui:fieldset label="upload-files">
			<aui:input label='<%= LanguageUtil.get(request, "file") + " 1" %>' name="file1" type="file" />

			<aui:input label='<%= LanguageUtil.get(request, "file") + " 2" %>' name="file2" type="file" />

			<aui:input label='<%= LanguageUtil.get(request, "file") + " 3" %>' name="file3" type="file" />
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

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />fallback').on(
		'change',
		'input',
		function(event) {
			var currentTarget = $(event.currentTarget);

			var value = currentTarget.val();

			if (value) {
				var extension = value.substring(value.lastIndexOf('.')).toLowerCase();
				var validExtensions = ['<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), "', '") %>'];

				if ((validExtensions.indexOf('*') == -1) && (validExtensions.indexOf(extension) == -1)) {
					alert('<%= UnicodeLanguageUtil.get(request, "document-names-must-end-with-one-of-the-following-extensions") %> <%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>');

					currentTarget.val('');
				}
			}
		}
	);
</aui:script>

<%
Date expirationDate = new Date(System.currentTimeMillis() + GetterUtil.getInteger(PropsUtil.get(PropsKeys.SESSION_TIMEOUT)) * Time.MINUTE);

Ticket ticket = TicketLocalServiceUtil.addTicket(user.getCompanyId(), User.class.getName(), user.getUserId(), TicketConstants.TYPE_IMPERSONATE, null, expirationDate, new ServiceContext());
%>

<aui:script use="liferay-upload">
	new Liferay.Upload(
		{
			boundingBox: '#<portlet:namespace />fileUpload',

			<%
			DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
			%>

			decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
			deleteFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>" name="/wiki/edit_page_attachment"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= WikiPage.class.getName() %>" />',
			fallback: '#<portlet:namespace />fallback',
			fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			maxFileSize: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> B',
			metadataContainer: '#<portlet:namespace />selectedFileNameMetadataContainer',
			metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
			namespace: '<portlet:namespace />',
			tempFileURL: {
				method: Liferay.Service.bind('/wiki.wikipage/get-temp-file-names'),
				params: {
					folderName: 'com.liferay.wiki.web.wiki.portlet.action.EditPageAttachmentsAction',
					nodeId: <%= node.getNodeId() %>
				}
			},
			tempRandomSuffix: '<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>',
			uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>" name="/wiki/edit_page_attachment"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= WikiPage.class.getName() %>" />'
		}
	);
</aui:script>

<portlet:actionURL name="/wiki/edit_page_attachment" var="editMultiplePageAttachmentsURL" />

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
	function <portlet:namespace />updateMultiplePageAttachments() {
		var $ = AUI.$;
		var _ = AUI._;

		var inputTpl = '<input id="<portlet:namespace />selectedFileName{0}" name="<portlet:namespace />selectedFileName" type="hidden" value="{1}" />';

		var selectedFiles = $('input[name=<portlet:namespace />selectUploadedFile]:checked');

		var selectedFilesHtml = selectedFiles.map(
			function(index, item) {
				return _.sub(inputTpl, index, $(item).val());
			}
		).get();

		$('#<portlet:namespace />selectedFileNameContainer').html(selectedFilesHtml.join(''));

		$(document.<portlet:namespace />fm2).ajaxSubmit(
			{
				dataType: 'json',
				success: function(responseData) {
					_.forEach(
						responseData,
						function(item, index) {
							var checkBox = $('input[data-fileName="' + item.originalFileName + '"]');

							checkBox.prop('checked', false);
							checkBox.addClass('hide');

							var li = checkBox.parent();

							li.removeClass('selectable selected');

							var cssClass;
							var childHTML;

							if (item.added) {
								cssClass = 'file-saved';
								var originalFileName = item.originalFileName;

								var pos = originalFileName.indexOf('<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>');

								if (pos != -1) {
									originalFileName = originalFileName.substr(0, pos);
								}

								if (originalFileName === item.fileName) {
									childHTML = '<span class="success-message"><%= UnicodeLanguageUtil.get(request, "successfully-saved") %></span>';
								}
								else {
									childHTML = '<span class="success-message"><%= UnicodeLanguageUtil.get(request, "successfully-saved") %> (' + item.fileName + ')</span>';
								}
							}
							else {
								cssClass = 'upload-error';

								childHTML = '<span class="error-message">' + item.errorMessage + '</span>';
							}

							li.addClass(cssClass);
							li.append(childHTML);
						}
					);
				}
			}
		);
	}
</aui:script>