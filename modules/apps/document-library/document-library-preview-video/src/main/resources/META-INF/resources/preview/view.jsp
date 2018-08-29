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
String videoThumbnailURL = (String)request.getAttribute(DLPreviewVideoWebKeys.VIDEO_THUMBNAIL_URL);

List<String> previewFileURLs = (List<String>)request.getAttribute(DLPreviewVideoWebKeys.PREVIEW_FILE_URLS);

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_view_file_entry_preview") + StringPool.UNDERLINE;
%>

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