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

<%@ include file="/init.jsp" %>

<liferay-util:html-top
	outputKey="document_library_external_video_embed_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/document_library/css/embed.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="video-embed-placeholder">
	<span aria-hidden="true" class="loading-animation"></span>

	<div class="video-embed-placeholder-text">
		<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
	</div>
</div>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute(FileEntry.class.getName());
%>

<portlet:resourceURL id="/document_library/get_embed_video_status" var="getEmbedVideoStatusURL">
	<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
</portlet:resourceURL>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"getEmbedVideoStatusURL", getEmbedVideoStatusURL
		).build()
	%>'
	module="document_library/js/embed/generating"
/>