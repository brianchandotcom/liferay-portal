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

<%@ include file="/preview/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_view_file_entry_preview") + StringPool.UNDERLINE;

FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

int previewFileCount = PDFProcessorUtil.getPreviewFileCount(fileVersion);

String previewQueryString = "&previewFileIndex=";

int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_ANY);

if (status != WorkflowConstants.STATUS_ANY) {
	previewQueryString += "&status=" + status;
}

String[] previewFileURLs = new String[1];

previewFileURLs[0] = DLURLHelperUtil.getPreviewURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, previewQueryString);

String previewFileURL = previewFileURLs[0];

Map<String, Object> context = new HashMap<>();

context.put("baseImageURL", previewFileURL);
context.put("currentPage", 1);
context.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
context.put("totalPages", previewFileCount);
%>

<liferay-util:html-top
	outputKey="document_library_preview_document_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/preview/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<soy:component-renderer
	componentId='<%= renderResponse.getNamespace() + randomNamespace + "previewDocument" %>'
	context="<%= context %>"
	module="preview/js/DocumentPreviewer.es"
	templateNamespace="com.liferay.document.library.preview.DocumentPreviewer.render"
/>