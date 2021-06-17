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

import ClayButton from '@clayui/button';
import React from 'react';

const INITIAL_RADIUS_CHOICE = [
	{
		label: 'Small',
		value: 10,
	},
	{
		label: 'Medium',
		value: 20,
	},
	{
		label: 'Large',
		value: 30,
	},
];

const DiagramHeader = (radiusChoice = INITIAL_RADIUS_CHOICE) => {
	return (
		<div className="d-flex diagram diagram-header justify-content-between">
			<ClayButton
				className="auto-mapping my-auto pull-right"
				displayType="secondary"
			>
				Auto-mapping
			</ClayButton>
		</div>
	);
};

export default DiagramHeader;
