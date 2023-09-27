<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		else {
			previewURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, null);
		}
	}
	%>

	<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= previewURL %>);"></div>
</c:if>