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
				<h3>Violation: ID Attribute Values Must Be Unique</h3>
				<p id="violation"> Text1 </p>
				<p id="violation"> Text2 </p>
			</div>
			<hr />
		</div>
	);
};
