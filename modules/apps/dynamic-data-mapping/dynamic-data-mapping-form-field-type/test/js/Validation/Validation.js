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
	beforeAll(() => {
		Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('renders checkbox to enable Validation', () => {
		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				label="Validator"
				name="validation"
				onChange={() => {}}
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

		expect(container).toMatchSnapshot();
	});

	it('enables validation after click on toogle', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				dataType="string"
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

		userEvent.click(inputCheckbox);

		expect(onChange).toHaveBeenLastCalledWith({
			target: {
				value: {
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
				},
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

		userEvent.click(inputCheckbox);

		expect(onChange).toHaveBeenLastCalledWith({
			target: {
				value: {
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
				},
			},
		});
	});

	it('renders parameter field with Date element', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				dataType="date"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={onChange}
				spritemap={spritemap}
				validation={{
					dataType: 'date',
					fieldName: 'dateField',
				}}
				validations={{
					date: [
						{
							label: '',
							name: 'futureDates',
							parameterMessage: '',
							template: 'futureDates({name}, "{parameter}")',
						},
					],
				}}
				value={defaultValue}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		userEvent.click(inputCheckbox);

		expect(onChange).toHaveBeenLastCalledWith({
			target: {
				value: {
					enableValidation: true,
					errorMessage: {
						en_US: undefined,
					},
					expression: {
						name: 'futureDates',
						value: 'futureDates(dateField, "{parameter}")',
					},
					parameter: {
						en_US: undefined,
					},
				},
			},
		});
	});
});
