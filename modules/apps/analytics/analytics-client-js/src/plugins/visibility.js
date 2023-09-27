/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
