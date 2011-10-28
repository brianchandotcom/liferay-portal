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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
boolean supportedAudio = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedAudio"));
boolean supportedVideo = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedVideo"));

String previewFileURL = (String)request.getAttribute("view_file_entry.jsp-previewFileURL");
String videoThumbnailURL = (String)request.getAttribute("view_file_entry.jsp-videoThumbnailURL");
%>

<aui:script use="aui-swf">
	new A.SWF(
		{
			boundingBox: '#<portlet:namespace />previewFileContent',
			fixedAttributes: {
				allowFullScreen: true,
				bgColor: '#000000'
			},
			flashVars: {
				<c:choose>
					<c:when test="<%= supportedAudio %>">
							'<%= AudioProcessor.PREVIEW_TYPE %>': '<%= previewFileURL %>'
					</c:when>
					<c:when test="<%= supportedVideo %>">
						'<%= VideoProcessor.PREVIEW_TYPE %>': '<%= previewFileURL %>',
						'<%= VideoProcessor.THUMBNAIL_TYPE %>': '<%= videoThumbnailURL %>'
					</c:when>
				</c:choose>
			},
			<c:if test="<%= supportedAudio %>">
				height: 27,
			</c:if>
			url: '<%= themeDisplay.getPathJavaScript() %>/misc/video_player/mpw_player.swf',
			useExpressInstall: true,
			version: 9
		}
	);
</aui:script>