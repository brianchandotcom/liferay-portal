<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

FileVersion fileVersion = fileEntry.getFileVersion();

boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);
boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);

boolean showImageContainer = false;
%>

<div class="view">
	<div class="body-row">
		<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

		<%@ include file="/html/portlet/document_library/view_file_entry_preview.jspf" %>
	</div>
</div>