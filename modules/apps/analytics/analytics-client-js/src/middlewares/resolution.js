/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Updates context with browser resolution information
 * @param {Object} request Request object to alter
 * @returns {Object} The updated request object
 */
function resolution(request) {
	const devicePixelRatio = window.devicePixelRatio || 1;

	const screenHeight =
		window.innerHeight ||
		document.documentElement.clientHeight ||
		document.body.clientHeight;

	const screenWidth =
		window.innerWidth ||
		document.documentElement.clientWidth ||
		document.body.clientWidth;

	Object.assign(request.context, {
		devicePixelRatio,
		screenHeight,
		screenWidth,
	});

	return request;
}

export {resolution};
export default resolution;
