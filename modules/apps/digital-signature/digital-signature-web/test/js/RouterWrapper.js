/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createMemoryHistory} from 'history';
import React, {cloneElement} from 'react';
import {Route, Router} from 'react-router-dom';

export default function RouterWrapper({
	children,
	path = '/',
	initialEntries = [{pathname: '/', search: ''}],
}) {
	return (
		<Router history={createMemoryHistory({initialEntries})}>
			<Route
				component={(props) => cloneElement(children, props)}
				path={path}
			/>
		</Router>
	);
}
