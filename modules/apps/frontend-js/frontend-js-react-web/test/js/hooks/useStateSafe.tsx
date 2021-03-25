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
import {act} from 'react-dom/test-utils';

import useStateSafe from '../../../src/main/resources/META-INF/resources/js/hooks/useStateSafe';

const {useEffect, useState} = React;

describe('useStateSafe()', () => {
	let states: Array<[boolean, boolean]>;

	beforeEach(() => {
		jest.useFakeTimers();

		states = [];
	});

	afterEach(cleanup);

	const Child = () => {
		const [loading, setLoading] = useStateSafe(true);

		useEffect(() => {
			setTimeout(
				() =>
					setLoading((previous: boolean) => {
						states.push([previous, false]);

						return false;
					}),
				100
			);
		}, [setLoading]);

		return <div>Loading? {loading}</div>;
	};

	const Parent = () => {
		const [showChild, setShowChild] = useState(true);

		return (
			<>
				<button onClick={() => setShowChild((current) => !current)}>
					Toggle
				</button>
				{showChild && <Child />}
			</>
		);
	};

	it('sets state in a mounted component', () => {
		render(<Parent />);

		expect(states).toEqual([]);

		act(() => {
			jest.runAllTimers();
		});

		expect(states).toEqual([[true, false]]);
	});

	it('is a harmless no-op in an unmounted component', () => {
		const {getByText} = render(<Parent />);

		expect(states).toEqual([]);

		fireEvent.click(getByText('Toggle'));

		expect(states).toEqual([]);

		jest.runAllTimers();

		expect(states).toEqual([]);
	});
});
