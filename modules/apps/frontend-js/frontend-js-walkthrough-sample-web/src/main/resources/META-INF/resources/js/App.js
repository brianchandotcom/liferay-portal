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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import React from 'react';

import '../css/main.scss';

export default function App() {
	return (
		<div>
			<h2>Walkthrough Test Portlet</h2>

			<p className="walkthrough-test-class">
				This widget is used to test basic Walkthrough features are
				functioning as expected. Simply add whatever Walkthrough
				elements you want to test to App.js and redeploy.
			</p>

			<hr />

			<div id="step-1">
				<ClayButton.Group spaced>
					<ClayButton type="submit">Click here</ClayButton>
				</ClayButton.Group>
			</div>

			<div id="step-2">
				<ClayForm.Group>
					<label htmlFor="basicInputText">Name</label>

					<ClayInput
						id="basicInputText"
						placeholder="Insert your name here"
						type="text"
					/>
				</ClayForm.Group>
			</div>
		</div>
	);
}
