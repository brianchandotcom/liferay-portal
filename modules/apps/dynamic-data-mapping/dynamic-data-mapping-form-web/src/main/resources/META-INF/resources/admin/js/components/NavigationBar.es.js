/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {useCallback, useLayoutEffect} from 'react';

import {useBack} from '../hooks/useBack.es';

const NAV_ITEMS = {
	0: '/',
	1: '/rules',
	2: '/report',
};

const NAV_ITEMS_REVERSE = {
	'/': 0,
	'/report': 2,
	'/rules': 1,
};

/**
 * This is a fake component that only takes advantage of the React lifecycle to
 * change the React Router route, currently the component is rendered via JSP
 * and it is necessary to control the interaction via JavaScript.
 */
export function NavigationBar({history, location}) {
	useBack();

	const onClick = useCallback(
		(event) => {
			if (
				event.target.type === 'button' ||
				event.target.tagName === 'SPAN'
			) {
				event.preventDefault();

				const target =
					event.target.type === 'button'
						? event.target
						: event.target.parentElement;

				const index = Number(target.parentElement.dataset.navItemIndex);

				const path = NAV_ITEMS[index];

				// Removes the active state of the previous element before
				// adding the active state for the pressed element.

				document
					.querySelector('.forms-navigation-bar li > .active')
					.classList.remove('active');

				target.classList.add('active');

				const method =
					path === location.pathname ? history.replace : history.push;

				method(path);
			}
		},
		[history, location.pathname]
	);

	useEventListener(
		'click',
		onClick,
		true,
		document.body.querySelector('.forms-navigation-bar')
	);

	useLayoutEffect(() => {
		let index = NAV_ITEMS_REVERSE[location.pathname];

		if (location.pathname === '/rules/editor') {
			index = 1;
		}

		// This will mark the active element of the NavigationBar for the first
		// time according to the pathname when the application is started.

		document
			.querySelector(
				`.forms-navigation-bar li[data-nav-item-index='${index}'] > button`
			)
			.classList.add('active');

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return null;
}
