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

import {render} from '@liferay/frontend-js-react-web';
import React from 'react';

import Walkthrough from './Walkthrough';

const DEFAULT_CONTAINER_ID = 'walkthroughContainer';

const DEFAULT_PROPS = {
	closeOnClickOutside: false,
	closeable: true,
	pages: {},
	skippable: true,
	steps: [],
};

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
	return <Walkthrough {...DEFAULT_PROPS} {...props} />;
}

export default function main(props = {}, siteGroupFriendlyURL) {
	render(Root, {...props, siteGroupFriendlyURL}, getDefaultContainer());
}
