<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
Exception exception = (Exception)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_PREVIEW_EXCEPTION);
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);
%>

<c:choose>
	<c:when test="<%= exception instanceof DLPreviewSizeException %>">

		<%
		DLPreviewSizeException dlPreviewSizeException = (DLPreviewSizeException)exception;
		%>

		<div class="preview-file-error-container">
			<h3><liferay-ui:message key="no-preview-available" /></h3>

			<p class="text-secondary">

				<%
				long maxFileSize = dlPreviewSizeException.getMaxFileSize();

				if (maxFileSize == 0) {
					maxFileSize = DLProcessorHelperUtil.getPreviewableProcessorMaxSize(fileVersion.getGroupId());
				}
				%>

				<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(maxFileSize, locale) %>" key="this-file-is-too-large-to-preview.-the-maximum-file-size-for-previews-is-x" />
			</p>

			<clay:link
				displayType="primary"
				href="<%= DLURLHelperUtil.getDownloadURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, StringPool.BLANK) %>"
				icon="download"
				label="download"
				title='<%= LanguageUtil.format(resourceBundle, "file-size-x", LanguageUtil.formatStorageSize(fileVersion.getSize(), locale), false) %>'
				type="button"
			/>
		</div>
	</c:when>
	<c:when test="<%= exception instanceof DLPreviewGenerationInProcessException %>">
		<clay:stripe
			message="generating-preview-will-take-a-few-minutes"
		/>
	</c:when>
	<c:otherwise>
		<div class="preview-file-error-container">
			<h3><liferay-ui:message key="no-preview-available" /></h3>

			<p class="text-secondary">
				<liferay-ui:message key="hmm-looks-like-this-item-does-not-have-a-preview-we-can-show-you" />
			</p>
		</div>
	</c:otherwise>
</c:choose>