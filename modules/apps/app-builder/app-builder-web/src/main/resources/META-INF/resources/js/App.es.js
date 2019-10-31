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

import {ClayModalProvider} from '@clayui/modal';
import React from 'react';
import {DragDropContext as dragDropContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import {Route, HashRouter as Router, Switch} from 'react-router-dom';

import {AppContextProvider} from './AppContext.es';
import {ToastContextProvider} from './components/toast/ToastContext.es';
import ListCustomObjects from './pages/custom-object/ListCustomObjects.es';
import ViewCustomObject from './pages/custom-object/ViewCustomObject.es';

export default dragDropContext(HTML5Backend)(props => {
	return (
		<AppContextProvider {...props}>
			<ToastContextProvider>
				<ClayModalProvider>
					<Router>
						<Switch>
							<Route
								component={ListCustomObjects}
								exact
								path="/"
							/>

							<Route
								component={ViewCustomObject}
								path="/custom-object/:dataDefinitionId(\d+)"
							/>
						</Switch>
					</Router>
				</ClayModalProvider>
			</ToastContextProvider>
		</AppContextProvider>
	);
});
