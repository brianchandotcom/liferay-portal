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

function getDimensions(element) {
	if (element) {
		const boundingClientRect = element.getBoundingClientRect();
		const {height, top} = boundingClientRect;
		return {height, top};
	}
	const height = getDocumentHeight();
	const top = getCurrentScrollPosition();
	return {height, top};
}

class ScrollTracker {
	constructor(steps = 4) {
		this.steps = steps;
		this.stepsReached = [];
	}

	dispose() {
		this.steps = null;
		this.stepsReached = null;
	}

	getDepth(element) {
		const {top, height} = getDimensions(element);

		let depth = 0;

		if (element) {
			depth = -top / height;
		} else {
			const visibleArea = window.innerHeight;

			depth = (top + visibleArea) / height;
		}

		return Math.round(depth * 100);
	}

	/**
	 * Processes the current scroll location and calculates the scroll depth level
	 * If the client reaches one of the pre-defined levels that is yet unreached
	 * it sends an analytics event
	 * @param {Function} The callback function that will process the depth reached.
	 */
	onDepthReached(fn, element) {
		const depth = this.getDepth(element);
		const step = Math.floor(100 / this.steps);

		const depthLevel = Math.floor(depth / step);

		if (!this.stepsReached.some(val => val === depthLevel)) {
			this.stepsReached.push(depthLevel);

			if (depthLevel > 0) {
				fn(depthLevel * step);
			}
		}
	}
}

export {ScrollTracker};