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
import {Route, HashRouter as Router, Switch} from 'react-router-dom';
import {CUSTOM_OBJECTS} from './components/search-container/constants.es';
import SearchContainer from './components/search-container/SearchContainer.es';
import EditCustomObject from './pages/custom-object/EditCustomObject.es';
import ViewCustomObject from './pages/custom-object/ViewCustomObject.es';

export default function App() {
	return (
		<Router>
			<Switch>
				<Route
					component={ViewCustomObject}
					path="/custom-object/:dataDefinitionId(\d+)"
				/>

				<Route
					component={EditCustomObject}
					path="/custom-object/edit"
				/>

				<Route
					exact
					path="/"
					render={() => (
						<SearchContainer
							actions={CUSTOM_OBJECTS.ACTIONS}
							columns={CUSTOM_OBJECTS.COLUMNS}
							emptyState={CUSTOM_OBJECTS.EMPTY_STATE}
							endpoint={CUSTOM_OBJECTS.ENDPOINT}
							formatter={CUSTOM_OBJECTS.FORMATTER}
						/>
					)}
				/>
			</Switch>
		</Router>
	);
}
