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
		<div class="preview-file-error-container">
			<h3><liferay-ui:message key="file-too-big-to-preview" /></h3>

			<p class="text-secondary">
				<liferay-ui:message key="file-exceeds-size-limit-to-preview-download-to-view-it" />
			</p>

			<clay:link
				buttonStyle="secondary"
				href="<%= DLURLHelperUtil.getDownloadURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, StringPool.BLANK) %>"
				label='<%= LanguageUtil.get(resourceBundle, "download") %>'
				title='<%= LanguageUtil.format(resourceBundle, "file-size-x", TextFormatter.formatStorageSize(fileVersion.getSize(), locale), false) %>'
			/>
		</div>
	</c:when>
	<c:when test="<%= exception instanceof DLPreviewGenerationInProcessException %>">
		<clay:alert
			message='<%= LanguageUtil.get(resourceBundle, "generating-preview-will-take-a-few-minutes") %>'
			title='<%= LanguageUtil.get(request, "info") + ":" %>'
		/>
	</c:when>
	<c:otherwise>
		<div class="preview-file-error-container">
			<h3><liferay-ui:message key="no-preview-available" /></h3>

			<p class="text-secondary">
				<liferay-ui:message key="hmm-looks-like-this-item-doesnt-have-a-preview-we-can-show-you" />
			</p>
		</div>
	</c:otherwise>
</c:choose>