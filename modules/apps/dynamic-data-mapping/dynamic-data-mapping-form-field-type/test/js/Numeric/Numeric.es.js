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

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import {PageProvider} from 'data-engine-js-components-web';
import React from 'react';

import Numeric from '../../../src/main/resources/META-INF/resources/Numeric/Numeric.es';

const globalLanguageDirection = Liferay.Language.direction;

const spritemap = 'icons.svg';

const defaultNumericConfig = {
	name: 'numericField',
	spritemap,
};

const NumericWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<Numeric {...props} />
	</PageProvider>
);

describe('Field Numeric', () => {
	// eslint-disable-next-line no-console
	const originalWarn = console.warn;

	beforeAll(() => {
		// eslint-disable-next-line no-console
		console.warn = (...args) => {
			if (/DataProvider: Trying/.test(args[0])) {
				return;
			}
			originalWarn.call(console, ...args);
		};

		Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	afterAll(() => {
		// eslint-disable-next-line no-console
		console.warn = originalWarn;

		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponse(JSON.stringify({}));
	});

	it('renders the default markup', () => {
		const {container} = render(
			<NumericWithProvider {...defaultNumericConfig} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is not readOnly', () => {
		const {container} = render(
			<NumericWithProvider {...defaultNumericConfig} readOnly={false} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a helptext', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				tip="Type something"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(
			<NumericWithProvider {...defaultNumericConfig} id="ID" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<NumericWithProvider {...defaultNumericConfig} label="label" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				placeholder="Placeholder"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is not required', () => {
		const {container} = render(
			<NumericWithProvider {...defaultNumericConfig} required={false} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				label="Numeric Field"
				showLabel={true}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				onChange={jest.fn()}
				value="123"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a key', () => {
		const {container} = render(
			<NumericWithProvider {...defaultNumericConfig} key="key" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('calls the field`s onChange callback', () => {
		const onChange = jest.fn();

		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				key="input"
				onChange={onChange}
			/>
		);

		const input = container.querySelector('input');

		fireEvent.change(input, {target: {value: '2'}});

		act(() => {
			jest.runAllTimers();
		});

		expect(onChange).toHaveBeenCalled();
	});

	it('changes the mask type', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				dataType="double"
				onChange={jest.fn()}
				value="22.22"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container.querySelector('input').value).toBe('22.22');
	});

	it('check if event is sent when decimal is being writen', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				key="input"
				onChange={jest.fn()}
			/>
		);

		const input = container.querySelector('input');

		fireEvent.change(input, {
			target: {
				value: '3.0',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(input.value).toBe('3.0');
	});

	it('check field value is rounded when fieldType is integer but it receives a double', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				key="input"
				onChange={jest.fn()}
				value="3.8"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const input = container.querySelector('input');

		expect(input.value).toBe('4');
	});

	it('round up value when changing from decimal to integer when symbol of language is comma', () => {
		const {container} = render(
			<NumericWithProvider
				{...defaultNumericConfig}
				dataType="integer"
				onChange={jest.fn()}
				symbols={{decimalSymbol: ','}}
				value="22,82"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container.querySelector('input').value).toBe('23');
	});

	describe('Confirmation Field', () => {
		it('renders the confirmation field with the same data type as the original field', () => {
			render(
				<NumericWithProvider
					{...defaultNumericConfig}
					confirmationValue="22.82"
					dataType="double"
					onChange={jest.fn()}
					requireConfirmation={true}
				/>
			);

			act(() => {
				jest.runAllTimers();
			});

			const confirmationField = document.getElementById(
				'numericFieldconfirmationField'
			);

			expect(confirmationField.value).toBe('22.82');
		});

		it('rounds the confirmation value if the data type is Integer', () => {
			render(
				<NumericWithProvider
					{...defaultNumericConfig}
					confirmationValue="22.82"
					dataType="integer"
					onChange={jest.fn()}
					requireConfirmation={true}
				/>
			);

			act(() => {
				jest.runAllTimers();
			});

			const confirmationField = document.getElementById(
				'numericFieldconfirmationField'
			);

			expect(confirmationField.value).toBe('23');
		});
	});
});
