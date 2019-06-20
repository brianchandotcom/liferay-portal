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
import SelectEntityInput from 'components/inputs/SelectEntityInput.es';
import {cleanup, render} from 'react-testing-library';

const ENTITY_SELECT_INPUT_TESTID = 'entity-select-input';

describe('SelectEntityInput', () => {
	afterEach(cleanup);

	it('should render type id', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '12345';

		const {getByTestId} = render(
			<SelectEntityInput
				onChange={mockOnChange}
				selectEntity={{
					id: 'entitySelect',
					title: 'Select Entity Test',
					url: ''
				}}
				value={defaultNumberValue}
			/>
		);

		const element = getByTestId(ENTITY_SELECT_INPUT_TESTID);

		expect(element.value).toBe(defaultNumberValue);
	});
});
