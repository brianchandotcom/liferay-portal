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

<%@ include file="/document_library/info/item/renderer/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);
%>

<c:if test="<%= fileVersion.isApproved() %>">

	<%
	String previewURL = StringPool.BLANK;

	if (Objects.equals(fileEntry.getVersion(), fileVersion.getVersion())) {
		if (ImageProcessorUtil.hasImages(fileVersion)) {
			previewURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imagePreview=1");
		}
		else if (PDFProcessorUtil.hasImages(fileVersion)) {
			previewURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&previewFileIndex=1");
		}
		else if (VideoProcessorUtil.hasVideo(fileVersion)) {
			previewURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1");
		}
	}
	%>

	<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= previewURL %>);"></div>
</c:if>