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

AUI().use('aui-base', () => {
	const frameElement = window.frameElement;

	if (
		!frameElement ||
		frameElement.getAttribute('id') !== 'simulationDeviceIframe'
	) {
		return;
	}

	document.body.classList.add('lfr-has-simulation-panel');

	frameElement.onload = function () {
		const loadingIndicator = window.parent.document.getElementsByClassName(
			'loading-animation-simulation-device'
		)[0];

		if (loadingIndicator) {
			loadingIndicator.remove();
		}

		deviceWrapper.classList.remove('lfr-device--is-navigating');
	};

	const deviceWrapper = window.parent.document.getElementsByClassName(
		'lfr-device'
	)[0];

	Liferay.on('beforeNavigate', () => {
		const loadingIndicator = document.createElement('section');
		loadingIndicator.classList.add('loading-animation-simulation-device');
		loadingIndicator.innerHTML = `
			<span aria-hidden="true" class="loading-animation"></span>
		`;

		deviceWrapper.parentNode.appendChild(loadingIndicator);
		deviceWrapper.classList.add('lfr-device--is-navigating');
	});

	const url = new URL(frameElement.contentWindow.location.href);
	const searchParams = new URLSearchParams(url.search);

	if (searchParams.has('p_l_mode')) {
		return;
	}

	searchParams.append('p_l_mode', 'preview');

	frameElement.contentWindow.location.replace(
		`${url.origin}${url.pathname}?${searchParams.toString()}`
	);
});
