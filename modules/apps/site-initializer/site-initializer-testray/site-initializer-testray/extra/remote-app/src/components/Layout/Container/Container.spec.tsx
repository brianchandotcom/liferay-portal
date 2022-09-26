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

import {fireEvent, render} from '@testing-library/react';
import {act} from 'react-dom/test-utils';
import {vi} from 'vitest';

import Container from './Container';

describe('Container', () => {
	beforeAll(() => {
		vi.useFakeTimers();
	});

	it('renders with success', () => {
		const {asFragment, queryByText} = render(
			<Container>
				<div>Element</div>
			</Container>
		);

		expect(queryByText('Element')).toBeTruthy();
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with title', () => {
		const {container, queryByText} = render(
			<Container title="Testflow">
				<h1>Element</h1>
			</Container>
		);

		expect(container.querySelector('h5')).toHaveTextContent('Testflow');
		expect(queryByText('Element')).toBeTruthy();
	});

	it('renders with title and collapsible actions', async () => {
		const {container} = render(
			<Container collapsable title="Testflow">
				<h1>Element</h1>
			</Container>
		);

		const collapsableButton = container.querySelector('button');

		expect(container.querySelector('h5')).toHaveTextContent('Testflow');
		expect(container.querySelector('.show')).toBeTruthy();
		expect(
			container.querySelector("button[aria-expanded='true']")
		).toBeTruthy();

		await act(async () => {
			await fireEvent.click(collapsableButton as HTMLElement);
		});

		act(() => {
			vi.runAllTimers();
		});

		expect(container.querySelector('.show')).toBeFalsy();
		expect(
			container.querySelector("button[aria-expanded='false']")
		).toBeTruthy();
	});
});
