/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, render} from '@testing-library/react';
import {PageProvider} from 'data-engine-js-components-web';
import React from 'react';

import KeyValue from '../../../src/main/resources/META-INF/resources/KeyValue/KeyValue.es';

const globalLanguageDirection = Liferay.Language.direction;

const spritemap = 'icons.svg';

const KeyValueWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<KeyValue {...props} />
	</PageProvider>
);

describe('KeyValue', () => {
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

	it('is not editable', () => {
		const {container} = render(
			<KeyValueWithProvider
				name="keyValue"
				readOnly={true}
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a helptext', () => {
		const {container} = render(
			<KeyValueWithProvider
				name="keyValue"
				spritemap={spritemap}
				tip="Type something"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(
			<KeyValueWithProvider
				id="Id"
				name="keyValue"
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<KeyValueWithProvider
				label="label"
				name="keyValue"
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a predefined Value', () => {
		const {container} = render(
			<KeyValueWithProvider
				name="keyValue"
				placeholder="Option 1"
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('hides keyword input', () => {
		const {container} = render(
			<KeyValueWithProvider
				name="keyValue"
				readOnly={true}
				spritemap={spritemap}
			/>
		);

		const keyValueInput = container.querySelectorAll('.key-value-input');

		expect(keyValueInput.length).toBe(0);
	});

	it('is not required', () => {
		const {container} = render(
			<KeyValueWithProvider
				name="keyValue"
				required={false}
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
			<KeyValueWithProvider
				label="text"
				name="keyValue"
				showLabel={true}
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(
			<KeyValueWithProvider
				name="keyValue"
				spritemap={spritemap}
				value="value"
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('renders component with a key', () => {
		const {container} = render(
			<KeyValueWithProvider
				keyword="key"
				name="keyValue"
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('shows keyword input', () => {
		const {container} = render(
			<KeyValueWithProvider
				name="keyValue"
				readOnly={true}
				showKeyword={true}
				spritemap={spritemap}
			/>
		);

		const keyValueInput = container.querySelectorAll('.key-value-input');

		expect(keyValueInput.length).toBe(1);
	});
});
