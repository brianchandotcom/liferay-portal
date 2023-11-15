/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function VideoStreaming({
	autoplay,
	loop,
	muted,
	subtitles,
	videoHeight,
	videoWidth,
}) {
	const content = document.querySelector('.videojs-container');

	const configuration = {
		autoplay,
		loop,
		muted,
		playbackRates: [0.5, 1, 1.5, 2],
		videoHeight,
		videoWidth,
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

		if (subtitles) {
			player.addRemoteTextTrack({
				default: true,
				kind: 'subtitles',
				label: 'English',
				language: 'en',
				src: subtitles,
			});
		}
	});
}
