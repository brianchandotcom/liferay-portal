/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Redirect, Route} from 'react-router-dom';

export default function ProtectedRoute({component: Component, ...rest}) {
	return (
		<Route
			{...rest}
			render={(props) =>
				themeDisplay.isSignedIn() &&
				(!Liferay.Session ||
					Liferay.Session.get('sessionState') === 'active') ? (
					<Component {...props} />
				) : (
					<Redirect to={{pathname: '/'}} />
				)
			}
		/>
	);
}
