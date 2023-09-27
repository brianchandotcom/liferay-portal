/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';

import {A11y} from './A11y';
import {A11yIframe} from './A11yIframe';

import type {A11yCheckerOptions} from './A11yChecker';

const DEFAULT_CONTAINER_ID = 'a11yContainer';

const getDefaultContainer = () => {
	let container = document.getElementById(DEFAULT_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

export default function main(
	props: Omit<A11yCheckerOptions, 'callback' | 'targets'>
) {
	render(
		window.themeDisplay.isStatePopUp() ? A11yIframe : A11y,
		props,
		getDefaultContainer()
	);
}
