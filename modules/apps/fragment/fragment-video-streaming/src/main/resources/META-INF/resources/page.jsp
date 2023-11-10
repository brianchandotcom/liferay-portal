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
		<video class="video-js" id="fragmentVideoJsURL" preload="auto">
			<source src="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4" type="video/mp4" />
		</video>

		<script src="https://vjs.zencdn.net/8.6.1/video.min.js"></script>
	</div>
</div>
q
<script>
	const content= document.querySelector('.videojs-container');

	//TODO get from the rendered
	const configuration = {
		autoplay: 'muted',
		height: null,
		loop: true,
		muted: true,
		width: null,
	}

	function resizeVideoJs() {
		const boundingClientRect = content.parentElement.getBoundingClientRect();

		const contentWidth = configuration.width || boundingClientRect.width;

		const contentHeight = configuration.height || contentWidth * 0.5625;

		content.firstElementChild.style.height = contentHeight + 'px';
		content.firstElementChild.style.width = contentWidth + 'px';
	}

	const player = videojs('fragmentVideoJsURL', {
		controls: true,
		...configuration
	});

	player.ready(() => {
		console.log('ready!');
		window.addEventListener('resize', resizeVideoJs);

		resizeVideoJs();
	});


</script>