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

<liferay-util:html-bottom outputKey="documentLibraryPlayer">
	<script src="<%= themeDisplay.getPathJavaScript() %>/misc/swfobject.js" type="text/javascript"></script>
</liferay-util:html-bottom>

<aui:script use="aui-base">
	var previewDivObject = A.one('#<portlet:namespace />previewFileContent');

	var so = new SWFObject(
		'<%= themeDisplay.getPathJavaScript() %>/misc/video_player/mpw_player.swf',
		'<portlet:namespace />previewFileContent',
		previewDivObject.getStyle('width'),
		previewDivObject.getStyle('height'),
		'9',
		'#000000'
	);

	so.addParam('allowFullScreen', 'true');

	if (<%= supportedAudio %>) {
		so.addVariable('<%= AudioProcessor.PREVIEW_TYPE %>', '<%= previewFileURL %>');
	}
	else if (<%= supportedVideo %>) {
		so.addVariable('<%= VideoProcessor.PREVIEW_TYPE %>', '<%= previewFileURL %>');
		so.addVariable('<%= VideoProcessor.THUMBNAIL_TYPE %>', '<%= videoThumbnailURL %>');
	}

	so.write('<portlet:namespace />previewFileContent');
</aui:script>