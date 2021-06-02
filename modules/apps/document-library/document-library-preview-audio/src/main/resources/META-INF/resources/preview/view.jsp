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
%>

<liferay-util:html-top
	outputKey="document_library_preview_audio_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/document-library-preview-css/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<div class="preview-file">
	<div class="preview-file-container">
		<audio
			class="preview-file-audio"
			controls
			controlsList="nodownload"
			style="max-width: <%= PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH %>px;"
		>

			<%
			for (String previewFileURL : previewFileURLs) {
				String type = null;

				if (Validator.isNotNull(previewFileURL)) {
					if (previewFileURL.endsWith("mp3")) {
						type = "audio/mp3";
					}
					else if (previewFileURL.endsWith("ogg")) {
						type = "audio/ogg";
					}
				}
			%>

				<c:if test="<%= type != null %>">
					<source src="<%= previewFileURL %>" type="<%= type %>" />
				</c:if>

			<%
			}
			%>

		</audio>
	</div>
</div>