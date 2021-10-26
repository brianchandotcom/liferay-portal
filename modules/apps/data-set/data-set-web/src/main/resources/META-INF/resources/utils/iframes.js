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
