/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';

const TARGET_OFFSET = 20;

export const GeometryType = PropTypes.shape({
	height: PropTypes.number.isRequired,
	left: PropTypes.number.isRequired,
	right: PropTypes.number.isRequired,
	top: PropTypes.number.isRequired,
	width: PropTypes.number.isRequired,
});

/**
 * Given a DOM Event object or a React synthetic event, stops all propagation.
 */
export function stopImmediatePropagation(event) {
	if (event.nativeEvent) {

		// This is a React synthetic event; must access nativeEvent instead.

		event.nativeEvent.stopImmediatePropagation();
	}
	else {
		event.stopImmediatePropagation();
	}
}

/**
 * Returns all targetable elements within `element`
 *
 * Currently, that means all visible "a", "button" and "input[type=submit]"
 * elements which have an "id".
 */
export function getTargetableElements(element, selectedTarget) {

	// Allowed targetable elements with id

	const selector = ['a[id]', 'button[id]', 'input[type=submit][id]'];

	// Other targetable element already selected

	if (selectedTarget) {
		selector.push(`#${selectedTarget}`);
	}

	// Look for links, buttons or inputs with type submit

	const elements = element.querySelectorAll(selector.join());

	// As first cut, only deal with visible items

	return Array.from(elements).filter((element) => {
		return _isVisible(element);
	});
}

/**
 * Checks `element` for visibility
 *
 * Useful, for example, to exclude elements that are concealed inside dropdowns.
 */
function _isVisible(element) {
	const {display, opacity, visibility} = getComputedStyle(element);

	const hidden =
		display === 'none' || opacity === 0 || visibility !== 'visible';

	return !hidden;
}

/**
 * Get dimensional information about `element`.
 *
 * Used here to get measurements for the "root" ("#content") element.
 */
export function getRootElementGeometry(rootElement) {
	const {
		height,
		left,
		right,
		top,
		width,
	} = rootElement.getBoundingClientRect();

	return {
		height: height + TARGET_OFFSET,
		left: left + TARGET_OFFSET / 2,
		right: right - TARGET_OFFSET / 2,
		top: top + TARGET_OFFSET / 2,
		width: width + TARGET_OFFSET,
	};
}

/**
 * Get dimensional information about `element`.
 *
 * Used here to get measurements for the "target" element.
 */
export function getElementGeometry(element) {
	const {
		bottom,
		height,
		left,
		right,
		top,
		width,
	} = element.getBoundingClientRect();

	return {
		bottom,
		height: height + TARGET_OFFSET,
		left,
		right,
		top,
		width: width + TARGET_OFFSET,
	};
}
