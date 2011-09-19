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

String previewFileURL = ParamUtil.getString(request, "video");
String videoThumbnailURL = ParamUtil.getString(request, "thumbnailURL");

request.setAttribute("view_file_entry.jsp-supportedAudio", String.valueOf(supportedAudio));
request.setAttribute("view_file_entry.jsp-supportedVideo", String.valueOf(supportedVideo));

request.setAttribute("view_file_entry.jsp-previewFileURL", previewFileURL);
request.setAttribute("view_file_entry.jsp-videoThumbnailURL", videoThumbnailURL);
%>

<div class="lfr-preview-file lfr-preview-video" id="<portlet:namespace />previewFile">
	<div class="lfr-preview-file-content lfr-preview-video-content" id="<portlet:namespace />previewFileContent"></div>
</div>

<liferay-util:include page="/html/portlet/document_library/player.jsp" />