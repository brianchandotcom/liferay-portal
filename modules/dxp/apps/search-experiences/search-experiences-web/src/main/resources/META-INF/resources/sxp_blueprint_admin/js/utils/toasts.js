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

import {openToast} from 'frontend-js-web';

const SUCCESS_MESSAGE_SESSION_ID =
	'com.liferay.search.experiences.web_successMessage';

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
	const successMessage = sessionStorage.getItem(SUCCESS_MESSAGE_SESSION_ID);

	if (successMessage) {
		openSuccessToast({message: successMessage});

		sessionStorage.removeItem(SUCCESS_MESSAGE_SESSION_ID);
	}
}

/**
 * Sets the success toast to appear on a redirected page. The redirected page
 * must use `openInitialSuccessToast` to show the success message that was set.
 * @param {String} message The success message to display in the toast.
 */
export function setInitialSuccessToast(message) {
	return sessionStorage.setItem(SUCCESS_MESSAGE_SESSION_ID, message);
}
