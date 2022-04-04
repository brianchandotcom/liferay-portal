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
import React from 'react';

import SpacingBox from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/SpacingBox';

const SpacingBoxTest = ({onChange = () => {}, value = {}}) => (
	<SpacingBox
		defaultValue="0"
		onChange={onChange}
		options={[
			{label: '0', value: '0'},
			{label: '10', value: '10'},
		]}
		value={value}
	/>
);

describe('SpacingBox', () => {
	let _getComputedStyle;
	let _liferayUtilSub;

	beforeEach(() => {
		_getComputedStyle = window.getComputedStyle;
		_liferayUtilSub = window.Liferay.Util.sub;
	});

	afterEach(() => {
		window.getComputedStyle = _getComputedStyle;
		window.Liferay.Util.sub = _liferayUtilSub;
	});

	it('renders given spacing values', async () => {
		render(<SpacingBoxTest value={{marginTop: 10}} />);
		expect(screen.getByLabelText('Padding Left')).toHaveTextContent('0');
		expect(screen.getByLabelText('Margin Top')).toHaveTextContent('10');
	});

	it('can be navigated with keyboard', () => {
		render(<SpacingBoxTest />);

		const grid = screen.getByRole('grid');

		screen.getByLabelText('Margin Top').focus();

		fireEvent.keyDown(grid, {key: 'ArrowDown'});
		fireEvent.keyDown(grid, {key: 'ArrowRight'});
		fireEvent.keyDown(grid, {key: 'ArrowRight'});
		fireEvent.keyDown(grid, {key: 'ArrowDown'});
		fireEvent.keyDown(grid, {key: 'ArrowLeft'});
		fireEvent.keyDown(grid, {key: 'ArrowRight'});

		expect(screen.getByLabelText('Padding Left')).toHaveFocus();
	});

	it('can be used to update spacing', () => {
		window.Liferay.Util.sub = (key, args) => {
			return args
				.reduce((key, arg) => key.replace('x', arg), key)
				.replaceAll('-', ' ');
		};

		const onChange = jest.fn();
		render(<SpacingBoxTest onChange={onChange} />);

		fireEvent.click(screen.getByLabelText('Padding Left'));
		fireEvent.click(screen.getByLabelText('set Padding Left to 10'));

		expect(onChange).toHaveBeenCalledWith('paddingLeft', '10');
	});

	it('shows token value next to token name in the dropdown', () => {
		window.getComputedStyle = (element) => {
			return {
				getPropertyValue: (key) => {
					return `[${element.className}][${key}]`;
				},
			};
		};

		render(<SpacingBoxTest />);

		fireEvent.click(screen.getByLabelText('Padding Left'));
		expect(screen.getByText('[pl-10][padding-left]')).toBeInTheDocument();
	});
});
