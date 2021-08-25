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

import CurrentStep from '../../src/main/resources/META-INF/resources/js/components/CurrentStep';

jest.mock('react-flow-renderer', () => ({
	useStore: () => ({
		getNodes: () => [],
	}),
	useZoomPanHelper: () => ({
		setCenter: jest.fn(),
	}),
}));

describe('The CurrentStep component should', () => {
	let container, getByText;

	beforeAll(() => {
		const result = render(
			<CurrentStep
				steps={['create', 'review', 'update', 'forward', 'finish']}
			/>
		);

		container = result.container;
		getByText = result.getByText;
	});

	it('Be rendered with steps and showing "more..." option', () => {
		expect(container.querySelector('.current-step')).toBeTruthy();
		expect(
			container.querySelector('.current-step-link.more-link')
		).toBeTruthy();
	});

	it('Be rendered dropdown options', () => {
		const moreOption = container.querySelector(
			'.current-step-link.more-link'
		);
		const dropdownMenuShow = '.dropdown-menu.show';

		expect(getByText('forward')).toBeTruthy();
		expect(getByText('finish')).toBeTruthy();

		expect(document.querySelector(dropdownMenuShow)).toBeNull();

		fireEvent.click(moreOption);

		expect(document.querySelector(dropdownMenuShow)).toBeTruthy();
	});
});
