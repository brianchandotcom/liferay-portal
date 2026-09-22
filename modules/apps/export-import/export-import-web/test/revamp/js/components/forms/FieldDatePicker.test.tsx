/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom';

import FieldDatePicker from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/FieldDatePicker';

describe('FieldDatePicker', () => {
	it('fills in the default time when a date is picked without one', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 --:--'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 00:00');
	});

	it('leaves an already complete date and time untouched', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 09:15'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 09:15');
	});

	it('leaves the value untouched when no default time is configured', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 --:--'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 --:--');
	});

	it('clears a stale error once a picked date receives its default time', () => {
		render(
			<FieldDatePicker
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={jest.fn()}
				time
				value="not a date"
			/>
		);

		fireEvent.blur(screen.getByLabelText('Start Date'));

		expect(
			screen.getByText('the-field-value-is-invalid')
		).toBeInTheDocument();

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 --:--'},
		});

		expect(
			screen.queryByText('the-field-value-is-invalid')
		).not.toBeInTheDocument();
	});
});
