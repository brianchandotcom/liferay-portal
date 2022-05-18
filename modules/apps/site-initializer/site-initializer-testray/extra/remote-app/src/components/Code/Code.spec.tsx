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

import Code from './';

describe('Code', () => {
	it('renders code with children', () => {
		const {asFragment, container} = render(
			<Code>com.liferay.testray.user</Code>
		);

		expect(container.querySelector('code')).toHaveTextContent(
			'com.liferay.testray.user'
		);
		expect(asFragment()).toMatchSnapshot();
	});
});
