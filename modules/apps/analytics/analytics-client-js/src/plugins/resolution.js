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
 * @param {object} request Request object to alter
 * @return {object} The updated request object
 */
function extendContextWithResolutionData(request) {
	const devicePixelRatio = window.devicePixelRatio || 1;

	const screenHeight =
		window.innerHeight ||
		document.documentElement.clientHeight ||
		document.body.clientHeight;

	const screenWidth =
		window.innerWidth ||
		document.documentElement.clientWidth ||
		document.body.clientWidth;

	request.context = {
		devicePixelRatio,
		screenHeight,
		screenWidth,
		...request.context,
	};

	return request;
}

/**
 * Plugin function that registers a custom middleware to alter the event context
 * with the current resolution of the browsers client area
 * @param {object} analytics The Analytics client
 */
function resolution(analytics) {
	analytics.registerMiddleware(extendContextWithResolutionData);
}

export {resolution};
export default resolution;
