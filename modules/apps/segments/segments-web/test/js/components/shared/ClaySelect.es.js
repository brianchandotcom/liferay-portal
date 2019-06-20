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

import ClaySelect from 'components/shared/ClaySelect.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

describe('ClaySelect', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {asFragment} = render(
			<ClaySelect onChange={jest.fn()} options={[]} />
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render with an item selected', () => {
		const {container} = render(
			<ClaySelect
				onChange={jest.fn()}
				options={[
					{
						label: 'aLabel',
						value: 'aValue'
					},
					{
						label: 'aLabel',
						value: 'bValue'
					}
				]}
				selected='bValue'
			/>
		);

		expect(container.firstChild.value).toEqual('bValue');
	});
});
