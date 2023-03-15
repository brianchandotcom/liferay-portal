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

import {sessionStorage} from 'frontend-js-web';

import isDefined from './functions/is_defined';

const NAMESPACE = 'com.liferay.search.experiences.web_';

/**
 * Keeps track of session ids so none are reused.
 */
export const SESSION_IDS = {
	ADD_SXP_ELEMENT_SIDEBAR: `${NAMESPACE}addSXPElementSidebar`,
	SUCCESS_MESSAGE: `${NAMESPACE}successMessage`,
};

export const SIDEBAR_STATE = {
	CLOSED: 'closed',
	OPEN: 'open',
};

/**
 * Gets the current state of the Add Elements Sidebar.
 * @return {string}
 */
export function getStorageAddSXPElementSidebar() {
	return sessionStorage.getItem(
		SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR,
		sessionStorage.TYPES.FUNCTIONAL
	);
}

/**
 * Helper function to set the session storage value of
 * SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR.
 * Toggles the state if `state` is undefined.
 * @param {String} state Either 'open' or 'closed'.
 */
export function setStorageAddSXPElementSidebar(state) {
	if (!isDefined(state)) {
		sessionStorage.setItem(
			SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR,
			sessionStorage.getItem(SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR) ===
				SIDEBAR_STATE.OPEN
				? SIDEBAR_STATE.CLOSED
				: SIDEBAR_STATE.OPEN,
			sessionStorage.TYPES.FUNCTIONAL
		);
	}
	else {
		sessionStorage.setItem(
			SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR,
			state,
			sessionStorage.TYPES.FUNCTIONAL
		);
	}

	if (process.env.NODE_ENV === 'development') {
		if (
			isDefined(state) &&
			state !== SIDEBAR_STATE.OPEN &&
			state !== SIDEBAR_STATE.CLOSED
		) {
			console.warn(
				`Session ID: ${SESSION_IDS.ADD_SXP_ELEMENT_SIDEBAR}`,
				`Parameter value must be ${SIDEBAR_STATE.OPEN} or ${SIDEBAR_STATE.CLOSED}'.`,
				`Use SIDEBAR_STATE.OPEN or SIDEBAR_STATE.CLOSE from utils/sessionStorage`,
				`'${state}' was passed in instead.`
			);
		}
	}
}
