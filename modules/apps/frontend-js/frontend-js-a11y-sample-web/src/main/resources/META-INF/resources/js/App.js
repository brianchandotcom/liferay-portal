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

export default () => {
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
				<p id="violation"> ID 1 </p>
				<p id="violation"> ID 2 </p>
				
				<h3>Moderate Violation: Landmarks must be unique</h3>
				<form aria-label="form-label"> Form 1 </form>
				<form aria-label="form-label"> Form 2 </form>
				
				<h3>Critical Violation: Buttons must have discernible text</h3>
				<p> There are buttons: </p>
				<button></button> 
				<button></button>
				<p></p>

				<h3>Serious Violation: Links must have discernible text</h3>
				<p> There's a link: </p>
				<a href="https://www.liferay.com"></a>
				<p></p>
			</div>
			<hr />
		</div>
	);
};
