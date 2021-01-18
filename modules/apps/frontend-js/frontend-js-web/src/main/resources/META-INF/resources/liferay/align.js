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
 * Returns true if value is a document.
 * @param {*} val
 * @return {boolean}
 */
function isDocument(val) {
	return val && typeof val === 'object' && val.nodeType === 9;
}

/**
 * Returns true if value is a window.
 * @param {*} val
 * @return {boolean}
 */
function isWindow(val) {
	return val && val === val.window;
}

/**
 * Gets the client height or width of the specified node. Scroll height is
 * not included.
 * @param {Element|Document|Window=} node
 * @param {string} property `Width` or `Height` property.
 * @return {number}
 */
function getClientSize(node, property) {
	let element = node;

	if (isWindow(node)) {
		element = node.document.documentElement;
	}
	if (isDocument(node)) {
		element = node.documentElement;
	}

	return element[`client${property}`];
}

/**
 * Gets the region of the element, document or window.
 * @param {Element|Document|Window=} element Optional element to test.
 * @return {!DOMRect} The returned value is a simulated DOMRect object which
 *     is the union of the rectangles returned by getClientRects() for the
 *     element, i.e., the CSS border-boxes associated with the element.
 */
function getDocumentRegion(element) {
	const height = getHeight(element);
	const width = getWidth(element);

	return makeRegion(height, height, 0, width, 0, width);
}

/**
 * Gets the height of the specified node. Scroll height is included.
 * @param {Element|Document|Window=} node
 * @return {number}
 */
function getHeight(node) {
	return getSize(node, 'Height');
}

/**
 * Gets the top offset position of the given node. This fixes the `offsetLeft` value of
 * nodes that were translated, which don't take that into account at all. That makes
 * the calculation more expensive though, so if you don't want that to be considered
 * either pass `ignoreTransform` as true or call `offsetLeft` directly on the node.
 * @param {!Element} node
 * @param {boolean=} ignoreTransform When set to true will ignore transform css
 *   when calculating the position. Defaults to false.
 * @return {number}
 */
function getOffsetLeft(node, ignoreTransform) {
	return node.offsetLeft + (ignoreTransform ? 0 : getTranslation(node).left);
}

/**
 * Gets the top offset position of the given node. This fixes the `offsetTop` value of
 * nodes that were translated, which don't take that into account at all. That makes
 * the calculation more expensive though, so if you don't want that to be considered
 * either pass `ignoreTransform` as true or call `offsetTop` directly on the node.
 * @param {!Element} node
 * @param {boolean=} ignoreTransform When set to true will ignore transform css
 *   when calculating the position. Defaults to false.
 * @return {number}
 */
function getOffsetTop(node, ignoreTransform) {
	return node.offsetTop + (ignoreTransform ? 0 : getTranslation(node).top);
}

/**
 * Gets the size of an element and its position relative to the viewport.
 * @param {!Document|Element|Window} node
 * @param {boolean=} includeScroll Flag indicating if the document scroll
 *   position should be considered in the element's region coordinates. Defaults
 *   to false.
 * @return {!DOMRect} The returned value is a DOMRect object which is the
 *     union of the rectangles returned by getClientRects() for the element,
 *     i.e., the CSS border-boxes associated with the element.
 */
function getRegion(node, includeScroll) {
	if (!node) {
		return {bottom: 0, height: 0, left: 0, right: 0, top: 0, width: 0};
	}

	if (isDocument(node) || isWindow(node)) {
		return getDocumentRegion(node);
	}

	return makeRegionFromBoundingRect(
		node.getBoundingClientRect(),
		includeScroll
	);
}

/**
 * Gets the scroll left position of the specified node.
 * @param {Element|Document|Window=} node
 * @return {number}
 */
function getScrollLeft(node) {
	if (isWindow(node)) {
		return node.pageXOffset;
	}
	if (isDocument(node)) {
		return node.defaultView.pageXOffset;
	}

	return node.scrollLeft;
}

/**
 * Gets the scroll top position of the specified node.
 * @param {Element|Document|Window=} node
 * @return {number}
 */
function getScrollTop(node) {
	if (isWindow(node)) {
		return node.pageYOffset;
	}
	if (isDocument(node)) {
		return node.defaultView.pageYOffset;
	}

	return node.scrollTop;
}

/**
 * Gets the height or width of the specified node. Scroll height is
 * included.
 * @param {Element|Document|Window=} node
 * @param {string} prop `Width` or `Height` property.
 * @return {number}
 */
function getSize(node, property) {
	if (isWindow(node)) {
		return getClientSize(node, property);
	}
	if (isDocument(node)) {
		const documentElement = node.documentElement;

		return Math.max(
			node.body[`scroll${property}`],
			documentElement[`scroll${property}`],
			node.body[`offset${property}`],
			documentElement[`offset${property}`],
			documentElement[`client${property}`]
		);
	}

	return Math.max(
		node[`client${property}`],
		node[`scroll${property}`],
		node[`offset${property}`]
	);
}

/**
 * Gets the transform matrix values for the given node.
 * @param {!Element} node
 * @return {Array<number>}
 */
function getTransformMatrixValues(node) {
	const style = getComputedStyle(node);

	const transform =
		style.mozTransform || style.msTransform || style.transform;

	if (transform !== 'none') {
		const values = [];
		const regex = /([\d-.\s]+)/g;
		let matches = regex.exec(transform);

		while (matches) {
			values.push(matches[1]);
			matches = regex.exec(transform);
		}

		return values;
	}
}

/**
 * Gets the number of translated pixels for the given node, for both the top and
 * left positions.
 * @param {!Element} node
 * @return {number}
 */
function getTranslation(node) {
	const values = getTransformMatrixValues(node);
	const translation = {
		left: 0,
		top: 0,
	};
	if (values) {
		translation.left = parseFloat(
			values.length === 6 ? values[4] : values[13]
		);
		translation.top = parseFloat(
			values.length === 6 ? values[5] : values[14]
		);
	}

	return translation;
}

/**
 * Gets the width of the specified node. Scroll width is included.
 * @param {Element|Document|Window=} node
 * @return {number}
 */
function getWidth(node) {
	return getSize(node, 'Width');
}

/**
 * Tests if a region intersects with another.
 * @param {DOMRect} sourceRectangle
 * @param {DOMRect} targetRectangle
 * @return {boolean}
 */
function intersectRegion(sourceRectangle, targetRectangle) {
	return intersectRect(
		sourceRectangle.top,
		sourceRectangle.left,
		sourceRectangle.bottom,
		sourceRectangle.right,
		targetRectangle.top,
		targetRectangle.left,
		targetRectangle.bottom,
		targetRectangle.right
	);
}

/**
 * Tests if a rectangle intersects with another.
 *
 * Note that coordinates starts from top to down (y), left to right (x):
 *
 * @param {number} x0 Horizontal coordinate of P0.
 * @param {number} y0 Vertical coordinate of P0.
 * @param {number} x1 Horizontal coordinate of P1.
 * @param {number} y1 Vertical coordinate of P1.
 * @param {number} x2 Horizontal coordinate of P2.
 * @param {number} y2 Vertical coordinate of P2.
 * @param {number} x3 Horizontal coordinate of P3.
 * @param {number} y3 Vertical coordinate of P3.
 * @return {boolean}
 */
function intersectRect(x0, y0, x1, y1, x2, y2, x3, y3) {
	return !(x2 > x1 || x3 < x0 || y2 > y1 || y3 < y0);
}

/**
 * Tests if a region is inside another.
 * @param {DOMRect} sourceRectangle
 * @param {DOMRect} targetRectangle
 * @return {boolean}
 */
function insideRegion(sourceRectangle, targetRectangle) {
	return (
		targetRectangle.top >= sourceRectangle.top &&
		targetRectangle.bottom <= sourceRectangle.bottom &&
		targetRectangle.right <= sourceRectangle.right &&
		targetRectangle.left >= sourceRectangle.left
	);
}

/**
 * Tests if a region is inside viewport region.
 * @param {DOMRect} region
 * @return {boolean}
 */
function insideViewport(region) {
	return insideRegion(getRegion(window), region);
}

/**
 * Computes the intersection region between two regions.
 * @param {DOMRect} sourceRectangle
 * @param {DOMRect} targetRectangle
 * @return {?DOMRect} Intersection region or null if regions doesn't
 *     intersects.
 */
function intersection(sourceRectangle, targetRectangle) {
	if (!intersectRegion(sourceRectangle, targetRectangle)) {
		return null;
	}
	const bottom = Math.min(sourceRectangle.bottom, targetRectangle.bottom);
	const right = Math.min(sourceRectangle.right, targetRectangle.right);
	const left = Math.max(sourceRectangle.left, targetRectangle.left);
	const top = Math.max(sourceRectangle.top, targetRectangle.top);

	return makeRegion(bottom, bottom - top, left, right, top, right - left);
}

/**
 * Makes a region object. It's a writable version of DOMRect.
 * @param {number} bottom
 * @param {number} height
 * @param {number} left
 * @param {number} right
 * @param {number} top
 * @param {number} width
 * @return {!DOMRect} The returned value is a DOMRect object which is the
 *     union of the rectangles returned by getClientRects() for the element,
 *     i.e., the CSS border-boxes associated with the element.
 */
function makeRegion(bottom, height, left, right, top, width) {
	return {
		bottom,
		height,
		left,
		right,
		top,
		width,
	};
}

/**
 * Makes a region from a DOMRect result from `getBoundingClientRect`.
 * @param  {!DOMRect} rectangle The returned value is a DOMRect object which is the
 *     union of the rectangles returned by getClientRects() for the element,
 *     i.e., the CSS border-boxes associated with the element.
 * @param {boolean=} includeScroll Flag indicating if the document scroll
 *   position should be considered in the element's region coordinates. Defaults
 *   to false.
 * @return {DOMRect} Writable version of DOMRect.
 */
function makeRegionFromBoundingRect(rectangle, includeScroll = false) {
	const deltaX = includeScroll ? getScrollLeft(document) : 0;
	const deltaY = includeScroll ? getScrollTop(document) : 0;

	return makeRegion(
		rectangle.bottom + deltaY,
		rectangle.height,
		rectangle.left + deltaX,
		rectangle.right + deltaX,
		rectangle.top + deltaY,
		rectangle.width
	);
}

/**
 * Align utility. Computes region or best region to align an element with
 * another. Regions are relative to viewport, make sure to use element with
 * position fixed, or position absolute when the element first positioned
 * parent is the body element.
 */

/**
 * Constants that represent the supported positions for `Align`.
 */
const ALIGN_POSITIONS = {
	BottomCenter: 4,
	BottomLeft: 5,
	BottomRight: 3,
	LeftCenter: 6,
	RightCenter: 2,
	TopCenter: 0,
	TopLeft: 7,
	TopRight: 1,
};

/**
 * Aliases for position constants.
 */
ALIGN_POSITIONS.Bottom = ALIGN_POSITIONS.BottomCenter;
ALIGN_POSITIONS.Left = ALIGN_POSITIONS.LeftCenter;
ALIGN_POSITIONS.Right = ALIGN_POSITIONS.RightCenter;
ALIGN_POSITIONS.Top = ALIGN_POSITIONS.TopCenter;

export {ALIGN_POSITIONS};

/**
 * Aligns the element with the best region around alignElement. The best
 * region is defined by clockwise rotation starting from the specified
 * `position`. The element is always aligned in the middle of alignElement
 * axis.
 * @param {!Element} element Element to be aligned.
 * @param {!Element} alignElement Element to align with.
 * @param {ALIGN_POSITIONS.Top|ALIGN_POSITIONS.Right|ALIGN_POSITIONS.Bottom|ALIGN_POSITIONS.Left} position
 *     The initial position to try. Options `ALIGN_POSITIONS.Top`, `ALIGN_POSITIONS.Right`,
 *     `ALIGN_POSITIONS.Bottom`, `ALIGN_POSITIONS.Left`.
 * @param {boolean} autoBestAlign Option to suggest or not the best region
 *      to align.
 * @return {string} The final chosen position for the aligned element.
 */
export function align(element, alignElement, position, autoBestAlign = true) {
	let bestRegion;

	if (autoBestAlign) {
		const suggestion = suggestAlignBestRegion(
			element,
			alignElement,
			position
		);
		position = suggestion.position;
		bestRegion = suggestion.region;
	}
	else {
		bestRegion = getAlignRegion(element, alignElement, position);
	}

	const computedStyle = window.getComputedStyle(element);
	if (computedStyle.getPropertyValue('position') !== 'fixed') {
		bestRegion.top += window.pageYOffset;
		bestRegion.left += window.pageXOffset;

		let offsetParent = element;
		while ((offsetParent = offsetParent.offsetParent)) {
			bestRegion.top -= getOffsetTop(offsetParent);
			bestRegion.left -= getOffsetLeft(offsetParent);
		}
	}

	element.style.top = bestRegion.top + 'px';
	element.style.left = bestRegion.left + 'px';

	return position;
}

/**
 * Returns the best region to align element with alignElement. This is similar
 * to `suggestAlignBestRegion`, but it only returns the region information,
 * while `suggestAlignBestRegion` also returns the chosen position.
 * @param {!Element} element Element to be aligned.
 * @param {!Element} alignElement Element to align with.
 * @param {ALIGN_POSITIONS.Top|ALIGN_POSITIONS.Right|ALIGN_POSITIONS.Bottom|ALIGN_POSITIONS.Left} position
 *     The initial position to try. Options `ALIGN_POSITIONS.Top`, `ALIGN_POSITIONS.Right`,
 *     `ALIGN_POSITIONS.Bottom`, `ALIGN_POSITIONS.Left`.
 * @return {DOMRect} Best region to align element.
 */
export function getAlignBestRegion(element, alignElement, position) {
	return suggestAlignBestRegion(element, alignElement, position).region;
}

/**
 * Returns the region to align element with alignElement. The element is
 * always aligned in the middle of alignElement axis.
 * @param {!Element} element Element to be aligned.
 * @param {!Element} alignElement Element to align with.
 * @param {ALIGN_POSITIONS.Top|ALIGN_POSITIONS.Right|ALIGN_POSITIONS.Bottom|ALIGN_POSITIONS.Left} position
 *     The position to align. Options `ALIGN_POSITIONS.Top`, `ALIGN_POSITIONS.Right`,
 *     `ALIGN_POSITIONS.Bottom`, `ALIGN_POSITIONS.Left`.
 * @return {DOMRect} Region to align element.
 */
export function getAlignRegion(element, alignElement, position) {
	const targetRectangle = getRegion(alignElement);
	const sourceRectangle = getRegion(element);
	let top = 0;
	let left = 0;

	switch (position) {
		case ALIGN_POSITIONS.TopCenter:
			top = targetRectangle.top - sourceRectangle.height;
			left =
				targetRectangle.left +
				targetRectangle.width / 2 -
				sourceRectangle.width / 2;
			break;
		case ALIGN_POSITIONS.RightCenter:
			top =
				targetRectangle.top +
				targetRectangle.height / 2 -
				sourceRectangle.height / 2;
			left = targetRectangle.left + targetRectangle.width;
			break;
		case ALIGN_POSITIONS.BottomCenter:
			top = targetRectangle.bottom;
			left =
				targetRectangle.left +
				targetRectangle.width / 2 -
				sourceRectangle.width / 2;
			break;
		case ALIGN_POSITIONS.LeftCenter:
			top =
				targetRectangle.top +
				targetRectangle.height / 2 -
				sourceRectangle.height / 2;
			left = targetRectangle.left - sourceRectangle.width;
			break;
		case ALIGN_POSITIONS.TopRight:
			top = targetRectangle.top - sourceRectangle.height;
			left = targetRectangle.right - sourceRectangle.width;
			break;
		case ALIGN_POSITIONS.BottomRight:
			top = targetRectangle.bottom;
			left = targetRectangle.right - sourceRectangle.width;
			break;
		case ALIGN_POSITIONS.BottomLeft:
			top = targetRectangle.bottom;
			left = targetRectangle.left;
			break;
		case ALIGN_POSITIONS.TopLeft:
			top = targetRectangle.top - sourceRectangle.height;
			left = targetRectangle.left;
			break;
		default:
			break;
	}

	return {
		bottom: top + sourceRectangle.height,
		height: sourceRectangle.height,
		left,
		right: left + sourceRectangle.width,
		top,
		width: sourceRectangle.width,
	};
}

/**
 * Looks for the best region for aligning the given element. The best
 * region is defined by clockwise rotation starting from the specified
 * `position`. The element is always aligned in the middle of alignElement
 * axis.
 * @param {!Element} element Element to be aligned.
 * @param {!Element} alignElement Element to align with.
 * @param {ALIGN_POSITIONS.Top|ALIGN_POSITIONS.Right|ALIGN_POSITIONS.Bottom|ALIGN_POSITIONS.Left} position
 *     The initial position to try. Options `ALIGN_POSITIONS.Top`, `ALIGN_POSITIONS.Right`,
 *     `ALIGN_POSITIONS.Bottom`, `ALIGN_POSITIONS.Left`.
 * @return {{position: string, region: DOMRect}} Best region to align element.
 */
export function suggestAlignBestRegion(element, alignElement, position) {
	let bestArea = 0;
	let bestPosition = position;
	let bestRegion = getAlignRegion(element, alignElement, bestPosition);
	let tryPosition = bestPosition;
	let tryRegion = bestRegion;
	const viewportRegion = getRegion(window);

	for (let i = 0; i < 8; ) {
		if (intersectRegion(viewportRegion, tryRegion)) {
			const visibleRegion = intersection(viewportRegion, tryRegion);
			const area = visibleRegion.width * visibleRegion.height;
			if (area > bestArea) {
				bestArea = area;
				bestRegion = tryRegion;
				bestPosition = tryPosition;
			}
			if (insideViewport(tryRegion)) {
				break;
			}
		}
		tryPosition = (position + ++i) % 8;
		tryRegion = getAlignRegion(element, alignElement, tryPosition);
	}

	return {
		position: bestPosition,
		region: bestRegion,
	};
}
