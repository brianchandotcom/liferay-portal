/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

let listenerCount = 0;

function onVisibilityChange() {
	const body = document.body;

	if (!document.hidden && body.classList.contains('c-prefers-focus-ring')) {
		body.classList.add('c-tab-returning');

		requestAnimationFrame(() => {
			requestAnimationFrame(() => {
				body.classList.remove('c-tab-returning');
			});
		});
	}
}

export function useTabReturnFocusRingAnimation() {
	useEffect(() => {
		if (listenerCount === 0) {
			document.addEventListener('visibilitychange', onVisibilityChange);
		}

		listenerCount++;

		return () => {
			listenerCount--;

			if (listenerCount === 0) {
				document.removeEventListener(
					'visibilitychange',
					onVisibilityChange
				);
			}
		};
	}, []);
}
