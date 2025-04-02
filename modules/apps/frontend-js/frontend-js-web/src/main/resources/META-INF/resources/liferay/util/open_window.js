/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getTop from './get_top';

export function openInDialog(event, config) {
	event.preventDefault();

	const {currentTarget} = event;

	// eslint-disable-next-line prefer-object-spread
	config = Object.assign(
		{},

		// eslint-disable-next-line prefer-object-spread
		Object.assign({}, currentTarget.dataset),
		config
	);

	if (!config.uri) {
		config.uri =
			currentTarget.dataset.href || currentTarget.getAttribute('href');
	}

	if (!config.title) {
		config.title = currentTarget.getAttribute('title');
	}

	openWindow(config);
}

export default function openWindow(config, callback) {
	const topUtil = getTop();

	config.openingWindow = window;

	topUtil.Liferay.Util.openModal(config, callback);
}
