<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
String randomNamespace = GetterUtil.getString(request.getAttribute("view_file_entry.jsp-randomNamespace"));

boolean supportedAudio = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedAudio"));
boolean supportedVideo = GetterUtil.getBoolean((String)request.getAttribute("view_file_entry.jsp-supportedVideo"));

String[] previewFileURLs = (String[])request.getAttribute("view_file_entry.jsp-previewFileURLs");
String videoThumbnailURL = (String)request.getAttribute("view_file_entry.jsp-videoThumbnailURL");

String mp3PreviewFileURL = null;
String mp4PreviewFileURL = null;
String oggPreviewFileURL = null;
String ogvPreviewFileURL = null;

for (String previewFileURL : previewFileURLs) {
	if (Validator.isNotNull(previewFileURL)) {
		if (previewFileURL.endsWith("mp3")) {
			mp3PreviewFileURL = previewFileURL;
		}
		else if (previewFileURL.endsWith("mp4")) {
			mp4PreviewFileURL = previewFileURL;
		}
		else if (previewFileURL.endsWith("ogg")) {
			oggPreviewFileURL = previewFileURL;
		}
		else if (previewFileURL.endsWith("ogv")) {
			ogvPreviewFileURL = previewFileURL;
		}
	}
}
%>

<c:choose>
	<c:when test="<%= supportedAudio %>">
		<aui:script use="aui-audio">
			var playing = false;

			var audio = new A.Audio({
				contentBox:
					'#<portlet:namespace /><%= randomNamespace %>previewFileContent',
				fixedAttributes: {
					allowfullscreen: 'true',
					wmode: 'opaque',
				},

				<c:if test="<%= Validator.isNotNull(oggPreviewFileURL) %>">
					oggUrl: '<%= HtmlUtil.escapeJS(oggPreviewFileURL) %>',
				</c:if>

				<c:if test="<%= Validator.isNotNull(mp3PreviewFileURL) %>">
					url: '<%= HtmlUtil.escapeJS(mp3PreviewFileURL) %>',
				</c:if>
			}).render();

			if (audio._audio) {
				var audioNode = audio._audio.getDOMNode();

				audioNode.addEventListener('pause', () => {
					playing = false;
				});

				audioNode.addEventListener('play', () => {
					window.parent.Liferay.fire(
						'<portlet:namespace /><%= randomNamespace %>Audio:play'
					);

					playing = true;
				});
			}

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:currentIndexChange',
				() => {
					if (playing) {
						audio.pause();
					}
				}
			);

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:close',
				() => {
					audio.load();
				}
			);
		</aui:script>
	</c:when>
	<c:when test="<%= supportedVideo %>">
		<aui:script use="aui-base,aui-video">
			var playing = false;

			var video = new A.Video({
				contentBox:
					'#<portlet:namespace /><%= randomNamespace %>previewFileContent',
				fixedAttributes: {
					allowfullscreen: 'true',
					bgColor: '#000000',
					wmode: 'opaque',
				},

				on: {
					pause: function () {
						playing = false;
					},
					play: function () {
						window.parent.Liferay.fire(
							'<portlet:namespace /><%= randomNamespace %>Video:play'
						);

						playing = true;
					},
				},

				<c:if test="<%= Validator.isNotNull(ogvPreviewFileURL) %>">
					ogvUrl: '<%= HtmlUtil.escapeJS(ogvPreviewFileURL) %>',
				</c:if>

				poster: '<%= HtmlUtil.escapeJS(videoThumbnailURL) %>',

				<c:if test="<%= Validator.isNotNull(mp4PreviewFileURL) %>">
					url: '<%= HtmlUtil.escapeJS(mp4PreviewFileURL) %>',
				</c:if>
			}).render();

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:currentIndexChange',
				() => {
					if (playing) {
						video.pause();
					}
				}
			);

			window.parent.Liferay.on(
				'<portlet:namespace /><%= randomNamespace %>ImageViewer:close',
				() => {
					video.load();
				}
			);
		</aui:script>
	</c:when>
</c:choose>