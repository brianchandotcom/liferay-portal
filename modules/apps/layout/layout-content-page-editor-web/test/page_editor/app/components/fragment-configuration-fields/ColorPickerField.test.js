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
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {ColorPickerField} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-configuration-fields/ColorPickerField';
import {StoreContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import {StyleBookContextProvider} from '../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page-design-options/hooks/useStyleBook';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			frontendTokens: {
				color1: {
					editorType: 'ColorPicker',
					label: 'Color 1',
					name: 'color1',
					tokenCategoryLabel: 'Category 1',
					tokenSetLabel: 'TokenSet 1',
					value: '#fff',
				},
				color2: {
					editorType: 'ColorPicker',
					label: 'Color 2',
					name: 'color2',
					tokenCategoryLabel: 'Category1',
					tokenSetLabel: 'TokenSet 2',
					value: '#000',
				},
				color3: {
					editorType: 'ColorPicker',
					label: 'Color 3',
					name: 'color3',
					tokenCategoryLabel: 'Category 2',
					tokenSetLabel: 'TokenSet 3',
					value: '#ff0',
				},
			},
			styleBookEntryId: 0,
			tokenOptimizationEnabled: true,
		},
	})
);

const INPUT_NAME = 'Color Picker';

const renderColorPickerField = (onValueSelect = () => {}) =>
	render(
		<StoreContextProvider initialState={{}} reducer={(state) => state}>
			<StyleBookContextProvider>
				<ColorPickerField
					field={{label: INPUT_NAME, name: INPUT_NAME}}
					onValueSelect={onValueSelect}
					value=""
				/>
			</StyleBookContextProvider>
		</StoreContextProvider>
	);

const openDropdown = () => {
	const dropdownButton = document.querySelector('.clay-color-picker button');

	userEvent.click(dropdownButton);
};

describe('ColorPickerField', () => {
	afterEach(() => {
		cleanup();
		jest.useFakeTimers();
	});

	it('renders the colorPickerField', async () => {
		const {getByText, getByTitle} = renderColorPickerField();

		openDropdown();

		const palette = [
			getByText('Category 1'),
			getByText('Category 2'),
			getByText('TokenSet 1'),
			getByText('TokenSet 2'),
			getByText('TokenSet 3'),
			getByTitle('Color 1'),
			getByTitle('Color 2'),
			getByTitle('Color 3'),
		];

		palette.forEach((item) => expect(item).toBeInTheDocument());
	});

	it('filters by category', () => {
		const {getByLabelText, queryByText} = renderColorPickerField();

		openDropdown();

		const searchForm = getByLabelText('search-form');

		act(() => {
			fireEvent.change(searchForm, {
				target: {value: 'Category 1'},
			});
			jest.runAllTimers();
		});

		expect(queryByText('Category 1')).toBeInTheDocument();
		expect(queryByText('Category 2')).not.toBeInTheDocument();
	});

	it('filters by tokenSet', () => {
		const {getByLabelText, queryByText} = renderColorPickerField();

		openDropdown();

		const searchForm = getByLabelText('search-form');

		act(() => {
			fireEvent.change(searchForm, {
				target: {value: 'TokenSet 1'},
			});
			jest.runAllTimers();
		});

		[queryByText('Category 1'), queryByText('TokenSet 1')].forEach((item) =>
			expect(item).toBeInTheDocument()
		);
		[queryByText('Category 2'), queryByText('TokenSet 2')].forEach((item) =>
			expect(item).not.toBeInTheDocument()
		);
	});

	it('filters by color', () => {
		const {
			getByLabelText,
			getByTitle,
			queryByText,
			queryByTitle,
		} = renderColorPickerField();

		openDropdown();

		const searchForm = getByLabelText('search-form');

		act(() => {
			fireEvent.change(searchForm, {
				target: {value: 'Color 1'},
			});
			jest.runAllTimers();
		});

		[
			queryByText('Category 1'),
			queryByText('TokenSet 1'),
			getByTitle('Color 1'),
		].forEach((item) => expect(item).toBeInTheDocument());
		[
			queryByText('Category 2'),
			queryByTitle('Color 2'),
			queryByTitle('Color 3'),
		].forEach((item) => expect(item).not.toBeInTheDocument());
	});

	it('shows empty results', () => {
		const {getByLabelText, queryByText} = renderColorPickerField();

		openDropdown();

		const searchForm = getByLabelText('search-form');

		act(() => {
			fireEvent.change(searchForm, {
				target: {value: 'Color 123'},
			});
			jest.runAllTimers();
		});

		expect(queryByText('no-results-found')).toBeInTheDocument();
	});
});
