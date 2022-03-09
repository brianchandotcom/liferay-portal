/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {isDefined} from './utils';

const NAMESPACE = 'com.liferay.search.experiences.web_';

/**
 * Keeps track of session ids so none are reused.
 */
export const SESSION_IDS = {
	ADD_SXP_ELEMENT_SIDEBAR: `${NAMESPACE}addSXPElementSidebar`,
	SUCCESS_MESSAGE: `${NAMESPACE}successMessage`,
};

/**
 * Helper function to set the session storage value of
 * SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR.
 * Toggles the state if `state` is undefined.
 * @param {String} state Either 'open' or 'closed'.
 */
export function setItemAddSXPElementSidebar(state) {
	if (!isDefined(state)) {
		sessionStorage.setItem(
			SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR,
			sessionStorage.getItem(SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR) ===
				'open'
				? 'closed'
				: 'open'
		);
	}
	else {
		sessionStorage.setItem(SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR, state);
	}

	if (process.env.NODE_ENV === 'development') {
		if (isDefined(state) && state !== 'open' && state !== 'closed') {
			console.warn(
				`Session ID: ${SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR}`,
				`Parameter value must be 'open' or 'closed'.`,
				`'${state}' was passed in instead.`
			);
		}
	}
}
