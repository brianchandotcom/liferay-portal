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

<%@ include file="/preview/init.jsp" %>

<%
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_view_file_entry_preview") + StringPool.UNDERLINE;

int previewFileCount = PDFProcessorUtil.getPreviewFileCount(fileVersion);

String previewQueryString = "&previewFileIndex=";

int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_ANY);

if (status != WorkflowConstants.STATUS_ANY) {
	previewQueryString += "&status=" + status;
}

String[] previewFileURLs = new String[1];

previewFileURLs[0] = DLUtil.getPreviewURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, previewQueryString);

String previewFileURL = previewFileURLs[0];
%>

<div class="lfr-preview-file" id="<portlet:namespace /><%= randomNamespace %>previewFile">
	<div class="lfr-preview-file-content" id="<portlet:namespace /><%= randomNamespace %>previewFileContent">
		<div class="lfr-preview-file-image-current-column">
			<div class="lfr-preview-file-image-container">
				<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="lfr-preview-file-image-current" id="<portlet:namespace /><%= randomNamespace %>previewFileImage" src="<%= previewFileURL + "1" %>" />
			</div>

			<span class="hide lfr-preview-file-actions" id="<portlet:namespace /><%= randomNamespace %>previewFileActions">
				<span class="lfr-preview-file-toolbar" id="<portlet:namespace /><%= randomNamespace %>previewToolbar"></span>

				<span class="lfr-preview-file-info">
					<span class="lfr-preview-file-index" id="<portlet:namespace /><%= randomNamespace %>previewFileIndex">1</span> <liferay-ui:message key="of" /> <span class="lfr-preview-file-count"><%= previewFileCount %></span>
				</span>
			</span>
		</div>

		<div class="lfr-preview-file-images" id="<portlet:namespace /><%= randomNamespace %>previewImagesContent">
			<div class="lfr-preview-file-images-content"></div>
		</div>
	</div>
</div>

<aui:script use="liferay-preview">
	new Liferay.Preview(
		{
			actionContent: '#<portlet:namespace /><%= randomNamespace %>previewFileActions',
			baseImageURL: '<%= previewFileURL %>',
			boundingBox: '#<portlet:namespace /><%= randomNamespace %>previewFile',
			contentBox: '#<portlet:namespace /><%= randomNamespace %>previewFileContent',
			currentPreviewImage: '#<portlet:namespace /><%= randomNamespace %>previewFileImage',
			imageListContent: '#<portlet:namespace /><%= randomNamespace %>previewImagesContent',
			maxIndex: <%= previewFileCount %>,
			previewFileIndexNode: '#<portlet:namespace /><%= randomNamespace %>previewFileIndex',
			toolbar: '#<portlet:namespace /><%= randomNamespace %>previewToolbar'
		}
	).render();

	var currentImage = A.one('.lfr-preview-file-image-current');

	if (currentImage && (currentImage.get('complete') || currentImage.get('naturalWidth'))) {
		currentImage.setStyle('background-image', 'none');
	}
</aui:script>