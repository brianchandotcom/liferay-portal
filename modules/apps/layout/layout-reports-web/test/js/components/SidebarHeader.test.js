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
import userEvent from '@testing-library/user-event';
import React from 'react';

import SidebarHeader from '../../../src/main/resources/META-INF/resources/js/components/SidebarHeader';

import '@testing-library/jest-dom/extend-expect';

import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/StoreContext';
import loadIssues from '../../../src/main/resources/META-INF/resources/js/utils/loadIssues';

jest.mock(
	'../../../src/main/resources/META-INF/resources/js/utils/loadIssues',
	() => jest.fn(() => () => {})
);

const mockPageURLs = [
	{languageId: 'en-US', title: 'English', url: 'English URL'},
];

describe('SidebarHeader', () => {
	afterEach(cleanup);

	it('renders relaunch button', () => {
		const {getByTitle} = render(
			<StoreContextProvider
				value={{
					data: {
						validConnection: true,
					},
				}}
			>
				<SidebarHeader />
			</StoreContextProvider>
		);

		expect(getByTitle('relaunch')).toBeInTheDocument();
	});

	it('does not render relaunch button if key is not configured', () => {
		const {queryByTitle} = render(
			<StoreContextProvider
				value={{
					data: {
						validConnection: false,
					},
				}}
			>
				<SidebarHeader />
			</StoreContextProvider>
		);

		expect(queryByTitle('relaunch')).not.toBeInTheDocument();
	});

	it('calls loadIssues when clicking relaunch button', () => {
		const {getByTitle} = render(
			<StoreContextProvider
				value={{
					data: {
						pageURLs: mockPageURLs,
						validConnection: true,
					},
				}}
			>
				<SidebarHeader />
			</StoreContextProvider>
		);

		const button = getByTitle('relaunch');

		userEvent.click(button);

		expect(loadIssues).toBeCalled();
	});
});
