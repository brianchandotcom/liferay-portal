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
	outputKey="document_library_video_embed_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/embed.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="video-embed-placeholder">
	<clay:icon
		symbol="video"
	/>

	<div class="video-embed-placeholder-text">
		<liferay-ui:message key="no-video-preview-available" />
	</div>
</div>