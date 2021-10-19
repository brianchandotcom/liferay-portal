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
import userEvent from '@testing-library/user-event';
import React from 'react';

import Numeric from '../../../src/main/resources/META-INF/resources/Numeric/Numeric';

const globalLanguageDirection = Liferay.Language.direction;

describe('Field Numeric', () => {
	beforeAll(() => {
		Liferay.Language.direction = {en_US: 'rtl'};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('renders the default markup', () => {
		const {container} = render(<Numeric />);

		expect(container).toMatchSnapshot();
	});

	it('has a name', () => {
		const {container} = render(<Numeric name="numericField" />);

		const input = container.querySelector('input');

		expect(input).toHaveAttribute('name', 'numericField');
	});

	it('enables input whenever readOnly is omitted', () => {
		const {container} = render(<Numeric />);

		const input = container.querySelector('input');

		expect(input).toBeEnabled();
	});

	it('disables input whenever readOnly is set', () => {
		const {container} = render(<Numeric readOnly />);

		const input = container.querySelector('input');

		expect(input).toBeDisabled();
	});

	it('has a helptext', () => {
		const {getAllByText} = render(<Numeric tip="Type something" />);

		expect(getAllByText('Type something')[0]).toBeInTheDocument();
	});

	it('has an id', () => {
		const {container} = render(<Numeric id="ID" />);

		const input = container.querySelector('input');

		expect(input).toHaveAttribute('id', 'ID');
	});

	it('has a label', () => {
		const {getByText} = render(<Numeric label="label" />);

		expect(getByText(/label/)).toBeInTheDocument();
	});

	it('has a placeholder', () => {
		const {container} = render(<Numeric placeholder="Placeholder" />);

		const input = container.querySelector('input');

		expect(input).toHaveAttribute('placeholder', 'Placeholder');
	});

	it('is required', () => {
		const {getByText} = render(<Numeric required />);

		expect(getByText(/required/)).toBeInTheDocument();
	});

	it('renders Label if showLabel is true', () => {
		const {getByText} = render(<Numeric label="Numeric Field" showLabel />);

		expect(getByText(/Numeric Field/)).toHaveClass('ddm-label');
	});

	it('has a value', () => {
		const {container} = render(<Numeric value="123" />);

		const input = container.querySelector('input');

		expect(input).toHaveValue('123');
	});

	it('fills with an input number', () => {
		const onChange = jest.fn();
		const {container} = render(<Numeric onChange={onChange} />);

		const input = container.querySelector('input');
		userEvent.type(input, '2');

		expect(onChange).toHaveBeenCalledWith({target: {value: '2'}});
	});

	it('changes the mask type', () => {
		const {container} = render(<Numeric dataType="double" value="22.22" />);

		expect(container.querySelector('input').value).toBe('22.22');
	});

	it('filters the non numeric characters when set to integer', () => {
		const onChange = jest.fn();
		const {container} = render(<Numeric onChange={onChange} />);

		const input = container.querySelector('input');
		userEvent.type(input, '3.0');

		expect(onChange).toHaveBeenLastCalledWith({
			target: {value: '30'},
		});
	});

	it('check field value is the same without decimal symbol when fieldType is integer but it receives a double', () => {
		const {container} = render(<Numeric value="3.8" />);

		const input = container.querySelector('input');

		expect(input.value).toBe('38');
	});

	it('remove decimal symbol from value when changing from decimal to integer when symbol of language is comma', () => {
		const {container} = render(
			<Numeric
				dataType="integer"
				symbols={{decimalSymbol: ','}}
				value="22,82"
			/>
		);

		expect(container.querySelector('input').value).toBe('2282');
	});

	describe('Confirmation Field', () => {
		it('renders the confirmation field with the same data type as the original field', () => {
			render(
				<Numeric
					confirmationValue="22.82"
					dataType="double"
					name="numericField"
					requireConfirmation
				/>
			);

			const confirmationField = document.getElementById(
				'numericFieldconfirmationField'
			);

			expect(confirmationField.value).toBe('22.82');
		});

		it('remove decimal symbol of the confirmation value if the data type is Integer', () => {
			render(
				<Numeric
					confirmationValue="22.82"
					name="numericField"
					requireConfirmation
				/>
			);

			const confirmationField = document.getElementById(
				'numericFieldconfirmationField'
			);

			expect(confirmationField.value).toBe('2282');
		});
	});

	describe('Integer Input Mask toggle', () => {
		it('has an inputMaskFormat', () => {
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="+99 (99) 9999-9999"
					name="numericField"
					value="123456789012"
				/>
			);

			expect(container).toMatchSnapshot();
		});

		it('applies mask to value', () => {
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="+99 (99) 9999-9999"
					value="123456789012"
				/>
			);

			const input = container.querySelector('input');

			expect(input.value).toBe('+12 (34) 5678-9012');
		});

		it('applies mask to predefined value', () => {
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="+99 (99) 9999-9999"
					predefinedValue="123456789012"
				/>
			);

			const input = container.querySelector('input');

			expect(input.value).toBe('+12 (34) 5678-9012');
		});

		it('truncates values over mask digit limit', () => {
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="+99 (099) 9999-9999"
					value="12345678901234"
				/>
			);

			const input = container.querySelector('input');

			expect(input.value).toBe('+12 (345) 6789-0123');
		});

		it('ignores optional digits whenever input is less than mandatory', () => {
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="+09 (099) 9999-9999"
					value="12345"
				/>
			);

			const input = container.querySelector('input');

			expect(input.value).toBe('+1 (23) 45');
		});

		it('sends unmasked value though onChange event', () => {
			const onChange = jest.fn();
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="E.g +99 (99) 9999-9999"
					onChange={onChange}
				/>
			);

			const input = container.querySelector('input');

			userEvent.type(input, 'E.g +55 (81) 2121-6000');

			expect(onChange).toHaveBeenLastCalledWith({
				target: {value: '558121216000'},
			});
		});

		it('limits predefined value size according to the mask', () => {
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="99-99"
					name="LPS-134259"
					predefinedValue="12345"
				/>
			);

			const input = container.querySelector('input[name="LPS-134259"]');

			expect(input.value).toBe('1234');
		});

		/**
		 * This test was skipped due to an issue on userEvent.type() that not
		 * allows simulate backspace key pressing (with the current
		 * @testing-library/use-event)
		 */
		xit('it allows to delete non numeric characters from mask', () => {
			const {container} = render(
				<Numeric
					inputMask
					inputMaskFormat="99-99"
					onChange={() => {}}
					predefinedValue="12"
				/>
			);

			const input = container.querySelector('input');

			userEvent.click(input);
			userEvent.type(input, '{backspace}');

			expect(input.value).toBe('1');
		});
	});

	describe('Decimal Input Mask toggle', () => {
		it('renders a suffix', () => {
			const {container, getByText} = render(
				<Numeric
					append="$"
					appendType="suffix"
					dataType="double"
					inputMask
					name="numericField"
					value="123"
				/>
			);

			const input = container.querySelector('input');

			expect(input.value).toBe('123');
			expect(getByText('$')).toHaveClass('input-group-text');
		});

		it('renders a prefix', () => {
			const {container, getByText} = render(
				<Numeric
					append="$"
					appendType="prefix"
					dataType="double"
					inputMask
					name="numericField"
					value="123"
				/>
			);

			const input = container.querySelector('input');

			expect(getByText('$')).toHaveClass('input-group-text');
			expect(input.value).toBe('123');
		});

		it('renders the thousand separator', () => {
			const {container} = render(
				<Numeric
					dataType="double"
					inputMask
					name="numericField"
					symbols={{decimalSymbol: '.', thousandsSeparator: ','}}
					value="1234"
				/>
			);

			const input = container.querySelector('input');

			expect(input.value).toBe('1,234');
		});

		it('hides the thousand separator if it is set to `none`', () => {
			const {container} = render(
				<Numeric
					dataType="double"
					inputMask
					name="numericField"
					symbols={{decimalSymbol: '.', thousandsSeparator: 'none'}}
					value="1234"
				/>
			);

			const input = container.querySelector('input');

			expect(input.value).toBe('1234');
		});

		it('allows user to input a decimal separator', () => {
			const onChange = jest.fn();
			const {container} = render(
				<Numeric
					dataType="double"
					inputMask
					name="numericField"
					onChange={onChange}
					symbols={{decimalSymbol: ','}}
				/>
			);

			const input = container.querySelector('input');

			userEvent.type(input, '1,234');

			expect(onChange).toHaveBeenLastCalledWith({
				target: {value: '1,23'},
			});
		});

		it('generates a placeholder', () => {
			const {container} = render(
				<Numeric
					dataType="double"
					decimalPlaces="2"
					inputMask
					name="numericField"
					symbols={{decimalSymbol: ','}}
				/>
			);

			const input = container.querySelector('input');

			expect(input).toHaveAttribute('placeholder', '0,00');
		});

		it('allows user to input ony the decimal quantity defined by decimal places field', () => {
			const onChange = jest.fn();
			const {container} = render(
				<Numeric
					dataType="double"
					decimalPlaces={3}
					inputMask
					name="numericField"
					onChange={onChange}
					symbols={{decimalSymbol: ','}}
				/>
			);

			const input = container.querySelector('input');

			userEvent.type(input, '1,2345678');

			expect(onChange).toHaveBeenLastCalledWith({
				target: {value: '1,234'},
			});
		});

		/**
		 * LPS-136519 / LPS-136523
		 */
		it('ignores non decimal input', () => {
			const onChange = jest.fn();
			const {container} = render(
				<Numeric
					append="999"
					appendType="suffix"
					dataType="double"
					inputMask
					name="numericField"
					onChange={onChange}
					symbols={{decimalSymbol: ','}}
				/>
			);

			const input = container.querySelector('input');

			userEvent.type(input, 'a# @e');

			expect(onChange).not.toHaveBeenCalled();
		});
	});
});
