/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import User from '../../js/components/project/User';

describe('User', () => {
	it('renders name', () => {
		const name = 'John Doe';
		const {getByText} = render(<User name={name} />);

		expect(getByText(name)).toBeInTheDocument();
	});

	it('renders image when provided', () => {
		const {container} = render(
			<User image="path/to/image.jpg" name="John Doe" />
		);

		const image = container.querySelector('img');

		expect(image?.getAttribute('src')).toBe('path/to/image.jpg');
		expect(image?.getAttribute('alt')).toBe('John Doe');
	});

	it('does not render image when not provided', () => {
		const name = 'John Doe';

		const {container} = render(<User name={name} />);

		const image = container.querySelector('img');

		expect(image).not.toBeInTheDocument();
	});
});
