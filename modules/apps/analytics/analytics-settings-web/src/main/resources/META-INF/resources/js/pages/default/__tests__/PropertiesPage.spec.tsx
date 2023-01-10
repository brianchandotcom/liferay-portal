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

import '@testing-library/jest-dom/extend-expect';
import {act, render, screen} from '@testing-library/react';
import fetch from 'jest-fetch-mock';

import PropertiesPage from '../PropertiesPage';

const response = {
	actions: {},
	facets: [],
	items: [],
	lastPage: 1,
	page: 1,
	pageSize: 20,
	totalCount: 2,
};

describe('PropertiesPage', () => {
	it('renders page title and description', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		await act(async () => {
			render(<PropertiesPage title="Properties" />);
		});

		const title = screen.getByText('Properties');
		const description = screen.getByText('property-description');

		expect(title).toBeInTheDocument();
		expect(description).toBeInTheDocument();
	});
});
