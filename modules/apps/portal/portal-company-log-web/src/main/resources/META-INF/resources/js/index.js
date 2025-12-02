/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {HashRouter, Route, Routes} from 'react-router';

import CompanyLogs from './pages/CompanyLogs';
import LogPreview from './pages/LogPreview';

export function App() {
	return (
		<div className="bg-white container mt-4 p-4">
			<HashRouter>
				<Routes>
					<Route element={<CompanyLogs />} path="/" />

					<Route
						element={<LogPreview />}
						path="/:companyId/:fileName"
					/>
				</Routes>
			</HashRouter>
		</div>
	);
}
