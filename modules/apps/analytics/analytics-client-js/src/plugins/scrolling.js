import debounce from 'metal-debounce';

const levelsReached = [];
const scrollDepthLevels = 4;

/**
 * Returns the current scroll position of the page
 * @return {number} Scroll position of the page
 */
function getCurrentScrollPosition() {
	return window.pageYOffset || document.documentElement.scrollTop;
}

/**
 * Returns the entire height of the document
 * @return {number} The normalized document height of the document
 */
function getDocumentHeight() {
	const heights = [
		document.body.clientHeight,
		document.documentElement.clientHeight,
		document.documentElement.scrollHeight,
	];

	return Math.max(...heights);
}

/**
 * Processes the current scroll location and calculates the scroll depth level
 * If the client reaches one of the pre-defined levels that is yet unreached
 * it sends an analytics event
 * @param {object} analytics The Analytics client
 */
function processScrollPosition(analytics) {
	const pageHeight = getDocumentHeight();
	const scrollTop = getCurrentScrollPosition();
	const visibleArea = window.innerHeight;

	const depth = Math.round((scrollTop + visibleArea) / pageHeight * 100);
	const step = Math.floor(100 / scrollDepthLevels);

	const depthLevel = Math.floor(depth / step);

	if (!levelsReached.some(val => val === depthLevel)) {
		levelsReached.push(depthLevel);

		if (depthLevel > 0) {
			analytics.send('depthReached', 'scrolling', {
				scrollDepth: depthLevel * step,
			});
		}
	}
}

/**
 * Plugin function that registers listener against scroll event
 * @param {object} analytics The Analytics client
 */
function scrolling(analytics) {
	document.addEventListener(
		'scroll',
		debounce(processScrollPosition.bind(null, analytics), 1500)
	);

	// Reset levels on SPA-enabled environments

	window.addEventListener('load', () =>
		levelsReached.splice(0, levelsReached.length)
	);
}

export {scrolling};
export default scrolling;