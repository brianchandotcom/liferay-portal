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
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
%>

<div class="lfr-dynamic-uploader" id="<portlet:namespace />uploaderContainer">
	<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
</div>

<div class="hide lfr-fallback" id="<portlet:namespace />fallback">
	<aui:input name="numOfFiles" type="hidden" value="3" />

	<aui:input label='<%= LanguageUtil.get(request, "file") + " 1" %>' name="file1" type="file" />

	<aui:input label='<%= LanguageUtil.get(request, "file") + " 2" %>' name="file2" type="file" />

	<aui:input label='<%= LanguageUtil.get(request, "file") + " 3" %>' name="file3" type="file" />
</div>

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

<liferay-util:buffer var="removeAttachmentIcon">
	<liferay-ui:icon
		iconCssClass="icon-remove"
	/>
</liferay-util:buffer>

<liferay-portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(wikiRequestHelper.getScopeGroupId()) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
	<portlet:param name="redirect" value="<%= wikiRequestHelper.getCurrentURL() %>" />
	<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
	<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
</liferay-portlet:actionURL>

<aui:script use="liferay-portlet-url,liferay-upload">
	var uploader = new Liferay.Upload(
		{
			boundingBox: '#<portlet:namespace />fileUpload',

			<%
			DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
			%>

			decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
			fallback: '#<portlet:namespace />fallback',
			fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			maxFileSize: '<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> ',
			namespace: '<portlet:namespace />',
			removeOnComplete: true,
			rootElement: '#<portlet:namespace />uploaderContainer',
			simultaneousUploads: 1,
			'strings.uploadsCompleteText': '<%= LanguageUtil.get(request, "all-files-are-saved") %>',
			uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>" name="/wiki/edit_page_attachment"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= WikiPage.class.getName() %>" />'
		}
	);

	uploader.on(
		'uploadComplete',
		function(event) {
			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />pageAttachments');

			if (searchContainer) {
				var rowColumns = [];

				var deleteURL = Liferay.PortletURL.createURL('<%= deleteURL.toString() %>');

				deleteURL.setParameter('fileName', event.name);

				rowColumns.push(event.name);
				rowColumns.push(uploader.formatStorage(event.size));
				rowColumns.push('<a href="' + deleteURL + '"><%= TrashUtil.isTrashEnabled(scopeGroupId) ? UnicodeLanguageUtil.get(resourceBundle, "move-to-the-recycle-bin") : UnicodeFormatter.toString(removeAttachmentIcon) %></a>');

				searchContainer.addRow(rowColumns, event.id);

				searchContainer.updateDataStore();
			}
		}
	);
</aui:script>