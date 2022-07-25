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
import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {LengthField} from '../../../../src/main/resources/META-INF/resources/page_editor/common/components/LengthField';

const renderLengthField = ({
	onValueSelect = () => {},
	value = '12px',
	field = {label: 'Length Field', name: 'length-field'},
} = {}) =>
	render(
		<LengthField
			field={field}
			onValueSelect={onValueSelect}
			value={value}
		/>
	);

describe('LengthField', () => {
	it('renders LengthField', () => {
		renderLengthField();

		expect(screen.getByText('Length Field')).toBeInTheDocument();
	});

	it('shows the value', () => {
		renderLengthField();

		expect(screen.getByLabelText('Length Field')).toHaveValue(12);
		expect(screen.getByLabelText('select-a-unit').textContent).toBe('PX');
	});

	it('changes the number of the value', () => {
		renderLengthField();
		const input = screen.getByLabelText('Length Field');

		userEvent.type(input, '20');

		expect(input).toHaveValue(20);
	});

	it('changes the unit of the value', () => {
		renderLengthField();

		userEvent.click(screen.getByText('%'));

		expect(screen.getByLabelText('select-a-unit').textContent).toBe('%');
	});

	it('keeps the empty input and the units when the value is cleared', () => {
		renderLengthField({value: '14vh'});
		const input = screen.getByLabelText('Length Field');

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

	it('does not allow typing letters when a unit is selected', () => {
		renderLengthField();
		const input = screen.getByLabelText('Length Field');

		userEvent.type(input, 'auto');

		expect(input).toHaveValue(null);
	});
});
