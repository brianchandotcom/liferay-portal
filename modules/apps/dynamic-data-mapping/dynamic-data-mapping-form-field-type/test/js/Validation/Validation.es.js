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

import Validation from '../../../src/main/resources/META-INF/resources/Validation/Validation.es';

const globalLanguageDirection = Liferay.Language.direction;

const spritemap = 'icons.svg';

const defaultValue = {
	errorMessage: {},
	expression: {},
	parameter: {},
};

const ValidationWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<Validation {...props} />
	</PageProvider>
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
		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				label="Validator"
				name="validation"
				spritemap={spritemap}
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
				name: 'notContains',
				value: 'NOT(contains(textfield, "{parameter}"))',
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
				name: 'neq',
				value: 'numericfield!={parameter}',
			},
			parameter: {
				en_US: undefined,
			},
		});
	});
});
