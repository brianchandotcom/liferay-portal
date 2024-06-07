/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function VideoStreaming({
	autoplay,
	loop,
	muted,
	src,
	subtitles,
	videoHeight,
	videoWidth,
}) {
	const content = document.querySelector('.videojs-container');

	const configuration = {
		autoplay,
		html5: {
			hls: {
				overrideNative: true,
			},
			vhs: {overrideNative: true},
		},
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
		const boundingClientRect =
			content.parentElement.getBoundingClientRect();

		const contentWidth = width || boundingClientRect.width;

		const contentHeight = height || contentWidth * 0.5625;

		const videoContainer = content.querySelector('.video-js');
		videoContainer.style.height = contentHeight + 'px';
		videoContainer.style.width = contentWidth + 'px';
	}

	// eslint-disable-next-line no-undef
	const player = videojs('fragmentVideoJsURL', configuration);

	player.ready(() => {
		window.addEventListener('resize', resizeVideoJs);

		resizeVideoJs();

		if (src) {
			player.src(src);

			if (subtitles) {
				player.addRemoteTextTrack({
					default: true,
					kind: 'subtitles',
					label: 'English',
					language: 'en',
					src: subtitles,
				});
			}

			player.qualitySelectorHls({
				vjsIconClass: 'vjs-icon-cog',
			});
		}
	});
}
