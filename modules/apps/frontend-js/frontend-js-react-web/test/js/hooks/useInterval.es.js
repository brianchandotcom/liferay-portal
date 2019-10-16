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

import useInterval from '../../../src/main/resources/META-INF/resources/js/hooks/useInterval.es';

const {useEffect, useState} = React;

const INTERVAL = 100;

const Component = ({callback, onRender, onSchedule}) => {
	const schedule = useInterval();

	useEffect(() => {
		if (onRender) {
			onRender(schedule);
		}
	});

	const [, forceUpdate] = useState();

	const invoke = () => {
		const cancel = schedule(callback, INTERVAL);

		if (onSchedule) {
			onSchedule(cancel);
		}
	};

	return (
		<>
			<button onClick={invoke}>Invoke</button>
			<button onClick={forceUpdate}>Update</button>
		</>
	);
};

describe('useInterval()', () => {
	beforeEach(jest.useFakeTimers);

	afterEach(cleanup);

	it('runs a function on a schedule', () => {
		const fn = jest.fn();

		const {getByText} = render(<Component callback={fn} />);

		fireEvent.click(getByText('Invoke'));

		expect(fn).not.toHaveBeenCalled();

		jest.advanceTimersByTime(INTERVAL);

		expect(fn).toHaveBeenCalledTimes(1);

		jest.advanceTimersByTime(INTERVAL);

		expect(fn).toHaveBeenCalledTimes(2);
	});

	it('returns a function that cancels the schedule', () => {
		const fn = jest.fn();

		let cancel;

		const onSchedule = handle => {
			cancel = handle;
		};

		const {getByText} = render(
			<Component callback={fn} onSchedule={onSchedule} />
		);

		fireEvent.click(getByText('Invoke'));

		expect(fn).not.toHaveBeenCalled();

		jest.advanceTimersByTime(INTERVAL);

		expect(fn).toHaveBeenCalledTimes(1);

		cancel();

		jest.advanceTimersByTime(INTERVAL);

		expect(fn).toHaveBeenCalledTimes(1);
	});

	it('does not run if unmounted', () => {
		const fn = jest.fn();

		const {getByText, unmount} = render(<Component callback={fn} />);

		fireEvent.click(getByText('Invoke'));

		unmount();

		jest.advanceTimersByTime(INTERVAL);

		expect(fn).not.toHaveBeenCalled();
	});

	it('does not run if unmounted and then remounted', () => {
		const fn = jest.fn();

		const {getByText, unmount} = render(<Component callback={fn} />);

		fireEvent.click(getByText('Invoke'));

		unmount();

		render(<Component callback={fn} />);

		jest.advanceTimersByTime(INTERVAL);

		expect(fn).not.toHaveBeenCalled();
	});

	it('preserves the identity of the schedule function', () => {
		const functions = [];

		const {getByText} = render(
			<Component onRender={schedule => functions.push(schedule)} />
		);

		expect(functions.length).toBe(1);

		fireEvent.click(getByText('Update'));

		expect(functions.length).toBe(2);

		expect(functions[0]).toBe(functions[1]);
	});
});
