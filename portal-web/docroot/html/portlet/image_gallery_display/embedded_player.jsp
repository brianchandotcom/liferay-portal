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

<%@ include file="/html/portlet/image_gallery_display/init.jsp" %>

<%
boolean supportedAudio = ParamUtil.getBoolean(request, "supportedAudio");
boolean supportedVideo = ParamUtil.getBoolean(request, "supportedVideo");

String audioPreviewURL = ParamUtil.getString(request, "audio");
String mp4PreviewURL = ParamUtil.getString(request, "mp4PreviewURL");
String ogvPreviewURL = ParamUtil.getString(request, "ogvPreviewURL");
String videoThumbnailURL = ParamUtil.getString(request, "thumbnailURL");

String[] previewFileURLs = null;

if (Validator.isNotNull(mp4PreviewURL) && Validator.isNotNull(ogvPreviewURL)){
	previewFileURLs = new String[2];
	previewFileURLs[0]= mp4PreviewURL;
	previewFileURLs[1] = ogvPreviewURL;
}
else if (Validator.isNotNull(mp4PreviewURL) || Validator.isNotNull(ogvPreviewURL)){
	previewFileURLs = new String[1];

	if (mp4PreviewURL != null) {
		previewFileURLs[0] = mp4PreviewURL;
	}
	else {
		previewFileURLs[0] = ogvPreviewURL;
	}
}
else {
	previewFileURLs = new String[1];
	previewFileURLs[0] = audioPreviewURL;
}

request.setAttribute("view_file_entry.jsp-supportedAudio", String.valueOf(supportedAudio));
request.setAttribute("view_file_entry.jsp-supportedVideo", String.valueOf(supportedVideo));

request.setAttribute("view_file_entry.jsp-previewFileURLs", previewFileURLs);
request.setAttribute("view_file_entry.jsp-videoThumbnailURL", videoThumbnailURL);
%>

<div class="lfr-preview-file lfr-preview-video" id="<portlet:namespace />previewFile">
	<div class="lfr-preview-file-content lfr-preview-video-content" id="<portlet:namespace />previewFileContent"></div>
</div>

<liferay-util:include page="/html/portlet/document_library/player.jsp" />