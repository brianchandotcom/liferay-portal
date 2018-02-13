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
%>

<div class="lfr-dynamic-uploader" id="<portlet:namespace />uploaderContainer">
	<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
</div>

<span id="<portlet:namespace />selectedFileNameContainer"></span>

<div class="hide" id="<portlet:namespace />metadataExplanationContainer"></div>

<div class="hide selected" id="<portlet:namespace />selectedFileNameMetadataContainer"></div>

<%
Date expirationDate = new Date(System.currentTimeMillis() + GetterUtil.getInteger(PropsUtil.get(PropsKeys.SESSION_TIMEOUT)) * Time.MINUTE);

Ticket ticket = TicketLocalServiceUtil.addTicket(user.getCompanyId(), User.class.getName(), user.getUserId(), TicketConstants.TYPE_IMPERSONATE, null, expirationDate, new ServiceContext());
%>

<aui:script use="liferay-portlet-url,liferay-upload">
	var uploader = new Liferay.Upload(
		{
			boundingBox: '#<portlet:namespace />fileUpload',

			<%
			DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
			%>

			decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
			deleteFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>" name="/message_boards/edit_message_attachments"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= MBMessage.class.getName() %>" />',
			fallback: '#<portlet:namespace />fallback',

			<%
			DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
			%>

			fileDescription: '<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',
			maxFileSize: '<%= dlConfiguration.fileMaxSize() %> ',
			namespace: '<portlet:namespace />',
			rootElement: '#<portlet:namespace />uploaderContainer',
			tempFileURL: {
				method: Liferay.Service.bind('/mb.mbmessage/get-temp-attachment-names'),
				params: {
					groupId: <%= scopeGroupId %>,
					folderName: '<%= MBMessageConstants.TEMP_FOLDER_NAME %>'
				}
			},
			tempRandomSuffix: '<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>',
			uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>" name="/message_boards/edit_message_attachments"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= MBMessage.class.getName() %>" />'
		}
	);
</aui:script>