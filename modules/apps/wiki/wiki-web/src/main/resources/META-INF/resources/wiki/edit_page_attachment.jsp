<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		maxFileSize:
			'<%= DLValidatorUtil.getMaxAllowableSize(themeDisplay.getScopeGroupId(), null) %> ',
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