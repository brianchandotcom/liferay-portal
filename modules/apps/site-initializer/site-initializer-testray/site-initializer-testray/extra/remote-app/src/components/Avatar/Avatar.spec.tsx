/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
