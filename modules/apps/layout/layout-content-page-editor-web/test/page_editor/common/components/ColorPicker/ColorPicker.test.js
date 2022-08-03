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
import {fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {StoreContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {StyleErrorsContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StyleErrorsContext';
import {ColorPicker} from '../../../../../src/main/resources/META-INF/resources/page_editor/common/components/ColorPicker/ColorPicker';

const COLOR_PICKER_CLASS = '.page-editor__color-picker';
const INPUT_NAME = 'Color Picker';
const TOKEN_VALUES = {
	blue: {
		editorType: 'ColorPicker',
		label: 'Blue',
		name: 'blue',
		tokenCategoryLabel: 'Category1',
		tokenSetLabel: 'TokenSet 1',
		value: '#4b9fff',
	},
	darkBlue: {
		editorType: 'ColorPicker',
		label: 'Dark Blue',
		name: 'darkBlue',
		tokenCategoryLabel: 'Category1',
		tokenSetLabel: 'TokenSet 1',
		value: '#00008b',
	},
	green: {
		editorType: 'ColorPicker',
		label: 'Green',
		name: 'green',
		tokenCategoryLabel: 'Category 2',
		tokenSetLabel: 'TokenSet 1',
		value: '#9be169',
	},
	orange: {
		editorType: 'ColorPicker',
		label: 'Orange',
		name: 'orange',
		tokenCategoryLabel: 'Category 1',
		tokenSetLabel: 'TokenSet 2',
		value: '#ffb46e',
	},
};

const FIELD = {label: INPUT_NAME, name: INPUT_NAME};

const renderColorPicker = ({
	onValueSelect = () => {},
	value = 'green',
	field = FIELD,
	editedTokenValues = {},
}) =>
	render(
		<StoreContextProvider initialState={{}} reducer={(state) => state}>
			<StyleErrorsContextProvider>
				<ColorPicker
					editedTokenValues={editedTokenValues}
					field={field}
					onValueSelect={onValueSelect}
					tokenValues={TOKEN_VALUES}
					value={value}
				/>
			</StyleErrorsContextProvider>
		</StoreContextProvider>
	);

const onTypeValue = (input, value) => {
	fireEvent.change(input, {
		target: {value},
	});

	fireEvent.blur(input);
};

describe('ColorPicker', () => {
	it('renders the ColorPicker', () => {
		const {baseElement} = renderColorPicker({});

		expect(
			baseElement.querySelector(`${COLOR_PICKER_CLASS}`)
		).toBeInTheDocument();
	});

	it('clears the value and sets "default"', async () => {
		const onValueSelect = jest.fn();

		const {getByTitle} = renderColorPicker({onValueSelect});

		fireEvent.click(getByTitle('clear-selection'));

		expect(onValueSelect).toBeCalledWith('Color Picker', null);
	});

	it('clears the value and sets the default value of the field if it exists', async () => {
		const {baseElement, getByTitle} = renderColorPicker({
			field: {...FIELD, defaultValue: '#abcabc'},
		});

		fireEvent.click(getByTitle('clear-selection'));

		expect(baseElement.querySelector('input')).toHaveValue('#ABCABC');
	});

	describe('When the value is an existing token', () => {
		it('renders the dropdown color picker', () => {
			const {getByLabelText, getByTitle} = renderColorPicker({});

			expect(getByTitle('detach-token')).toBeInTheDocument();
			expect(getByLabelText('Green')).toBeInTheDocument();
		});

		it('shows action buttons when the color picker is clicked', async () => {
			const {baseElement, getByLabelText} = renderColorPicker({});

			fireEvent.click(getByLabelText('Green'));

			expect(baseElement.querySelector(COLOR_PICKER_CLASS)).toHaveClass(
				'hovered'
			);
		});

		it('change to input color picker when detach token button is clicked', async () => {
			const {baseElement, getByTitle} = renderColorPicker({});

			fireEvent.click(getByTitle('detach-token'));

			expect(getByTitle('value-from-stylebook')).toBeInTheDocument();
			expect(baseElement.querySelector('input')).toHaveValue('#9BE169');
			expect(
				baseElement.querySelector('.clay-color-picker')
			).toBeInTheDocument();
		});

		it('does not show the action buttons when the value is default', () => {
			const {queryByTitle} = renderColorPicker({
				value: null,
			});

			expect(queryByTitle('detach-token')).not.toBeInTheDocument();
			expect(
				queryByTitle('value-from-stylebook')
			).not.toBeInTheDocument();
		});

		it('disabled the color when the token references itself', async () => {
			const {getByTitle} = renderColorPicker({
				field: {...FIELD, name: 'orange'},
				value: '#fff',
			});

			fireEvent.click(getByTitle('value-from-stylebook'));

			expect(getByTitle('Orange')).toBeDisabled();
		});

		it('disables the colors when the tokens are mutually referenced', async () => {
			const {getByTitle} = renderColorPicker({
				editedTokenValues: {
					orange: {
						name: 'blue',
						value: '#ffb46e',
					},
				},
				field: {...FIELD, name: 'blue'},
				value: '#fff',
			});

			fireEvent.click(getByTitle('value-from-stylebook'));

			expect(getByTitle('Orange')).toBeDisabled();
			expect(getByTitle('Blue')).toBeDisabled();
		});
	});

	describe('When the value is an hexadecimal', () => {
		it('renders the autocomplete color picker', () => {
			const {baseElement, getByTitle} = renderColorPicker({
				value: '#ffb46e',
			});

			expect(getByTitle('value-from-stylebook')).toBeInTheDocument();
			expect(baseElement.querySelector('input')).toHaveValue('#FFB46E');
			expect(
				baseElement.querySelector('.clay-color-picker')
			).toBeInTheDocument();
		});

		it('change to dropdown color picker when value from stylebook button is clicked', async () => {
			const {getByLabelText, getByTitle} = renderColorPicker({
				value: '#fff',
			});

			fireEvent.click(getByTitle('value-from-stylebook'));
			fireEvent.click(getByTitle('Blue'));

			expect(getByTitle('detach-token')).toBeInTheDocument();
			expect(getByLabelText('Blue')).toBeInTheDocument();
		});

		it('sets a token if the written value is an existing token', async () => {
			const {baseElement, getByLabelText, getByTitle} = renderColorPicker(
				{
					value: '#fff',
				}
			);

			onTypeValue(baseElement.querySelector('input'), 'green');

			expect(getByTitle('detach-token')).toBeInTheDocument();
			expect(getByLabelText('Green')).toBeInTheDocument();
		});

		it('sets the previous value when the input value is removed', async () => {
			const {baseElement} = renderColorPicker({
				value: '#444444',
			});
			const input = baseElement.querySelector('input');

			onTypeValue(input, '');

			expect(input).toHaveValue('#444444');
		});

		it('sets the previous value when the input value is an invalid hexcolor', async () => {
			const {baseElement} = renderColorPicker({
				value: '#444444',
			});
			const input = baseElement.querySelector('input');

			onTypeValue(input, '#44');

			expect(input).toHaveValue('#444444');
		});

		it('takes a 6-digit hexcolor even if the input value has more digits', async () => {
			const {baseElement} = renderColorPicker({
				value: '#444444',
			});
			const input = baseElement.querySelector('input');

			onTypeValue(input, '#55555555555');

			expect(input).toHaveValue('#555555');
		});

		it('converts the 3-digit hexcolor to a 6-digit hexcolor', async () => {
			const {baseElement} = renderColorPicker({
				value: '#444444',
			});
			const input = baseElement.querySelector('input');

			onTypeValue(baseElement.querySelector('input'), '#abc');

			expect(input).toHaveValue('#AABBCC');
		});

		describe('Input errors', () => {
			it('renders an error when the written token does not exist', async () => {
				const {baseElement, getByText} = renderColorPicker({
					value: '#fff',
				});

				onTypeValue(baseElement.querySelector('input'), 'prim');

				expect(
					getByText('this-token-does-not-exist')
				).toBeInTheDocument();
			});

			it('Clears an error when the clear selection button is clicked', async () => {
				const {
					baseElement,
					getByTitle,
					queryByText,
				} = renderColorPicker({
					value: '#fff',
				});

				onTypeValue(baseElement.querySelector('input'), 'prim');

				fireEvent.click(getByTitle('clear-selection'));

				expect(
					queryByText('this-token-does-not-exist')
				).not.toBeInTheDocument();
			});

			it('renders an error when the written token is the same that the name field', async () => {
				const {baseElement, getByText} = renderColorPicker({
					field: {...FIELD, name: 'orange'},
					value: '#fff',
				});

				onTypeValue(baseElement.querySelector('input'), 'orange');

				expect(
					getByText('tokens-cannot-reference-itself')
				).toBeInTheDocument();
			});

			it('renders an error when two tokens are mutually referenced', async () => {
				const {baseElement, getByText} = renderColorPicker({
					editedTokenValues: {
						blue: {
							name: 'orange',
							value: '#ffb46e',
						},
					},
					field: {...FIELD, name: 'orange'},
					value: '#fff',
				});

				onTypeValue(baseElement.querySelector('input'), 'blue');

				expect(
					getByText('tokens-cannot-be-mutually-referenced')
				).toBeInTheDocument();
			});
		});
	});
});
