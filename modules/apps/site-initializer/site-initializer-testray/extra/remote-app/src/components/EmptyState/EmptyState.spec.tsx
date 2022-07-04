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

import EmptyState from '.';

describe('EmptyState', () => {
	it('renders with default properties', () => {
		const {asFragment, container, queryByText} = render(<EmptyState />);

		expect(queryByText('No results found')).toBeTruthy();
		expect(queryByText('Sorry, there are no results found')).toBeTruthy();
		expect(container.querySelector('img')?.src).toBe(
			'http://localhost:3000/states/empty_state.gif'
		);
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with custom properties', () => {
		const {container, queryByText} = render(
			<EmptyState
				description="No users found"
				title="No users"
				type="BLANK"
			/>
		);

		expect(queryByText('No users')).toBeTruthy();
		expect(queryByText('No users found')).toBeTruthy();
		expect(container.querySelector('img')?.src).toBeUndefined();
	});
});
