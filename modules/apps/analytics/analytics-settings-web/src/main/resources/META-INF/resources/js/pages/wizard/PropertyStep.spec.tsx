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
import {act, render} from '@testing-library/react';
import React from 'react';

import PropertyStep from './PropertyStep';

const response = {
	actions: {},
	facets: [],
	items: [],
	lastPage: 1,
	page: 1,
	pageSize: 20,
	totalCount: 3,
};

describe('Property Step', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('render PropertyStep without crashing', async () => {
		await act(async () => {
			fetch.mockResponseOnce(JSON.stringify(response));

			const {container, getByText} = render(
				<PropertyStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const propertyStepTitle = getByText('property-assignment');

			const propertyStepDescription = getByText('property-description');

			expect(propertyStepTitle).toBeInTheDocument();

			expect(propertyStepDescription).toBeInTheDocument();

			expect(container.firstChild).toHaveClass('sheet');
		});
	});
});
