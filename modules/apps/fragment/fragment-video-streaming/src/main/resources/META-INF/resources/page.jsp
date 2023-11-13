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

<div style="display: flex; justify-content: left;">
	<div class="videojs-container">
		<video class="video-js" controls id="fragmentVideoJsURL" preload="auto">
			<source src="<%= (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_SOURCE_URL) %>" type="video/mp4" />
		</video>

		<script src="https://vjs.zencdn.net/8.6.1/video.min.js"></script>
	</div>
</div>

<script>
	const content = document.querySelector('.videojs-container');

	const configuration = {
		autoplay: <%= (Boolean)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_AUTOPLAY) %>,
		loop: <%= (Boolean)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_LOOP) %>,
		muted: <%= (Boolean)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_MUTED) %>,
		videoHeight:
			'<%= (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_VIDEO_HEIGHT) %>',
		videoWidth:
			'<%= (String)request.getAttribute(VideoStreamingWebKeys.VIDEO_STREAMING_VIDEO_WIDTH) %>',
	};

	const height = configuration.videoHeight
		? configuration.videoHeight.replace('px', '')
		: configuration.videoHeight;
	const width = configuration.videoWidth
		? configuration.videoWidth.replace('px', '')
		: configuration.videoWidth;

	function resizeVideoJs() {
		const boundingClientRect = content.parentElement.getBoundingClientRect();

		const contentWidth = width || boundingClientRect.width;

		const contentHeight = height || contentWidth * 0.5625;

		content.firstElementChild.style.height = contentHeight + 'px';
		content.firstElementChild.style.width = contentWidth + 'px';
	}

	const player = videojs('fragmentVideoJsURL', configuration);

	player.ready(() => {
		window.addEventListener('resize', resizeVideoJs);

		resizeVideoJs();
	});
</script>