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

import {
	MARK_NAVIGATION_START,
	MARK_VIEW_DURATION,
	PAGE,
} from '../utils/constants';
import {getDuration} from '../utils/performance';

const applicationId = PAGE;

/**
 * Sends page load information on the window load event
 * @param {Object} analytics The Analytics client
 */
function onload(analytics) {
	const perfData = window.performance.timing;

	const pageLoadTime = perfData.loadEventStart - perfData.navigationStart;

	const props = {
		pageLoadTime,
	};

	analytics.send('pageLoaded', applicationId, props);
}

/**
 * Sends view duration information on the window unload event
 * @param {Object} analytics The Analytics client
 */
function unload(analytics) {
	const navigationStartMark = window.performance.getEntriesByName(
		MARK_NAVIGATION_START
	);
	const navigationStart = navigationStartMark.length
		? MARK_NAVIGATION_START
		: 'navigationStart';

	const duration = getDuration(MARK_VIEW_DURATION, navigationStart);

	const props = {
		viewDuration: duration,
	};

	analytics.send('pageUnloaded', applicationId, props);
}

/**
 * Plugin function that registers listeners against browser time events
 * @param {Object} analytics The Analytics client
 */
function timing(analytics) {
	const onLoad = onload.bind(null, analytics);

	window.addEventListener('load', onLoad);

	const onUnload = unload.bind(null, analytics);

	window.addEventListener('unload', onUnload);

	return () => {
		window.removeEventListener('load', onLoad);
		window.removeEventListener('unload', onUnload);
	};
}

export {timing};
export default timing;
