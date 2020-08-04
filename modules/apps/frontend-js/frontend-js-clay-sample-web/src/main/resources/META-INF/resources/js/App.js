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

import ClayAlert from '@clayui/alert';
import React from 'react';

import '../css/main.scss';

export default () => {
	return (
		<div>
			<ClayAlert title="Info">
				This widget is used to test out Clay components. Simply add
				whatever JS you want to App.js and redeploy.
			</ClayAlert>

			<div className="clay-test-class">This is where your code goes.</div>
		</div>
	);
};
