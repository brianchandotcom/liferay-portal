/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {FormProvider} from 'data-engine-js-components-web';
import React from 'react';

import Validation from '../../../src/main/resources/META-INF/resources/Validation/Validation';

const globalLanguageDirection = Liferay.Language.direction;
const validations = {
	numeric: [
		{
			label: '',
			name: 'eq',
			parameterMessage: 'parameter',
			template: '{name}=={parameter}',
		},
	],
	string: [
		{
			label: '',
			name: 'contains',
			parameterMessage: 'parameter',
			template: 'contains({name}, "{parameter}")',
		},
	],
};

const ValidationWithProvider = ({validations, ...props}) => (
	<FormProvider initialState={{validations}}>
		<Validation {...props} />
	</FormProvider>
);

describe('ValidationTextAndNumeric', () => {
	beforeAll(() => {
		Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('allows user to delete parameter value for non-default languages', () => {
		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				defaultLanguageId="en_US"
				editingLanguageId="pt_BR"
				name="validation"
				validation={{
					fieldName: 'textField',
				}}
				validations={validations}
				value={{
					errorMessage: {},
					expression: {
						name: 'contains',
						value: 'contains(textField, "{parameter}")',
					},
					parameter: {
						en_US: 'Test',
						pt_BR: '',
					},
				}}
				visible
			/>
		);

		const inputParameter = container.querySelector(
			'input[name="validation_parameter"]'
		);

		expect(inputParameter.value).toBe('');
	});

	it('always sets the decimal separator as dot for parameter value', () => {
		const onChange = jest.fn();
		const value = {
			errorMessage: {},
			expression: {
				name: 'eq',
				value: 'numericField=={parameter}',
			},
			parameter: {},
		};

		const {container} = render(
			<ValidationWithProvider
				dataType="double"
				editingLanguageId="en_US"
				name="validation"
				onChange={onChange}
				symbols={{
					decimalSymbol: ',',
				}}
				validation={{
					fieldName: 'numericField',
				}}
				validations={validations}
				value={value}
				visible
			/>
		);

		const inputParameter = container.querySelector(
			'input[name="validation_parameter"]'
		);

		userEvent.type(inputParameter, '-1,2');

		expect(onChange).toHaveBeenLastCalledWith({
			target: {
				value: {
					...value,
					enableValidation: true,
					parameter: {
						en_US: '-1.2',
					},
				},
			},
		});
	});
});
