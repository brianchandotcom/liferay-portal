/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {HashRouter, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from '../AppContext';
import EnvelopeForm from './envelope/EnvelopeForm';
import EnvelopeList from './envelope/EnvelopeList';
import EnvelopeView from './envelope/EnvelopeView';

const DigitalSignature = (props) => (
	<AppContextProvider {...props}>
		<HashRouter>
			<Switch>
				<Route component={EnvelopeList} exact path="/" />

				<Route
					component={EnvelopeView}
					exact
					path="/envelope/:envelopeId"
				/>

				<Route component={EnvelopeForm} exact path="/new-envelope" />
			</Switch>
		</HashRouter>
	</AppContextProvider>
);

export default DigitalSignature;
