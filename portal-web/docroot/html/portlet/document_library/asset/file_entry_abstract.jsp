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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

boolean showThumbnail = false;

if (fileEntry.getVersion().equals(fileVersion.getVersion())) {
	showThumbnail = true;
}
%>

<p class="asset-description">
	<%= HtmlUtil.escape(StringUtil.shorten(fileEntry.getDescription(), abstractLength)) %>
</p>

<c:if test="<%= fileVersion.isApproved() %>">
	<div class="asset-resource-info">
		<c:choose>
			<c:when test="<%= showThumbnail && ImageProcessorUtil.hasImages(fileVersion) %>">
				<div>
					<img alt="<liferay-ui:message key="image" />" src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imageThumbnail=1") %>" />
				</div>
			</c:when>
			<c:when test="<%= showThumbnail && PDFProcessorUtil.hasImages(fileVersion) %>">
				<div>
					<img alt="<liferay-ui:message key="document" />" src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&documentThumbnail=1") %>" />
				</div>
			</c:when>
			<c:when test="<%= showThumbnail && VideoProcessorUtil.hasVideo(fileVersion) %>">
				<div>
					<img alt="<liferay-ui:message key="video" />" src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1") %>" />
				</div>
			</c:when>
			<c:otherwise>

				<%
				String taglibFileEntryTitle = "<span class='hide-accessible'>" + fileEntry.getTitle() + "</span>";
				%>

				<liferay-ui:icon
					iconCssClass="icon-download"
					label="<%= true %>"
					message='<%= LanguageUtil.format(pageContext, "download-x", taglibFileEntryTitle, false) + " (" + TextFormatter.formatStorageSize(fileVersion.getSize(), locale) + ")" %>'
					url="<%= assetRenderer.getURLDownload(themeDisplay) %>"
				/>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>