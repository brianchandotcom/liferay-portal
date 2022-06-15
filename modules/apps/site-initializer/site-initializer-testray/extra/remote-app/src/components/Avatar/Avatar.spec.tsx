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

import {render} from '@testing-library/react';

import Avatar from './Avatar';

describe('Avatar', () => {
	it('renders with success', () => {
		const {asFragment} = render(<Avatar />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with name prop and test initials', () => {
		const {queryByText, rerender} = render(<Avatar name="Jane Doe" />);

		expect(queryByText('JD')).toBeTruthy();
		expect(queryByText('Jane Doe')).toBeFalsy();

		rerender(<Avatar displayName name="Liferay User" />);

		expect(queryByText('LU')).toBeTruthy();
		expect(queryByText('Liferay User')).toBeTruthy();
	});

	it('renders with name prop and test initials', () => {
		const {container} = render(
			<Avatar name="Jane Doe" url="https://liferay.com/jane-doe.png" />
		);

		expect(container.querySelector('img')?.alt).toBe('Jane Doe');
		expect(container.querySelector('img')?.src).toBe(
			'https://liferay.com/jane-doe.png'
		);
	});
});
