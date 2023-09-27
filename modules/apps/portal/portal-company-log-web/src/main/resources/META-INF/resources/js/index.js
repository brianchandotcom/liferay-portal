/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {HashRouter, Route, Switch} from 'react-router-dom';

import CompanyLogs from './pages/CompanyLogs';
import LogPreview from './pages/LogPreview';

const App = () => (
	<div className="bg-white container mt-4 p-4">
		<HashRouter>
			<Switch>
				<Route component={CompanyLogs} exact path="/" />

				<Route
					component={LogPreview}
					exact
					path="/:companyId/:fileName"
				/>
			</Switch>
		</HashRouter>
	</div>
);

export default App;
