/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				This widget is used to test that basic Walkthrough features are
				functioning as expected. Simply add whatever Walkthrough
				elements you want to App.js and redeploy.
			</p>

			<hr />

			<div id="step-1">
				<ClayButton.Group spaced>
					<ClayButton type="submit">Click Here</ClayButton>
				</ClayButton.Group>
			</div>

			<div id="step-2">
				<ClayForm.Group>
					<label htmlFor="basicInputText">Name</label>

					<ClayInput
						id="basicInputText"
						placeholder="Insert your name here."
						type="text"
					/>
				</ClayForm.Group>
			</div>
		</div>
	);
}
