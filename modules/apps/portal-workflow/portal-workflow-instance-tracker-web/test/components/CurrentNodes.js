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
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import CurrentNodes from '../../src/main/resources/META-INF/resources/js/components/CurrentNodes';

jest.mock('react-flow-renderer', () => ({
	useStore: () => ({
		getState: () => ({
			nodes: [
				{data: {label: 'Create'}, id: 'create'},
				{data: {label: 'Review'}, id: 'review'},
				{data: {label: 'Update'}, id: 'update'},
				{data: {label: 'Forward'}, id: 'forward'},
				{data: {label: 'Finish'}, id: 'finish'},
			],
		}),
	}),
	useZoomPanHelper: () => ({
		setCenter: jest.fn(),
	}),
}));

describe('The CurrentNodes component should', () => {
	let container;
	let getByText;

	beforeEach(() => {
		const result = render(
			<CurrentNodes
				nodesNames={['create', 'review', 'update', 'forward', 'finish']}
			/>
		);

		container = result.container;
		getByText = result.getByText;
	});

	it('Be rendered with nodes and showing "more..." option', () => {
		expect(container.querySelector('.current-node')).toBeTruthy();
		expect(
			container.querySelector('.current-node-link.more-link')
		).toBeTruthy();
	});

	it('Be rendered dropdown options', () => {
		const moreOption = container.querySelector(
			'.current-node-link.more-link'
		);
		const dropdownMenuShow = '.dropdown-menu.show';

		expect(getByText('Forward')).toBeTruthy();
		expect(getByText('Finish')).toBeTruthy();

		expect(document.querySelector(dropdownMenuShow)).toBeNull();

		fireEvent.click(moreOption);

		expect(document.querySelector(dropdownMenuShow)).toBeTruthy();
	});
});
