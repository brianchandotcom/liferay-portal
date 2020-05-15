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
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from '../../AppContext.es';
import ListApps from './ListApps.es';
import EditApp from './edit/EditApp.es';

export default (props) => {
	return (
		<AppContextProvider {...props}>
			<ClayModalProvider>
				<Router>
					<Switch>
						<Route component={ListApps} exact path="/" />

						<Route
							component={EditApp}
							path="/standard/deploy/:dataDefinitionId"
						/>
					</Switch>
				</Router>
			</ClayModalProvider>
		</AppContextProvider>
	);
};
