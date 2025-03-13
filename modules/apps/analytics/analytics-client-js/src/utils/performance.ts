/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Get Duration between marks
 */
export function getDuration(
	measureName: string,
	startMark: string,
	endMark?: string
) {
	window.performance.measure(measureName, startMark, endMark);

	const entry = window.performance.getEntriesByName(measureName).pop();

	return entry && typeof entry.duration === 'number' ? ~~entry.duration : 0;
}

/**
 * Create mark
 */
export function createMark(markName: string) {
	window.performance.clearMarks(markName);
	window.performance.mark(markName);
}
