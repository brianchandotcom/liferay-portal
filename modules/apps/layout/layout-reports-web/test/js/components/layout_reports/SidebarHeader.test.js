/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import SidebarHeader from '../../../../src/main/resources/META-INF/resources/js/components/SidebarHeader';

import '@testing-library/jest-dom/extend-expect';

import {StoreContextProvider} from '../../../../src/main/resources/META-INF/resources/js/context/StoreContext';
import loadIssues from '../../../../src/main/resources/META-INF/resources/js/utils/loadIssues';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/utils/loadIssues',
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
