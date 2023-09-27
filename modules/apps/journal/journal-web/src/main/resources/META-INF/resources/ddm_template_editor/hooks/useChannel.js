/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

export function useChannel() {
	const [channel] = useState(() => {
		const listeners = new Set();

		return {
			onData(callback) {
				listeners.add(callback);

				return () => {
					listeners.delete(callback);
				};
			},

			sendData(data) {
				listeners.forEach((callback) => {
					callback(data);
				});
			},
		};
	});

	return channel;
}
