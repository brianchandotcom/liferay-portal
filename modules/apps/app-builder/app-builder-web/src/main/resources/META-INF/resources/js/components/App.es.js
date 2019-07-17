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

import {CUSTOM_OBJECTS, FORM_VIEWS} from '../utils/constants.es';
import React from 'react';
import {Route, HashRouter as Router, Switch} from 'react-router-dom';
import SearchContainer from './search-container/SearchContainer.es';

export default function App() {
	return (
		<div className="container-fluid container-fluid-max-xl main-content-body">
			<Router>
				<Switch>
					<Route
						path="/custom-object/:dataDefinitionId"
						render={props => (
							<SearchContainer
								actions={FORM_VIEWS.ACTIONS}
								columns={FORM_VIEWS.COLUMNS}
								emptyState={FORM_VIEWS.EMPTY_STATE}
								endpoint={FORM_VIEWS.ENDPOINT(
									props.match.params.dataDefinitionId
								)}
								formatter={FORM_VIEWS.FORMATTER}
								key="1"
							/>
						)}
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
								key="2"
							/>
						)}
					/>
				</Switch>
			</Router>
		</div>
	);
}
