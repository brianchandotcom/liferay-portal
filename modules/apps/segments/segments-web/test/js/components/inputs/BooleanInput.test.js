/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import BooleanInput from '../../../../src/main/resources/META-INF/resources/js/components/inputs/BooleanInput';
import {testControlledInput} from '../../utils';

const OPTIONS_BOOLEAN_INPUT_TESTID = 'options-boolean';

describe('BooleanInput', () => {
	afterEach(cleanup);

	it('renders type boolean', () => {
		const mockOnChange = jest.fn();

		const defaultBoolValue = 'true';

		const {asFragment, getByTestId} = render(
			<BooleanInput
				onChange={mockOnChange}
				propertyLabel="Test label"
				value={defaultBoolValue}
			/>
		);

		expect(asFragment()).toMatchSnapshot();

		const element = getByTestId(OPTIONS_BOOLEAN_INPUT_TESTID);

		testControlledInput({
			element,
			mockFunc: mockOnChange,
			newValue: 'false',
			value: defaultBoolValue,
		});
	});
});
