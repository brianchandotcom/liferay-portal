/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DEFAULT_OPTIONS = {
	directories: 0,
	height: 480,
	left: 80,
	location: 1,
	menubar: 1,
	resizable: 1,
	scrollbars: 'yes',
	status: 0,
	toolbar: 0,
	top: 180,
	width: 640,
};

export default function printPage(url, options = DEFAULT_OPTIONS) {
	const nextOptions = {
		...DEFAULT_OPTIONS,
		...options,
	};

	const arrayOptions = Object.entries(nextOptions).map(
		(key, value) => `${key}=${value}`
	);

	const stringOptions = arrayOptions.join(',');

	window.open(url, '', stringOptions);
}
