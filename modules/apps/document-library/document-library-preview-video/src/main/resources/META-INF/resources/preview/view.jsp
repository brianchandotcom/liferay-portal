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

Map<String, Object> context = new HashMap<>();

List<String> previewFileURLs = (List<String>)request.getAttribute(DLPreviewVideoWebKeys.PREVIEW_FILE_URLS);

context.put(
	"videoSources",
	new ArrayList<Map<String, String>>() {
		{
			for (String previewFileURL : previewFileURLs) {
				if (Validator.isNotNull(previewFileURL)) {
					if (previewFileURL.endsWith("mp4")) {
						add(MapUtil.fromArray("type", "video/mp4", "url", previewFileURL));
					}
					else if (previewFileURL.endsWith("ogv")) {
						add(MapUtil.fromArray("type", "video/ogv", "url", previewFileURL));
					}
				}
			}
		}
	});
context.put("videoPosterURL", (String)request.getAttribute(DLPreviewVideoWebKeys.VIDEO_POSTER_URL));
%>

<liferay-util:html-top
	outputKey="document_library_preview_video_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/preview/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<soy:component-renderer
	componentId='<%= renderResponse.getNamespace() + randomNamespace + "previewVideo" %>'
	context="<%= context %>"
	module="preview/js/VideoPreviewer.es"
	templateNamespace="com.liferay.document.library.preview.VideoPreviewer.render"
/>