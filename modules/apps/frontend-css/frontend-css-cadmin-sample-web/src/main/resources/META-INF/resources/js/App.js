/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import '../css/main.scss';

export default function App() {
	return (
		<div>
			<h2>Cadmin Test Portlet</h2>

			<p className="cadmin-test-class">
				This widget is used as an admin component to test Clay Admin.
			</p>

			<hr />

			<div>
				<body className="test">
					<p> This is a body without cadmin.</p>
				</body>

				<body className="cadmin">
					<p> This is a body with cadmin.</p>
				</body>

				<body className="blue-background cadmin">
					<p>
						This is a body with cadmin and CSS changes higher than
						cadmin.
					</p>
				</body>
			</div>

			<hr />
		</div>
	);
}
