/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */

const container = fragmentElement.querySelector('.dsr-primary-logo');

if (container) {
	const apiImg = container.querySelector('.dsr-logo-api');
	const customElement = container.querySelector(
		'[data-lfr-editable-id="primary-logo"]'
	);
	const customImg =
		customElement && customElement.tagName === 'IMG'
			? customElement
			: customElement && customElement.querySelector('img');
	const logoName = container.querySelector('.dsr-logo-name');

	const hasCustomImage =
		customImg &&
		customImg.getAttribute('src') &&
		!customImg.getAttribute('src').startsWith('data:image') &&
		customImg.getAttribute('src') !== '';

	if (apiImg) {
		apiImg.style.opacity = hasCustomImage ? '0' : '1';
	}

	if (customElement) {
		customElement.style.opacity = hasCustomImage ? '1' : '0';
	}

	if (logoName) {
		logoName.style.opacity = hasCustomImage || apiImg ? '0' : '1';
	}
}

if (!window.onFileEntrySaved) {
	window.onFileEntrySaved = (data) => {

		// eslint-disable-next-line no-console
		console.info(data);
	};

	Liferay.on('fileEntrySaved', window.onFileEntrySaved);
}

if (!window.onMessagePosted) {
	window.onMessagePosted = (data) => {

		// eslint-disable-next-line no-console
		console.info(data);
	};

	Liferay.on('messagePosted', window.onMessagePosted);
}
