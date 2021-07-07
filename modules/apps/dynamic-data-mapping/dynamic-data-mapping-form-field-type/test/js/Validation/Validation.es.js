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
import {FormProvider} from 'data-engine-js-components-web';
import React from 'react';

import Validation from '../../../src/main/resources/META-INF/resources/Validation/Validation';

const globalLanguageDirection = Liferay.Language.direction;

const spritemap = 'icons.svg';

const defaultValue = {
	errorMessage: {},
	expression: {},
	parameter: {},
};

const ValidationWithProvider = ({validations, ...props}) => (
	<FormProvider initialState={{validations}}>
		<Validation {...props} />
	</FormProvider>
);

describe('Validation', () => {
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
		fetch.mockResponseOnce(JSON.stringify({}));
	});

	it('renders checkbox to enable Validation', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				label="Validator"
				name="validation"
				onChange={onChange}
				spritemap={spritemap}
				validations={{
					string: [
						{
							label: '',
							name: '',
							parameterMessage: '',
							template: '',
						},
					],
				}}
				value={defaultValue}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('enables validation after click on toogle', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={onChange}
				spritemap={spritemap}
				validation={{
					dataType: 'string',
					fieldName: 'textfield',
				}}
				validations={{
					string: [
						{
							label: '',
							name: 'contains',
							parameterMessage: '',
							template: 'contains({name}, "{parameter}")',
						},
					],
				}}
				value={defaultValue}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		fireEvent.click(inputCheckbox);

		act(() => {
			jest.runAllTimers();
		});

		expect(onChange).toHaveBeenCalledWith(expect.any(Object), {
			enableValidation: true,
			errorMessage: {
				en_US: undefined,
			},
			expression: {
				name: 'contains',
				value: 'contains(textfield, "{parameter}")',
			},
			parameter: {
				en_US: undefined,
			},
		});
	});

	it('renders parameter field with Numeric element', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				dataType="numeric"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={onChange}
				spritemap={spritemap}
				validation={{
					dataType: 'integer',
					fieldName: 'numericfield',
				}}
				validations={{
					numeric: [
						{
							label: '',
							name: 'eq',
							parameterMessage: '',
							template: '{name}=={parameter}',
						},
					],
				}}
				value={defaultValue}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		fireEvent.click(inputCheckbox);

		act(() => {
			jest.runAllTimers();
		});

		expect(onChange).toHaveBeenCalledWith(expect.any(Object), {
			enableValidation: true,
			errorMessage: {
				en_US: undefined,
			},
			expression: {
				name: 'eq',
				value: 'numericfield=={parameter}',
			},
			parameter: {
				en_US: undefined,
			},
		});
	});
});
