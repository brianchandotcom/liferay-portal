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
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import StatusLabel from '../../../src/main/resources/META-INF/resources/workflow_status/js/components/status-label/StatusLabel';

describe('The WorkflowStatus should', () => {
	const INITIAL_PROPS = {
		showInstanceTracker: true,
		statusMessage: 'Status Message',
		statusStyle: 'info',
	};

	beforeEach(cleanup);

	it('render as Linked Label', () => {
		render(<StatusLabel {...INITIAL_PROPS} />);

		const hasLink = document.querySelector('a');

		expect(hasLink).toBeTruthy();

		expect(hasLink).toHaveAttribute(
			'title',
			Liferay.Language.get('track-workflow')
		);
	});

	it('render as not Linked Label', () => {
		render(
			<StatusLabel {...{...INITIAL_PROPS, showInstanceTracker: false}} />
		);

		const hasLink = document.querySelector('a');

		expect(hasLink).not.toBeInTheDocument();
	});

	it('open a modal on link click', async () => {
		render(<StatusLabel {...INITIAL_PROPS} />);

		const link = document.querySelector('a');

		await act(async () => {
			fireEvent.click(link);
		});

		const instanceTrackerModal = document.querySelector(
			'.modal-full-screen'
		);

		expect(instanceTrackerModal).toBeInTheDocument();
	});
});
