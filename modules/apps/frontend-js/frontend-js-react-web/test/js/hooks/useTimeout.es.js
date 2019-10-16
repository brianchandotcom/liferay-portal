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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import useTimeout from '../../../src/main/resources/META-INF/resources/js/hooks/useTimeout.es';

const {useEffect, useState} = React;

const INTERVAL = 100;

const Component = ({callback, onDelay, onRender}) => {
	const delay = useTimeout();

	useEffect(() => {
		if (onRender) {
			onRender(delay);
		}
	});

	const [, forceUpdate] = useState();

	const invoke = () => {
		const cancel = delay(callback, INTERVAL);

		if (onDelay) {
			onDelay(cancel);
		}
	};

	return (
		<>
			<button onClick={invoke}>Invoke</button>
			<button onClick={forceUpdate}>Update</button>
		</>
	);
};

describe('useTimeout()', () => {
	beforeEach(jest.useFakeTimers);

	afterEach(cleanup);

	it('runs a function after a delay', () => {
		const fn = jest.fn();

		const {getByText} = render(<Component callback={fn} />);

		fireEvent.click(getByText('Invoke'));

		expect(fn).not.toHaveBeenCalled();

		jest.runAllTimers();

		expect(fn).toHaveBeenCalled();
	});

	it('returns a function that cancels the timeout', () => {
		const fn = jest.fn();

		let cancel;

		const onDelay = handle => {
			cancel = handle;
		};

		const {getByText} = render(
			<Component callback={fn} onDelay={onDelay} />
		);

		fireEvent.click(getByText('Invoke'));

		expect(fn).not.toHaveBeenCalled();

		cancel();

		jest.runAllTimers();

		expect(fn).not.toHaveBeenCalled();
	});

	it('does not run if unmounted', () => {
		const fn = jest.fn();

		const {getByText, unmount} = render(<Component callback={fn} />);

		fireEvent.click(getByText('Invoke'));

		unmount();

		jest.runAllTimers();

		expect(fn).not.toHaveBeenCalled();
	});

	it('does not run if unmounted and then remounted', () => {
		const fn = jest.fn();

		const {getByText, unmount} = render(<Component callback={fn} />);

		fireEvent.click(getByText('Invoke'));

		unmount();

		render(<Component callback={fn} />);

		jest.runAllTimers();

		expect(fn).not.toHaveBeenCalled();
	});

	it('preserves the identity of the delay function', () => {
		const functions = [];

		const {getByText} = render(
			<Component onRender={delay => functions.push(delay)} />
		);

		expect(functions.length).toBe(1);

		fireEvent.click(getByText('Update'));

		expect(functions.length).toBe(2);

		expect(functions[0]).toBe(functions[1]);
	});
});
