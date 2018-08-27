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

<%
FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);

boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
int status = ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_ANY);

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_view_file_entry_preview") + StringPool.UNDERLINE;

boolean emptyPreview = false;
String[] previewFileURLs = null;

if (hasAudio) {
	String previewQueryString = "&audioPreview=1";

	if (status != WorkflowConstants.STATUS_ANY) {
		previewQueryString += "&status=" + status;
	}

	emptyPreview = true;
	previewFileURLs = new String[PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS.length];

	for (int i = 0; i < PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS.length; i++) {
		if (AudioProcessorUtil.getPreviewFileSize(fileVersion, PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS[i]) > 0) {
			emptyPreview = false;
			previewFileURLs[i] = DLUtil.getPreviewURL(fileVersion.getFileEntry(), fileVersion, themeDisplay, previewQueryString + "&type=" + PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS[i]);
		}
	}
}
%>

<c:choose>
	<c:when test="<%= emptyPreview %>">
		<div class="alert alert-info">
			<liferay-ui:message arguments="<%= fileVersion.getTitle() %>" key="cannot-generate-preview-for-x" />
		</div>
	</c:when>
	<c:when test="<%= !hasAudio %>">
		<div class="alert alert-info">
			<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="lfr-preview-audio" id="<portlet:namespace /><%= randomNamespace %>previewFile">
			<div class="lfr-preview-audio-content" id="<portlet:namespace /><%= randomNamespace %>previewFileContent"></div>
		</div>

		<%
		String mp3PreviewFileURL = null;
		String oggPreviewFileURL = null;

		for (String previewFileURL : previewFileURLs) {
			if (Validator.isNotNull(previewFileURL)) {
				if (previewFileURL.endsWith("mp3")) {
					mp3PreviewFileURL = previewFileURL;
				}
				else if (previewFileURL.endsWith("ogg")) {
					oggPreviewFileURL = previewFileURL;
				}
			}
		}
		%>

		<aui:script use="aui-audio">
			var playing = false;

			var audio = new A.Audio(
				{
					contentBox: '#<portlet:namespace /><%= randomNamespace %>previewFileContent',
					fixedAttributes: {
						allowfullscreen: 'true',
						wmode: 'opaque'
					}

					<c:if test="<%= Validator.isNotNull(oggPreviewFileURL) %>">
						, oggUrl: '<%= HtmlUtil.escapeJS(oggPreviewFileURL) %>'
					</c:if>

					<c:if test="<%= Validator.isNotNull(mp3PreviewFileURL) %>">
						, url: '<%= HtmlUtil.escapeJS(mp3PreviewFileURL) %>'
					</c:if>
				}
			).render();

			if (audio._audio) {
				var audioNode = audio._audio.getDOMNode();

				audioNode.addEventListener(
					'pause',
					function() {
						playing = false;
					}
				);

				audioNode.addEventListener(
					'play',
					function() {
						window.parent.Liferay.fire('<portlet:namespace /><%= randomNamespace %>Audio:play');

						playing = true;
					}
				);
			}

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:currentIndexChange',
				function() {
					if (playing) {
						audio.pause();
					}
				}
			);

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:close',
				function() {
					audio.load();
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>