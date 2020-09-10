/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {render} from 'frontend-js-react-web';
import React from 'react';

import App from './App.es';
import {StoreProvider} from './components/StoreContext.es';

// eslint-disable-next-line no-unused-vars
let instance = null;

function BOM(props) {
	return (
		<StoreProvider>
			<App
				ref={(component) => {
					instance = component;
				}}
				showFilters={false}
				{...props}
			/>
		</StoreProvider>
	);
}

export default function (componentId, id, props) {
	const portletFrame = window.document.getElementById(id);

	render(BOM, props, portletFrame);
}
