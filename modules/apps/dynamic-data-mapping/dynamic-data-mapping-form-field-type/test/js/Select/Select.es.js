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
import {PageProvider} from 'data-engine-js-components-web';
import React from 'react';
import ReactDOM from 'react-dom';

import Select from '../../../src/main/resources/META-INF/resources/Select/Select.es';

const spritemap = 'icons.svg';

const createOptions = (length) => {
	const options = [];

	for (let counter = 1; counter <= length; counter++) {
		options.push({
			label: 'label' + counter,
			name: 'name' + counter,
			value: 'item' + counter,
		});
	}

	return options;
};
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

	it('does not render and empty option', () => {
		const option = {
			checked: false,
			disabled: false,
			id: 'id',
			inline: false,
			label: 'label',
			name: 'name',
			showLabel: true,
			value: 'item',
		};

		const {container} = render(
			<SelectWithProvider
				options={[option]}
				showEmptyOption={false}
				spritemap={spritemap}
			/>
		);

		const dropDownItem = container.querySelector(
			'.dropdown-menu .dropdown-item'
		);

		expect(dropDownItem.innerHTML).toBe(option.label);
	});

	it('does not show an empty option when the search input is available', async () => {
		const handleFieldEdited = jest.fn();

		const {container} = render(
			<SelectWithProvider
				dataSourceType="manual"
				multiple={false}
				onChange={handleFieldEdited}
				options={createOptions(12)}
				showEmptyOption={false}
				spritemap={spritemap}
			/>
		);

		const dropdownTrigger = container.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		const emptyOption = container.querySelector('[label=choose-an-option]');

		expect(emptyOption).toBeNull();
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

	it('renders an empty option', () => {
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
				showEmptyOption={true}
				spritemap={spritemap}
			/>
		);

		const dropDownItem = container.querySelector(
			'.dropdown-menu .dropdown-item'
		);

		expect(dropDownItem.innerHTML).toBe('choose-an-option');
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
				options={createOptions(2)}
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
				options={createOptions(7)}
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

	it('shows an empty option when the search input is available', async () => {
		const handleFieldEdited = jest.fn();

		const {container} = render(
			<SelectWithProvider
				dataSourceType="manual"
				multiple={false}
				onChange={handleFieldEdited}
				options={createOptions(12)}
				showEmptyOption={true}
				spritemap={spritemap}
			/>
		);

		const dropdownTrigger = container.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		fireEvent.click(dropdownTrigger);

		const emptyOption = container.querySelector('[label=choose-an-option]');

		expect(emptyOption).not.toBeNull();
	});

	it('shows a search input when the number of options is more than the maximum allowed', async () => {
		const handleFieldEdited = jest.fn();

		const {container} = render(
			<SelectWithProvider
				dataSourceType="manual"
				multiple={true}
				onChange={handleFieldEdited}
				options={createOptions(12)}
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

		expect(container).toMatchSnapshot();
	});

	it('filters according to the input and calls onChange callback when an item is selected using search', async () => {
		const handleFieldEdited = jest.fn();

		const {container, getByTestId} = render(
			<SelectWithProvider
				dataSourceType="manual"
				multiple={true}
				onChange={handleFieldEdited}
				options={createOptions(12)}
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

		const input = container.querySelector('input');

		fireEvent.change(input, {
			target: {
				value: 'label1',
			},
		});

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();

		const labelItem = await waitForElement(() =>
			getByTestId('labelItem-item11')
		);

		fireEvent.click(labelItem);

		act(() => {
			jest.runAllTimers();
		});

		expect(handleFieldEdited).toHaveBeenCalledWith(expect.any(Object), [
			'item11',
		]);
	});

	it('adjusts dropdown menu position during scroll', async () => {
		const {container} = render(
			<SelectWithProvider
				dataSourceType="manual"
				options={createOptions(12)}
				spritemap={spritemap}
			/>
		);

		const dropdownTrigger = container.querySelector(
			'.form-builder-select-field.input-group-container'
		);

		jest.spyOn(dropdownTrigger, 'getBoundingClientRect').mockImplementation(
			() => {
				return {
					height: 40,
					top: 50,
				};
			}
		);

		window.pageYOffset = 100;

		fireEvent.scroll(container);

		act(() => {
			jest.runAllTimers();
		});

		const dropdownMenu = container.querySelector('.ddm-select-dropdown');

		expect(dropdownMenu.style).toHaveProperty('top', '190px');
	});
});
