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
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

if (fileVersion == null) {
	fileVersion = fileEntry.getFileVersion();
}

boolean showThumbnail = false;

if (fileEntry.getVersion().equals(fileVersion.getVersion())) {
	showThumbnail = true;
}

DLProcessor dlProcessor = DLProcessorRegistryUtil.getDLProcessor(DLProcessorConstants.IMAGE_PROCESSOR);

boolean hasImages = false;

if (dlProcessor != null) {
	ImageProcessor imageProcessor = (ImageProcessor)dlProcessor;

	hasImages = imageProcessor.hasImages(fileVersion);
}

dlProcessor = DLProcessorRegistryUtil.getDLProcessor(DLProcessorConstants.PDF_PROCESSOR);

boolean hasPDFImages = false;

if (dlProcessor != null) {
	PDFProcessor pdfProcessor = (PDFProcessor)dlProcessor;

	hasPDFImages = pdfProcessor.hasImages(fileVersion);
}

dlProcessor = DLProcessorRegistryUtil.getDLProcessor(DLProcessorConstants.VIDEO_PROCESSOR);

boolean hasVideo = false;

if (dlProcessor != null) {
	VideoProcessor videoProcessor = (VideoProcessor)dlProcessor;

	hasVideo = videoProcessor.hasVideo(fileVersion);
}
%>

<p class="asset-description">
	<%= HtmlUtil.escape(StringUtil.shorten(fileEntry.getDescription(), abstractLength)) %>
</p>

<c:if test="<%= fileVersion.isApproved() %>">
	<div class="asset-resource-info">
		<c:choose>
			<c:when test="<%= showThumbnail && hasImages %>">
				<div>
					<img alt="" src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imageThumbnail=1") %>" />
				</div>
			</c:when>
			<c:when test="<%= showThumbnail && hasPDFImages %>">
				<div>
					<img alt="" src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&documentThumbnail=1") %>" />
				</div>
			</c:when>
			<c:when test="<%= showThumbnail && hasVideo %>">
				<div>
					<img alt="" src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1") %>" />
				</div>
			</c:when>
			<c:otherwise>

				<%
				String taglibFileEntryTitle = "<span class='aui-helper-hidden-accessible'>" + fileEntry.getTitle() + "</span>";
				%>

				<liferay-ui:icon
					image="download"
					label="<%= true %>"
					message='<%= LanguageUtil.format(pageContext, "download-x", taglibFileEntryTitle) + " (" + TextFormatter.formatStorageSize(fileVersion.getSize(), locale) + ")" %>'
					url="<%= assetRenderer.getURLDownload(themeDisplay) %>"
				/>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>