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
import {cleanup, fireEvent, render, wait} from '@testing-library/react';
import React from 'react';

import {StoreContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {ColorPicker} from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ColorPicker';

const CONFIG = {
	tokenReuseEnabled: true,
};
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

const renderColorPicker = ({onValueSelect = () => {}, value = 'white'}) =>
	render(
		<StoreContextProvider initialState={{}} reducer={(state) => state}>
			<ColorPicker
				config={CONFIG}
				field={{label: INPUT_NAME, name: INPUT_NAME}}
				onValueSelect={onValueSelect}
				tokenValues={TOKEN_VALUES}
				value={value}
			/>
		</StoreContextProvider>
	);

describe('ColorPicker', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders the ColorPicker', () => {
		const {baseElement} = renderColorPicker({});

		expect(
			baseElement.querySelector(`${COLOR_PICKER_CLASS}`)
		).toBeInTheDocument();
	});

	it('clears the value', async () => {
		const {getByLabelText, getByTitle} = renderColorPicker({
			value: 'green',
		});

		fireEvent.click(getByTitle('clear-selection'));

		await wait(() => {
			expect(getByLabelText('default')).toBeInTheDocument();
		});
	});

	describe('When the value is an existing token', () => {
		it('renders the stylebook color picker', () => {
			const {getByLabelText, getByTitle} = renderColorPicker({
				value: 'orange',
			});

			expect(getByTitle('detach-token')).toBeInTheDocument();
			expect(getByLabelText('Orange')).toBeInTheDocument();
		});

		it('shows action buttons when the color picker is clicked', async () => {
			const {baseElement, getByLabelText} = renderColorPicker({
				value: 'orange',
			});

			fireEvent.click(getByLabelText('Orange'));

			await wait(() => {
				expect(
					baseElement.querySelector(COLOR_PICKER_CLASS)
				).toHaveClass('hovered');
			});
		});

		it('change to autocomplete color picker when detach token button is clicked', async () => {
			const {baseElement, getByRole, getByTitle} = renderColorPicker({
				value: 'orange',
			});

			fireEvent.click(getByTitle('detach-token'));

			await wait(() => {
				expect(getByTitle('value-from-stylebook')).toBeInTheDocument();
				expect(getByRole('combobox').value).toBe('#ffb46e');
				expect(
					baseElement.querySelector('.clay-color-picker')
				).toBeInTheDocument();
			});
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
	});

	describe('When the value is an hexadecimal', () => {
		it('renders the autocomplete color picker', () => {
			const {baseElement, getByRole, getByTitle} = renderColorPicker({
				value: '#ffb46e',
			});

			expect(getByTitle('value-from-stylebook')).toBeInTheDocument();
			expect(getByRole('combobox').value).toBe('#ffb46e');
			expect(
				baseElement.querySelector('.clay-color-picker')
			).toBeInTheDocument();
		});

		it('change to stylebook color picker when value from stylebook button is clicked', async () => {
			const {getByLabelText, getByTitle} = renderColorPicker({
				value: '#fff',
			});

			fireEvent.click(getByTitle('value-from-stylebook'));
			fireEvent.click(getByTitle('Blue'));

			await wait(() => {
				expect(getByTitle('detach-token')).toBeInTheDocument();
				expect(getByLabelText('Blue')).toBeInTheDocument();
			});
		});

		it('renders an error when the written token is wrong', async () => {
			const {getByRole, getByText} = renderColorPicker({
				value: '#fff',
			});

			const input = getByRole('combobox');

			fireEvent.change(input, {
				target: {value: 'prim'},
			});

			fireEvent.blur(input);

			await wait(() => {
				expect(
					getByText('this-token-does-not-exist')
				).toBeInTheDocument();
			});
		});

		it('sets a token if the written value is an existing token', async () => {
			const {getByLabelText, getByRole, getByTitle} = renderColorPicker({
				value: '#fff',
			});

			const input = getByRole('combobox');

			fireEvent.change(input, {
				target: {value: 'green'},
			});

			fireEvent.blur(input);

			await wait(() => {
				expect(getByTitle('detach-token')).toBeInTheDocument();
				expect(getByLabelText('Green')).toBeInTheDocument();
			});
		});

		it('sets a token when the value is selected from the autocomplete dropdown', async () => {
			const {getByLabelText, getByRole, getByTitle} = renderColorPicker({
				value: '#fff',
			});

			fireEvent.change(getByRole('combobox'), {
				target: {value: 'gre'},
			});
			fireEvent.click(getByRole('option'));

			await wait(() => {
				expect(getByTitle('detach-token')).toBeInTheDocument();
				expect(getByLabelText('Green')).toBeInTheDocument();
			});
		});

		it('sets the previous value when the input value is removed', async () => {
			const {getByRole} = renderColorPicker({
				value: '#444444',
			});

			const input = getByRole('combobox');

			fireEvent.change(input, {
				target: {value: ''},
			});

			fireEvent.blur(input);

			await wait(() => {
				expect(getByRole('combobox').value).toBe('#444444');
			});
		});
	});
});
