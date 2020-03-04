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

<%@ include file="/document_library/ct_display/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileVersion);
%>

<p>
	<b><liferay-ui:message key="id" /></b>: <%= fileEntry.getFileEntryId() %>
</p>

<p>
	<b><liferay-ui:message key="version" /></b>: <%= fileVersion.getVersion() %>
</p>

<p>
	<b><liferay-ui:message key="title" /></b>: <%= HtmlUtil.escape(fileVersion.getTitle()) %>
</p>

<p>
	<b><liferay-ui:message key="description" /></b>: <%= HtmlUtil.escape(fileVersion.getDescription()) %>
</p>

<b><liferay-ui:message key="content" /></b>:

<liferay-util:html-top
	outputKey="document_library_preview_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/document_library/css/document_library_preview.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="view">
	<div class="body-row">
		<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

		<%
		dlViewFileVersionDisplayContext.renderPreview(request, response);
		%>

	</div>
</div>