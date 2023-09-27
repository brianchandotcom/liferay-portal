/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast, sessionStorage} from 'frontend-js-web';

import {SESSION_IDS} from './sessionStorage';

export function openErrorToast(config) {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
		...config,
	});
}

export function openSuccessToast(config) {
	openToast({
		message: Liferay.Language.get('your-request-completed-successfully'),
		title: Liferay.Language.get('success'),
		type: 'success',
		...config,
	});
}

/**
 * Used for showing a success toast when the page first loads. For example,
 * when a new blueprint is created and redirected to the edit page.
 */
export function openInitialSuccessToast() {
	const successMessage = sessionStorage.getItem(
		SESSION_IDS.SUCCESS_MESSAGE,
		sessionStorage.TYPES.NECESSARY
	);

	if (successMessage) {
		openSuccessToast({message: successMessage});

		sessionStorage.removeItem(SESSION_IDS.SUCCESS_MESSAGE);
	}
}

/**
 * Sets the success toast to appear on a redirected page. The redirected page
 * must use `openInitialSuccessToast` to show the success message that was set.
 * @param {String} message The success message to display in the toast.
 */
export function setInitialSuccessToast(message) {
	return sessionStorage.setItem(
		SESSION_IDS.SUCCESS_MESSAGE,
		message,
		sessionStorage.TYPES.NECESSARY
	);
}
