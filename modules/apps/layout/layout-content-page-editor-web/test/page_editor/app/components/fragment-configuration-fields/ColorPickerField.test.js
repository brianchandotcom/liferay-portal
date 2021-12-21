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
import {cleanup, fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';

import {ColorPickerField} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-configuration-fields/ColorPickerField';
import {StoreContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {StyleBookContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-design-options/hooks/useStyleBook';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			frontendTokens: {
				blue: {
					editorType: 'ColorPicker',
					label: 'Blue',
					name: 'blue',
					tokenCategoryLabel: 'Category1',
					tokenSetLabel: 'TokenSet 1',
					value: '#4b9fff',
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
			},
			styleBookEntryId: 0,
			tokenReuseEnabled: true,
		},
	})
);

const INPUT_NAME = 'Color Picker';
const COLOR_PICKER_FIELD_CLASS = '.page-editor__color-picker-field';

const renderColorPickerField = ({onValueSelect = () => {}, value = 'white'}) =>
	render(
		<StoreContextProvider initialState={{}} reducer={(state) => state}>
			<StyleBookContextProvider>
				<ColorPickerField
					field={{label: INPUT_NAME, name: INPUT_NAME}}
					onValueSelect={onValueSelect}
					value={value}
				/>
			</StyleBookContextProvider>
		</StoreContextProvider>
	);

describe('ColorPickerField', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders the ColorPickerField', () => {
		const {baseElement} = renderColorPickerField({});

		expect(
			baseElement.querySelector(
				`${COLOR_PICKER_FIELD_CLASS}__color-picker`
			)
		).toBeInTheDocument();
	});

	it('clears the value', async () => {
		const {getByLabelText, getByTitle} = renderColorPickerField({
			value: 'green',
		});

		fireEvent.click(getByTitle('clear-selection'));

		await waitFor(() => {
			expect(getByLabelText('default')).toBeInTheDocument();
		});
	});

	describe('When the value is an existing token', () => {
		it('renders the stylebook color picker', () => {
			const {getByLabelText, getByTitle} = renderColorPickerField({
				value: 'orange',
			});

			expect(getByTitle('detach-token')).toBeInTheDocument();
			expect(getByLabelText('Orange')).toBeInTheDocument();
		});

		it('shows action buttons when the color picker is clicked', async () => {
			const {baseElement, getByLabelText} = renderColorPickerField({
				value: 'orange',
			});

			fireEvent.click(getByLabelText('Orange'));

			await waitFor(() => {
				expect(
					baseElement.querySelector(COLOR_PICKER_FIELD_CLASS)
				).toHaveClass('hovered');
			});
		});

		it('change to autocomplete color picker when detach token button is clicked', async () => {
			const {baseElement, getByRole, getByTitle} = renderColorPickerField(
				{
					value: 'orange',
				}
			);

			fireEvent.click(getByTitle('detach-token'));

			await waitFor(() => {
				expect(getByTitle('value-from-stylebook')).toBeInTheDocument();
				expect(getByRole('combobox').value).toBe('#ffb46e');
				expect(
					baseElement.querySelector('.clay-color-picker')
				).toBeInTheDocument();
			});
		});

		it('does not show the action buttons when the value is default', () => {
			const {queryByTitle} = renderColorPickerField({
				value: null,
			});

			expect(queryByTitle('detach-token')).not.toBeInTheDocument();
			expect(
				queryByTitle('value-from-stylebook')
			).not.toBeInTheDocument();
		});
	});

	describe('When the value is an hexadecimal', () => {
		it('renders the autocomplete color picker', () => {
			const {baseElement, getByRole, getByTitle} = renderColorPickerField(
				{
					value: '#ffb46e',
				}
			);

			expect(getByTitle('value-from-stylebook')).toBeInTheDocument();
			expect(getByRole('combobox').value).toBe('#ffb46e');
			expect(
				baseElement.querySelector('.clay-color-picker')
			).toBeInTheDocument();
		});

		it('change to stylebook color picker when value from stylebook button is clicked', async () => {
			const {getByLabelText, getByTitle} = renderColorPickerField({
				value: '#fff',
			});

			fireEvent.click(getByTitle('value-from-stylebook'));
			fireEvent.click(getByTitle('Blue'));

			await waitFor(() => {
				expect(getByTitle('detach-token')).toBeInTheDocument();
				expect(getByLabelText('Blue')).toBeInTheDocument();
			});
		});

		it('renders an error when the written token is wrong', async () => {
			const {getByRole, getByText} = renderColorPickerField({
				value: '#fff',
			});

			const input = getByRole('combobox');

			fireEvent.change(input, {
				target: {value: 'prim'},
			});

			fireEvent.blur(input);

			await waitFor(() => {
				expect(
					getByText('this-token-does-not-exist')
				).toBeInTheDocument();
			});
		});

		it('sets a token if the written value is an existing token', async () => {
			const {
				getByLabelText,
				getByRole,
				getByTitle,
			} = renderColorPickerField({
				value: '#fff',
			});

			const input = getByRole('combobox');

			fireEvent.change(input, {
				target: {value: 'green'},
			});

			fireEvent.blur(input);

			await waitFor(() => {
				expect(getByTitle('detach-token')).toBeInTheDocument();
				expect(getByLabelText('Green')).toBeInTheDocument();
			});
		});

		it('sets a token when the value is selected from the autocomplete dropdown', async () => {
			const {
				getByLabelText,
				getByRole,
				getByTitle,
			} = renderColorPickerField({
				value: '#fff',
			});

			fireEvent.change(getByRole('combobox'), {
				target: {value: 'gre'},
			});
			fireEvent.click(getByRole('option'));

			await waitFor(() => {
				expect(getByTitle('detach-token')).toBeInTheDocument();
				expect(getByLabelText('Green')).toBeInTheDocument();
			});
		});

		it('sets the previous value when the input value is removed', async () => {
			const {getByRole} = renderColorPickerField({
				value: '#444444',
			});

			const input = getByRole('combobox');

			fireEvent.change(input, {
				target: {value: ''},
			});

			fireEvent.blur(input);

			await waitFor(() => {
				expect(getByRole('combobox').value).toBe('#444444');
			});
		});
	});
});
