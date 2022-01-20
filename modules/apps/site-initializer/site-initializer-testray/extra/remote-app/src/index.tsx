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

import ReactDOM from 'react-dom';
import {HashRouter, Route, Routes} from 'react-router-dom';

import HelloBar from './routes/hello-bar/pages/HelloBar';
import HelloFoo from './routes/hello-foo/pages/HelloFoo';
import HelloWorld from './routes/hello-world/pages/HelloWorld';

import './common/styles/index.scss';

const App = () => (
	<HashRouter>
		<Routes>
			<Route element={<HelloWorld />} index />

			<Route element={<HelloFoo />} path="/hello-foo" />

			<Route element={<HelloBar />} path="/hello-bar" />
		</Routes>
	</HashRouter>
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
