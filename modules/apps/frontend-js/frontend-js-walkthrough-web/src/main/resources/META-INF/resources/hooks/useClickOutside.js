/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const {useEffect} = React;

export function useClickOutside(elements, handler) {
	useEffect(() => {
		const listener = (event) => {
			const {target} = event;

			/**
			 * Detect clicks on elements or their descendent elements.
			 */
			const filtered = elements.filter((element) => {
				if (typeof element === 'string') {
					return !!target.closest(element);
				}

				return element && element.contains(target);
			});

			if (!filtered.length) {
				handler(event);
			}
		};

		document.addEventListener('mousedown', listener);
		document.addEventListener('touchstart', listener);

		return () => {
			document.removeEventListener('mousedown', listener);
			document.removeEventListener('touchstart', listener);
		};
	}, [elements, handler]);
}
