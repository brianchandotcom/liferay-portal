/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {HashRouter, Route, Routes} from 'react-router';

import {AppContextProvider} from '../AppContext';
import EnvelopeForm from './envelope/EnvelopeForm';
import EnvelopeList from './envelope/EnvelopeList';
import EnvelopeView from './envelope/EnvelopeView';

const DigitalSignature = (props) => (
	<AppContextProvider {...props}>
		<HashRouter>
			<Routes>
				<Route element={<EnvelopeList />} path="/" />

				<Route
					element={<EnvelopeView />}
					path="/envelope/:envelopeId"
				/>

				<Route element={<EnvelopeForm />} path="/new-envelope" />
			</Routes>
		</HashRouter>
	</AppContextProvider>
);

export default DigitalSignature;
