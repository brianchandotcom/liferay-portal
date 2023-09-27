/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/use-state-naming-pattern */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import useInterval from '../../../src/main/resources/META-INF/resources/js/hooks/useInterval';

const {useState} = React;

const INTERVAL = 100;

type Callback = ReturnType<typeof jest.fn>;
type Schedule = ReturnType<typeof useInterval>;

const Component = ({
	callback,
	onRender,
	onSchedule,
}: {
	callback?: Callback;
	onRender?: (schedule: Schedule) => void;
	onSchedule?: (callback: () => void) => void;
}) => {
	const schedule = useInterval();

	if (onRender) {
		onRender(schedule);
	}

	const [, forceUpdate] = useState(1);

	const invoke = () => {
		if (callback) {
			const cancel = schedule(callback, INTERVAL);

			if (onSchedule) {
				onSchedule(cancel);
			}
		}
	};

	return (
		<>
			<button onClick={invoke}>Invoke</button>
			<button onClick={() => forceUpdate((count) => count + 1)}>
				Update
			</button>
		</>
	);
};

describe('useInterval()', () => {
	beforeEach(() => jest.useFakeTimers());

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

		let cancel: () => void;

		const onSchedule = (callback: () => void): void => {
			cancel = callback;
		};

		const {getByText} = render(
			<Component callback={fn} onSchedule={onSchedule} />
		);

		fireEvent.click(getByText('Invoke'));

		expect(fn).not.toHaveBeenCalled();

		jest.advanceTimersByTime(INTERVAL);

		expect(fn).toHaveBeenCalledTimes(1);

		cancel!();

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
		const functions: Array<Schedule> = [];

		const {getByText} = render(
			<Component onRender={(schedule) => functions.push(schedule)} />
		);

		expect(functions.length).toBe(1);

		fireEvent.click(getByText('Update'));

		expect(functions.length).toBe(2);

		expect(functions[0]).toBe(functions[1]);
	});
});
