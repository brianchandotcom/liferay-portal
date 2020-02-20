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
import {DragDropContextProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import App from './components/App';
import {ControlsProvider} from './components/Controls';
import {initializeConfig} from './config/index';
import {reducer} from './reducers/index';
import {StoreContextProvider} from './store/index';

/**
 * Container component that sets up context that is global to the entire app.
 *
 * This is a separate functional component instead of being directly inlined in
 * this module's default export because hooks can only be used inside functional
 * components (the default export is not a functional component but rather a
 * function that returns a component).
 */
function Container({state}) {
	const initialState = reducer(state, {type: 'INIT'});

	return (
		<StoreContextProvider initialState={initialState} reducer={reducer}>
			<ControlsProvider>
				<App />
			</ControlsProvider>
		</StoreContextProvider>
	);
}

/**
 * Default application export.
 *
 * We should define contexts here instead of Container component, as Container
 * is re-rendered when hooks change.
 */
export default function(data) {
	initializeConfig(data.config);

	return (
		<DragDropContextProvider backend={HTML5Backend}>
			<Container state={data.state} />
		</DragDropContextProvider>
	);
}
