/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

let handlers = [];

process.on('exit', invokeHandlers);
process.on('SIGINT', invokeHandlers);
process.on('SIGTERM', invokeHandlers);

export default function onExit(handler) {
	handlers.push(handler);
}

function invokeHandlers() {
	for (const handler of handlers) {
		try {
			handler();
		}
		catch (error) {
			console.error(
				'Exception thrown while running onExit handler:',
				error
			);
		}
	}

	handlers = [];
}
