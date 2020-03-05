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

import {PopoverBase} from '../../../../src/main/resources/META-INF/resources/js/components/popover/PopoverBase.es';

describe('PopoverBase', () => {
	afterEach(() => {
		cleanup();
	});

	it('renders correct placement className', () => {
		const {container, queryByText} = render(
			<PopoverBase placement="bottom" visible>
				<PopoverBase.Header>
					<h1>Header</h1>
				</PopoverBase.Header>
				<PopoverBase.Body>
					<p>Body</p>
				</PopoverBase.Body>
				<PopoverBase.Footer>
					<span>Footer</span>
				</PopoverBase.Footer>
			</PopoverBase>
		);

		expect(container.querySelector('div.arrow')).not.toBeNull();
		expect(container.querySelector('.hide')).toBeNull();
		expect(container.querySelector('.clay-popover-bottom')).not.toBeNull();
		expect(queryByText('Header')).not.toBeNull();
		expect(queryByText('Body')).not.toBeNull();
		expect(queryByText('Footer')).not.toBeNull();
	});

	it('renders with placement as none', () => {
		const {container, queryByText} = render(
			<PopoverBase>
				<PopoverBase.Header>
					<h1>Header</h1>
				</PopoverBase.Header>
				<PopoverBase.Body>
					<p>Body</p>
				</PopoverBase.Body>
				<PopoverBase.Footer>
					<span>Footer</span>
				</PopoverBase.Footer>
			</PopoverBase>
		);

		expect(container.querySelector('div.arrow')).toBeNull();
		expect(container.querySelector('.hide')).not.toBeNull();
		expect(container.querySelector('.clay-popover-none')).not.toBeNull();
		expect(queryByText('Header')).not.toBeNull();
		expect(queryByText('Body')).not.toBeNull();
		expect(queryByText('Footer')).not.toBeNull();
	});
});
