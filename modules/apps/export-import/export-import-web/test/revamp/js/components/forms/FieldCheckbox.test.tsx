/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import {FieldCheckbox} from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/FieldCheckbox';

describe('FieldCheckbox', () => {
	it('renders with a label and handles change', () => {
		const handleChange = jest.fn();
		const labelText = 'Test Checkbox';

		render(
			<FieldCheckbox
				checked={false}
				label={labelText}
				name="testCheckbox"
				onChange={handleChange}
			/>
		);

		const checkbox = screen.getByLabelText(labelText);
		expect(checkbox).not.toBeChecked();
	});

	it('renders with a description', () => {
		const labelText = 'Test Checkbox';
		const descriptionText = 'This is a test description';

		render(
			<FieldCheckbox
				checked={false}
				description={descriptionText}
				label={labelText}
				name="testCheckbox"
				onChange={jest.fn()}
			/>
		);

		expect(screen.getByText(descriptionText)).toBeInTheDocument();
	});

	it('renders as checked', () => {
		const labelText = 'Test Checkbox';

		render(
			<FieldCheckbox
				checked={true}
				label={labelText}
				name="testCheckbox"
				onChange={jest.fn()}
			/>
		);

		const checkbox = screen.getByLabelText(labelText);
		expect(checkbox).toBeChecked();
	});
});
