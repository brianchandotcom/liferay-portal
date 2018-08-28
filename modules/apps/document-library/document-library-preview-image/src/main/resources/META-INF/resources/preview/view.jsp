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
%>

<c:choose>
	<c:when test="<%= !ImageProcessorUtil.hasImages(fileVersion) %>">
		<c:choose>
			<c:when test="<%= !DLProcessorRegistryUtil.isPreviewableSize(fileVersion) %>">
				<div class="alert alert-info">
					<liferay-ui:message key="file-is-too-large-for-preview-or-thumbnail-generation" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info">
					<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>

		<%
		String previewQueryString = "&imagePreview=1";

		int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_ANY);

		if (status != WorkflowConstants.STATUS_ANY) {
			previewQueryString += "&status=" + status;
		}

		String previewFileURL = DLUtil.getPreviewURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, previewQueryString);

		String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_view_file_entry_preview") + StringPool.UNDERLINE;
		%>

		<div class="lfr-preview-file lfr-preview-image" id="<portlet:namespace /><%= randomNamespace %>previewFile">
			<div class="lfr-preview-file-content lfr-preview-image-content" id="<portlet:namespace /><%= randomNamespace %>previewFileContent">
				<div class="lfr-preview-file-image-current-column">
					<div class="lfr-preview-file-image-container">
						<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="lfr-preview-file-image-current" src="<%= previewFileURL %>" />
					</div>
				</div>
			</div>
		</div>

		<aui:script use="aui-base">
			var currentImage = A.one('.lfr-preview-file-image-current');

			if (currentImage && (currentImage.get('complete') || currentImage.get('naturalWidth'))) {
			currentImage.setStyle('background-image', 'none');
			}
		</aui:script>
	</c:otherwise>
</c:choose>