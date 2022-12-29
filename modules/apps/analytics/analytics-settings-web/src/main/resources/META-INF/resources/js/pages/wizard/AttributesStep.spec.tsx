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

import AttributesStep from './AttributesStep';

const response = {
	account: 25,
	order: 0,
	people: 43,
	product: 0,
};

describe('Attributes Step', () => {
	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('render AttributesStep without crashing', async () => {
		fetch.mockResponseOnce(JSON.stringify(response));

		await act(async () => {
			const {container, getByText} = render(
				<AttributesStep onCancel={() => {}} onChangeStep={() => {}} />
			);

			const attributesStepTitle = getByText('attributes');

			const attributesStepDescription = getByText(
				'attributes-step-description'
			);

			expect(attributesStepTitle).toBeInTheDocument();

			expect(attributesStepDescription).toBeInTheDocument();

			expect(container.firstChild).toHaveClass('sheet');
		});
	});
});
