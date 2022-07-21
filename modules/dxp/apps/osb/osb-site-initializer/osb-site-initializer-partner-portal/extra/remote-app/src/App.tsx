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

import {useState} from 'react';

import reactLogo from './assets/react.svg';

import './App.css';

function App() {
	const [count, setCount] = useState(0);

	return (
		<div className="App">
			<div>
				<a href="https://vitejs.dev" target="_blank">
					<img alt="Vite logo" className="logo" src="/vite.svg" />
				</a>

				<a href="https://reactjs.org" target="_blank">
					<img
						alt="React logo"
						className="logo react"
						src={reactLogo}
					/>
				</a>
			</div>

			<h1>Vite + React</h1>

			<div className="card">
				<button onClick={() => setCount((count) => count + 1)}>
					count is {count}
				</button>

				<p>
					Edit <code>src/App.tsx</code> and save to test HMR
				</p>
			</div>

			<p className="read-the-docs">
				Click on the Vite and React logos to learn more
			</p>
		</div>
	);
}

export default App;
