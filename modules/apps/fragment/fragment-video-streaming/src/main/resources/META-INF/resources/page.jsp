<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:html-top
	outputKey="vide_sctreaming_css"
>
	<link href="https://vjs.zencdn.net/8.6.1/video-js.min.css" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div style="display: flex; justify-content: flex-start;">
	<div class="videojs-container">
		<video class="video-js" controls id="fragmentVideoJsURL" preload="auto">
			<source src="<%= (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_SOURCE_URL) %>" type="video/mp4" />
		</video>

		<script src="https://vjs.zencdn.net/8.6.1/video.min.js"></script>
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
			"subtitles", (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_SUBTITLES)
		).put(
			"videoHeight", (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_VIDEO_HEIGHT)
		).put(
			"videoWidth", (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_VIDEO_WIDTH)
		).build()
	%>'
	module="js/VideoStreaming"
/>