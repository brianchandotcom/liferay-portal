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
import React from 'react';

import ColorPicker from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/ColorPicker';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			tokenReuseEnabled: true,
		},
	})
);

const COLORS = {
	'Category 1': {
		'TokenSet 1': [
			{
				label: 'Color 1',
				name: 'color1',
				value: '#0b5fff',
			},
		],
		'TokenSet 2': [
			{
				label: 'Color 2',
				name: 'color2',
				value: '#ffffff',
			},
		],
	},
	'Category 2': {
		'TokenSet 3': [
			{
				label: 'Color 3',
				name: 'color3',
				value: '#0b5fff',
			},
		],
	},
};

const renderColorPicker = ({
	active = false,
	label = 'default',
	onSetActive = () => {},
	onValueChange = () => {},
	showSelector = true,
	value = '#fff',
}) =>
	render(
		<ColorPicker
			active={active}
			colors={COLORS}
			label={label}
			onSetActive={onSetActive}
			onValueChange={onValueChange}
			showSelector={showSelector}
			value={value}
		/>
	);

describe('ColorPicker', () => {
	afterEach(() => {
		cleanup();
		jest.useFakeTimers();
	});

	it('renders the ColorPicker without label and sploch', () => {
		const {getByTitle} = renderColorPicker({showSelector: false});

		expect(getByTitle('value-from-stylebook')).toBeInTheDocument();
	});

	it('renders the ColorPicker as selector with label and sploch', async () => {
		const label = 'Danger';
		const value = '#ffb46e';

		const {baseElement, getByLabelText} = renderColorPicker({label, value});

		expect(getByLabelText(label)).toBeInTheDocument();
		expect(
			baseElement.querySelector(
				'.page-editor__color-picker__selector-splotch'
			)
		).toHaveStyle(`background: ${value}`);
	});

	it('opens the ColorPicker', async () => {
		const {getByText, getByTitle} = renderColorPicker({active: true});

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
		const {getByLabelText, queryByText} = renderColorPicker({active: true});
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
		const {getByLabelText, queryByText} = renderColorPicker({active: true});
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
		} = renderColorPicker({active: true});
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
		const {getByLabelText, queryByText} = renderColorPicker({active: true});
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
