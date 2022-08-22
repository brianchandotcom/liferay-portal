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

import {AdvancedSelectField} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/fragment-configuration-fields/AdvancedSelectField';

const FIELD = {
	cssProperty: 'font-size',
	defaultValue: '',
	icon: 'font-size',
	label: 'font-size',
	name: 'fontSize',
	type: 'select',
};

const OPTIONS = [
	{
		label: 'Inherited',
		value: '',
	},
	{
		label: 'Font Size Small',
		value: 'fontSizeSm',
	},
	{
		label: 'Font Size Base',
		value: 'fontSizeBase',
	},
	{
		label: 'Font Size Large',
		value: 'fontSizeLg',
	},
];

const TOKEN_VALUES = {
	fontSizeBase: {
		cssVariable: 'font-size-base',
		editorType: 'Length',
		label: 'Font Size Base',
		name: 'fontSizeBase',
		tokenSetLabel: 'Font Size',
		value: '0.875rem',
	},
	fontSizeLg: {
		cssVariable: 'font-size-lg',
		editorType: 'Length',
		label: 'Font Size Large',
		name: 'fontSizeLg',
		tokenSetLabel: 'Font Size',
		value: '1.125rem',
	},
	fontSizeSm: {
		cssVariable: 'font-size-sm',
		editorType: 'Length',
		label: 'Font Size Small',
		name: 'fontSizeSm',
		tokenSetLabel: 'Font Size',
		value: '1rem',
	},
};

const ITEM = {
	config: {
		landscapeMobile: {
			styles: {
				fontSize: 'fontSizeLg',
			},
		},
		styles: {
			fontSize: 'fontSizeDesktop',
		},
		tablet: {
			styles: {
				fontSize: 'fontSizeBase',
			},
		},
	},
};

const renderAdvancedSelectField = ({
	field = FIELD,
	onValueSelect = () => {},
	selectedViewportSize = 'desktop',
	value = '',
	canDetachTokenValues = true,
} = {}) =>
	render(
		<AdvancedSelectField
			canDetachTokenValues={canDetachTokenValues}
			field={field}
			item={ITEM}
			onValueSelect={onValueSelect}
			options={OPTIONS}
			selectedViewportSize={selectedViewportSize}
			tokenValues={TOKEN_VALUES}
			value={value}
		/>
	);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'Desktop'},
				tablet: {label: 'tablet'},
			},
		},
	})
);

describe('AdvancedSelectField', () => {
	it('renders AdvancedSelectField', () => {
		renderAdvancedSelectField();

		expect(screen.getByLabelText('font-size')).toBeInTheDocument();
	});

	it('changes the value', () => {
		renderAdvancedSelectField();
		const select = screen.getByLabelText('font-size');

		userEvent.selectOptions(select, 'fontSizeSm');
		fireEvent.change(select);

		expect(select.options[1].selected).toBeTruthy();
	});

	it('displays Detach button if the selected option is a token', () => {
		renderAdvancedSelectField({value: 'fontSizeSm'});

		expect(screen.getByTitle('detach-token')).toBeInTheDocument();
	});

	it('renders an input with the token value when Detach button is clicked', () => {
		renderAdvancedSelectField({value: 'fontSizeLg'});

		userEvent.click(screen.getByTitle('detach-token'));

		const input = screen.getByLabelText('font-size');

		expect(input).toBeInTheDocument();
		expect(input).toHaveValue('1.125rem');
	});

	it('saves the value when a new value is typed and the user leaves the input', () => {
		const onValueSelect = jest.fn();
		renderAdvancedSelectField({
			onValueSelect,
			value: 'mystyle',
		});
		const input = screen.getByLabelText('font-size');

		userEvent.type(input, 'initial');
		fireEvent.blur(input);

		expect(onValueSelect).toBeCalledWith(FIELD.name, 'initial');
		expect(input).toHaveValue('initial');
	});

	it('saves the value when a new value is typed and Enter key is pressed', () => {
		const onValueSelect = jest.fn();
		renderAdvancedSelectField({
			onValueSelect,
			value: 'mystyle',
		});
		const input = screen.getByLabelText('font-size');

		userEvent.type(input, 'initial');
		fireEvent.keyDown(input, {key: 'Enter'});

		expect(onValueSelect).toBeCalledWith(FIELD.name, 'initial');
		expect(input).toHaveValue('initial');
	});

	it('keeps the last value when the input is cleared', () => {
		renderAdvancedSelectField({
			value: 'mystyle',
		});
		const input = screen.getByLabelText('font-size');

		userEvent.type(input, '');
		fireEvent.blur(input);

		expect(input).toHaveValue('mystyle');
	});

	it('renders the select when a token value is selected from the Value From Stylebook button', () => {
		renderAdvancedSelectField({
			value: 'mystyle',
		});

		userEvent.click(screen.getByText('Font Size Base'));

		expect(screen.getByLabelText('font-size').tagName).toBe('SELECT');
	});

	it('renders the LengthField when the field has units', () => {
		renderAdvancedSelectField({
			field: {
				...FIELD,
				typeOptions: {
					showLengthField: true,
				},
			},
			value: 'mystyle',
		});

		expect(screen.getByTitle('select-units')).toBeInTheDocument();
	});

	it('does not render the Detach button when user does not have update permission', () => {
		renderAdvancedSelectField({
			canDetachTokenValues: false,
			value: 'fontSizeLg',
		});

		expect(screen.queryByTitle('detach-token')).not.toBeInTheDocument();
	});

	it('does not render the Value from Stylebook button when user does not have update permission', () => {
		renderAdvancedSelectField({
			canDetachTokenValues: false,
			value: 'my size',
		});

		expect(
			screen.queryByTitle('value-from-stylebook')
		).not.toBeInTheDocument();
	});

	it('clears the value when the "Reset" button is clicked and the viewport is Desktop', () => {
		renderAdvancedSelectField({
			value: 'fontSizeLg',
		});

		userEvent.click(screen.getByTitle('reset-to-initial-value'));

		const select = screen.getByLabelText('font-size');

		expect(select.tagName).toBe('SELECT');
		expect(select.nextSibling.textContent).toBe('Inherited');
	});

	it('sets the value of the previous viewport when the "Reset" button is clicked', () => {
		renderAdvancedSelectField({
			selectedViewportSize: 'landscapeMobile',
			value: 'fontSizeSm',
		});

		userEvent.click(screen.getByTitle('reset-to-tablet-value'));

		const select = screen.getByLabelText('font-size');

		expect(select.tagName).toBe('SELECT');
		expect(select.nextSibling.textContent).toBe('Font Size Base');
	});
});
