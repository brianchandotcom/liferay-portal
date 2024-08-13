<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div style="display: flex; justify-content: flex-start; overflow: hidden;">
	<div class="videojs-container">
		<aui:link href="https://vjs.zencdn.net/8.6.1/video-js.min.css" rel="stylesheet" type="text/css" />
		<aui:link href="https://unpkg.com/videojs-quality-selector-hls@1.1.1/dist/videojs-quality-selector-hls.css" rel="stylesheet" type="text/css" />

		<video class="video-js" controls id="fragmentVideoJsURL" preload="auto">
		</video>

		<aui:script src="https://vjs.zencdn.net/8.6.1/video.min.js"></aui:script>

		<aui:script src="https://unpkg.com/videojs-quality-selector-hls@1.1.1/dist/videojs-quality-selector-hls.js" type="text/javascript"></aui:script>
	</div>
</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"autoplay", (Boolean)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_AUTOPLAY)
		).put(
			"loop", (Boolean)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_LOOP)
		).put(
			"muted", (Boolean)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_MUTED)
		).put(
			"src", (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_SOURCE_URL)
		).put(
			"subtitles", (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_SUBTITLES)
		).put(
			"videoHeight", (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_VIDEO_HEIGHT)
		).put(
			"videoWidth", (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_VIDEO_WIDTH)
		).build()
	%>'
	module="{VideoStreaming} from fragment-video-streaming"
/>