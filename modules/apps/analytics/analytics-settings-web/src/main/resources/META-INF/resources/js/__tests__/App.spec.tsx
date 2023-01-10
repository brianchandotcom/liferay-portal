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
import {render} from '@testing-library/react';
import React from 'react';

import App, {EPageView} from '../App';

const INITIAL_PROPS = {
	connected: false,
	liferayAnalyticsURL: '',
	token: '',
};

describe('App', () => {
	it('renders without crashing', () => {
		const {container} = render(<App {...INITIAL_PROPS} />);

		expect(container.firstChild).toHaveClass('analytics-settings-web');
	});

	it('renders wizard view when not connected', () => {
		const {getAllByTestId} = render(<App {...INITIAL_PROPS} />);

		expect(getAllByTestId(EPageView.Wizard)).toBeTruthy();
	});

	it('renders default view when connected', () => {
		const {getAllByTestId} = render(<App {...INITIAL_PROPS} connected />);

		expect(getAllByTestId(EPageView.Default)).toBeTruthy();
	});
});
