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

int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_ANY);

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_view_file_entry_preview") + StringPool.UNDERLINE;

boolean emptyPreview = false;
String[] previewFileURLs = null;
String videoThumbnailURL = DLUtil.getPreviewURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, "&videoThumbnail=1");

String previewQueryString = "&videoPreview=1";

if (status != WorkflowConstants.STATUS_ANY) {
	previewQueryString += "&status=" + status;
}

emptyPreview = true;

if (PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS.length > 0) {
	previewFileURLs = new String[PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS.length];

	for (int i = 0; i < PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS.length; i++) {
		if (VideoProcessorUtil.getPreviewFileSize(fileVersion, PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS[i]) > 0) {
			emptyPreview = false;
			previewFileURLs[i] = DLUtil.getPreviewURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, previewQueryString + "&type=" + PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS[i]);
		}
	}
}
else {
	emptyPreview = false;

	previewFileURLs = new String[1];

	previewFileURLs[0] = videoThumbnailURL;
}
%>

<c:choose>
	<c:when test="<%= emptyPreview %>">
		<div class="alert alert-info">
			<liferay-ui:message arguments="<%= fileVersion.getTitle() %>" key="cannot-generate-preview-for-x" />
		</div>
	</c:when>
	<c:when test="<%= !VideoProcessorUtil.hasVideo(fileVersion) %>">
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
		<div class="lfr-preview-file lfr-preview-video" id="<portlet:namespace /><%= randomNamespace %>previewFile">
			<div class="lfr-preview-file-content lfr-preview-video-content">
				<div class="lfr-preview-file-video-current-column">
					<div id="<portlet:namespace /><%= randomNamespace %>previewFileContent"></div>
				</div>
			</div>
		</div>

		<%
		String mp4PreviewFileURL = null;
		String ogvPreviewFileURL = null;

		for (String previewFileURL : previewFileURLs) {
			if (Validator.isNotNull(previewFileURL)) {
				if (previewFileURL.endsWith("mp4")) {
					mp4PreviewFileURL = previewFileURL;
				}
				else if (previewFileURL.endsWith("ogv")) {
					ogvPreviewFileURL = previewFileURL;
				}
			}
		}
		%>

		<aui:script use="aui-base,aui-video">
			var playing = false;

			var video = new A.Video(
				{
					contentBox: '#<portlet:namespace /><%= randomNamespace %>previewFileContent',
					fixedAttributes: {
						allowfullscreen: 'true',
						bgColor: '#000000',
						wmode: 'opaque'
					},

					on: {
						'pause' : function() {
						playing = false;
					},
					'play': function() {
						window.parent.Liferay.fire('<portlet:namespace /><%= randomNamespace %>Video:play');

						playing = true;
					}
				},

				<c:if test="<%= Validator.isNotNull(ogvPreviewFileURL) %>">
					ogvUrl: '<%= HtmlUtil.escapeJS(ogvPreviewFileURL) %>',
				</c:if>

				poster: '<%= HtmlUtil.escapeJS(videoThumbnailURL) %>'

				<c:if test="<%= Validator.isNotNull(mp4PreviewFileURL) %>">
					, url: '<%= HtmlUtil.escapeJS(mp4PreviewFileURL) %>'
				</c:if>
				}
			).render();

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:currentIndexChange',
				function() {
					if (playing) {
						video.pause();
					}
				}
			);

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:close',
				function() {
					video.load();
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>