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

const App = () => {
	const [count, setCount] = useState(0);

	return (
		<div>
			<h1>Hello World with Vite</h1>

			<div>
				<button onClick={() => setCount((count) => count + 1)}>
					count is {count}
				</button>

				<p>
					Edit <code>src/App.tsx</code> and save to test HMR
				</p>
			</div>

			<p>Click on the Vite and React logos to learn more</p>
		</div>
	);
};

export default App;
