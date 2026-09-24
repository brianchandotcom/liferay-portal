/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';

import '@testing-library/jest-dom';

import FieldDatePicker from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/FieldDatePicker';

function ControlledFieldDatePicker({
	initialValue = '',
	onChange,
	...props
}: Omit<React.ComponentProps<typeof FieldDatePicker>, 'value'> & {
	initialValue?: string;
}) {
	const [value, setValue] = useState(initialValue);

	return (
		<FieldDatePicker
			{...props}
			onChange={(nextValue) => {
				setValue(nextValue as string);

				onChange?.(nextValue);
			}}
			value={value}
		/>
	);
}

describe('FieldDatePicker', () => {
	afterEach(() => {
		(Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock).mockReturnValue(
			'en-US'
		);
	});

	it('fills in the default time when a date is picked without one', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
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
				dateFormat="yyyy-MM-dd"
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
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
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 --:--'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 --:--');
	});

	it('fills in the default time when the typed time is cleared', () => {
		const onChange = jest.fn();

		render(
			<ControlledFieldDatePicker
				dateFormat="yyyy-MM-dd"
				defaultTime="00:00"
				initialValue="2026-09-19 08:30"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
			/>
		);

		const input = screen.getByLabelText('Start Date');

		fireEvent.change(input, {target: {value: '2026-09-19'}});

		fireEvent.blur(input, {target: {value: '2026-09-19'}});

		expect(onChange).toHaveBeenLastCalledWith('2026-09-19 00:00');
	});

	it('clears a stale error once a picked date receives its default time', () => {
		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={jest.fn()}
				time
				use12Hours={false}
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

	it('displays a canonical 24-hour value as 12-hour when use12Hours is set', () => {
		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={jest.fn()}
				time
				use12Hours
				value="2026-09-19 17:00"
			/>
		);

		expect(screen.getByLabelText('Start Date')).toHaveValue(
			'2026-09-19 05:00 PM'
		);
	});

	it('converts a typed 12-hour value back to the canonical 24-hour value', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 05:00 PM'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 17:00');
	});

	it('round-trips midnight and noon correctly in 12-hour mode', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 12:00 AM'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 00:00');

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 12:00 PM'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 12:00');
	});

	it('fills in the default time in 12-hour mode when a date is picked without one', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 --:-- --'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 00:00');
	});

	it('normalizes the 12-hour unset placeholder when no default time is configured', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 --:-- --'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 --:--');
	});

	it('derives the 12-hour clock from the portal locale when use12Hours is omitted', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				value="2026-09-19 17:00"
			/>
		);

		const input = screen.getByLabelText('Start Date');

		expect(input).toHaveValue('2026-09-19 05:00 PM');
		expect(input).toHaveAttribute('placeholder', 'YYYY-MM-DD HH:MM AM');

		fireEvent.change(input, {target: {value: '2026-09-19 08:30 AM'}});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 08:30');
	});

	it('keeps the 24-hour clock for a 24-hour portal locale', () => {
		['es-ES', 'ja-JP'].forEach((locale) => {
			(
				Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock
			).mockReturnValue(locale);

			const onChange = jest.fn();

			const {unmount} = render(
				<FieldDatePicker
					dateFormat="yyyy-MM-dd"
					label="Start Date"
					name="startDate"
					onChange={onChange}
					time
					value="2026-09-19 17:00"
				/>
			);

			const input = screen.getByLabelText('Start Date');

			expect(input).toHaveValue('2026-09-19 17:00');
			expect(input).toHaveAttribute('placeholder', 'YYYY-MM-DD HH:MM');

			fireEvent.change(input, {target: {value: '2026-09-19 08:30'}});

			expect(onChange).toHaveBeenCalledWith('2026-09-19 08:30');

			unmount();
		});
	});

	it('normalizes a single-digit hour and a lowercase period typed in 12-hour mode', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 8:00 pm'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 20:00');

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 8:05 am'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 08:05');
	});

	it('pads a single-digit hour typed in 24-hour mode', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-09-19 8:00'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-19 08:00');
	});

	it('keeps an explicit placeholder', () => {
		render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				placeholder="Pick a date"
				time
			/>
		);

		expect(screen.getByLabelText('Start Date')).toHaveAttribute(
			'placeholder',
			'Pick a date'
		);
	});

	it('leaves the time alone while it is still being typed', async () => {
		const user = userEvent.setup({delay: null});

		render(
			<ControlledFieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				time
			/>
		);

		const input = screen.getByLabelText('Start Date');

		await user.click(input);
		await user.keyboard('2026-09-19 07:30');

		expect(input).toHaveValue('2026-09-19 07:30');
	});

	it('accepts a PM time typed one character at a time', async () => {
		const onChange = jest.fn();
		const user = userEvent.setup({delay: null});

		render(
			<ControlledFieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
			/>
		);

		const input = screen.getByLabelText('Start Date');

		await user.click(input);
		await user.keyboard('2026-09-19 07:30 PM');

		expect(input).toHaveValue('2026-09-19 07:30 PM');
		expect(onChange).toHaveBeenLastCalledWith('2026-09-19 19:30');
	});

	it('accepts an hour retyped over an existing one', async () => {
		const onChange = jest.fn();
		const user = userEvent.setup({delay: null});

		render(
			<ControlledFieldDatePicker
				dateFormat="yyyy-MM-dd"
				initialValue="2026-09-19 05:00"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
			/>
		);

		const input = screen.getByLabelText('Start Date');

		await user.clear(input);
		await user.keyboard('2026-09-19 11:00 PM');

		expect(onChange).toHaveBeenLastCalledWith('2026-09-19 23:00');
	});

	it('normalizes the zero hour emitted when the hour segment is cleared', () => {
		const onChange = jest.fn();

		render(
			<ControlledFieldDatePicker
				dateFormat="yyyy-MM-dd"
				initialValue="2026-10-01 09:30"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
			/>
		);

		const input = screen.getByLabelText('Start Date');

		fireEvent.change(input, {target: {value: '2026-10-01 00:30 AM'}});

		expect(onChange).toHaveBeenLastCalledWith('2026-10-01 00:30');

		fireEvent.change(input, {target: {value: '2026-10-01 00:30 PM'}});

		expect(onChange).toHaveBeenLastCalledWith('2026-10-01 12:30');
	});

	it('reports an hour outside the 12-hour clock as invalid', () => {
		const onChange = jest.fn();

		render(
			<ControlledFieldDatePicker
				dateFormat="yyyy-MM-dd"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '2026-10-01 13:30 PM'},
		});

		fireEvent.blur(screen.getByLabelText('Start Date'));

		expect(
			screen.getByText('the-field-value-is-invalid')
		).toBeInTheDocument();
	});

	it('stores a date typed in the given order as a canonical date', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="dd.MM.yyyy"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '26.09.2026 17:00'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-26 17:00');
	});

	it('renders one stored date in whatever order it is asked for', () => {
		const cases = [
			['MM/dd/yyyy', '09/26/2026 17:00', 'MM/DD/YYYY HH:MM'],
			['dd.MM.yyyy', '26.09.2026 17:00', 'DD.MM.YYYY HH:MM'],
			['yyyy. MM. dd.', '2026. 09. 26. 17:00', 'YYYY. MM. DD. HH:MM'],
		];

		cases.forEach(([dateFormat, displayValue, placeholder]) => {
			const {unmount} = render(
				<FieldDatePicker
					dateFormat={dateFormat}
					label="Start Date"
					name="startDate"
					onChange={jest.fn()}
					time
					use12Hours={false}
					value="2026-09-26 17:00"
				/>
			);

			const input = screen.getByLabelText('Start Date');

			expect(input).toHaveAttribute('placeholder', placeholder);
			expect(input).toHaveValue(displayValue);

			unmount();
		});
	});

	it('fills in the default time for a date picked in the given order', () => {
		const onChange = jest.fn();

		render(
			<FieldDatePicker
				dateFormat="dd.MM.yyyy"
				defaultTime="00:00"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
			/>
		);

		fireEvent.change(screen.getByLabelText('Start Date'), {
			target: {value: '26.09.2026 --:--'},
		});

		expect(onChange).toHaveBeenCalledWith('2026-09-26 00:00');
	});

	it('reports a date the given order cannot account for as invalid', () => {
		render(
			<ControlledFieldDatePicker
				dateFormat="dd.MM.yyyy"
				label="Start Date"
				name="startDate"
				time
				use12Hours={false}
			/>
		);

		const input = screen.getByLabelText('Start Date');

		fireEvent.change(input, {target: {value: '45.13.2026 17:00'}});
		fireEvent.blur(input);

		expect(
			screen.getByText('the-field-value-is-invalid')
		).toBeInTheDocument();
	});

	it('accepts a date typed one character at a time in the given order', async () => {
		const onChange = jest.fn();
		const user = userEvent.setup({delay: null});

		render(
			<ControlledFieldDatePicker
				dateFormat="dd.MM.yyyy"
				label="Start Date"
				name="startDate"
				onChange={onChange}
				time
				use12Hours={false}
			/>
		);

		const input = screen.getByLabelText('Start Date');

		await user.click(input);
		await user.keyboard('26.09.2026 07:30');

		expect(input).toHaveValue('26.09.2026 07:30');
		expect(onChange).toHaveBeenLastCalledWith('2026-09-26 07:30');
	});

	it('has no accessibility violations', async () => {
		const {container} = render(
			<FieldDatePicker
				dateFormat="yyyy-MM-dd"
				helpMessage="Uses the selected time zone"
				label="Start Date"
				name="startDate"
				time
			/>
		);

		await checkAccessibility({context: container});
	});
});
