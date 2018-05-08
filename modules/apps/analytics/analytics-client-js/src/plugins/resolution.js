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