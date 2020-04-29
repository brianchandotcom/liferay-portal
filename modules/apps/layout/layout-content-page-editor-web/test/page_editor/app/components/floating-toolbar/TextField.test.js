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
import {cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {TextField} from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/floating-toolbar/TextField';

const INPUT_NAME = 'test';

const renderTextField = (typeOptions = {}, onValueSelect = () => {}) =>
	render(
		<TextField
			field={{label: INPUT_NAME, name: INPUT_NAME, typeOptions}}
			onValueSelect={onValueSelect}
			value={''}
		/>
	);

describe('TextField', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders an input of type text by default', () => {
		const {getByLabelText, queryByText} = renderTextField();

		const input = getByLabelText(INPUT_NAME);

		expect(input).toBeInTheDocument();
		expect(input.type).toBe('text');
		expect(
			queryByText('you-have-entered-invalid-data')
		).not.toBeInTheDocument();
	});

	it('renders an input of type url when validation type url is defined', () => {
		const {getByLabelText} = renderTextField({validation: {type: 'url'}});

		const input = getByLabelText(INPUT_NAME);

		expect(input).toBeInTheDocument();
		expect(input.type).toBe('url');
	});

	it('renders an input of type number when validation type number is defined', () => {
		const {getByLabelText} = renderTextField({
			validation: {type: 'number'},
		});

		const input = getByLabelText(INPUT_NAME);

		expect(input).toBeInTheDocument();
		expect(input.type).toBe('number');
	});

	it('renders an input of type email when validation type email is defined', () => {
		const {getByLabelText} = renderTextField({validation: {type: 'email'}});

		const input = getByLabelText(INPUT_NAME);

		expect(input).toBeInTheDocument();
		expect(input.type).toBe('email');
	});

	it('renders an input with the pattern attribute if validation type is pattern', () => {
		const regexp = '[0-9]*';

		const {getByLabelText} = renderTextField({
			validation: {regexp, type: 'pattern'},
		});

		const input = getByLabelText(INPUT_NAME);

		expect(input).toBeInTheDocument();
		expect(input.type).toBe('text');
		expect(input.pattern).toBe(regexp);
	});

	it('renders an input with the maxLength/minLength attributes when defined in the validation', () => {
		const {getByLabelText} = renderTextField({
			validation: {maxLength: 3, minLength: 0, type: 'text'},
		});

		const input = getByLabelText(INPUT_NAME);

		expect(input).toBeInTheDocument();
		expect(input.type).toBe('text');
		expect(input.maxLength).toEqual(3);
		expect(input.minLength).toEqual(0);
	});

	it('renders an input with max/min attributes when type is number and it is defined in the validation', () => {
		const {getByLabelText} = renderTextField({
			validation: {max: 3, min: 0, type: 'number'},
		});

		const input = getByLabelText(INPUT_NAME);

		expect(input).toBeInTheDocument();
		expect(input.type).toBe('number');
		expect(input.max).toEqual('3');
		expect(input.min).toEqual('0');
	});

	it('shows the error message defined in the typeoptions when the value is not valid', () => {
		const errorMessage = 'bad';

		const {getByLabelText, getByText} = renderTextField({
			validation: {errorMessage, max: 3, min: 0, type: 'number'},
		});

		const input = getByLabelText(INPUT_NAME);

		userEvent.type(input, '5');

		expect(getByText(errorMessage)).toBeInTheDocument();
	});

	it('shows the default error message when no one is provided', () => {
		const {getByLabelText, getByText} = renderTextField({
			validation: {max: 3, min: 0, type: 'number'},
		});

		const input = getByLabelText(INPUT_NAME);

		userEvent.type(input, '5');

		expect(getByText('you-have-entered-invalid-data')).toBeInTheDocument();
	});

	it('calls the onValueSelect callback when the input changes', () => {
		const onValueSelect = jest.fn();
		const {getByLabelText} = renderTextField({}, onValueSelect);

		const input = getByLabelText(INPUT_NAME);

		userEvent.type(input, 'something');

		fireEvent.blur(input, {event: {target: {checkValidity: () => true}}});

		expect(onValueSelect).toBeCalledWith(INPUT_NAME, 'something');
	});

	it('does not call the onValueSelect callback when the input is not valid', () => {
		const onValueSelect = jest.fn();
		const {getByLabelText} = renderTextField(
			{validation: {max: 10, type: 'number'}},
			onValueSelect
		);

		const input = getByLabelText(INPUT_NAME);

		userEvent.type(input, 12);

		fireEvent.blur(input);

		expect(onValueSelect).not.toBeCalled();
	});
});
