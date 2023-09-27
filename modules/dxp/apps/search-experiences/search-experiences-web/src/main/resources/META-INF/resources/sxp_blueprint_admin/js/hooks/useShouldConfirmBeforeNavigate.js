/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';
import {useEffect} from 'react';

/**
 * This ensures that a confirmation appears upon navigating away if there are
 * warnings such as unsaved changes. It adds a listener to the window to
 * detect when the user closes the window or refreshes the page. There is also
 * an SPA lifecycle event used to detect when the user cancels or navigates to
 * a new page within Liferay.
 *
 * @param {boolean|Function} requiresConfirmation True if the user should be prompted
 * @param {string} message Message to display in prompt
 */
export default function useShouldConfirmBeforeNavigate(
	requiresConfirmation,
	message = Liferay.Language.get(
		'you-have-unsaved-changes-do-you-want-to-proceed-without-saving'
	)
) {
	useEffect(() => {
		const getRequiresConfirmationValue = () => {
			if (typeof requiresConfirmation === 'function') {
				return requiresConfirmation();
			}
			else {
				return requiresConfirmation;
			}
		};

		// The beforeunload event occurs when user reloads or closes the window. The
		// confirmation is specific to the browser. Using preventDefault() will
		// signal the browser to show the default confirmation.

		const handleBeforeUnload = (event) => {
			if (getRequiresConfirmationValue()) {
				event.preventDefault();

				// Setting returnValue is required for activation in certain browsers like Chrome.
				// Its string message was once used to customize the confirmation message, but now
				// for security purposes, each browser is in control of its own message.
				// https://developer.mozilla.org/en-US/docs/Web/API/WindowEventHandlers/onbeforeunload

				event.returnValue = '';
			}
		};

		// The beforeNavigate event occurs when the user navigates to a new page on
		// Liferay. Using preventDefault() prevents the browser from navigating to
		// the new page.
		// https://help.liferay.com/hc/en-us/articles/360030709511-Available-SPA-Lifecycle-Events

		const handleBeforeNavigate = (event) => {
			if (getRequiresConfirmationValue()) {
				openConfirmModal({
					message,
					onConfirm: (isConfirmed) => {
						if (!isConfirmed) {
							event.originalEvent.preventDefault();
						}
					},
				});
			}
		};

		window.addEventListener('beforeunload', handleBeforeUnload);
		Liferay.on('beforeNavigate', handleBeforeNavigate);

		return () => {
			window.removeEventListener('beforeunload', handleBeforeUnload);
			Liferay.detach('beforeNavigate', handleBeforeNavigate);
		};
	}, [requiresConfirmation, message]);
}
