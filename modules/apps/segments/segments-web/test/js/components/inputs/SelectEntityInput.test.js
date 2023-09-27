/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import SelectEntityInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/SelectEntityInput';

const ENTITY_SELECT_INPUT_TESTID = 'entity-select-input';

describe('SelectEntityInput', () => {
	afterEach(cleanup);

	it('renders type id', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '12345';

		const {getByTestId} = render(
			<SelectEntityInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				selectEntity={{
					id: 'entitySelect',
					title: 'Select Entity Test',
					url: '',
				}}
				value={defaultNumberValue}
			/>
		);

		const element = getByTestId(ENTITY_SELECT_INPUT_TESTID);

		expect(element.value).toBe(defaultNumberValue);
	});
});
