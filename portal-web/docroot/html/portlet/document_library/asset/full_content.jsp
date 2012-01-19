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
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

FileEntry fileEntry = fileVersion.getFileEntry();

boolean showThumbnail = false;

if (fileEntry.getVersion().equals(fileVersion.getVersion())) {
	showThumbnail = true;
}
%>

<div class="asset-resource-info">
	<aui:a href="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>">
		<c:choose>
			<c:when test="<%= showThumbnail && ImageProcessorUtil.hasImages(fileVersion) %>">
				<div>
					<img src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imageThumbnail=1") %>" />

					<%= fileVersion.getTitle() %>
				</div>
			</c:when>
			<c:when test="<%= showThumbnail && PDFProcessorUtil.hasImages(fileVersion) %>">
				<div>
					<img src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&documentThumbnail=1") %>" />

					<%= fileVersion.getTitle() %>
				</div>
			</c:when>
			<c:when test="<%= showThumbnail && VideoProcessorUtil.hasVideo(fileVersion) %>">
				<div>
					<img src="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1") %>" />

					<%= fileVersion.getTitle() %>
				</div>
			</c:when>
			<c:otherwise>
				<img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= fileVersion.getIcon() %>.png" /><%= HtmlUtil.escape(fileVersion.getTitle()) %>
			</c:otherwise>
		</c:choose>
	</aui:a>
</div>

<p class="asset-description"><%= HtmlUtil.escape(fileVersion.getDescription()) %></p>

<liferay-ui:custom-attributes-available className="<%= DLFileEntryConstants.getClassName() %>">
	<liferay-ui:custom-attribute-list
		className="<%= DLFileEntryConstants.getClassName() %>"
		classPK="<%= (fileVersion != null) ? fileVersion.getFileVersionId() : 0 %>"
		editable="<%= false %>"
		label="<%= true %>"
	/>
</liferay-ui:custom-attributes-available>