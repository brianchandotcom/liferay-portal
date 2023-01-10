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
import {render, screen} from '@testing-library/react';
import React from 'react';

import Loading from '../Loading';

describe('Loading', () => {
	it('renders Loading component without crashing', async () => {
		const {container} = render(<Loading />);

		const loadingAnimationChild = container.querySelector(
			'span.loading-animation'
		);

		const loadingElement = screen.getByTestId('loading');

		expect(loadingElement).toBeInTheDocument();

		expect(loadingElement.contains(loadingAnimationChild)).toBe(true);
	});

	it('renders Loading component with "inline" className', () => {
		render(<Loading inline />);

		const loadingElement = screen.getByTestId('loading');

		expect(loadingElement).toHaveClass('inline-item inline-item-before');
	});

	it('rendes Loading component with "loading-absolute" className', () => {
		const {container} = render(<Loading absolute />);

		const loadingAnimationChild = container.querySelector(
			'span.loading-animation'
		);

		const loadingElement = screen.getByTestId('loading');

		expect(loadingElement).toBeInTheDocument();

		expect(loadingElement.contains(loadingAnimationChild)).toBe(true);

		expect(loadingAnimationChild).toHaveClass('loading-absolute');
	});

	it('renders Loading component with new style', () => {
		render(<Loading style={{backgroundColor: 'orange'}} />);

		const loadingElement = screen.getByTestId('loading');

		expect(loadingElement).toHaveAttribute(
			'style',
			'background-color: orange;'
		);
	});
});
