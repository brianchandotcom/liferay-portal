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

import {ApolloProvider} from '@apollo/client';
import ReactDOM from 'react-dom';
import {HashRouter, Outlet, Route, Routes} from 'react-router-dom';

import Layout from './components/Layout/Layout';
import ClayIconProvider from './context/ClayIconProvider';
import apolloClient from './graphql/apolloClient';
import Manage from './pages/Manage';
import Home from './pages/Project/Home';
import Overview from './pages/Project/Overview';
import Build from './pages/Project/Routines/Build';
import Routines from './pages/Project/Routines/Routines';
import Suites from './pages/Project/Suites';
import Testflow from './pages/Testflow';

import './styles/index.scss';

const App = () => (
	<ApolloProvider client={apolloClient}>
		<ClayIconProvider>
			<HashRouter>
				<Routes>
					<Route element={<Layout />} path="/">
						<Route element={<Home />} index />

						<Route element={<Outlet />} path="project/:project">
							<Route element={<Home />} index />

							<Route element={<Overview />} path="overview" />

							<Route path="routines">
								<Route element={<Routines />} index />

								<Route element={<Build />} path="build" />
							</Route>

							<Route element={<Suites />} path="suites" />
						</Route>

						<Route element={<Manage />} path="manage" />

						<Route element={<Testflow />} path="testflow" />

						<Route element={<div>Page not found</div>} path="*" />
					</Route>
				</Routes>
			</HashRouter>
		</ClayIconProvider>
	</ApolloProvider>
);

class WebComponent extends HTMLElement {
	connectedCallback() {
		ReactDOM.render(<App />, this);
	}
}

const ELEMENT_ID = 'liferay-remote-app-testray';

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, WebComponent);
}
