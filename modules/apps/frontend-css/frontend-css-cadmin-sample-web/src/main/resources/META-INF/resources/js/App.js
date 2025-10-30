/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

function App() {
	return (
		<div>
			<h2>Cadmin Test Portlet</h2>

			<p className="cadmin-test-class">
				This widget is used as an admin component to test Clay Admin.
			</p>

			<hr />

			<div className="cadmin-test-wrapper">
				<div className="cadmin-test-unstyled">
					<p> This is a regular div.</p>
				</div>

				<div className="cadmin-test-styled">
					<p> This is a regular div.</p>
				</div>

				<div className="cadmin-test-styled-override">
					<p> This is a regular div.</p>
				</div>

				<div className="cadmin cadmin-test-unstyled">
					<p> This is a div with cadmin.</p>
				</div>

				<div className="cadmin cadmin-test-styled">
					<p> This is a div with cadmin.</p>
				</div>

				<div className="cadmin cadmin-test-styled-override">
					<p> This is a div with cadmin.</p>
				</div>
			</div>

			<hr />
		</div>
	);
}

export {App};
