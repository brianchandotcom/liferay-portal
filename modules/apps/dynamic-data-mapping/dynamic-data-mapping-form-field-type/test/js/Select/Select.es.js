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
import {
	act,
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import {PageProvider} from 'dynamic-data-mapping-form-renderer';
import React from 'react';
import ReactDOM from 'react-dom';

import Select from '../../../src/main/resources/META-INF/resources/Select/Select.es';

const spritemap = 'icons.svg';

const SelectWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<Select {...props} />
	</PageProvider>
);

describe('Select', () => {
	// eslint-disable-next-line no-console
	const originalWarn = console.warn;

	afterAll(() => {
		// eslint-disable-next-line no-console
		console.warn = originalWarn;
	});

	beforeAll(() => {
		// eslint-disable-next-line no-console
		console.warn = (...args) => {
			if (/DataProvider: Trying/.test(args[0])) {
				return;
			}
			originalWarn.call(console, ...args);
		};

		ReactDOM.createPortal = jest.fn((element) => {
			return element;
		});
	});

	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponse(JSON.stringify({}));
	});

	it('is not editable', () => {
		render(<SelectWithProvider readOnly spritemap={spritemap} />);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownTrigger = document.body.querySelector(
			'.select-field-trigger'
		);

		expect(dropdownTrigger).toHaveClass('disabled');
	});

	it('has a help text', () => {
		const {container} = render(
			<SelectWithProvider spritemap={spritemap} tip="Type something" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(
			<SelectWithProvider id="Id" spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders options', () => {
		const {container} = render(
			<SelectWithProvider
				options={[
					{
						checked: false,
						disabled: false,
						id: 'id',
						inline: false,
						label: 'label',
						name: 'name',
						showLabel: true,
						value: 'item',
					},
				]}
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders no options when options come empty', () => {
		const {container} = render(
			<SelectWithProvider options={[]} spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<SelectWithProvider label="label" spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is closed by default', () => {
		const {container} = render(
			<SelectWithProvider open={false} spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it("has class dropdown-opened when it's opened", () => {
		const {container} = render(
			<SelectWithProvider open spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a placeholder', () => {
		const {container} = render(
			<SelectWithProvider
				placeholder="Placeholder"
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a predefinedValue', () => {
		const {container} = render(
			<SelectWithProvider
				predefinedValue={['Select']}
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is not required', () => {
		const {container} = render(
			<SelectWithProvider required={false} spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('puts an asterisk when field is required', () => {
		const {container} = render(
			<SelectWithProvider
				label="This is the label"
				required
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders Label if showLabel is true', () => {
		const {container} = render(
			<SelectWithProvider label="text" showLabel spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(
			<SelectWithProvider spritemap={spritemap} value={['value']} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a key', () => {
		const {container} = render(
			<SelectWithProvider key="key" spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('calls onChange callback when an item is selected', async () => {
		const handleFieldEdited = jest.fn();

		const {container, getByTestId} = render(
			<SelectWithProvider
				dataSourceType="manual"
				onChange={handleFieldEdited}
				options={[
					{
						label: 'label',
						value: 'item',
					},
					{
						label: 'label2',
						value: 'item2',
					},
				]}
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownTrigger = container.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownItem = await waitForElement(() =>
			getByTestId('dropdownItem-0')
		);

		fireEvent.click(dropdownItem);

		act(() => {
			jest.runAllTimers();
		});

		expect(handleFieldEdited).toHaveBeenCalled();
	});

	it('calls onChange callback when an item is selected using multiselect', async () => {
		const handleFieldEdited = jest.fn();

		const {container, getByTestId} = render(
			<SelectWithProvider
				dataSourceType="manual"
				multiple={true}
				onChange={handleFieldEdited}
				options={[
					{
						label: 'label1',
						name: 'name1',
						value: 'item1',
					},
					{
						label: 'label2',
						name: 'name2',
						value: 'item2',
					},
					{
						label: 'label3',
						name: 'name3',
						value: 'item3',
					},
					{
						label: 'label4',
						name: 'name4',
						value: 'item4',
					},
					{
						label: 'label5',
						name: 'name5',
						value: 'item5',
					},
					{
						label: 'label6',
						name: 'name6',
						value: 'item6',
					},
					{
						label: 'label7',
						name: 'name7',
						value: 'item7',
					},
				]}
				spritemap={spritemap}
			/>
		);

		const dropdownTrigger = container.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		act(() => {
			jest.runAllTimers();
		});

		const labelItem = await waitForElement(() =>
			getByTestId('labelItem-item7')
		);

		fireEvent.click(labelItem);

		act(() => {
			jest.runAllTimers();
		});

		expect(handleFieldEdited).toHaveBeenCalledWith(expect.any(Object), [
			'item7',
		]);
		expect(container).toMatchSnapshot();
	});
});
