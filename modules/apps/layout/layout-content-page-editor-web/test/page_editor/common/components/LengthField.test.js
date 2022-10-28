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

import '@testing-library/jest-dom/extend-expect';
import {fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {LengthField} from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/LengthField';

const FIELD = {label: 'length-field', name: 'lengthField'};

const renderLengthField = ({
	onValueSelect = () => {},
	value = '12px',
	field = FIELD,
	selectedViewportSize = 'desktop',
} = {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={() => {}}
			getState={() => ({
				selectedViewportSize,
			})}
		>
			<LengthField
				field={field}
				onValueSelect={onValueSelect}
				value={value}
			/>
		</StoreAPIContextProvider>
	);

describe('LengthField', () => {
	function openUnitDropdown() {

		// Hackily work around:
		//
		//      "TypeError: Cannot read property '_defaultView' of undefined"
		//
		// Caused by: https://github.com/jsdom/jsdom/issues/2499

		userEvent.click(screen.getByLabelText('select-a-unit'));

		document.activeElement.blur = () => {};
	}

	it('renders LengthField', () => {
		renderLengthField();

		expect(screen.getByText('length-field')).toBeInTheDocument();
	});

	it('shows the value', () => {
		renderLengthField();

		expect(screen.getByLabelText('length-field')).toHaveValue(12);
		expect(screen.getByLabelText('select-a-unit').textContent).toBe('PX');
	});

	it('changes the number of the value', () => {
		renderLengthField();
		const input = screen.getByLabelText('length-field');

		userEvent.type(input, '20');

		expect(input).toHaveValue(20);
	});

	it('saves the value', () => {
		const onValueSelect = jest.fn();
		renderLengthField({onValueSelect});
		const input = screen.getByLabelText('length-field');

		userEvent.type(input, '24');
		fireEvent.blur(input);

		expect(onValueSelect).toBeCalledWith(FIELD.name, '24px');
	});

	it('saves the value when the Enter button is pressed', () => {
		const onValueSelect = jest.fn();
		renderLengthField({onValueSelect});
		const input = screen.getByLabelText('length-field');

		userEvent.type(input, '30');
		fireEvent.keyUp(input, {key: 'Enter'});

		expect(onValueSelect).toBeCalledWith(FIELD.name, '30px');
	});

	it('changes the unit of the value', () => {
		renderLengthField();

		openUnitDropdown();

		userEvent.click(screen.getByText('%'));

		expect(screen.getByLabelText('select-a-unit').textContent).toBe('%');
	});

	it('keeps the empty input and the units when the value is cleared', () => {
		renderLengthField({value: '14vh'});
		const input = screen.getByLabelText('length-field');

		userEvent.type(input, '');

		expect(input).toHaveValue();
		expect(screen.getByLabelText('select-a-unit').textContent).toBe('VH');
	});

	it('renders an icon code in the button if custom option is selected', () => {
		renderLengthField({value: 'calc(12px - 3px)'});

		expect(
			screen
				.getByLabelText('select-a-unit')
				.querySelector('.lexicon-icon-code')
		).toBeInTheDocument();
	});

	it('focuses the input when custom option is selected', () => {
		renderLengthField();

		openUnitDropdown();

		userEvent.click(screen.getByText('CUSTOM'));

		expect(screen.getByLabelText('length-field')).toHaveFocus();
	});

	it('does not allow typing letters when a unit is selected', () => {
		renderLengthField();
		const input = screen.getByLabelText('length-field');

		userEvent.type(input, 'auto');

		expect(input).toHaveValue(null);
	});

	it('allows a default unit and disables the button', () => {
		const field = {
			...FIELD,
			typeOptions: {
				defaultUnit: '%',
			},
		};

		renderLengthField({field});

		const button = screen.getByLabelText('select-a-unit');

		expect(button.textContent).toBe('%');
		expect(button).toBeDisabled();
	});

	it('renders the restore button when a value is introduced', () => {
		renderLengthField({
			field: {defaultValue: '', label: 'opacity', name: 'opacity'},
			value: '',
		});
		const input = screen.getByLabelText('opacity');

		expect(screen.queryByTitle('reset-to-x-value')).not.toBeInTheDocument();

		userEvent.type(input, '100');
		fireEvent.blur(input);

		expect(screen.queryByTitle('reset-to-x-value')).toBeInTheDocument();
	});

	it('clears the value when the restore button is clicked', () => {
		renderLengthField({field: {label: 'opacity', name: 'opacity'}});

		userEvent.click(screen.getByTitle('reset-to-x-value'));

		expect(screen.getByLabelText('opacity').textContent).toBe('');
	});

	describe('LengthField when it is part of a Select field', () => {
		const field = {
			...FIELD,
			typeOptions: {
				showLengthField: true,
			},
		};

		it('focuses the input when the currently option is custom and a other unit is selected', () => {
			renderLengthField({field, value: 'calc(12px - 3px)'});

			openUnitDropdown();

			userEvent.click(screen.getByText('%'));

			expect(screen.getByLabelText('length-field')).toHaveFocus();
		});

		it('does not save the value and keeps the previous value when the input is cleared', () => {
			const onValueSelect = jest.fn();
			renderLengthField({
				field,
				onValueSelect,
				value: 'initial',
			});
			const input = screen.getByLabelText('length-field');

			userEvent.type(input, '');
			fireEvent.blur(input);

			expect(input).toHaveValue('initial');
			expect(onValueSelect).not.toBeCalled();
		});
	});
});
