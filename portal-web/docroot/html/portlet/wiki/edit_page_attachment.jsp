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

<aui:form action="<%= editPageAttachmentURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= String.valueOf(node.getNodeId()) %>" />
	<aui:input name="title" type="hidden" value="<%= wikiPage.getTitle() %>" />
	<aui:input name="numOfFiles" type="hidden" value="3" />

	<div class="lfr-dynamic-uploader">
		<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
	</div>

	<div class="lfr-fallback aui-helper-hidden" id="<portlet:namespace />fallback">
		<aui:fieldset label="upload-files">
			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 1" %>' name="file1" type="file" />

			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 2" %>' name="file2" type="file" />

			<aui:input label='<%= LanguageUtil.get(pageContext, "file") + " 3" %>' name="file3" type="file" />
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" />

			<%
			String taglibOnClick = "parent.location = '" + HtmlUtil.escapeURL(redirect) + "';";
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
Date expirationDate = new Date(System.currentTimeMillis() + PropsValues.SESSION_TIMEOUT * 60 * 1000);

ServiceContext serviceContext = new ServiceContext();

Ticket ticket = TicketLocalServiceUtil.addTicket(
	user.getCompanyId(), User.class.getName(), user.getUserId(),
	TicketConstants.TYPE_IMPERSONATE, null, expirationDate,
	serviceContext);	
%>

<aui:script use="liferay-upload">
	new Liferay.Upload(
		{
			allowedFileTypes: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			container: '#<portlet:namespace />fileUpload',
			fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
			fallbackContainer: '#<portlet:namespace />fallback',
			maxFileSize: <%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %> / 1024,
			namespace: '<portlet:namespace />',
			uploadFile: '<liferay-portlet:actionURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/wiki/edit_page_attachment" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL>&ticket=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= WikiPage.class.getName() %>" />'
		}
	);
</aui:script>