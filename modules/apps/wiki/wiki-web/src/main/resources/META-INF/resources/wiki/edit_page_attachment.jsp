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

DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
%>

<div class="lfr-dynamic-uploader" id="<portlet:namespace />uploaderContainer">
	<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
</div>

<div class="hide lfr-fallback" id="<portlet:namespace />fallback">
	<aui:input name="numOfFiles" type="hidden" value="3" />

	<%
	String acceptedExtensions = StringUtil.merge(dlConfiguration.fileExtensions(), StringPool.COMMA_AND_SPACE);
	%>

	<aui:input label='<%= LanguageUtil.get(request, "file") + " 1" %>' name="file1" type="file">
		<aui:validator name="acceptFiles">
			'<%= acceptedExtensions %>'
		</aui:validator>
	</aui:input>

	<aui:input label='<%= LanguageUtil.get(request, "file") + " 2" %>' name="file2" type="file">
		<aui:validator name="acceptFiles">
			'<%= acceptedExtensions %>'
		</aui:validator>
	</aui:input>

	<aui:input label='<%= LanguageUtil.get(request, "file") + " 3" %>' name="file3" type="file">
		<aui:validator name="acceptFiles">
			'<%= acceptedExtensions %>'
		</aui:validator>
	</aui:input>
</div>

<liferay-portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" />
	<portlet:param name="redirect" value="<%= wikiRequestHelper.getCurrentURL() %>" />
	<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
	<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
</liferay-portlet:actionURL>

<aui:script use="liferay-portlet-url,liferay-upload">
	var uploader = new Liferay.Upload({
		boundingBox: '#<portlet:namespace />fileUpload',

		<%
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
		%>

		decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
		deleteFile: '<%= deleteURL.toString() %>',
		fallback: '#<portlet:namespace />fallback',
		fileDescription:
			'<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',
		maxFileSize: '<%= DLValidatorUtil.getMaxAllowableSize(null) %> ',
		namespace: '<portlet:namespace />',
		rootElement: '#<portlet:namespace />uploaderContainer',
		tempFileURL: {
			method: Liferay.Service.bind('/wiki.wikipage/get-temp-file-names'),
			params: {
				nodeId: <%= node.getNodeId() %>,
				folderName: '<%= WikiConstants.TEMP_FOLDER_NAME %>',
			},
		},
		tempRandomSuffix: '<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>',
		uploadFile:
			'<liferay-portlet:actionURL name="/wiki/edit_page_attachment"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" /><portlet:param name="title" value="<%= wikiPage.getTitle() %>" /></liferay-portlet:actionURL>',
	});
</aui:script>