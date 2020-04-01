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

import {act, fireEvent} from '@testing-library/react';

import Validation from '../../../src/main/resources/META-INF/resources/Validation/Validation.es';
import withContextMock from '../__mocks__/withContextMock.es';

let component;
const spritemap = 'icons.svg';
const defaultValue = {
	errorMessage: {},
	expression: {},
	parameter: {},
};

const ValidationWithContextMock = withContextMock(Validation);

describe('Validation', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders checkbox to enable Validation', () => {
		component = new ValidationWithContextMock({
			dataType: 'string',
			label: 'Validator',
			name: 'validation',
			spritemap,
			value: defaultValue,
		});

		expect(component).toMatchSnapshot();
	});

	it('enables validation after click on toogle', done => {
		jest.useFakeTimers();

		const handleFieldEdited = data => {
			expect(data.value).toEqual({
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
			done();
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new ValidationWithContextMock({
			defaultLanguageId: 'en_US',
			editingLanguageId: 'en_US',
			events,
			expression: {},
			label: 'Validator',
			name: 'validation',
			spritemap,
			validation: {
				dataType: 'string',
				fieldName: 'textfield',
			},
			value: defaultValue,
		});

		const inputCheckbox = component.element.querySelector(
			'input[type="checkbox"]'
		);

		fireEvent.click(inputCheckbox);

		act(() => {
			jest.runAllTimers();
		});
	});

	it('renders parameter field with Numeric element', done => {
		jest.useFakeTimers();

		const handleFieldEdited = data => {
			expect(data.value).toEqual({
				enableValidation: true,
				errorMessage: {
					en_US: undefined,
				},
				expression: {
					name: 'lt',
					value: 'numericfield<{parameter}',
				},
				parameter: {
					en_US: undefined,
				},
			});
			done();
		};

		const events = {fieldEdited: handleFieldEdited};

		component = new ValidationWithContextMock({
			dataType: 'numeric',
			defaultLanguageId: 'en_US',
			editingLanguageId: 'en_US',
			events,
			expression: {},
			label: 'Validator',
			name: 'validation',
			spritemap,
			validation: {
				dataType: 'integer',
				fieldName: 'numericfield',
			},
			value: defaultValue,
		});

		const inputCheckbox = component.element.querySelector(
			'input[type="checkbox"]'
		);

		fireEvent.click(inputCheckbox);

		act(() => {
			jest.runAllTimers();
		});
	});
});
