<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
boolean supportedAudio = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedAudio"));
boolean supportedVideo = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedVideo"));

String[] previewFileURLs = (String[])request.getAttribute("view_file_entry.jsp-previewFileURLs");
String videoThumbnailURL = (String)request.getAttribute("view_file_entry.jsp-videoThumbnailURL");

String mp4PreviewFileURL = null;
String ogvPreviewFileURL = null;

for (String previewFileURL : previewFileURLs) {
	if (previewFileURL.endsWith("mp4")){
		mp4PreviewFileURL = previewFileURL;
	}
	else if (previewFileURL.endsWith("ogv")){
		ogvPreviewFileURL = previewFileURL;
	}
}
%>

<c:if test="<%= supportedAudio%>">
	<aui:script use="aui-swf">
		new A.SWF(
			{
				boundingBox: '#<portlet:namespace />previewFileContent',
				fixedAttributes: {
					allowFullScreen: true,
					bgColor: '#000000'
				},
				flashVars: {
					'mp3': '<%= previewFileURLs[0] %>'
				},
				height: 27,
				url: '<%= themeDisplay.getPathJavaScript() %>/misc/video_player/mpw_player.swf',
				useExpressInstall: true,
				version: 9
			}
		);
	</aui:script>
</c:if>
<c:if test="<%= supportedVideo%>">
	<aui:script use="aui-base,aui-video">
		new A.Video(
			{
				boundingBox: '#<portlet:namespace />previewFileContent',
				fixedAttributes: {
					allowfullscreen: 'true',
					bgColor: '#000000'
				},
				<c:if test="<%= Validator.isNotNull(ogvPreviewFileURL) %>">
					ogvUrl: '<%= ogvPreviewFileURL %>',
				</c:if>

				poster: '<%= videoThumbnailURL %>',

				<c:if test="<%= Validator.isNotNull(mp4PreviewFileURL) %>">
					url: '<%= mp4PreviewFileURL %>'
				</c:if>
			}
		).render();
	</aui:script>
</c:if>