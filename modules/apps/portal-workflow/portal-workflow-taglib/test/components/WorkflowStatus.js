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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import WorkflowStatus from '../../src/main/resources/META-INF/resources/workflow_status/js/WorkflowStatus';

describe('The WorkflowStatus should', () => {
	const INITIAL_PROPS = {
		id: '1',
		idLabel: 'ID',
		instanceId: '05050',
		showInstanceTracker: true,
		showStatusLabel: true,
		statusLabel: 'Status',
		statusMessage: 'Status Message',
		statusStyle: 'secondary',
		version: '1.0.0',
		versionLabel: 'version',
	};

	afterEach(cleanup);

	it('render with all valid props', () => {
		const {queryByText} = render(<WorkflowStatus {...INITIAL_PROPS} />);

		const hasStatus = queryByText('Status:');

		const hasStatusMessage = queryByText('Status Message');

		const isLabelSecondary = document.querySelector('.label-secondary');

		expect(hasStatus).toBeTruthy();

		expect(hasStatusMessage).toBeTruthy();

		expect(isLabelSecondary).toBeTruthy();
	});

	it('render without a status label', () => {
		render(
			<WorkflowStatus
				{...{
					...INITIAL_PROPS,
					showStatusLabel: false,
				}}
			/>
		);

		const hasStatus = document.querySelector('workflow-label');

		expect(hasStatus).toBeFalsy();
	});
});
