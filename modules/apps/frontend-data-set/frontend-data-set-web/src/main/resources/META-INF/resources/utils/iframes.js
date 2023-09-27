/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OPEN_MODAL, OPEN_MODAL_FROM_IFRAME} from './eventsDefinitions';
import {logError} from './logError';

export function isPageInIframe() {
	return window.location !== window.parent.location;
}

const registeredModals = new Set();
let modalsCounter = 0;

if (!isPageInIframe()) {
	Liferay.on(OPEN_MODAL_FROM_IFRAME, (payload) => {
		if (!registeredModals.size) {
			return logError('No registered modals found.');
		}

		const modalsArray = Array.from(registeredModals);
		const lastRegisteredModal = modalsArray[modalsArray.length - 1];

		Liferay.fire(OPEN_MODAL, {
			...payload,
			id: lastRegisteredModal,
		});
	});
}

Liferay.on('beforeNavigate', () => {
	registeredModals.clear();
});

export function subscribeModal() {
	registeredModals.add(++modalsCounter);

	return modalsCounter;
}

export function unsubscribeModal(id) {
	registeredModals.delete(id);
}
