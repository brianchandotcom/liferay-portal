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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Loading from '../../../../src/main/resources/META-INF/resources/js/components/loading/Loading';

describe('Loading', () => {
	beforeEach(() => {
		cleanup();
	});

	it('renders with loading', () => {
		const {container, queryByText, rerender} = render(
			<Loading isLoading>
				<h1>Liferay</h1>
			</Loading>
		);

		expect(container.querySelector('.loading-animation')).toBeTruthy();
		expect(queryByText('Liferay')).toBeFalsy();

		rerender(
			<Loading isLoading={false}>
				<h1>Liferay</h1>
			</Loading>
		);

		expect(container.querySelector('.loading-animation')).toBeFalsy();
		expect(queryByText('Liferay')).toBeTruthy();
	});
});
