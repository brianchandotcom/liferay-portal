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

import React from 'react';
import ReactDOM from 'react-dom';

import apiEndpointDefinitions from '../../../../../../dev/apiEndpointDefinitions';
import App from './App.es';
import {StoreProvider} from './components/StoreContext.es';

import 'clay-css/src/scss/atlas.scss';

import '../css/main.scss';

window.Liferay = {
	authToken: 'fakeToken',
};

window.themeDisplay = {
	getPathImage: () => './testPath/',
};

const props = {
	spritemap: '/test-icons.svg',
	modelSelectorMakerEndpoint: apiEndpointDefinitions.MAKER,
	modelSelectorYearEndpoint: apiEndpointDefinitions.YEAR,
	modelSelectorModelEndpoint: apiEndpointDefinitions.MODEL,
	areasEndpoint: apiEndpointDefinitions.AREAS,
	foldersEndpoint: apiEndpointDefinitions.FOLDERS,
	showFilters: false,
};

ReactDOM.render(
	<StoreProvider>
		<App {...props} />
	</StoreProvider>,
	document.getElementById('root')
);
