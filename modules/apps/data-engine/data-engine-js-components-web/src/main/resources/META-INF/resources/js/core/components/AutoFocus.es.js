/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useLayoutEffect, useRef, useState} from 'react';

import {usePage} from '../hooks/usePage.es';

export function AutoFocus({children}) {
	const childRef = useRef();
	const {activePage, containerElement} = usePage();
	const [increment, setIncrement] = useState(0);

	useLayoutEffect(() => {
		if (childRef.current && containerElement?.current) {
			if (childRef.current.querySelector('.loading-animation')) {

				/**
				 * This hack is to update the variable increment and cause one
				 * more render until all loadings are removed from the screen
				 * and so the field will be ready to focus
				 */

				setTimeout(() => setIncrement((value) => value + 1), 5);
			}
			else {
				if (!document.activeElement) {
					const firstInput = childRef.current.querySelector('input');
					const sidebarOpen = document.querySelector(
						'.ddm-form-builder--sidebar-open'
					);

					const userViewContent = containerElement.current.querySelector(
						'.ddm-user-view-content'
					);

					if (
						firstInput &&
						!containerElement.current.contains(
							document.activeElement
						) &&
						(sidebarOpen || userViewContent)
					) {
						firstInput.focus();

						if (firstInput.select) {
							firstInput.select();
						}
					}
				}
			}
		}
	}, [activePage, childRef, containerElement, increment]);

	return React.cloneElement(children, {
		ref: (node) => {
			childRef.current = node;
		},
	});
}
