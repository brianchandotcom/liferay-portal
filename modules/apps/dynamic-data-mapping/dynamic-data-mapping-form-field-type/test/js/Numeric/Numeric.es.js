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

import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import Numeric from '../../../src/main/resources/META-INF/resources/Numeric/Numeric.es';

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

		expect(container).toMatchSnapshot();
	});

	it('is not readOnly', () => {
		const {container} = render(<Numeric readOnly={false} />);

		expect(container).toMatchSnapshot();
	});

	it('has a helptext', () => {
		const {container} = render(<Numeric tip="Type something" />);

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(<Numeric id="ID" />);

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(<Numeric label="label" />);

		expect(container).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		const {container} = render(<Numeric placeholder="Placeholder" />);

		expect(container).toMatchSnapshot();
	});

	it('is not required', () => {
		const {container} = render(<Numeric required={false} />);

		expect(container).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		const {container} = render(
			<Numeric label="Numeric Field" showLabel={true} />
		);

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(<Numeric value="123" />);

		expect(container).toMatchSnapshot();
	});

	it('has a key', () => {
		const {container} = render(<Numeric key="key" />);

		expect(container).toMatchSnapshot();
	});

	it('fills with an input number', () => {
		let output;
		const {container} = render(
			<Numeric
				onChange={({target: {value}}) => {
					output = value;
				}}
			/>
		);

		const input = container.querySelector('input');

		userEvent.type(input, '2');

		expect(output).toBe('2');
	});

	it('changes the mask type', () => {
		const {container} = render(<Numeric dataType="double" value="22.22" />);

		expect(container.querySelector('input').value).toBe('22.22');
	});

	it('filters the non numeric characters when set to integer', () => {
		let output = '';
		const {container} = render(
			<Numeric
				onChange={({target: {value}}) => {
					output += value;
				}}
			/>
		);

		const input = container.querySelector('input');

		// due to an issue on jest was needed to call type() for each key
		// https://github.com/testing-library/user-event/issues/387

		userEvent.type(input, '3');
		userEvent.type(input, '.');
		userEvent.type(input, '0');

		expect(output).toBe('30');
	});

	it('check field value is rounded when fieldType is integer but it receives a double', () => {
		const {container} = render(<Numeric value="3.8" />);

		const input = container.querySelector('input');

		expect(input.value).toBe('4');
	});

	it('round up value when changing from decimal to integer when symbol of language is comma', () => {
		const {container} = render(
			<Numeric
				dataType="integer"
				symbols={{decimalSymbol: ','}}
				value="22,82"
			/>
		);

		expect(container.querySelector('input').value).toBe('23');
	});

	describe('Confirmation Field', () => {
		it('renders the confirmation field with the same data type as the original field', () => {
			render(
				<Numeric
					confirmationValue="22.82"
					dataType="double"
					name="numericField"
					requireConfirmation={true}
				/>
			);

			const confirmationField = document.getElementById(
				'numericFieldconfirmationField'
			);

			expect(confirmationField.value).toBe('22.82');
		});

		it('rounds the confirmation value if the data type is Integer', () => {
			render(
				<Numeric
					confirmationValue="22.82"
					dataType="integer"
					name="numericField"
					requireConfirmation={true}
				/>
			);

			const confirmationField = document.getElementById(
				'numericFieldconfirmationField'
			);

			expect(confirmationField.value).toBe('23');
		});
	});
});
