/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import '../css/main.scss';

export default function App() {
	return (
		<div>
			<h2>A11y Test Portlet</h2>

			<p className="a11y-test-class">
				This widget is used to test basic A11y Tool features are
				functioning as expected. Simply add whatever A11y violations you
				want to test to App.js and redeploy.
			</p>

			<hr />

			<div>
				{/* This is where your code goes... */}

				<h3>Minor Violation: ID Attribute Values Must Be Unique</h3>

				<p id="violation">ID 1</p>

				<p id="violation">ID 2</p>

				<h3>Moderate Violation: Landmarks Must Be Unique</h3>

				<form aria-label="form-label">Form 1</form>

				<form aria-label="form-label">Form 2</form>

				<h3>Critical Violation: Buttons Must Have Discernible Text</h3>

				<p>There are buttons: </p>

				<button></button>

				<button></button>

				<p></p>

				<h3>Serious Violation: Links Must Have Discernible Text</h3>

				<p>There is a link: </p>

				<a href="https:\/\/www.liferay.com"></a>

				<p></p>
			</div>

			<hr />
		</div>
	);
}
