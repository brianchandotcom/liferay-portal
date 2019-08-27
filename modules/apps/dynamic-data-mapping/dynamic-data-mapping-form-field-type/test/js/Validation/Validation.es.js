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

import Validation from '../../../src/main/resources/META-INF/resources/Validation/Validation.es';

let component;
const spritemap = 'icons.svg';

describe('Validation', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('renders checkbox to enable Validation', () => {
		component = new Validation({
			dataType: 'string',
			label: 'Validator',
			name: 'validation',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('enables validation after click on toogle', done => {
		jest.useFakeTimers();

		component = new Validation({
			label: 'Validator',
			name: 'validation',
			spritemap,
			validation: {
				dataType: 'string',
				fieldName: 'textfield'
			}
		});

		jest.runAllTimers();

		component.once('stateSynced', () => {
			expect(component.value).toMatchSnapshot();
			done();
		});

		component.refs.enableValidation.value = true;
		component.refs.enableValidation.emit('fieldEdited');

		jest.runAllTimers();
	});

	it('renders parameter field with TextField element', done => {
		jest.useFakeTimers();

		component = new Validation({
			enableValidation: true,
			label: 'Validator',
			name: 'validation',
			spritemap,
			validation: {
				dataType: 'string',
				fieldName: 'textfield'
			}
		});

		component.once('stateSynced', () => {
			expect(component.value.expression).toMatchSnapshot();
			done();
		});

		component.refs.enableValidation.value = true;
		component.refs.selectedValidation.value = 'notContains';
		component.refs.selectedValidation.emit('fieldEdited');

		jest.runAllTimers();
	});

	it('renders parameter field with Numeric element', done => {
		jest.useFakeTimers();

		component = new Validation({
			dataType: 'numeric',
			enableValidation: true,
			label: 'Validator',
			name: 'validation',
			spritemap,
			validation: {
				dataType: 'integer',
				fieldName: 'numericfield'
			}
		});

		component.once('stateSynced', () => {
			expect(component.value.expression).toMatchSnapshot();
			done();
		});

		component.refs.enableValidation.value = true;
		component.refs.selectedValidation.value = 'lt';
		component.refs.selectedValidation.emit('fieldEdited');

		jest.runAllTimers();
	});

	it('renders parameter field with Text element and then with Numeric after update dataType', done => {
		jest.useFakeTimers();

		component = new Validation({
			enableValidation: true,
			label: 'Validator',
			name: 'validation',
			spritemap,
			validation: {
				dataType: 'string'
			}
		});

		component.once('stateSynced', () => {
			expect(component.value.expression).toMatchSnapshot();
			done();
		});

		component.refs.enableValidation.value = true;
		component.refs.selectedValidation.value = 'notContains';
		component.refs.selectedValidation.emit('fieldEdited');
		component.validation = {
			...component.validation,
			dataType: 'integer'
		};

		jest.runAllTimers();
	});

	describe('Regression Tests', () => {
		describe('LPS-88007', () => {
			it('does not render "Show Error Message" and "The Value" as required fields', () => {
				jest.useFakeTimers();

				component = new Validation({
					enableValidation: true,
					label: 'Validator',
					name: 'validation',
					spritemap,
					validation: {
						dataType: 'string',
						fieldName: 'textfield'
					},
					value: {
						errorMessage: 'An error message',
						expression: 'NOT(contains(textfield, ""))'
					}
				});

				jest.runAllTimers();

				expect(component.refs.errorMessage.required).toBe(false);
				expect(component.refs.parameterMessage.required).toBe(false);
			});
		});
	});
});
