/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {worker} from './mocks/browser';


export function setupMocks() {
	worker
		.start({
			onUnhandledRequest: 'bypass',
			serviceWorker: {
				options: {
					scope: '/group/',
				},
				url: '/o/exportimport-web/mockServiceWorker.js',
			},
		})
		.then((registration) => {
			Liferay.on('destroyPortlet', async () => {
				if (
					registration &&
					typeof registration.unregister ===
						'function'
				) {
					await registration.unregister();
				}
			});
		})
		.catch((error) => {
			console.error('Error starting the service worker:', error);
		});
}

setupMocks();
