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

import React, {createContext, useState} from 'react';
import {Route, Switch} from 'react-router-dom';

import {withParams} from '../../shared/components/router/routerUtil.es';
import SLAFormPage from './form-page/SLAFormPage.es';
import SLAListPage from './list-page/SLAListPage.es';

const SLAContext = createContext();

const SLAContainer = () => {
	const [SLAUpdated, setSLAUpdated] = useState(false);

	return (
		<SLAContext.Provider value={{SLAUpdated, setSLAUpdated}}>
			<Switch>
				<Route
					exact
					path="/sla/:processId/list/:pageSize/:page"
					render={withParams(SLAListPage)}
				/>

				<Route
					exact
					path="/sla/:processId/new"
					render={withParams(SLAFormPage)}
				/>

				<Route
					exact
					path="/sla/:processId/edit/:id"
					render={withParams(SLAFormPage)}
				/>
			</Switch>
		</SLAContext.Provider>
	);
};

export {SLAContext};
export default SLAContainer;
