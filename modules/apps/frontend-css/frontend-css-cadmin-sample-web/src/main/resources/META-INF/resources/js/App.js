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
