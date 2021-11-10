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

<%@ include file="/image_gallery_display/init.jsp" %>

<%
boolean supportedAudio = false;
boolean supportedVideo = false;

String mp3PreviewURL = ParamUtil.getString(request, "mp3PreviewURL");
String mp4PreviewURL = ParamUtil.getString(request, "mp4PreviewURL");
String oggPreviewURL = ParamUtil.getString(request, "oggPreviewURL");
String ogvPreviewURL = ParamUtil.getString(request, "ogvPreviewURL");
String videoThumbnailURL = ParamUtil.getString(request, "thumbnailURL");

List<String> previewFileURLs = new ArrayList<String>(4);

if (Validator.isNotNull(mp3PreviewURL)) {
	previewFileURLs.add(mp3PreviewURL);

	supportedAudio = true;
}

if (Validator.isNotNull(mp4PreviewURL)) {
	previewFileURLs.add(mp4PreviewURL);

	supportedVideo = true;
}

if (Validator.isNotNull(oggPreviewURL)) {
	previewFileURLs.add(oggPreviewURL);

	supportedAudio = true;
}

if (Validator.isNotNull(ogvPreviewURL)) {
	previewFileURLs.add(ogvPreviewURL);

	supportedVideo = true;
}

request.setAttribute("view_file_entry.jsp-supportedAudio", String.valueOf(supportedAudio));
request.setAttribute("view_file_entry.jsp-supportedVideo", String.valueOf(supportedVideo));

request.setAttribute("view_file_entry.jsp-previewFileURLs", previewFileURLs.toArray(new String[0]));
request.setAttribute("view_file_entry.jsp-videoThumbnailURL", videoThumbnailURL);
%>

<c:choose>
	<c:when test="<%= supportedAudio %>">
		<div class="lfr-preview-audio" id="<portlet:namespace />previewFile">
			<div class="lfr-preview-audio-content pl-0" id="<portlet:namespace />previewFileContent"></div>
		</div>
	</c:when>
	<c:when test="<%= supportedVideo %>">
		<div class="lfr-preview-file lfr-preview-video position-relative" id="<portlet:namespace />previewFile">
			<div class="border-bottom border-secondary lfr-preview-file-content lfr-preview-video-content pb-5 pl-0 position-relative" id="<portlet:namespace />previewFileContent"></div>
		</div>
	</c:when>
</c:choose>

<liferay-util:include page="/image_gallery_display/player.jsp" servletContext="<%= application %>" />