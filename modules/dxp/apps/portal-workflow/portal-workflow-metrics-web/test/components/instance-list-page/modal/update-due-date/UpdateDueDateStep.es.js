/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React, {cloneElement, useState} from 'react';

import UpdateDueDateStep from '../../../../../src/main/resources/META-INF/resources/js/components/instance-list-page/modal/update-due-date/UpdateDueDateStep.es';

import '@testing-library/jest-dom/extend-expect';

const ContainerMock = ({children}) => {
	const [value, setValue] = useState('');

	return cloneElement(children, {setValue, value});
};

const wrapperMock = {
	wrapper: ContainerMock,
};

describe('The TimePickerInput component should be render with AM/PM format', () => {
	afterEach(cleanup);

	test('Render with error state and select any option', () => {
		const {getAllByRole, getByPlaceholderText} = render(
			<UpdateDueDateStep.TimePickerInput format="H:mm a" isAmPm />,
			wrapperMock
		);

		const timeInput = getByPlaceholderText('HH:mm am/pm');

		expect(timeInput.parentNode).toHaveClass('has-error');
		expect(timeInput.value).toBe('');

		fireEvent.focus(timeInput);

		const items = getAllByRole('listitem');

		items.forEach((item) => {
			expect(item.innerHTML).toMatch(/[0-9]{1,2}:[0-9]{2}\s(AM|PM)/);
		});

		fireEvent.mouseDown(items[0]);

		expect(timeInput.parentNode).not.toHaveClass('has-error');
		expect(timeInput.value).toBe('12:00 AM');

		fireEvent.focus(timeInput);
		fireEvent.mouseDown(items[1]);

		expect(timeInput.value).toBe('12:30 AM');

		fireEvent.change(timeInput, {target: {value: '14:00'}});

		expect(timeInput.parentNode).toHaveClass('has-error');

		fireEvent.change(timeInput, {target: {value: '2:00 PM'}});

		expect(timeInput.parentNode).not.toHaveClass('has-error');
	});
});

describe('The TimePickerInput component should be render without AM/PM format', () => {
	test('Render with error state and select any option', () => {
		const {getAllByRole, getByPlaceholderText} = render(
			<UpdateDueDateStep.TimePickerInput format="H:mm" />,
			wrapperMock
		);

		const timeInput = getByPlaceholderText('HH:mm');

		expect(timeInput.parentNode).toHaveClass('has-error');
		expect(timeInput.value).toBe('');

		fireEvent.focus(timeInput);

		const items = getAllByRole('listitem');

		items.forEach((item) => {
			expect(item.innerHTML).toMatch(/[0-9]{1,2}:[0-9]{2}/);
		});

		fireEvent.mouseDown(items[0]);

		expect(timeInput.parentNode).not.toHaveClass('has-error');
		expect(timeInput.value).toBe('00:00');

		fireEvent.focus(timeInput);
		fireEvent.mouseDown(items[1]);

		expect(timeInput.value).toBe('00:30');

		fireEvent.change(timeInput, {target: {value: '12:00 AM'}});

		expect(timeInput.parentNode).toHaveClass('has-error');

		fireEvent.change(timeInput, {target: {value: '14:00'}});

		expect(timeInput.parentNode).not.toHaveClass('has-error');

		fireEvent.focus(timeInput);

		let timePickerPopover = document.querySelector('.clay-popover-bottom');

		expect(timePickerPopover).toBeTruthy();

		fireEvent.blur(timeInput);

		timePickerPopover = document.querySelector('.clay-popover-bottom');

		expect(timePickerPopover).toBeFalsy();
	});
});
