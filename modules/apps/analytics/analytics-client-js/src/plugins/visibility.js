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

import {PAGE} from '../utils/constants';

const applicationId = PAGE;
const beforeunloadEventListener = 'beforeunload';
let enableTabEvent = true;

/**
 * Handle differents browser versions to Visibility API
 */
function getHiddenKey() {
	let hidden;
	let visibilityChange;

	if (typeof document.hidden !== 'undefined') {
		hidden = 'hidden';
		visibilityChange = 'visibilitychange';
	}
	else if (typeof document.msHidden !== 'undefined') {
		hidden = 'msHidden';
		visibilityChange = 'msvisibilitychange';
	}
	else if (typeof document.webkitHidden !== 'undefined') {
		hidden = 'webkitHidden';
		visibilityChange = 'webkitvisibilitychange';
	}

	return {
		hidden,
		visibilityChange,
	};
}

/**
 * Sends tabBlurred or tabFocused event on visibilityChange
 * @param {Object} analytics The Analytics client
 */
function handleVisibilityChange(analytics) {
	const {hidden} = getHiddenKey();

	if (enableTabEvent) {
		if (document[hidden]) {
			analytics.send('tabBlurred', applicationId);
		}
		else {
			analytics.send('tabFocused', applicationId);
		}
	}
}

/**
 * Plugin function that registers listeners against browser visibility tabs
 * @param {Object} analytics The Analytics client
 */
function visibility(analytics) {
	const {visibilityChange} = getHiddenKey();

	if (visibilityChange) {
		const onVisibilityChange = handleVisibilityChange.bind(null, analytics);

		window.addEventListener(
			beforeunloadEventListener,
			enableTabEventHandle
		);
		document.addEventListener(visibilityChange, onVisibilityChange);

		return () => {
			window.removeEventListener(
				beforeunloadEventListener,
				enableTabEventHandle
			);
			document.removeEventListener(visibilityChange, onVisibilityChange);
		};
	}
}

function enableTabEventHandle() {
	enableTabEvent = false;
}

export {visibility};
export default visibility;
