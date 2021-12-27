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
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {Report} from '../../../src/main/resources/META-INF/resources/admin/js/pages/Report';

let mockResource;

jest.mock('@clayui/data-provider', () => ({
	useResource: jest.fn().mockImplementation(() => ({
		resource: mockResource,
	})),
}));

describe('Report', () => {
	const originalLiferayUtil = window.Liferay.Util;

	beforeAll(() => {
		window.Liferay = {
			...window.Liferay,
			Util: {
				...originalLiferayUtil,
				sub: (...params) => params.join(' '),
			},
		};
	});

	afterAll(() => {
		window.Liferay.Loader = originalLiferayUtil;
	});

	afterEach(cleanup);

	it('renders title and subtitle for no entries', () => {
		mockResource = {totalItems: 0};

		const {container, getByText} = render(<Report />);

		/* TODO: figure out why getByText is not working for the subtitle */
		const subtitle = container.querySelector(
			'.lfr-ddm__form-report__subtitle'
		);

		expect(getByText('x-entries 0')).toBeInTheDocument();
		expect(subtitle).toHaveTextContent('there-are-no-entries');
	});

	it('renders title in singular if there is only one entry', () => {
		mockResource = {totalItems: 1};

		const {getByText} = render(<Report />);

		expect(getByText('x-entry 1')).toBeInTheDocument();
	});

	it('renders title in plural if there are more than one entry', () => {
		mockResource = {totalItems: 2};

		const {getByText} = render(<Report />);

		expect(getByText('x-entries 2')).toBeInTheDocument();
	});

	it('renders the last modified date on the subtitle for one or more entries', () => {
		const lastModifiedDate = 'The last entry was sent about a minute ago.';
		mockResource = {lastModifiedDate, totalItems: 1};

		const {getByText} = render(<Report />);

		expect(getByText(lastModifiedDate)).toBeInTheDocument();
	});
});
