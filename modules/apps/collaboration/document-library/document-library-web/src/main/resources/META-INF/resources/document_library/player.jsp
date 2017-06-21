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

<%@ include file="/document_library/init.jsp" %>

<%
String randomNamespace = GetterUtil.getString(request.getAttribute("view_file_entry.jsp-randomNamespace"));

boolean supportedAudio = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedAudio"));
boolean supportedVideo = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedVideo"));

String[] previewFileURLs = (String[])request.getAttribute("view_file_entry.jsp-previewFileURLs");
String videoThumbnailURL = (String)request.getAttribute("view_file_entry.jsp-videoThumbnailURL");

String mp3PreviewFileURL = null;
String mp4PreviewFileURL = null;
String oggPreviewFileURL = null;
String ogvPreviewFileURL = null;

for (String previewFileURL : previewFileURLs) {
	if (previewFileURL.endsWith("mp3")) {
		mp3PreviewFileURL = previewFileURL;
	}
	else if (previewFileURL.endsWith("mp4")) {
		mp4PreviewFileURL = previewFileURL;
	}
	else if (previewFileURL.endsWith("ogg")) {
		oggPreviewFileURL = previewFileURL;
	}
	else if (previewFileURL.endsWith("ogv")) {
		ogvPreviewFileURL = previewFileURL;
	}
}
%>

<c:choose>
	<c:when test="<%= supportedAudio %>">
		<aui:script use="aui-audio">
			var audio = new A.Audio(
				{
					contentBox: '#<portlet:namespace /><%= randomNamespace %>previewFileContent',
					fixedAttributes: {
						allowfullscreen: 'true',
						wmode: 'opaque'
					}

					<c:if test="<%= Validator.isNotNull(oggPreviewFileURL) %>">
						, oggUrl: '<%= HtmlUtil.escapeJS(oggPreviewFileURL) %>'
					</c:if>

					<c:if test="<%= Validator.isNotNull(mp3PreviewFileURL) %>">
						, url: '<%= HtmlUtil.escapeJS(mp3PreviewFileURL) %>'
					</c:if>
				}
			).render();
		</aui:script>
	</c:when>
	<c:when test="<%= supportedVideo %>">
		<aui:script use="aui-base,aui-video">
			var isVideoPlaying = false;

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
							isVideoPlaying = false;
						},
						'play': function() {
							window.parent.Liferay.fire('<portlet:namespace /><%= randomNamespace %>Video:play');

							isVideoPlaying = true;
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
					if (isVideoPlaying) {
						video.pause();
					}
				}
			);
		</aui:script>
	</c:when>
</c:choose>