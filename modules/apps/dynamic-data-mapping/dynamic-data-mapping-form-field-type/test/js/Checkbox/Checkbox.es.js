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

import {act, cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {PageProvider} from 'data-engine-js-components-web';
import React from 'react';

import Checkbox from '../../../src/main/resources/META-INF/resources/Checkbox/Checkbox.es';

const spritemap = 'icons.svg';

const CheckboxWithProvider = (props) => (
	<PageProvider value={{editingLanguageId: 'en_US'}}>
		<Checkbox {...props} />
	</PageProvider>
);

describe('Field Checkbox', () => {
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
	});

	afterAll(() => {
		// eslint-disable-next-line no-console
		console.warn = originalWarn;
	});

	afterEach(cleanup);

	beforeEach(() => {
		jest.useFakeTimers();
		fetch.mockResponseOnce(JSON.stringify({}));
	});

	it('is not editable', () => {
		const {container} = render(
			<CheckboxWithProvider readOnly={true} spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a helptext', () => {
		const {container} = render(
			<CheckboxWithProvider spritemap={spritemap} tip="Type something" />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has an id', () => {
		const {container} = render(
			<CheckboxWithProvider id="ID" spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a label', () => {
		const {container} = render(
			<CheckboxWithProvider label="label" spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a predefined Value', () => {
		const {container} = render(
			<CheckboxWithProvider
				placeholder="Option 1"
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
			<CheckboxWithProvider required={false} spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is shown as a switcher', () => {
		const {container} = render(
			<CheckboxWithProvider showAsSwitcher spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('is shown as checkbox', () => {
		const {container} = render(
			<CheckboxWithProvider
				showAsSwitcher={false}
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
			<CheckboxWithProvider
				label={true}
				showLabel={true}
				spritemap={spritemap}
			/>
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a spritemap', () => {
		const {container} = render(
			<CheckboxWithProvider spritemap={spritemap} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a value', () => {
		const {container} = render(
			<CheckboxWithProvider spritemap={spritemap} value={true} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('has a key', () => {
		const {container} = render(
			<CheckboxWithProvider key="key" value={true} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
	});

	it('call the onChange callback on the field change', () => {
		const handleFieldEdited = jest.fn();

		render(
			<CheckboxWithProvider
				onChange={handleFieldEdited}
				spritemap={spritemap}
			/>
		);

		userEvent.click(document.body.querySelector('input'));

		act(() => {
			jest.runAllTimers();
		});

		expect(handleFieldEdited).toHaveBeenCalled();
	});

	describe('Maximum Repetitions Info', () => {
		it('does not show the maximum repetitions info', () => {
			const {container} = render(<CheckboxWithProvider value={true} />);

			const ddmInfo = container.querySelector('.ddm-info');

			expect(ddmInfo).toBeNull();
		});

		it('does not show the maximum repetitions info if the value is false', () => {
			const {container} = render(
				<CheckboxWithProvider
					showMaximumRepetitionsInfo={true}
					value={false}
				/>
			);

			const ddmInfo = container.querySelector('.ddm-info');

			expect(ddmInfo).toBeNull();
		});

		it('shows the maximum repetitions info', () => {
			const {container} = render(
				<CheckboxWithProvider
					showMaximumRepetitionsInfo={true}
					value={true}
				/>
			);

			const ddmInfo = container.querySelector('.ddm-info');

			expect(ddmInfo).not.toBeNull();
		});
	});
});
