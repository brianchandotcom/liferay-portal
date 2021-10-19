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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import NumericInputMask from '../../../src/main/resources/META-INF/resources/NumericInputMask/NumericInputMask';

const globalLanguageDirection = Liferay.Language.direction;

const DECIMAL_SYMBOLS = [
	{label: '0,00', reference: ',', value: ','},
	{label: '0.00', reference: '.', value: '.'},
];

const THOUSANDS_SEPARATORS = [
	{label: '1 000', reference: ' ', value: ' '},
	{label: "1'000", reference: "'", value: "'"},
	{label: 'None', reference: 'none', value: 'none'},
	{label: '1,000', reference: ',', value: ','},
	{label: '1.000', reference: '.', value: '.'},
];

describe('Field Numeric Input Mask', () => {
	beforeAll(() => {
		jest.useFakeTimers();
		Liferay.Language.direction = {en_US: 'rtl'};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('shows the Thousands Separator, Decimal Separator, Decimal places and Prefix or Suffix field by default', () => {
		const {container} = render(
			<NumericInputMask
				append=""
				decimalSymbols={DECIMAL_SYMBOLS}
				thousandsSeparators={THOUSANDS_SEPARATORS}
				visible={true}
			/>
		);

		const appendField = container.querySelector(
			'[data-field-name="append"]'
		);
		const appendFieldInput = container.querySelector(
			'input[name="append"]'
		);
		const decimalPlacesFieldInput = container.querySelector(
			'input[name="decimal_places"]'
		);
		const appendTypeField = container.querySelector(
			'[data-field-name="appendType"]'
		);
		const decimalSymbolField = container.querySelector(
			'[data-field-name="decimalSymbol"]'
		);
		const thousandsSeparatorField = container.querySelector(
			'[data-field-name="thousandsSeparator"]'
		);

		expect(appendField).toBeInTheDocument();
		expect(appendFieldInput.maxLength).toBe(10);
		expect(appendTypeField).not.toBeInTheDocument();
		expect(decimalPlacesFieldInput).toBeInTheDocument();
		expect(decimalSymbolField).toBeInTheDocument();
		expect(thousandsSeparatorField).toBeInTheDocument();
	});

	it('shows the radio selector to choose the append type when there is a suffix or preffix text', () => {
		const {container} = render(
			<NumericInputMask
				append="$"
				decimalSymbols={DECIMAL_SYMBOLS}
				thousandsSeparators={THOUSANDS_SEPARATORS}
				visible={true}
			/>
		);

		const appendTypeField = container.querySelector(
			'[data-field-name="appendType"]'
		);

		expect(appendTypeField).toBeInTheDocument();
	});

	it('disables the decimalSymbol option if its equal to the selected thousands separator option', () => {
		render(
			<NumericInputMask
				append=""
				decimalSymbol=""
				decimalSymbols={DECIMAL_SYMBOLS}
				thousandsSeparator=","
				thousandsSeparators={THOUSANDS_SEPARATORS}
				visible={true}
			/>
		);

		const disabledOption = document.querySelector(
			'.dropdown-item.disabled:last-of-type'
		);

		expect(disabledOption.getAttribute('label')).toBe('0,00');
	});

	it('disables the thousandsSeparator option if its equal to the selected decimal symbol option', () => {
		render(
			<NumericInputMask
				append=""
				decimalSymbol="."
				decimalSymbols={DECIMAL_SYMBOLS}
				thousandsSeparator=""
				thousandsSeparators={THOUSANDS_SEPARATORS}
				visible={true}
			/>
		);

		const disabledOption = document.querySelector(
			'.dropdown-item.disabled:last-of-type'
		);

		expect(disabledOption.getAttribute('label')).toBe('1.000');
	});
});
