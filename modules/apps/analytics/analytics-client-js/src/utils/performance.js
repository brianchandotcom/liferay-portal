/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Get Duration between marks
 * @param {string} measureName
 * @param {string} startMark
 * @param {string} [endMark=undefined]
 * @returns {number}
 */
export function getDuration(measureName, startMark, endMark = undefined) {
	window.performance.measure(measureName, startMark, endMark);

	const {duration} = window.performance.getEntriesByName(measureName).pop();

	return ~~duration;
}

/**
 * Create mark
 * @param {string} markName
 */
export function createMark(markName) {
	window.performance.clearMarks(markName);
	window.performance.mark(markName);
}
