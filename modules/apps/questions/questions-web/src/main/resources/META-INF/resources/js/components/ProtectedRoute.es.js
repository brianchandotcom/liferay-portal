/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Navigate, Outlet} from 'react-router';

export default function ProtectedRoute({children}) {
	const isAllowed =
		themeDisplay.isSignedIn() &&
		(!Liferay.Session || Liferay.Session.sessionState === 'active');

	if (!isAllowed) {
		return <Navigate replace to="/login" />;
	}

	return children ? children : <Outlet />;
}
