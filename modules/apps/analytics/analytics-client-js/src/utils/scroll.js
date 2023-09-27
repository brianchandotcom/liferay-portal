/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Returns the current scroll position of the page
 * @returns {number} Scroll position of the page
 */
function getCurrentScrollPosition() {
	return window.pageYOffset || document.documentElement.scrollTop;
}

/**
 * Returns the entire height of the document
 * @returns {number} The normalized document height of the document
 */
function getDocumentHeight() {
	const heights = [
		document.body.clientHeight,
		document.documentElement.clientHeight,
		document.documentElement.scrollHeight,
	];

	return Math.max.apply(null, heights);
}

function getDimensions(element) {
	const height = getDocumentHeight();
	const top = getCurrentScrollPosition();

	let positions = {
		height,
		top,
	};

	if (element) {
		const boundingClientRect = element.getBoundingClientRect();
		const {bottom, height, top} = boundingClientRect;

		positions = {
			bottom,
			height,
			top,
		};
	}

	return positions;
}

function isPartiallyInViewport(element) {
	const {bottom, left, right, top} = element.getBoundingClientRect();

	const innerHeight =
		window.innerHeight || document.documentElement.clientHeight;
	const innerWidth =
		window.innerWidth || document.documentElement.clientWidth;

	const verticallyInViewport = top <= innerHeight && bottom >= 0;
	const horizontallyInViewport = left <= innerWidth && right >= 0;

	return verticallyInViewport && horizontallyInViewport;
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

	getDepthValue(element) {
		const {bottom, height, top} = getDimensions(element);
		const visibleArea = window.innerHeight;

		let depthValue = (visibleArea - top) / height;

		if (top <= 0 && bottom >= 0) {
			depthValue = visibleArea / (height + top);
		}
		else if (!element) {
			depthValue = (top + visibleArea) / height;
		}

		return depthValue;
	}

	/**
	 * Calculates the depth of the element on the page. If the
	 * element is not passed as a parameter the calculation must be
	 * performed to get the page depth
	 * @param {Object} element The Blog DOM element
	 * @returns {number} depth percentage from 0 to 100
	 */
	getDepth(element) {
		const value = this.getDepthValue(element);

		const depth = Math.round(value * 100);

		return Math.min(Math.max(depth, 0), 100);
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

		if (this.stepsReached.every((val) => val < depthLevel)) {
			this.stepsReached.push(depthLevel);

			if (depthLevel > 0) {
				fn(depthLevel * step);
			}
		}
	}
}

export {isPartiallyInViewport, ScrollTracker};
