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

import {useEventListener} from 'frontend-js-react-web';
import {useMemo} from 'react';

import {useConfig} from '../../../core/hooks/useConfig.es';

const NAV_ITEMS = {
	0: '/',
	1: '/rules',
	2: '/report',
};

/**
 * Updates the search param so that the navigation bar rendered
 * on the backend via JSP can come with the active element when
 * the page is updated.
 */
const setSearchParam = (name, value) => {
	const url = new URL(window.location.toString());

	url.searchParams.set(name, value);

	window.history.replaceState({path: url.toString()}, '', url.toString());
};

/**
 * This is a fake component that only takes advantage of the React lifecycle to
 * change the React Router route, currently the component is rendered via JSP
 * and it is necessary to control the interaction via JavaScript.
 */
export const NavigationBar = ({history, location}) => {
	const {portletNamespace} = useConfig();

	const formNavElement = useMemo(
		() => document.body.querySelector('.forms-navigation-bar'),
		[]
	);

	useEventListener(
		'click',
		(event) => {
			if (event.target.type === 'button') {
				const index = Number(
					event.target.parentElement.dataset.navItemIndex
				);

				const path = NAV_ITEMS[index];

				// Removes the active state of the previous element before
				// adding the active state for the pressed element.

				document
					.querySelector('.forms-navigation-bar li > .active')
					.classList.remove('active');

				event.target.classList.add('active');

				setSearchParam(`${portletNamespace}activeNavItem`, index);

				const method =
					path === location.pathname ? history.replace : history.push;

				method(path);
			}
		},
		true,
		formNavElement
	);

	return null;
};
