/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import {MaxLengthProperties} from '../../../../components/ObjectField/Tabs/BasicInfo/MaxLengthProperties';

const defaultProps = {
	errors: {},
	objectField: {businessType: 'Text' as ObjectFieldBusinessTypeName},
	objectFieldSettings: [
		{name: 'showCounter', value: true},
		{name: 'maxLength', value: 280},
	] as ObjectFieldSetting[],
	onSettingsChange: jest.fn(),
	setValues: jest.fn(),
};

const renderComponent = () => render(<MaxLengthProperties {...defaultProps} />);

describe('MaxLengthProperties', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('accepts a value within the maximum allowed for a Text field', () => {
		renderComponent();

		const input = screen.getByDisplayValue('280');

		fireEvent.change(input, {target: {value: '28'}});

		expect(defaultProps.onSettingsChange).toHaveBeenCalledWith({
			name: 'maxLength',
			value: 28,
		});
	});

	it('does not accept a value greater than the maximum allowed for a Text field', () => {
		renderComponent();

		const input = screen.getByDisplayValue('280');

		fireEvent.change(input, {target: {value: '281'}});

		expect(defaultProps.onSettingsChange).toHaveBeenCalledWith({
			name: 'maxLength',
			value: 280,
		});
	});
});
