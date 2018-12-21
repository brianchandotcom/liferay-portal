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
List<String> previewFileURLs = (List<String>)request.getAttribute(DLPreviewAudioWebKeys.PREVIEW_FILE_URLS);

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_view_file_entry_preview") + StringPool.UNDERLINE;

	Map<String, Object> context = new HashMap<>();

	context.put(
		"audioSources",
		new ArrayList<Map<String, String>>() {
			{
				for (String previewFileURL : previewFileURLs) {
					if (Validator.isNotNull(previewFileURL)) {
						if (previewFileURL.endsWith("mp3")) {
							add(MapUtil.fromArray("type", "audio/mp3", "url", previewFileURL));
						}
						else if (previewFileURL.endsWith("ogg")) {
							add(MapUtil.fromArray("type", "audio/ogg", "url", previewFileURL));
						}
					}
				}
			}
		});
%>

<liferay-util:html-top
	outputKey="document_library_preview_audio_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/preview/css/main.css") %>" rel="stylesheet" type="text/css" />

	<style type="text/css">
		.preview-file .preview-file-audio {
			max-width: <%= PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH %>px;
		}
	</style>
</liferay-util:html-top>

<soy:component-renderer
	componentId='<%= renderResponse.getNamespace() + randomNamespace + "Audio" %>'
	context="<%= context %>"
	module="<%= (String)request.getAttribute(DLPreviewAudioWebKeys.MODULE_PATH) %>"
	templateNamespace="com.liferay.document.library.preview.AudioPreviewer.render"
/>