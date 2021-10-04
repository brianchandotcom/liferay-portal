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
