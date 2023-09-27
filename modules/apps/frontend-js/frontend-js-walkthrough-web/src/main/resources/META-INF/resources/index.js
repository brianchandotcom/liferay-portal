/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@liferay/frontend-js-react-web';
import {localStorage} from 'frontend-js-web';
import React from 'react';

import Walkthrough from './components/Walkthrough';
import {LOCAL_STORAGE_KEYS} from './utils';

const DEFAULT_CONTAINER_ID = 'walkthroughContainer';

const getDefaultContainer = () => {
	let container = document.getElementById(DEFAULT_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

function Root(props) {
	if (
		!localStorage.getItem(
			LOCAL_STORAGE_KEYS.SKIPPABLE,
			localStorage.TYPES.NECESSARY
		)
	) {
		return <Walkthrough {...props} />;
	}

	return null;
}

export default function main(props = {}) {
	render(Root, props, getDefaultContainer());
}
