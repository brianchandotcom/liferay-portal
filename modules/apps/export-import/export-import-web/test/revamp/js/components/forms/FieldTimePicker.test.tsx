/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// eslint-disable-next-line @liferay/portal/no-cross-module-deep-import
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {fireEvent, render, screen, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';

import '@testing-library/jest-dom';

import FieldTimePicker from '../../../../../src/main/resources/META-INF/resources/revamp/js/components/forms/FieldTimePicker';

const user = userEvent.setup({delay: null});

function ControlledFieldTimePicker({
	initialValue = '',
	onChange,
	...props
}: Omit<React.ComponentProps<typeof FieldTimePicker>, 'value'> & {
	initialValue?: string;
}) {
	const [value, setValue] = useState(initialValue);

	return (
		<FieldTimePicker
			{...props}
			onChange={(nextValue) => {
				setValue(nextValue);

				onChange?.(nextValue);
			}}
			value={value}
		/>
	);
}

describe('FieldTimePicker', () => {
	afterEach(() => {
		(Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock).mockReturnValue(
			'en-US'
		);
	});

	it('renders a stored time on the 12-hour clock of a 12-hour portal locale', () => {
		render(
			<FieldTimePicker label="Time of Day" name="time" value="17:00" />
		);

		const input = screen.getByLabelText('Time of Day');

		expect(input).toHaveValue('05:00 PM');
		expect(input).toHaveAttribute('placeholder', 'HH:MM AM');
	});

	it('renders midnight as twelve on the 12-hour clock', () => {
		render(
			<FieldTimePicker label="Time of Day" name="time" value="00:00" />
		);

		expect(screen.getByLabelText('Time of Day')).toHaveValue('12:00 AM');
	});

	it('keeps the 24-hour clock for a 24-hour portal locale', () => {
		(Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock).mockReturnValue(
			'es-ES'
		);

		render(
			<FieldTimePicker label="Time of Day" name="time" value="17:00" />
		);

		const input = screen.getByLabelText('Time of Day');

		expect(input).toHaveValue('17:00');
		expect(input).toHaveAttribute('placeholder', 'HH:MM');
	});

	it('reports a typed 12-hour time as a canonical 24-hour value', () => {
		const onChange = jest.fn();

		render(
			<FieldTimePicker
				label="Time of Day"
				name="time"
				onChange={onChange}
			/>
		);

		const input = screen.getByLabelText('Time of Day');

		fireEvent.change(input, {target: {value: '5:00 pm'}});

		expect(onChange).toHaveBeenLastCalledWith('17:00');

		fireEvent.change(input, {target: {value: '12:00 AM'}});

		expect(onChange).toHaveBeenLastCalledWith('00:00');

		fireEvent.change(input, {target: {value: '17:00'}});

		expect(onChange).toHaveBeenLastCalledWith('17:00');
	});

	it('pads a typed short hour on the 24-hour clock', () => {
		(Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock).mockReturnValue(
			'es-ES'
		);

		const onChange = jest.fn();

		render(
			<FieldTimePicker
				label="Time of Day"
				name="time"
				onChange={onChange}
			/>
		);

		fireEvent.change(screen.getByLabelText('Time of Day'), {
			target: {value: '9:30'},
		});

		expect(onChange).toHaveBeenLastCalledWith('09:30');
	});

	it('holds a partial time while typing and reformats it once the field loses focus', async () => {
		const onChange = jest.fn();

		render(
			<ControlledFieldTimePicker
				label="Time of Day"
				name="time"
				onChange={onChange}
			/>
		);

		const input = screen.getByLabelText('Time of Day');

		await user.type(input, '5:0');

		expect(input).toHaveValue('5:0');
		expect(onChange).toHaveBeenLastCalledWith('');

		await user.type(input, '0 PM');

		expect(input).toHaveValue('5:00 PM');
		expect(onChange).toHaveBeenLastCalledWith('17:00');

		await user.tab();

		expect(input).toHaveValue('05:00 PM');
	});

	it('hides the external error while unaccepted text is being typed', async () => {
		render(
			<ControlledFieldTimePicker
				errorMessage="This field is required"
				label="Time of Day"
				name="time"
			/>
		);

		const input = screen.getByLabelText('Time of Day');

		expect(screen.getByText('This field is required')).toBeInTheDocument();

		await user.type(input, '5:0');

		expect(
			screen.queryByText('This field is required')
		).not.toBeInTheDocument();
		expect(input).toHaveAttribute('aria-invalid', 'false');
	});

	it('keeps unaccepted text and reports it once the field loses focus', async () => {
		const onBlur = jest.fn();

		render(
			<>
				<ControlledFieldTimePicker
					errorMessage="This field is required"
					label="Time of Day"
					name="time"
					onBlur={onBlur}
				/>

				<button type="button">outside</button>
			</>
		);

		const input = screen.getByLabelText('Time of Day');

		await user.type(input, 'junk');

		await user.click(screen.getByRole('button', {name: 'outside'}));

		expect(input).toHaveValue('junk');
		expect(input).toHaveAttribute('aria-describedby', 'timefieldFeedback');
		expect(input).toHaveAttribute('aria-invalid', 'true');
		expect(
			screen.getByText('please-enter-a-valid-time')
		).toBeInTheDocument();
		expect(
			screen.queryByText('This field is required')
		).not.toBeInTheDocument();
		expect(onBlur).toHaveBeenCalledTimes(1);
	});

	it('clears the invalid time error once the text is removed', async () => {
		render(<ControlledFieldTimePicker label="Time of Day" name="time" />);

		const input = screen.getByLabelText('Time of Day');

		await user.type(input, 'junk');

		await user.tab();

		expect(
			screen.getByText('please-enter-a-valid-time')
		).toBeInTheDocument();

		await user.clear(input);

		expect(
			screen.queryByText('please-enter-a-valid-time')
		).not.toBeInTheDocument();
	});

	it('shows a value pushed from outside in place of the draft', () => {
		const {rerender} = render(
			<FieldTimePicker label="Time of Day" name="time" value="" />
		);

		const input = screen.getByLabelText('Time of Day');

		fireEvent.change(input, {target: {value: '5:0'}});

		expect(input).toHaveValue('5:0');

		rerender(
			<FieldTimePicker label="Time of Day" name="time" value="09:15" />
		);

		expect(input).toHaveValue('09:15 AM');
	});

	it('drops the draft and its error once the field is disabled', async () => {
		const {rerender} = render(
			<FieldTimePicker label="Time of Day" name="time" value="" />
		);

		const input = screen.getByLabelText('Time of Day');

		await user.type(input, 'junk');

		await user.tab();

		expect(
			screen.getByText('please-enter-a-valid-time')
		).toBeInTheDocument();

		rerender(
			<FieldTimePicker
				disabled
				label="Time of Day"
				name="time"
				value=""
			/>
		);

		expect(input).toHaveValue('');
		expect(input).toBeDisabled();
		expect(
			screen.queryByText('please-enter-a-valid-time')
		).not.toBeInTheDocument();
	});

	it('opens a time picker showing the stored time from the clock button', async () => {
		render(
			<FieldTimePicker label="Time of Day" name="time" value="17:00" />
		);

		const button = screen.getByRole('button', {name: 'select-time'});

		expect(button).toHaveAttribute('aria-expanded', 'false');

		await user.click(button);

		const dialog = screen.getByRole('dialog', {name: 'select-time'});

		expect(button).toHaveAttribute('aria-expanded', 'true');
		expect(button).toHaveAttribute('aria-controls', dialog.id);
		expect(within(dialog).getByLabelText('hours')).toHaveValue('05');
		expect(within(dialog).getByLabelText('minutes')).toHaveValue('00');
		expect(within(dialog).getByLabelText('am-pm')).toHaveValue('PM');
	});

	it('labels the time picker controls in the portal language', async () => {
		render(
			<FieldTimePicker label="Time of Day" name="time" value="17:00" />
		);

		await user.click(screen.getByRole('button', {name: 'select-time'}));

		const dialog = screen.getByRole('dialog', {name: 'select-time'});

		expect(within(dialog).getByLabelText('am-pm')).toBeInTheDocument();
		expect(within(dialog).getByLabelText('clear-time')).toBeInTheDocument();
		expect(
			within(dialog).getByLabelText('decrease-time')
		).toBeInTheDocument();
		expect(within(dialog).getByLabelText('hours')).toBeInTheDocument();
		expect(
			within(dialog).getByLabelText('increase-time')
		).toBeInTheDocument();
		expect(within(dialog).getByLabelText('minutes')).toBeInTheDocument();
	});

	it('shows the picker segments on the 24-hour clock of a 24-hour portal locale', async () => {
		(Liferay.ThemeDisplay.getBCP47LanguageId as jest.Mock).mockReturnValue(
			'es-ES'
		);

		render(
			<FieldTimePicker label="Time of Day" name="time" value="17:00" />
		);

		await user.click(screen.getByRole('button', {name: 'select-time'}));

		const dialog = screen.getByRole('dialog', {name: 'select-time'});

		expect(within(dialog).getByLabelText('hours')).toHaveValue('17');
		expect(
			within(dialog).queryByLabelText('am-pm')
		).not.toBeInTheDocument();
	});

	it('reports a time completed in the picker and shows it in the text field', async () => {
		const onChange = jest.fn();

		render(
			<ControlledFieldTimePicker
				label="Time of Day"
				name="time"
				onChange={onChange}
			/>
		);

		const input = screen.getByLabelText('Time of Day');

		await user.click(screen.getByRole('button', {name: 'select-time'}));

		const dialog = screen.getByRole('dialog', {name: 'select-time'});

		fireEvent.keyDown(within(dialog).getByLabelText('hours'), {
			key: 'ArrowUp',
		});

		expect(onChange).not.toHaveBeenCalled();
		expect(input).toHaveValue('');

		fireEvent.keyDown(within(dialog).getByLabelText('minutes'), {
			key: 'ArrowUp',
		});

		expect(onChange).not.toHaveBeenCalled();

		fireEvent.keyDown(within(dialog).getByLabelText('am-pm'), {
			key: 'ArrowUp',
		});

		expect(onChange).toHaveBeenLastCalledWith('13:00');
		expect(input).toHaveValue('01:00 PM');
	});

	it('clears the field from the picker', async () => {
		const onChange = jest.fn();

		render(
			<ControlledFieldTimePicker
				initialValue="17:00"
				label="Time of Day"
				name="time"
				onChange={onChange}
			/>
		);

		await user.click(screen.getByRole('button', {name: 'select-time'}));

		fireEvent.click(
			within(screen.getByRole('dialog', {name: 'select-time'})).getByRole(
				'button',
				{name: 'clear-time'}
			)
		);

		expect(onChange).toHaveBeenLastCalledWith('');
		expect(screen.getByLabelText('Time of Day')).toHaveValue('');
	});

	it('replaces unaccepted text and its error with a time picked from the picker', async () => {
		render(<ControlledFieldTimePicker label="Time of Day" name="time" />);

		const input = screen.getByLabelText('Time of Day');

		await user.type(input, 'junk');

		await user.tab();

		expect(
			screen.getByText('please-enter-a-valid-time')
		).toBeInTheDocument();

		await user.click(screen.getByRole('button', {name: 'select-time'}));

		const dialog = screen.getByRole('dialog', {name: 'select-time'});

		fireEvent.keyDown(within(dialog).getByLabelText('hours'), {
			key: 'ArrowUp',
		});
		fireEvent.keyDown(within(dialog).getByLabelText('minutes'), {
			key: 'ArrowUp',
		});
		fireEvent.keyDown(within(dialog).getByLabelText('am-pm'), {
			key: 'ArrowDown',
		});

		expect(input).toHaveValue('01:00 AM');
		expect(
			screen.queryByText('please-enter-a-valid-time')
		).not.toBeInTheDocument();
	});

	it('disables the clock button with the field', () => {
		render(<FieldTimePicker disabled label="Time of Day" name="time" />);

		expect(
			screen.getByRole('button', {name: 'select-time'})
		).toBeDisabled();
	});

	it('renders with an error message', () => {
		const errorMessage = 'This field is required';

		render(
			<FieldTimePicker
				errorMessage={errorMessage}
				label="Time of Day"
				name="time"
			/>
		);

		expect(screen.getByText(errorMessage)).toBeInTheDocument();
		expect(screen.getByLabelText('Time of Day')).toHaveAttribute(
			'aria-invalid',
			'true'
		);
	});

	it('renders with a help message', () => {
		const helpMessage = 'Uses the server time zone';

		render(
			<FieldTimePicker
				helpMessage={helpMessage}
				label="Time of Day"
				name="time"
			/>
		);

		expect(screen.getByText(helpMessage)).toBeInTheDocument();
		expect(screen.getByLabelText('Time of Day')).toHaveAttribute(
			'aria-describedby',
			'timefieldFeedback'
		);
	});

	it('marks the input as required', () => {
		render(<FieldTimePicker label="Time of Day" name="time" required />);

		const input = screen.getByLabelText(/Time of Day/);

		expect(input).toBeRequired();
		expect(input).toHaveAttribute('aria-required', 'true');
	});

	it('renders as disabled when disabled prop is true', () => {
		render(
			<FieldTimePicker
				disabled
				label="Time of Day"
				name="time"
				value="09:45"
			/>
		);

		expect(screen.getByLabelText('Time of Day')).toBeDisabled();
	});

	it('calls onBlur once the field loses focus', async () => {
		const onBlur = jest.fn();

		render(
			<>
				<FieldTimePicker
					label="Time of Day"
					name="time"
					onBlur={onBlur}
					value="09:45"
				/>

				<button type="button">outside</button>
			</>
		);

		await user.click(screen.getByLabelText('Time of Day'));

		expect(onBlur).not.toHaveBeenCalled();

		await user.click(screen.getByRole('button', {name: 'outside'}));

		expect(onBlur).toHaveBeenCalledTimes(1);
	});

	it('has no accessibility violations', async () => {
		const {container} = render(
			<FieldTimePicker
				helpMessage="Uses the server time zone"
				label="Time of Day"
				name="time"
			/>
		);

		await checkAccessibility({context: container});
	});
});
