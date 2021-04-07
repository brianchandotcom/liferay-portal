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

import {waitForElement} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {EVENT_TYPES} from 'data-engine-js-components-web/js/core/actions/eventTypes.es';
import {Field} from 'data-engine-js-components-web/js/core/components/Field/Field.es';
import {FormNoopProvider} from 'data-engine-js-components-web/js/core/hooks/useForm.es';
import {PageProvider} from 'data-engine-js-components-web/js/core/hooks/usePage.es';
import React from 'react';

import MetalFieldMock from '../__mock__/MetalFieldMock.es';

const fieldTypes = [
	{
		javaScriptModule: 'MetalFieldMock.es',
		name: 'metal_field',
	},
];

const fieldProps = {
	name: 'metal_field_name',
	type: 'metal_field',
	value: 'Foo',
};

const FieldWithProvider = ({field, onAction, onBlur, onChange, onFocus}) => (
	<FormNoopProvider onAction={onAction}>
		<PageProvider value={{fieldTypes}}>
			<Field
				field={field}
				onBlur={onBlur}
				onChange={onChange}
				onFocus={onFocus}
			/>
		</PageProvider>
	</FormNoopProvider>
);

describe('FieldCompabilityLayer -> Metal+Soy', () => {
	const originalLiferayLoader = window.Liferay.Loader;

	beforeAll(() => {
		window.Liferay = {
			...window.Liferay,
			Loader: {
				require: (modules, resolve) =>
					resolve({default: MetalFieldMock}),
			},
		};
	});

	afterAll(() => {
		window.Liferay.Loader = originalLiferayLoader;
	});

	afterEach(cleanup);

	it('renders the Metal field', async () => {
		const {container} = render(<FieldWithProvider field={fieldProps} />);

		await waitForElement(() => container.querySelector('input'), {
			container,
		});

		expect(container).toMatchSnapshot();
	});

	it('dispatch an onChange event when editing some field value', async () => {
		const onChange = jest.fn();

		const {container} = render(
			<FieldWithProvider field={fieldProps} onChange={onChange} />
		);

		await waitForElement(() => container.querySelector('input'), {
			container,
		});

		const input = container.querySelector('input');

		fireEvent.input(input, {
			target: {
				value: 'test',
			},
		});

		expect(onChange).toHaveBeenCalledWith({
			fieldInstance: expect.any(Object),
			originalEvent: expect.any(Object),
			value: 'test',
		});
	});

	it('dispatch the onBlur event', async () => {
		const onBlur = jest.fn();

		const {container} = render(
			<FieldWithProvider field={fieldProps} onBlur={onBlur} />
		);

		await waitForElement(() => container.querySelector('input'), {
			container,
		});

		const input = container.querySelector('input');

		fireEvent.blur(input);

		expect(onBlur).toHaveBeenCalledWith(
			{
				fieldInstance: expect.any(Object),
				originalEvent: expect.any(Object),
				value: undefined,
			},
			expect.any(Object)
		);
	});

	it('dispatch the onFocus event', async () => {
		const onFocus = jest.fn();

		const {container} = render(
			<FieldWithProvider field={fieldProps} onFocus={onFocus} />
		);

		await waitForElement(() => container.querySelector('input'), {
			container,
		});

		const input = container.querySelector('input');

		fireEvent.focus(input);

		expect(onFocus).toHaveBeenCalledWith({
			fieldInstance: expect.any(Object),
			originalEvent: expect.any(Object),
			value: undefined,
		});
	});

	it('dispatch the EVENT_TYPES.FIELD_REMOVED event when removing the field repeated', async () => {
		const onAction = jest.fn();

		const {container, getByText} = render(
			<FieldWithProvider field={fieldProps} onAction={onAction} />
		);

		await waitForElement(() => getByText('Remove'), {container});

		const button = getByText('Remove');

		fireEvent.click(button);

		expect(onAction).toHaveBeenCalledWith({
			payload: 'metal_field_name',
			type: EVENT_TYPES.FIELD.REMOVED,
		});
	});

	it('dispatch the EVENT_TYPES.FIELD_REPEATED event when repeating the same field', async () => {
		const onAction = jest.fn();

		const {container, getByText} = render(
			<FieldWithProvider field={fieldProps} onAction={onAction} />
		);

		await waitForElement(() => getByText('Duplicate'), {container});

		const button = getByText('Duplicate');

		fireEvent.click(button);

		expect(onAction).toHaveBeenCalledWith({
			payload: 'metal_field_name',
			type: EVENT_TYPES.FIELD.REPEATED,
		});
	});
});
