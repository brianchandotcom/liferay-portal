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

import React from 'react';

import '@clayui/css/lib/css/atlas.css';

import 'swagger-ui-react/swagger-ui.css';

import APIGUI from './APIGUI';

import 'graphiql/graphiql.css';

import '../css/main.css';
import {AppStateProvider} from './hooks/appState';
import appReducer, {initialState} from './reducers/appReducer';

const App = (props) => {
	return (
		<>
			<AppStateProvider initialState={initialState} reducer={appReducer}>
				<APIGUI props={props} />
			</AppStateProvider>
		</>
	);
};

export default App;
