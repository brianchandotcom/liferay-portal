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

import DefaultPage from '../../../src/main/resources/META-INF/resources/admin/js/components/DefaultPage';

describe('DefaultPage', () => {
	afterEach(cleanup);

	it('shows the form title, form description, page description and page title', () => {
		const {getByText} = render(
			<DefaultPage
				formDescription="Form description"
				formTitle="Form title"
				pageDescription="Page description"
				pageTitle="Page title"
			/>
		);

		expect(getByText('Form title')).toBeInTheDocument();
		expect(getByText('Form description')).toBeInTheDocument();
		expect(getByText('Page description')).toBeInTheDocument();
		expect(getByText('Page title')).toBeInTheDocument();
	});

	it('shows submit again button when the show submit again flag is enabled', () => {
		const {getByText} = render(<DefaultPage showSubmitAgainButton />);

		expect(getByText('submit-again')).toBeInTheDocument();
	});

	it('hides submit again button when the show submit again flag is disabled', () => {
		const {queryByText} = render(<DefaultPage />);

		expect(queryByText('submit-again')).not.toBeInTheDocument();
	});

	it('hides partial results button if no url is provided', () => {
		const {queryByText} = render(
			<DefaultPage showPartialResultsToRespondents />
		);

		expect(queryByText('see-partial-results')).not.toBeInTheDocument();
	});

	it('shows partial results button when Show partial results to respondents is enabled', () => {
		const {queryByText} = render(
			<DefaultPage
				formReportDataURL="http://liferay.com/"
				showPartialResultsToRespondents
			/>
		);

		expect(queryByText('see-partial-results')).toBeInTheDocument();
	});

	it('hides partial results button when Show partial results to respondents is disabled', () => {
		const {queryByText} = render(<DefaultPage />);

		expect(queryByText('see-partial-results')).not.toBeInTheDocument();
	});
});
