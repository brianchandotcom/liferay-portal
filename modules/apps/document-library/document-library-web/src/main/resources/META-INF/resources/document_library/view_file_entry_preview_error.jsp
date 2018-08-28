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

<div class="alert alert-info">
	<c:choose>
		<c:when test="<%= exception instanceof DLPreviewSizeException %>">
			<liferay-ui:message key="file-is-too-large-for-preview-or-thumbnail-generation" />
		</c:when>
		<c:when test="<%= exception instanceof DLPreviewGenerationInProcessException %>">
			<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= fileVersion.getTitle() %>" key="cannot-generate-preview-for-x" />
		</c:otherwise>
	</c:choose>
</div>