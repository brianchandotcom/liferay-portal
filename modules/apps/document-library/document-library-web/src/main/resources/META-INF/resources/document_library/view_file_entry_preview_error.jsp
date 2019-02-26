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

<%@ include file="/document_library/init.jsp" %>

<%
Exception exception = (Exception)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_PREVIEW_EXCEPTION);
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);
%>

	<c:choose>
		<c:when test="<%= exception instanceof DLPreviewSizeException %>">
			<div class="file-entry-preview-error-container">
				<h3>File too big to preview</h3>
				<p class="text-secondary">File exceeds size limit to preview, download to view it.</p>
				<clay:link
					buttonStyle="secondary"
					href="<%= DLUtil.getDownloadURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, StringPool.BLANK) %>"
					label='<%= LanguageUtil.get(resourceBundle, "download") %>'
					title='<%= LanguageUtil.get(resourceBundle, "download") + " (" + TextFormatter.formatStorageSize(fileVersion.getSize(), locale) + ")" %>'
				/>
			</div>
			<%--<liferay-ui:message key="file-is-too-large-for-preview-or-thumbnail-generation" />--%>
		</c:when>
		<c:when test="<%= exception instanceof DLPreviewGenerationInProcessException %>">
			<div class="alert alert-info">
				<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
			</div>
		</c:when>
		<c:otherwise>
			<div class="file-entry-preview-error-container">
				<h3>No preview available</h3>
				<p class="text-secondary">Hmm... looks like this item doesn't have a preview we can show you.</p>
			</div>
			<%--<liferay-ui:message arguments="<%= fileVersion.getTitle() %>" key="cannot-generate-preview-for-x" />--%>
		</c:otherwise>
	</c:choose>
