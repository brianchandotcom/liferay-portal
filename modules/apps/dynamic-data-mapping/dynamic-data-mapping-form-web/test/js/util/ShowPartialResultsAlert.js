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
import userEvent from '@testing-library/user-event';
import React from 'react';

import ShowPartialResultsAlert from '../../../src/main/resources/META-INF/resources/admin/js/util/ShowPartialResultsAlert';

describe('Show Partial Results Alert', () => {
	afterEach(() => {
		cleanup();
	});

	it('shows the alert without dismiss button when showPartialResultsToRespondents is enabled and dismissible is false', () => {
		const {queryByText} = render(
			<ShowPartialResultsAlert
				dismissible={false}
				showPartialResultsToRespondents={true}
			/>
		);

		expect(queryByText('understood')).not.toBeInTheDocument();
		expect(
			queryByText(
				'your-responses-will-be-visible-to-all-form-respondents'
			)
		).toBeInTheDocument();
	});

	it('shows the alert with a dismissible button', () => {
		const {queryByText} = render(
			<ShowPartialResultsAlert
				dismissible={true}
				showPartialResultsToRespondents={true}
			/>
		);

		expect(queryByText('understood')).toBeInTheDocument();
	});

	it('hides the alert when clicking on Understood button', () => {
		const {container, queryByText} = render(
			<ShowPartialResultsAlert
				dismissible={true}
				showPartialResultsToRespondents={true}
			/>
		);

		const button = queryByText('understood');

		userEvent.click(button);

		const hiddenAlert = container.querySelector(
			'.lfr-ddm__show-partial-results-alert--hidden'
		);

		expect(hiddenAlert).toBeInTheDocument();
	});
});
