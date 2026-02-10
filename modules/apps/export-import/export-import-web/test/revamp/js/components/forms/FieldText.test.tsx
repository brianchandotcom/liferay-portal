/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import FieldText from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/FieldText';

describe('FieldText', () => {
	it('renders with an initial value', () => {
		const labelText = 'Test Field';
		const initialValue = 'Initial Text';
		render(
			<FieldText
				label={labelText}
				name="testField"
				value={initialValue}
			/>
		);

		expect(screen.getByLabelText(labelText)).toHaveValue(initialValue);
	});

	it('renders with an error message', () => {
		const labelText = 'Test Field';
		const errorMessage = 'This field is required';
		render(
			<FieldText
				errorMessage={errorMessage}
				label={labelText}
				name="testField"
			/>
		);

		expect(screen.getByText(errorMessage)).toBeInTheDocument();
	});

	it('renders as disabled when disabled prop is true', () => {
		const labelText = 'Test Field';
		render(
			<FieldText disabled={true} label={labelText} name="testField" />
		);

		expect(screen.getByLabelText(labelText)).toBeDisabled();
	});
});
